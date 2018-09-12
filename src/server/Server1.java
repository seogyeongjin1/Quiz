package server;
//쓰레드 => Runnable,Thread상속 
import java.util.*; // ArrayList (접속자 저장) ==>  ip , Vector
import java.io.*;
import java.net.*;
public class Server1 implements Runnable{

   //  클라이언트의 정보 저장 
   ArrayList<Client> wait=new ArrayList<Client>();
   ServerSocket ss;// 교환소켓 
   // 서버가동 
   public Server1()
   {
      try
      {
         ss=new ServerSocket(7777);
         /*
          *   bind => 개통  ===> ip,port
          *   listen => 기다린다 (대기상태)
          */
         System.out.println("Server Start...");
      }catch(Exception ex) {}
   }
   // 쓰레드 동작 
   public void run()
   {
      //  접속자 정보 저장 
      try
      {
         while(true)
         {
            Socket s=ss.accept();// 접속
            // s==> 클라이언트의 ip, port
            System.out.println(s.getInetAddress().getHostAddress());
            System.out.println(s.getPort());
            
            Client client=new Client(s);
            wait.add(client);
            client.start();// 통신시작 
         }
      }catch(Exception ex){}
   }
   public static void main(String[] args) {
      // TODO Auto-generated method stub
        Server1 server=new Server1();
        new Thread(server).start();// run()호출 
   }
   
   // 내부 클래스 
   class Client extends Thread
   {
      // 클라이언트의 통신 
      Socket s;
      BufferedReader in;// 클라이언트 수신
      OutputStream out;// 클라이언트 송신
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
         // 통신 
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


