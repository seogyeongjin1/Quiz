package server;
//������ => Runnable,Thread��� 
import java.util.*; // ArrayList (������ ����) ==>  ip , Vector
import java.io.*;
import java.net.*;
public class Server1 implements Runnable{

   //  Ŭ���̾�Ʈ�� ���� ���� 
   ArrayList<Client> wait=new ArrayList<Client>();
   ServerSocket ss;// ��ȯ���� 
   // �������� 
   public Server1()
   {
      try
      {
         ss=new ServerSocket(7777);
         /*
          *   bind => ����  ===> ip,port
          *   listen => ��ٸ��� (������)
          */
         System.out.println("Server Start...");
      }catch(Exception ex) {}
   }
   // ������ ���� 
   public void run()
   {
      //  ������ ���� ���� 
      try
      {
         while(true)
         {
            Socket s=ss.accept();// ����
            // s==> Ŭ���̾�Ʈ�� ip, port
            System.out.println(s.getInetAddress().getHostAddress());
            System.out.println(s.getPort());
            
            Client client=new Client(s);
            wait.add(client);
            client.start();// ��Ž��� 
         }
      }catch(Exception ex){}
   }
   public static void main(String[] args) {
      // TODO Auto-generated method stub
        Server1 server=new Server1();
        new Thread(server).start();// run()ȣ�� 
   }
   
   // ���� Ŭ���� 
   class Client extends Thread
   {
      // Ŭ���̾�Ʈ�� ��� 
      Socket s;
      BufferedReader in;// Ŭ���̾�Ʈ ����
      OutputStream out;// Ŭ���̾�Ʈ �۽�
      public Client(Socket s)
      {
         try
         {
            this.s=s;
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            // byte ==> 2byte
            out=s.getOutputStream();
         }catch(Exception ex) {}
      }
      public void run()
      {
         // ��� 
         try
         {
            while(true)
            {
               String msg=in.readLine();
               System.out.println("Client=>"+msg);
               for(int i=0;i<wait.size();i++)
               {
                  Client c=wait.get(i);
                  c.out.write((msg+"\n").getBytes());
               }
            }
         }catch(Exception ex)
         {
            
         }
      }
   }
}


