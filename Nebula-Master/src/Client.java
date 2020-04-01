import java.net.*; 
import java.io.*; 
  
public class Client { 
	
	private String address;
	private int port;
     
    public Client(String address, int port) 
    { 
       this.address = address;
       this.port = port;
    } 
  
    public Object sendForExecution(Computation computation) throws UnknownHostException, IOException, ClassNotFoundException {
    	Socket socket = new Socket(address, port);
        System.out.println("Connected!");

        byte[] bytes = new byte[16 * 1024];
        Computation  actualComputation =  computation;
    //   Computation actualComputation = new ActualComputation(); 
        InputStream in;
        String className;
        
        if(actualComputation.getClass().getName().contains("Lambda")) {
        	String fullClassName = (actualComputation.getClass().getName());
        	String outerClassName = fullClassName.substring(0, fullClassName.indexOf("$"));
        	className = outerClassName;
        	in = actualComputation.getClass().getClassLoader().getResourceAsStream(outerClassName + ".class");
        }
       	else {    		
        	in = actualComputation.getClass().getClassLoader().getResourceAsStream(actualComputation.getClass().getName() + ".class");
        	className = actualComputation.getClass().getName() ;
       	}
        //Computation actualComputation = (Computation & Serializable) () -> System.out.println("Asdasdasd");
        OutputStream out = socket.getOutputStream();
        InputStream inStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inStream);
       
        System.out.println("Sending Class name to the Server...");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(className + ".class");
       // objectOutputStream.writeObject("asdad" + ".class");

        System.out.println("Class name sent");

       //Waiting notification class name received 
        objectInputStream.readObject();
                
        int count; 
        byte[] destBytes = new byte[16 * 1024];
        
        System.out.println("Sending Class file to the Server...");
        
        //Reading bytes of the class file
        while ((count = in.read(bytes)) > 0) {
            
        	System.arraycopy(bytes, 0, destBytes, 4, count);
        	//Adding before the bytes the actual length(number of the bytes)
        	destBytes[3] = (byte) (count&127);
        	destBytes[2] = (byte) ((count>>4)&127);
        	destBytes[1] = (byte) ((count>>8)&127);
        	destBytes[0] = (byte) ((count>>16)&127);
        	out.write(destBytes, 0, count + 4);

        }
        System.out.println("Class file sent");
      
        //Waiting to be notified that the class file is received
        System.out.println("Class file received: " + objectInputStream.readObject()); 
       // ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
      
        //Sending Computation instance
        System.out.println("Sending Computation object to the Server...");
        objectOutputStream.writeObject(actualComputation);
        
        System.out.println("Waiting for the result from the Server...");
        Object result = objectInputStream.readObject();

        System.out.println("Closing socket and terminating program.");
        socket.close();
        
        return result;
    }
    
} 