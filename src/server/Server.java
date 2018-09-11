package server;
import java.util.*;// Vector(클라이언트 정보)
import java.net.*;// ServerSocket(서버=교환) , Socket(클라이언트와 통신)
import java.io.*;// OutputStream(byte),BufferedReader(char)
import common.*;// 기능 번호 
public class Server implements Runnable{
    // 저장공간 (클라이언트 정보 저장)
	Vector<Client> waitVc=new Vector<Client>();
	// 접속담당 (교환소켓)
	ServerSocket ss;
	private final int PORT=3355;
	// 서버가동
	public Server() 
	{
		try
		{
			ss=new ServerSocket(PORT);
			System.out.println("Server Start...");
		}catch(Exception ex) {}
	}
	// 접속대기 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				Socket s=ss.accept();// new Socket()
				Client client=new Client(s);
				client.start();// run()
			}
		}catch(Exception ex) {}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
	// 통신시작
	class Client extends Thread
	{
		Socket s;
		OutputStream out;
		BufferedReader in;
		
		String id,name,sex;
		
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
					// 100|id|name|sex
					String msg=in.readLine();//out.write()
					System.out.println(msg);
					StringTokenizer st=
							new StringTokenizer(msg, "|");
					int protocol=Integer.parseInt(st.nextToken());
					switch(protocol)
					{
					case Function.LOGIN:
					     {
					    	 // 로그인 => 입력받는다 
						     id=st.nextToken();
						     //name=st.nextToken();
						     //sex=st.nextToken();
						     
						     // 이미 접속된 사람들에게 전송 => 로그인하고 있는 사람 
						     messageAll(Function.LOGIN+"|"
						        +id/*+"|"+name+"|"+sex*/);
						     // 저장
						     waitVc.addElement(this);
						     // 상대방의 정보를 본인 받는다 
						     messageTo(Function.MYLOG+"|");
						     for(Client client:waitVc)
						     {
						    	 messageTo(Function.LOGIN+"|"
									     +client.id+"|"
						    			 /*+client.name
						    			 +"|"+client.sex*/); 
						     }
						     
					     }
						 break;
					case Function.CHAT:
					     {
					    	 String str=st.nextToken();
					    	 messageAll(Function.CHAT+"|["+name+"]"+str);
					     }
					     break;
					}
				}
			}catch(Exception ex){}
		}
		// 전체 전송 
		public void messageAll(String msg)
		{
			for(Client client:waitVc)
			{
				try
				{
					client.messageTo(msg);
				}catch(Exception ex){}
			}
		}
		// 개별 전송
		public void messageTo(String msg)
		{
			try
			{
				out.write((msg+"\n").getBytes());
			}catch(Exception ex){}
		}
	}

}

