import java.net.*; 
import java.io.*;
import java.lang.reflect.InvocationTargetException; 
  
public class Server 
{ 
	ServerSocket serverSocket;
    
    // constructor with port 
    public Server(int port) throws IOException, ClassNotFoundException 
    { 
    	serverSocket = new ServerSocket(port);
    	
        
    }
        
    public void start() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    	
    	System.out.println("ServerSocket awaiting connections...");
    	
    	while(true) {
            Socket socket = serverSocket.accept(); 
            System.out.println("Connection from " + socket + "!");

            InputStream socketIn = socket.getInputStream();
            DataInputStream in = new DataInputStream(new BufferedInputStream(socketIn));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            
            System.out.println("Waiting for Class name...");

            ////////////////////  
            
            ObjectInputStream objectInputStream = new ObjectInputStream(socketIn);
            String className = (String) objectInputStream.readObject();
            out.writeObject("Class name received");
            
     
            System.out.println("Waiting for Class file...");
            
            byte[] bytes = new byte[16 * 1024];
     
    		FileOutputStream fos = new FileOutputStream(Main1.class.getResource("Server.class").getPath().replace("Server.class", className));
            
            //Read length of Class file from the stream of bytes
        	in.read(bytes, 0, 4);
        	int length = 0;
        	length = bytes[3];
        	length |= (bytes[2]<<4);
        	length |= (bytes[1]<<8);
        	length |= (bytes[0]<<16);
    		
        	//Read the Class file bytes
    		in.read(bytes, 4, length);
    		
    		//Write the Class file
    		fos.write(bytes, 4, length);            
    		System.out.println("Class file received");          
 
    		//Notifie that the Class file is received
            out.writeObject("File received");
         
            System.out.println("Class loaded");
            Computation c = Computation.class.cast(objectInputStream.readObject()); 
            Object result = c.compute();
            System.out.println("Returning result");
            out.writeObject(result);
             
            socket.close();
        }	
    }
    
} 