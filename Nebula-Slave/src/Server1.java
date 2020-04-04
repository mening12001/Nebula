import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server1 {
	
	
	Socket socket;
	
	public Server1(Socket socket) {
		this.socket = socket;
	}
	
	public void start() throws ClassNotFoundException, IOException {
		
	
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

            ((MClassLoader)Server1.class.getClassLoader()).loadClasss(className.replace(".class", ""));
            Computation c = Computation.class.cast(objectInputStream.readObject());
            Object result = c.compute();
            
            System.out.println("Returning result");
            out.writeObject(result);
             
            socket.close();
		
	}

}
