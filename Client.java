import java.net.*;
import java.io.*;
public class Client {
Socket socket;
BufferedReader br;
PrintWriter out;
public Client()
{
try {
System.out.println("Sending Request to Server");
socket=new Socket("127.0.0.1",7777);
System.out.println("Connection done.");
br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
out=new PrintWriter(socket.getOutputStream());
startReading();
startWriting();

} catch (Exception e) {
//TODO: handle exception
}
}

public void startReading()
{
//thread-read kore kore data dite thakbe
Runnable r1=()-> {
System.out.println("Reader Started..");
try{
while(true)
{
  String msg=br.readLine();
if(msg.equals("exit"))
{
System.out.println("Server terminated the chat");
socket.close();
break;
}
System.out.println("Server: "+msg);

}
} catch (Exception e) {
//e.printStackTrace();
System.out.println("Connection is closed");
}
};
new Thread(r1).start();
}
public void startWriting()
{
//thread-data user er theke nibe and clint porjonto send korte thkbe
Runnable r2=()-> {
System.out.println("Writer started...");
try{
while(!socket.isClosed())
{
BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
String content=br1.readLine();
out.println(content);
out.flush();
if (content.equals("exit")){
socket.close();
break;
}
}
System.out.println("Connection is closed");
  } catch (Exception e) {
e.printStackTrace();
}
};
new Thread(r2).start();

}
public static void main (String[] args){
System.out.println("This is Client...");
new Client();
}
}
