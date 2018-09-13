package server;
import java.util.*;
import common.Function;

import java.net.*;
import java.io.*;


//         이게 현재 진짜 서버



/*
 *   1. 연결 기계 (핸드폰) ==> 구매 
 *      ======
 *       Socket : 다른 컴퓨터와 연결 
 *       
 *   2. 셋팅 ==> 개통 (전화번호,전화선)
 *             === ip , port 
 *             유심에 심는다
 *             =========
 *             bind(ip,port)
 *   3. 대기 (전화가 올때 까지 기다린다)
 *             listen()
 *   =========================== 개인용 (P2P)
 *   멀티 
 *   ====
 *    1) 교환소켓
 *    2) 통신소켓 ==> 접속자마다 생성 ==> Thread
 */
public class Server implements Runnable{
    // 서버 소켓 생성 
	private ServerSocket ss;
	private final int PORT=7777;
	// 클라이언트 정보를 저장 
	private ArrayList<Client> waitList=
			   new ArrayList<Client>();
	// 클라이언트의 IP,id....
	public Server()// 프로그램에서 시작과 동시 수행 : 생성자,main
	{
		// 서버 ==> 구동할때 한개 컴퓨터에서 두번을 실행 할 수 없다
		try
		{
			ss=new ServerSocket(PORT);
			// 생성자 ==> bind():개통 , listen(): 대기 
			System.out.println("Server Start...");
		}catch(Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	// 접속했을때 처리하는 기능 
	public void run()
	{
		try
		{
			// 클라이언트의 발신자 IP확인 ==> Socket
			while(true)
			{
			   // Socket s==> 접속한 클라이언트의 정보(IP,PORT)
			   Socket s=ss.accept();// 클라이언트가 접속시에만 호출 
			   Client client=new Client(s);
			   // 쓰레드와 클라이언트의 통신이 시작된다 
			   client.start();
			}
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server=new Server();
        new Thread(server).start();
	}
	
	// 통신 준비 ==> 내부 클래스 
    class Client extends Thread
    {
    	// 로그인시 전송하는 데이터 id,name
    	String id;
    	String name;
    	String pos;// 위치 
    	Socket s;// 연결
    	BufferedReader in;
    	OutputStream out;
    	public Client(Socket s)
    	{
    		try
    		{
    			this.s=s;
    			in=new BufferedReader(
    					new InputStreamReader(s.getInputStream()));
    			// 쓰레드 담당하는 클라이언트의 전송을 받을 변수
    			out=s.getOutputStream();
    			// 쓰레드 담당하는 클라이언트 메세지 전송
    		}catch(Exception ex){}
    	}
    	// 통신 
    	/*
    	     서버   =======> 
    	      데이터를 받아서 처리
    	      결과을 보내준다
    	     클라이언트 ====>
    	        요청을 위해 필요한 데이터 전송
    	        로그인 : ID,PWD
    	        결과값을 받아서 화면에 출력  
    	 */
    	public void run()
    	{
    		try
    		{
    			// 100|id|name
    			while(true)
    			{
    				String msg=in.readLine();
    				System.out.println("Client=>요청값:"+msg);
    				// 클라이언트에 전송한 메세지
    				// 처리 ==> 결과값 보내기
    				
    				// 100|id|name
    				// 번호 ==> 기능 (요청 번호)
    				StringTokenizer st=new StringTokenizer(msg, "|");
    				int protocal=Integer.parseInt(st.nextToken());
    				switch(protocal)
    				{
    				  case Function.LOGIN:// login.jsp
    				  {
    					 id=st.nextToken();
    					 pos="대기실";
    					 // 접속한 모든 사람에게 로그인을 알려준다(테이블에 출력)
    					 messageAll(Function.LOGIN+"|"+id+"|"+pos);
    					 // 본인은 추가하지 않는다 
    					 // 본인을 추가 
    					 waitList.add(this);
    					 // 1. 로그인 ==> 대기실로 변경 
    					 //messageTo(Function.MYLOG+"|"+id);
    					 // 2. 접속자 명단을 전송 
    					 for(Client client:waitList)
    					 {
    						 messageTo(Function.MYLOG+"|"
    								+client.id+"|"
    								+client.pos);
    					 }
    				  }
    				  break;
    				  // 채팅 요청처리
    				  case Function.WAITCHAT:
    				  {
    					  String id = st.nextToken();
    					  String chat=st.nextToken();
    					  messageAll(Function.WAITCHAT+"|["+id+"]| "+chat);
    				  }
    				  break;
    				}
    			}
    		}catch(Exception ex){}
    	}
    	/*
    	 *   서버 ==> 클라이언트 전송 메세지 
    	 */
    	//  전체 전송하는 메세지
    	public void messageAll(String msg)
    	{
    		try
    		{
    			for(Client client:waitList)
    			{
    				client.messageTo(msg);
    			}
    		}catch(Exception ex){}
    	}
    	//  개인적 전송하는 메세지 
    	public void messageTo(String msg)
    	{
    		try
    		{
    			out.write((msg+"\n").getBytes());
    		}catch(Exception ex){}
    	}
    	
    }
}







