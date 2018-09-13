/*package server;
import java.util.*;

import common.Function;

import java.net.*;
import java.io.*;

 *   1. ���� ��� (�ڵ���) ==> ���� 
 *      ======
 *       Socket : �ٸ� ��ǻ�Ϳ� ���� 
 *       
 *   2. ���� ==> ���� (��ȭ��ȣ,��ȭ��)
 *             === ip , port 
 *             ���ɿ� �ɴ´�
 *             =========
 *             bind(ip,port)
 *   3. ��� (��ȭ�� �ö� ���� ��ٸ���)
 *             listen()
 *   =========================== ���ο� (P2P)
 *   ��Ƽ 
 *   ====
 *    1) ��ȯ����
 *    2) ��ż��� ==> �����ڸ��� ���� ==> Thread
 
public class ex_Server implements Runnable{
    // ���� ���� ���� 
	private ServerSocket ss;
	private final int PORT=3355;
	// Ŭ���̾�Ʈ ������ ���� 
	private ArrayList<Client> waitList=
			   new ArrayList<Client>();
	// Ŭ���̾�Ʈ�� IP,id....
	public ex_Server()// ���α׷����� ���۰� ���� ���� : ������,main
	{
		// ���� ==> �����Ҷ� �Ѱ� ��ǻ�Ϳ��� �ι��� ���� �� �� ����
		try
		{
			ss=new ServerSocket(PORT);
			// ������ ==> bind():���� , listen(): ��� 
			System.out.println("Server Start...");
		}catch(Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	// ���������� ó���ϴ� ��� 
	public void run()
	{
		try
		{
			// Ŭ���̾�Ʈ�� �߽��� IPȮ�� ==> Socket
			while(true)
			{
			   // Socket s==> ������ Ŭ���̾�Ʈ�� ����(IP,PORT)
			   Socket s=ss.accept();// Ŭ���̾�Ʈ�� ���ӽÿ��� ȣ�� 
			   Client client=new Client(s);
			   // ������� Ŭ���̾�Ʈ�� ����� ���۵ȴ� 
			   client.start();
			}
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ex_Server server=new ex_Server();
        new Thread(server).start();
	}
	
	// ��� �غ� ==> ���� Ŭ���� 
    class Client extends Thread
    {
    	// �α��ν� �����ϴ� ������ id,name
    	String id;
    	String name;
    	String pos;// ��ġ 
    	Socket s;// ����
    	BufferedReader in;
    	OutputStream out;
    	public Client(Socket s)
    	{
    		try
    		{
    			this.s=s;
    			in=new BufferedReader(
    					new InputStreamReader(s.getInputStream()));
    			// ������ ����ϴ� Ŭ���̾�Ʈ�� ������ ���� ����
    			out=s.getOutputStream();
    			// ������ ����ϴ� Ŭ���̾�Ʈ �޼��� ����
    		}catch(Exception ex){}
    	}
    	// ��� 
    	
    	     ����   =======> 
    	      �����͸� �޾Ƽ� ó��
    	      ����� �����ش�
    	     Ŭ���̾�Ʈ ====>
    	        ��û�� ���� �ʿ��� ������ ����
    	        �α��� : ID,PWD
    	        ������� �޾Ƽ� ȭ�鿡 ���  
    	 
    	public void run()
    	{
    		try
    		{
    			// 100|id|name
    			while(true)
    			{
    				String msg=in.readLine();
    				System.out.println("Client=>��û��:"+msg);
    				// Ŭ���̾�Ʈ�� ������ �޼���
    				// ó�� ==> ����� ������
    				
    				// 100|id|name
    				// ��ȣ ==> ��� (��û ��ȣ)
    				StringTokenizer st=new StringTokenizer(msg, "|");
    				int no=Integer.parseInt(st.nextToken());
    				switch(no)
    				{
    				  case Function.LOGIN:// login.jsp
    				  {
    					 id=st.nextToken();
    					 name=st.nextToken();
    					 pos="����";
    					 // ������ ��� ������� �α����� �˷��ش�(���̺��� ���)
    					 messageAll(Function.LOGIN+"|"+id+"|"+name+"|"+pos);
    					 // ������ �߰����� �ʴ´� 
    					 // ������ �߰� 
    					 waitList.add(this);
    					 // 1. �α��� ==> ���Ƿ� ���� 
    					 messageTo(Function.MYLOG+"|"+id);
    					 // 2. ������ ������ ���� 
    					 for(Client client:waitList)
    					 {
    						 messageTo(Function.LOGIN+"|"
    								+client.id+"|"
    								+client.name+"|"
    								+client.pos);
    					 }
    					 // ������ �� ����
    					 
    					  *   �α��� 
    					  *   �氳��
    					  *   �����
    					  *   �泪���� 
    					  *   ==> �������α׷� (X)
    					  
    				  }
    				  break;
    				  // ä�� ��ûó��
    				  case Function.CHAT:
    				  {
    					  String data=st.nextToken();
    					  messageAll(Function.CHAT+"|["+name+"]"+data);
    				  }
    				  break;
    				}
    			}
    		}catch(Exception ex){}
    	}
    	
    	 *   ���� ==> Ŭ���̾�Ʈ ���� �޼��� 
    	 
    	//  ��ü �����ϴ� �޼���
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
    	//  ������ �����ϴ� �޼��� 
    	public void messageTo(String msg)
    	{
    		try
    		{
    			out.write((msg+"\n").getBytes());
    		}catch(Exception ex){}
    	}
    	
    }
}







*/