package server;
import java.util.*;// Vector(클라이언트 정보)
import java.net.*;// ServerSocket(서버=교환) , Socket(클라이언트와 통신)
import java.io.*;// OutputStream(byte),BufferedReader(char)
import common.*;// 기능 번호 
public class Server implements Runnable{

   //  클라이언트의 정보 저장 
   ArrayList<Client> wait=new ArrayList<Client>();
   ServerSocket ss;// 교환소켓 
   // 서버가동 
   public Server()
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
        Server server=new Server();
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
         // 통신  ==> Function.LOGIN|id|name
         try
         {
            while(true)
            {
               String msg=in.readLine();
               StringTokenizer st=new StringTokenizer(msg, "|");
               int no=Integer.parseInt(st.nextToken());
               switch(no)
               {
                 case Function.LOGIN:
                 {
                  
                 }break;
                 case Function.MYLOG:
                 {
                  
                 }break;
                 case Function.MAKEROOM:
                 {
                  
                 }break;
                 case Function.ROOMIN:
                 {
                  
                 }break;
                 case Function.ROOMOUT:
                 {
                  
                 }break;
                 case Function.MYROOMOUT:
                 {
                  
                 }break;
                 case Function.WAITUPDATE:
                 {
                  
                 }break;
                 case Function.ROOMPOSITION:
                 {
                  
                 }break;
                 case Function.GAMEREADY:
                 {
                  
                 }break;
                 case Function.GAMESTART:
                 {
                  
                 }break;
                 case Function.GAMEYESNO:
                 {
                  
                 }break;
                 case Function.GAMESCORE:
                 {
                  
                 }break;
                 case Function.GAMERANK:
                 {
                  
                 }break;
                 case Function.GAMEEND:
                 {
                  
                 }break;
                 case Function.CHAT:
                 {
                  
                 }break;
                 case Function.GAMECHAT:
                 {
                  
                 }break;
                 case Function.LOGOUT:
                 {
                  
                 }break;        
               }
            }
         }catch(Exception ex)
         {
            
         }
      }
      // 한명
      public synchronized void messageTo(String msg)
      {
         try
         {
            out.write((msg+"\n").getBytes());
         }catch(Exception ex) {}
      }
      //  전체 메세지
      public synchronized void messageAll(String msg)
      {
         try
         {
            for(Client client:wait)
            {
               client.messageTo(msg);
            }
         }catch(Exception ex) {}
      }
   }
}













