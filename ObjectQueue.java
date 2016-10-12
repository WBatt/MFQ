import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

	// ObjectQueue.java

	public class ObjectQueue implements ObjectQueueInterface{
		private Object[] item;
		private int front;
		private int rear;
		private int count;

	    public ObjectQueue() {
	        item = new Object[1];
	        front = 0;
	        rear  = -1;
	        count = 0;
	    }
	    
	    @Override
	    public boolean isEmpty() {
	        return count == 0;
	    }
	    
	    @Override
	    public boolean isFull() {
	        return count == item.length;
	    }
	    
	    @Override
	    public void clear() {
	        item = new Object[1];
	        front = 0;
	        rear  = -1;
	        count = 0;
	    }
	    
	    @Override
	    public void insert(Object o) {
	        if (isFull())
	            resize(2 * item.length);
	        rear = (rear+1) % item.length;
	        item[rear] = o;
	        ++count;
	    }
	    
	    @Override
	    public Object remove() {
	        if (isEmpty()) {
	            System.out.println("Queue Underflow");
	            System.exit(1);
	        }
	        Object temp = item[front];
	        item[front] = null;
	        front = (front+1) % item.length;
	        --count;
	        if (item.length != 1 && count == item.length/4)
	            resize(item.length/2);
	        return temp;
	    }
	    
	    @Override
	    public Object query() {
	        if (isEmpty()) {
	            System.out.println("Queue Underflow (query)");
	            System.exit(1);
	        }
	        return item[front];
	    }
	    
	    private void resize(int size) {
	        Object[] temp = new Object[size];
	        for (int i = 0; i < count; ++i) {
	            temp[i] = item[front];
	            front = (front+1) % item.length;
	        }
	        front = 0;
	        rear = count-1;
	        item = temp;
	    }
	}
