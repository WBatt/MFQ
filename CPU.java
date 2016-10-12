
public class CPU {
	
	private int qClock;
	private boolean busy=false;
	private Job job;

	public int jobArrival(){
		return job.ArrivalTime();
	}
	
	public void getJob(Object newJob){
		this.job = (Job) newJob;	
	}
	
	public void setQClock(int quantum){
		qClock = quantum;
	}
	
	public int QClock(){
		return qClock;
	}
	
	public void incrementQLevel(int q){
		job.setQueue(q);
	}
	
	public void decrementQClock(){
		qClock--;
	}
	
	public void decrementJClock(){
		job.decrementJobTime();
	}
	
	public int getJobTime(){
		return job.jobClock();		
	}
	
	public void setJobTime(){
		job.setTimeRemaining();
	}
	
	public int getJobQueue(){
		return job.Queue();
	}
	public int outPrint(){
		return job.PID();
	}
	
	public void setBusy(){
		busy = true;
	}
	
	public void setFree(){
		busy = false;
	}
	
	public boolean isBusy(){
		return busy;
	}
	
	public Job returnJob(){
		return job;
	}
	
	public int returnPID(){
		return job.PID();
	}
	
	public void resetJob(){
		job = null;
		busy = false;
	}
}
