package server;
import java.util.*;
import server.Room;
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
   Vector<Client> waitVc=
            new Vector<Client>();
   Vector<Room> roomVc=new Vector<Room>();
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
    					 waitVc.addElement(this);
    					 // 1. 로그인 ==> 대기실로 변경 
    					 //messageTo(Function.MYLOG+"|"+id);
    					 // 2. 접속자 명단을 전송 
    					 for(Client client:waitVc)
    					 {
    						 messageTo(Function.MYLOG+"|"
    								+client.id+"|"
    								+client.pos);
    					 }
    					 
    					 //방정보 전송
						 for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								messageTo(Function.MAKEROOM+"|"
								           +room.roomName+"|"
								           +room.roomPosition+"|"
								           +room.current+"/"+room.maxcount);
							}
    				  }
    				  break;
    				  // 방만들기
    				  case Function.MAKEROOM:
						{
							// 데이터 받기
							Room room=new Room(
									st.nextToken(),
									st.nextToken(), 
									Integer.parseInt(st.nextToken()));
							room.userVC.addElement(this);
							pos="게임준비중";
							roomVc.addElement(room);
							messageAll(Function.MAKEROOM+"|"
							           +room.roomName+"|"
							           +room.roomPosition+"|"
							           +room.current+"/"+room.maxcount);
							
							// 출력 ==> client
							messageAll(Function.WAITUPDATE+"|"
									+id+"|"+pos);
							
							
							// 2/6
							// 명령(방들어가기)
							messageTo(Function.MYROOMIN+"|"
									+id+"|"+room.roomName+"/"+room.current);
							
							
							
						}
						break;
						
    				  case Function.GAMESTART: //게임시작
    				  {
    					  String rn=st.nextToken(); //룸네임 받아오기
    					  int roompos=Integer.parseInt(st.nextToken()); //룸위치 지정
    					  pos = "게임중";
    					  for(int i=0;i<roomVc.size();i++)
							{    						  
								Room room=roomVc.elementAt(i);
								
								if(rn.equals(room.roomName))
								{
									
									for(int j=0;j<room.userVC.size();j++)
									{
										Client user=room.userVC.elementAt(j);
										user.messageTo(Function.GAMESTART+"|"
											+roompos+"|"
											+"===== 게임 START!! =====");
										/*user.messageTo(Function.GAMECHAT+"|"+id+"|"
												+"===== 게임 START!! =====");*/
										
										messageAll(Function.WAITUPDATE+"|"
												+user.id+"|"+user.pos);
									}
								}							
							}
    				  }
					  break;
    				  /*case Function.MYROOMIN:
						{
							
							 *   방찾는다
							 *   현재인원 증가
							 *   위치 변경
							 *   ==========
							 *   방에 있는 사람 
							 *     => 방에 들어가는 사람의 정보 전송
							 *     => 입장메세지 
							 *   방에 들어가는 사람 처리
							 *     => 방으로 변경
							 *     => 방에 있는 사람의 모든 정보를 받는다 
							 *   대기실 처리
							 *     => 1) 인원 (table1)
							 *        2) 위치 (table2)
							 *        
							 *   강퇴 , 초대 , 게임 
							 
							String rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current++;
									pos=room.roomName;
									// 방에 있는 사람 처리
									for(int j=0;j<room.userVC.size();j++)
									{
										Client user=room.userVC.elementAt(j);
										user.messageTo(Function.ROOMADD+"|"
											+id+"|"+name+"|"+sex+"|"+avata);
										user.messageTo(Function.GAMECHAT
												+"|[알림 ☞]"+name+"님이 입장하셨습니다");
									}
									// 방에 들어가는 사람 처리
									room.userVC.add(this);
									messageTo(Function.MYROOMIN+"|"
											+id+"|"+name+"|"
											+sex+"|"+avata+"|"+room.roomName);
									for(int k=0;k<room.userVC.size();k++)
									{
										Client user=room.userVC.elementAt(k);
										if(!id.equals(user.id))
										{
										  messageTo(Function.ROOMADD+"|"
											+user.id+"|"+user.name+"|"
											+user.sex+"|"+user.avata);
										}
									}
									// 대기실 
									messageAll(Function.WAITUPDATE+"|"
											+id+"|"+pos+"|"+room.roomName+"|"
											+room.current+"|"+room.maxcount);
								}
							}
						}
						break;
						case Function.ROOMOUT:
						{
							
							 *   방찾는다
							 *   현재인원 증가
							 *   위치 변경
							 *   ==========
							 *   방에 있는 사람 
							 *     => 방에 들어가는 사람의 정보 전송
							 *     => 입장메세지 
							 *   방에 들어가는 사람 처리
							 *     => 방으로 변경
							 *     => 방에 있는 사람의 모든 정보를 받는다 
							 *   대기실 처리
							 *     => 1) 인원 (table1)
							 *        2) 위치 (table2)
							 *        
							 *   강퇴 , 초대 , 게임 
							 
							String rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current--;
									pos="대기실";
									// 방에 있는 사람 처리
									for(int j=0;j<room.userVC.size();j++)
									{
										Client user=room.userVC.elementAt(j);
										user.messageTo(Function.ROOMOUT+"|"+id+"|"+name);
										user.messageTo(Function.GAMECHAT
												+"|[알림 ☞]"+name+"님이 퇴장하셨습니다");
									}
									// 방에 들어가는 사람 처리
									//room.userVc.addElement(this);
									messageTo(Function.MYROOMOUT+"|");
									for(int k=0;k<room.userVC.size();k++)
									{
										Client user=room.userVC.elementAt(k);
										if(id.equals(user.id))
										{
										   room.userVC.removeElementAt(k);
										   break;
										}
									}
									// 대기실 
									messageAll(Function.WAITUPDATE+"|"
											+id+"|"+pos+"|"+room.roomName+"|"
											+room.current+"|"+room.maxcount);
									if(room.current<1)
									{
										roomVc.removeElementAt(i);
										break;
									}
								}
							}
						}
						break;*/
						
						
					
    				  
    				  
    				  // 채팅 요청처리
    				  case Function.WAITCHAT:
    				  {
    					  String id = st.nextToken();
    					  String chat=st.nextToken();
    					  messageAll(Function.WAITCHAT+"|["+id+"]| "+chat);
    				  }
    				  break;
    				  
    				  case Function.GAMECHAT:
    				  {
    					  String id = st.nextToken();
    					  String chat=st.nextToken();
    					  messageAll(Function.GAMECHAT+"|["+id+"]| "+chat);
    				  }
    				  break;
    				  
    				//로그아웃
    				  case Function.LOGOUT:
						{
							messageAll(Function.LOGOUT+"|"+id);
							messageTo(Function.MYLOGOUT+"|");
							for(int i=0;i<waitVc.size();i++)
							{
								Client user=waitVc.elementAt(i);
								if(id.equals(user.id))
								{
									waitVc.removeElementAt(i);
									in.close();
									out.close();
									break;
								}
							}
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
    			for(Client client:waitVc)
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






