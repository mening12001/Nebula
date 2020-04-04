import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MClassLoader extends ClassLoader{

	String classToBeRealoaded = "";
    public MClassLoader() {
        
    }

    
    public Class loadClasss(String name) throws ClassNotFoundException {
    	classToBeRealoaded = name;
    	return loadClass(name);
    }
    
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        if(!classToBeRealoaded.equals(name))
                return super.loadClass(name);

        try {
         
            String url = "file:" + Main1.class.getResource("Server.class").getPath().replace("Server.class", name + ".class").substring(1);
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(name,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}