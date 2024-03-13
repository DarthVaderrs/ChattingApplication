import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
     ServerSocket server;
     Socket socket;
      BufferedReader br;
      PrintWriter out;


    
        //Constructor..
    
       public Server()
      {
      try  {
             server=new ServerSocket(8080);
             System.out.println("server is ready to accept connection");
             System.out.println("waiting...");
             socket=server.accept();

             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out =new PrintWriter(socket.getOutputStream());

             startReading();
             startWriting();
           }
      catch (Exception e) {
       e.printStackTrace();
         }
         }

    
          public void startReading(){

          //multithtreading
          // thread- will read data 

           Runnable r1 =()-> 
           {
               
             System.out.println("reader started..");
             try{
             while(true){
              String msg = br.readLine();
              if(msg.equals("exit"))
       {
        System.out.println("Client terminated the chat");   
        socket.close();
        break;
       }

System.out.println("Client : "+msg);
  }

}catch(Exception e){
  e.printStackTrace();
}
  };
  new Thread(r1).start();

      }
public void startWriting()
    {
        //thread- will recieve data and sent to client
        Runnable r2 =()-> {
          System.out.println("writer started..");
          
          try {     
          while(true && !socket.isClosed() )
          {
              BufferedReader br1= new BufferedReader(new java.io.InputStreamReader(System.in));
              String Content =br1.readLine();

             out.println(Content);
             out.flush();

             if(Content.equals("exit")){
              socket.close();
              break;
             }
          }
         }
         catch (Exception e) {
          e.printStackTrace();
        }

         };

         
         new Thread(r2).start();


     }
          public static void main(String[] args)
         {
          System.out.println("this is server..going to start server");
         new Server();
         }
  
}
