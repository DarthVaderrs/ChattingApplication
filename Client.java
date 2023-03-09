import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;


public class Client extends JFrame {

    Socket socket;

   BufferedReader br;
   PrintWriter out;

   //components declare
   private JLabel heading=new JLabel("Client Area");
   private JTextArea messagArea=new JTextArea();
   private JTextField messageInput=new JTextField();
   private Font font=new Font("Roboto",Font.PLAIN,20);
   




   //Constructor
    public Client()
    {
        try {
           // System.out.println("Sending request to server");
           // socket=new Socket("127.0.0.1",8080);
           // System.out.println("connection done.");


           // br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           // out =new PrintWriter(socket.getOutputStream());


           createGUI();
           // startReading();
           // startWriting();



        } catch (Exception e) {
            //todo:to hANDLE exception
        }

    }



    private void createGUI()
    {



      //gui code


      this.setTitle("Client Messanger[END]");
      this.setSize(600,600);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




      //coding for component
      heading.setFont(font);
      messagArea.setFont(font);
      messageInput.setFont(font);

      heading.setHorizontalAlignment(SwingConstants.CENTER);
      heading.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
      



      //layout of frame
      this.setLayout(new BorderLayout());

      //add component to frame
      this.add(heading,BorderLayout.NORTH);
      this.add(messageInput,BorderLayout.SOUTH);
      this.add(messagArea,BorderLayout.CENTER);

      







      this.setVisible(true);

    }



    //Method(start reading)
    public void startReading(){

        //multithtreading
        // thread- will read data 

         Runnable r1 =()-> 
         {

            

           System.out.println("reader started..");
           
           try{

           while(true){

             String msg = br.readLine();

            if(msg.equals("exit")) {
      System.out.println("Server terminated the chat");   
      socket.close();
      break;
     }

      System.out.println("Server : "+msg);
    
    }
    }

    catch(Exception e){
        e.printStackTrace();
        }
      
};


new Thread(r1).start();

    }






    //method(start writing)
    public void startWriting()
    {
        //thread- will recieve data and sent to client


        Runnable r2 =()-> {
          System.out.println("writer started..");
         
         try{
          while(true)
          {
            


              BufferedReader br1= new BufferedReader(new java.io.InputStreamReader(System.in));
              String content=br1.readLine();
              out.println(content);
              out.flush();

              if(content.equals("exit"))
              {
                socket.close();
                break;
              }

           
          }
        } catch (Exception e) {

          //todo:handle exception
          e.printStackTrace();
        }

         };

         
         new Thread(r2).start();


     }

     


    public static void main(String[] args) {


      System.out.println("this is client");
      new Client();
     }
}