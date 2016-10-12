import java.io.PrintWriter;


public class Job{

	
	
	private int ArrivalTime, PID, jClock, CPU_Time_Required, currentQueue;
	public Job(int Arrival, int name, int CPU){
		ArrivalTime = Arrival;
		PID = name;
		CPU_Time_Required = CPU;
	}
	
	
	public int jobClock(){
		return CPU_Time_Required;
	}
	
	public int timeRequired(){
		return CPU_Time_Required;
	}
	
	public void setTimeRemaining(){
		jClock = CPU_Time_Required;
	}
	
	public void decrementJobTime(){
		--CPU_Time_Required;
	}

	
	public int ArrivalTime(){
		return ArrivalTime;
	}
	
	public int PID(){
		return PID;
	}
	
	public void setQueue(int q){
		currentQueue = q;
	}
	
	public int Queue(){
		return currentQueue;
	}
	
	public void display(){
		System.out.println("Arrival: " + PID);
	}
	
	
}
