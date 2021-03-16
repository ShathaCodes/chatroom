import java.io.*;
import java.net.*;
import java.util.Vector;

public class ChatServer {
	
	private Vector<ServerThread> v = new Vector<ServerThread>();
	
	public void start(){
		 try {
            ServerSocket server = new ServerSocket(9876);
            System.out.println("Server is waiting for connetions...");
            while (true){
                Socket socket = server.accept();
                ServerThread s= new ServerThread(socket);
				v.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public synchronized void broadcast(String message , String name){
		for(int i=0 ; i< v.size() ; i++){
			ServerThread st = v.get(i);
			if ( st.username != name){
			try {
			st.writeMessage(name+ " : " + message);
			}
			catch (IOException e) { e.printStackTrace();}
			}
		}
	}

    public static void main(String[] args){
		ChatServer cs = new ChatServer();
		cs.start();
       
    }
    class ServerThread extends Thread {
        Socket socket;
        ObjectInputStream ois;
        ObjectOutputStream oos ;
		String username ;
        ServerThread(Socket socket) {
        try{
			this.socket = socket;
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
			username = (String) ois.readObject();
                        String msg = "Bienvenue " + username + " vous etes bien connectes";
                        if (! v.isEmpty()){
			msg+= " : ";
			for(int i=0 ; i< v.size() ; i++){
			ServerThread st = v.get(i);
			msg += st.username + " ";
			}
			msg += " sont aussi connectes";
                        }
			writeMessage(msg );	
            System.out.println("username " + username + " has joined the chatroom");			
            this.start();
			}
        catch(IOException e){e.printStackTrace();}
		catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
    }
	public void writeMessage(String msg) throws IOException{
		oos.writeObject(msg);
	}
    public void run(){
		while(true){
        try {      
                String message = (String) ois.readObject(); 
				System.out.println("Message Received: " + message); 
                if(message.equalsIgnoreCase("quit")) {
					v.remove(this);
					break;
				}	
        broadcast(message, username);					
        } catch (IOException e) {
            e.printStackTrace();
        }
		catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
		}
          try{ socket.close();ois.close();oos.close();}
          catch (IOException e) {
            e.printStackTrace();
        }
    }
	}

}

