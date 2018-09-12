package server;
import java.util.*;// Vector(Ŭ���̾�Ʈ ����)
import java.net.*;// ServerSocket(����=��ȯ) , Socket(Ŭ���̾�Ʈ�� ���)
import java.io.*;// OutputStream(byte),BufferedReader(char)
import common.*;// ��� ��ȣ 
import server.Server1.Client;
public class Server implements Runnable{

    // ������� (Ŭ���̾�Ʈ ���� ����)
	 ArrayList<Client> wait=new ArrayList<Client>();
	// ���Ӵ�� (��ȯ����)
	ServerSocket ss;
	private final int PORT=7777;
	// ��������
	public Server() 
	{
		try
		{
			ss=new ServerSocket(PORT);// ip�ν� 
			// ��ȭ��==> ����(��ȭ��ȣ, �������μ�) 
			System.out.println("Server Start...");
		}catch(Exception ex) {} //  ���� IP(��ȭ��ȣ),PORT(��ȭ��) 
		// Socket s=new Socket(ip,port)
	}
	// ���Ӵ��  
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		}catch(Exception ex) {}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
	// ��Ž���
	class Client extends Thread
	{
		Socket s;
		OutputStream out; // ����� �����ֱ�
		BufferedReader in;// Ŭ���̾�Ʈ�κ��� ��û��
		String id,name,chat,location;
		
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				out=s.getOutputStream();
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			}catch(Exception ex) {}
		}
		
		public void run()
		{
			try
			{
				while(true)
				{
					
					String msg=in.readLine();//out.write()
					System.out.println(msg);
					StringTokenizer st=
							new StringTokenizer(msg, "|");
					int protocol=Integer.parseInt(st.nextToken());
					System.out.println(protocol);
					
					switch(protocol)
					{
					case Function.LOGIN:
					     {
					    	 // �α��� => �Է¹޴´� 
						     id=st.nextToken();
						     location = "�����";

					    	 //wait.add(this);
						     // �̹� ���ӵ� ����鿡�� ���� => �α����ϰ� �ִ� ��� 
						     messageAll(Function.LOGIN+"|"
						        +id+"|"+location);
						     System.out.println("�޼��� ���´�");
						     // ����
						      
						     // ������ ������ ���� �޴´� 
						     //messageTo(Function.MYLOG+"|");
						     //  ���� ���� 
						     for(Client client:wait)
						     {
						    	 messageTo(Function.MYLOG +"|"
									     +client.id + "|"+client.location); 
						     }
					     }
						 break;
					case Function.MAKEROOM:
						
						break;
					case Function.ROOMIN:
						
						break;
					case Function.ROOMOUT:
						
						break;
					case Function.MYROOMOUT:
	
						break;
					case Function.WAITUPDATE:
	
						break;
					case Function.ROOMPOSITION:
	
						break;
					case Function.GAMEREADY:
	
						break;
					case Function.GAMESTART:
	
						break;
					case Function.GAMEYESNO:
	
						break;
					case Function.GAMESCORE:
	
						break;
					case Function.GAMERANK:
	
						break;
					case Function.GAMEEND:
	
						break;
					case Function.CHAT:
					     {
					    	 id=st.nextToken();
					    	 chat=st.nextToken();
						     
						     // �̹� ���ӵ� ����鿡�� ���� => �α����ϰ� �ִ� ��� 
						     messageAll(Function.CHAT+"|"
						        +id+"|"+chat);
					     }
					     break;
					case Function.GAMECHAT:
						
						break;
					case Function.LOGOUT:
	
						break;
					
					     
					}
				}
			}catch(Exception ex){}
		}
		// ��ü ���� 
		public void messageAll(String msg)
		{
			for(Client client:wait)
			{
				try
				{
					client.messageTo(msg);
				}catch(Exception ex){}
			}
		}
		// ���� ����
		public void messageTo(String msg)
		{
			try
			{
				out.write((msg+"\n").getBytes());
			}catch(Exception ex){}
		}
	}

   //  Ŭ���̾�Ʈ�� ���� ���� 
   ArrayList<Client> wait=new ArrayList<Client>();
   ServerSocket ss;// ��ȯ���� 
   // �������� 
   public Server()
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
        Server server=new Server();
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
         // ���  ==> Function.LOGIN|id|name
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
      // �Ѹ�
      public synchronized void messageTo(String msg)
      {
         try
         {
            out.write((msg+"\n").getBytes());
         }catch(Exception ex) {}
      }
      //  ��ü �޼���
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













