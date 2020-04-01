import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Main1 {

	public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub

    	Server serv = new Server(12345);
    	serv.start();
	
	}

}
