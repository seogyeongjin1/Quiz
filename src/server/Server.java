package server;
import java.util.*;// Vector(클라이언트 정보)
import java.net.*;// ServerSocket(서버=교환) , Socket(클라이언트와 통신)
import java.io.*;// OutputStream(byte),BufferedReader(char)
import common.*;// 기능 번호 
import server.Server1.Client;
public class Server implements Runnable{
    // 저장공간 (클라이언트 정보 저장)
	 ArrayList<Client> wait=new ArrayList<Client>();
	// 접속담당 (교환소켓)
	ServerSocket ss;
	private final int PORT=7777;
	// 서버가동
	public Server() 
	{
		try
		{
			ss=new ServerSocket(PORT);// ip인식 
			// 전화기==> 유심(전화번호, 무선라인선) 
			System.out.println("Server Start...");
		}catch(Exception ex) {} //  서버 IP(전화번호),PORT(전화선) 
		// Socket s=new Socket(ip,port)
	}
	// 접속대기 
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		OutputStream out; // 결과값 보내주기
		BufferedReader in;// 클라이언트로부터 요청값
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
					    	 // 로그인 => 입력받는다 
						     id=st.nextToken();
						     location = "대기중";

					    	 //wait.add(this);
						     // 이미 접속된 사람들에게 전송 => 로그인하고 있는 사람 
						     messageAll(Function.LOGIN+"|"
						        +id+"|"+location);
						     System.out.println("메세지 보냈다");
						     // 저장
						      
						     // 상대방의 정보를 본인 받는다 
						     //messageTo(Function.MYLOG+"|");
						     //  대기실 갱신 
						     for(Client client:wait)
						     {
						    	 messageTo(Function.MYLOG +"|"
									     +client.id + "|"+client.location); 
						     }
					     }
						 break;
					case Function.CHAT:
					     {
					    	 id=st.nextToken();
					    	 chat=st.nextToken();
						     
						     // 이미 접속된 사람들에게 전송 => 로그인하고 있는 사람 
						     messageAll(Function.CHAT+"|"
						        +id+"|"+chat);
					     }
					     break;
					     
					}
				}
			}catch(Exception ex){}
		}
		// 전체 전송 
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

