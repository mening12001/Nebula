import java.io.Serializable;

public class ActualComputation implements Computation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Override
	public Object compute() {
		// TODO Auto-generated method stub
		System.out.println("This is the sent ActualComputaion Object");
		
		int result = 12 + 15;
		
		return result;
	}

	
}
