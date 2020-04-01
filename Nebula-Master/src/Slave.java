import java.io.IOException;

public class Slave {

	private Client client;
	
	public Slave(String address, int port) {
		client = new Client(address, port);
	}
	
	
	public Object compute(Computation computation) {
		Object result = null;
		try {
			result = client.sendForExecution(computation);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
