import java.io.IOException;

public class Main{

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
       
		//For simplicity, it is considered that the server/slave is running locally
		Slave slave = new Slave("localhost", 12345);
		
		
		//First example: Using an Object that implements the Computation interface
		
		int result1 = (int) slave.compute(new ActualComputation());
		
		System.out.println("ActualComputation result: " + result1);
		
		
		//Second example: Elegantly, executing a lambda on the remote server, a remote lambda if you may
		
		int result2 = (int) slave.compute(() -> {
			System.out.println("This is a Remote Lambda "); 
			int result = 25 + 26;
			return result;}
		);
		
		System.out.println("Remote lambda result: " + result2);
	}

}
