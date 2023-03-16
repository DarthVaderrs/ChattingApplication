import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",8081);
            System.out.println("connection done.");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out =new PrintWriter(socket.getOutputStream());


           createGUI();
           handleEvents();

            startReading();
           // startWriting();



        } catch (Exception e) {
            //todo:to hANDLE exception
        }

    }

      
      private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

          

          @Override
          public void keyTyped(KeyEvent e) {
            // tODO Auto-generated method stub



            
          }

          @Override
          public void keyPressed(KeyEvent e) {
            //tODO Auto-generated method stub



            
          }

          @Override
          public void keyReleased(KeyEvent e) {


            // tODO Auto-generated method stub

             // System.out.println("key released"+e.getKeyCode());
              if(e.getKeyCode()==10) {
               // System.out.println("You have pressed ENTER button");
                String contentToSend=messageInput.getText();
                messagArea.append("Me :"+contentToSend+"\n");
                out.println(contentToSend);
                out.flush();
                messageInput.setText("");
                messageInput.requestFocus();



              }

            
          }
          


        });



      }



    private void createGUI()
    {



      //gui code


      this.setTitle("Client Messanger[END]");
      this.setSize(600,700);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




      //coding for component
      heading.setFont(font);
      messagArea.setFont(font);
      messageInput.setFont(font);

      heading.setIcon(new ImageIcon("appLogo.jpg"));

      heading.setHorizontalTextPosition(SwingConstants.CENTER);
      heading.setVerticalTextPosition(SwingConstants.BOTTOM);
      heading.setHorizontalAlignment(SwingConstants.CENTER);
      heading.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
      messagArea.setEditable(false);
      


      //textfieldWriring
      messageInput.setHorizontalAlignment(SwingConstants.RIGHT);


      //layout of frame
      this.setLayout(new BorderLayout());

      //add component to frame
      this.add(heading,BorderLayout.NORTH);
      JScrollPane jScrollPane=new JScrollPane(messagArea);
      this.add(messageInput,BorderLayout.SOUTH);
      this.add(jScrollPane, BorderLayout.CENTER);
      
      







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
      JOptionPane.showMessageDialog(this,"Server terminated the chat");
      messageInput.setEnabled(false);
      socket.close();
      break;
     }

      //System.out.println("Server : "+msg);
      messagArea.append("Server : " +msg+"\n ");     

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