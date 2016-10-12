import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Scanner;


public class MFQ {

	private PrintWriter pw;
	ObjectQueue Q1 = new ObjectQueue();
	ObjectQueue Q2 = new ObjectQueue();
	ObjectQueue Q3 = new ObjectQueue();
	ObjectQueue Q4 = new ObjectQueue();
	ObjectQueue InputQueue = new ObjectQueue();
	CPU cpu = new CPU();
	private int SysClock=0;
	
	
	
	
	public MFQ(PrintWriter pw){
		this.pw = pw;
	}
	
	public void getJobs() throws FileNotFoundException{
		String delims = "[ ]+";
		Scanner fileScan = new Scanner(new File("mfq.txt"));
		while(fileScan.hasNext()){
			String str = fileScan.nextLine();
			String[] tokens = str.split(delims);
			int first = Integer.parseInt(tokens[0]), 
				second = Integer.parseInt(tokens[1]), 
				third = Integer.parseInt(tokens[2]);
			Job tempJob = new Job(first, second, third);
			InputQueue.insert(tempJob);
		}
	}
	
	public void outputHeader(){
		Formatter fmt = new Formatter();
	    pw.println(fmt.format("%5s %20s %10s %s %10s %11s", "", "", "", "CPU", "Total", "Lowest"));
	    fmt = new Formatter();
	    pw.println(fmt.format("%5s %18s %10s %6s %10s %9s", "", "System", "", "Time", "Time in", "Level"));
	    fmt = new Formatter();
	    pw.println(fmt.format("%5s %17s %6s %12s %9s %9s", "Event", "Time", "PID", "Needed", "System", "Queue"));
	    pw.println();
	}
	
	public void runSimulation(){
		while(!InputQueue.isEmpty() || !Q1.isEmpty() || !Q2.isEmpty() || !Q3.isEmpty() || !Q4.isEmpty() || cpu.isBusy()==true){
			SysClock++;
			Job tempJob = new Job(-1,-1,-1);
			if(!InputQueue.isEmpty()){
				tempJob = (Job) InputQueue.query();
				if(tempJob.ArrivalTime()==SysClock){
					Formatter fmt = new Formatter();
			        pw.println(fmt.format("%5s %14s %7s %9s %23s %25s", "Arrival", SysClock, tempJob.PID(), tempJob.timeRequired(), "", ""));
					tempJob.setQueue(1);
					InputQueue.remove();
					Q1.insert(tempJob);
				}
			}
			if(cpu.isBusy()==false){
				whichQueue();
			}else{
				cpu.decrementQClock();
				cpu.decrementJClock();
				if(cpu.getJobTime()==0){
					Formatter fmt = new Formatter();
			        pw.println(fmt.format("%5s %12s %7s %14s %4s %10s", "Departure", SysClock, cpu.outPrint(), "", SysClock-cpu.jobArrival(), cpu.getJobQueue()));
					cpu.resetJob();
					whichQueue();
				}else if (cpu.QClock() == 0 || tempJob.ArrivalTime() == SysClock){
					CPUtoQueue();
					whichQueue();
				}
			}				
		}
	}
	
	
	
	public void outStats(){
	}
	
	
	
	public void whichQueue(){
		if(!Q4.isEmpty() && Q3.isEmpty() && Q2.isEmpty() && Q1.isEmpty()){
			if(cpu.returnJob()!=null)
				CPUtoQueue();
			cpu.getJob(Q4.remove());
			cpu.setQClock(16);
			cpu.setJobTime();
			cpu.setBusy();
		}if(!Q3.isEmpty() && Q2.isEmpty() && Q1.isEmpty()){
			if(cpu.returnJob()!=null)
				CPUtoQueue();
			cpu.getJob(Q3.remove());
			cpu.setQClock(8);
			cpu.setJobTime();
			cpu.setBusy();
		}if(!Q2.isEmpty() && Q1.isEmpty()){
			if(cpu.returnJob()!=null)
				CPUtoQueue();
			cpu.getJob(Q2.remove());
			cpu.setQClock(4);
			cpu.setJobTime();
			cpu.setBusy();
		}if(!Q1.isEmpty()){
			if(cpu.returnJob()!=null)
				CPUtoQueue();
			cpu.getJob(Q1.remove());
			cpu.setQClock(2);
			cpu.setJobTime();
			cpu.setBusy();
		}
	}
	
	public void CPUtoQueue(){
		if(cpu.getJobQueue() == 1){
			cpu.incrementQLevel(2);
			Q2.insert(cpu.returnJob());
		}else if(cpu.getJobQueue() == 2){
			cpu.incrementQLevel(3);
			Q3.insert(cpu.returnJob());
		}else if(cpu.getJobQueue() == 3){
			cpu.incrementQLevel(4);
			Q4.insert(cpu.returnJob());
		}else if(cpu.getJobQueue() == 4){
			Q4.insert(cpu.returnJob());
		}
		cpu.resetJob();
	}
}
