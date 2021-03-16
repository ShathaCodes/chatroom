import java.net.*;
import java.io.*;
import java.util.*;
 
public class ChatClient {
	ObjectOutputStream  oos;
	ObjectInputStream ois ;
	String name="Aloga";
	boolean continueLooping=true;

	public void start() throws UnknownHostException, IOException, ClassNotFoundException{
		InetAddress host = InetAddress.getLocalHost();
        Socket socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
		Scanner in = new Scanner(System.in);  
		
		System.out.print(name + "> id= ");  
        name = in.nextLine();  
		oos.writeObject(name); 
		new ListenFromServer().start();
        while(true){ 
		    System.out.print(name + "> ");
			String message = in.nextLine(); 			
			oos.writeObject(message);
			if(message.equals("quit")) break;
        }
		continueLooping=false;
		in.close();
		ois.close();
        oos.close();
		socket.close();
	}
 public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException{
        ChatClient cc = new ChatClient();
		cc.start();
    }
	class ListenFromServer extends Thread {
		public void run() {
			while(continueLooping) {
				try {
					String msg = (String) ois.readObject();
					System.out.println(msg);
					System.out.print(name + "> ");
				}
				catch(IOException e) {}
				catch(ClassNotFoundException e2) {}
			}
		}
	}
}