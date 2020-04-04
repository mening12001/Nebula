import java.net.*; 
import java.io.*;
import java.lang.reflect.InvocationTargetException; 
  
public class Server 
{ 
	ServerSocket serverSocket;
    
    // constructor with port 
    public Server(Integer port) throws IOException, ClassNotFoundException 
    { 
    	serverSocket = new ServerSocket(port);    
    }
        
    public void start() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    	
    	System.out.println("ServerSocket awaiting connections...");
    	
    	while(true) {	 
    	 	Socket socket = serverSocket.accept();
            MClassLoader classLoader = new MClassLoader();
            Class<?> serverClass = classLoader.loadClasss("Server1");
    		serverClass.getMethod("start").invoke(serverClass.getConstructor(Socket.class).newInstance(socket));
    	}
        	
    }
    
} 