package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import common.Function;

public class ClientMainForm extends JFrame implements ActionListener,Runnable{
	CardLayout card = new CardLayout();
	MainView mv = new MainView();  //�α���â ����
	WaitRoom wr = new WaitRoom();  //���â ����
	GameRoom gr = new GameRoom();  //���ӷ� ����
	Socket s;
    BufferedReader in;//  ����
    OutputStream out;// ���� 
    String msg,id,name,sex;
	public ClientMainForm()
	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		 setLayout(card);
		 
		 add("GR",gr);
		 add("MV",mv);
	     add("MF",wr);
		 	 
		 
	     setSize(1600,900);
	     setVisible(true);
	     
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClientMainForm();
	}

	@Override
	   public void actionPerformed(ActionEvent e) {
	      // TODO Auto-generated method stub
	      if(e.getSource()==gr.tf1)
	      {
	         String s = gr.tf1.getText();
	         gr.tf1.setText("");
	      }
	      
	      if(e.getSource()==mv.b1)
	      {
	         try
	         {
	            s=new Socket("211.238.142.63", 7777);
	            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
	               // byte ==> 2byte
	            out=s.getOutputStream();
	            msg=mv.tf.getText();
	            card.show(getContentPane(), "MF");
	            
	         }catch(Exception ex) {}
	         new Thread(this).start();
	      }
	      else if(e.getSource()==wr.tf)
	      {
	         msg=wr.tf.getText();
	         if(msg.trim().length()<1)
	            return;
	         try
	         {
	            out.write((msg+"\n").getBytes());
	         }catch(Exception ex) {}
	         wr.tf.setText("");
	         wr.tf.requestFocus();
	      }
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
					    	 // �α��� => �Է¹޴´� 
						     id=st.nextToken();
						     
						     // �̹� ���ӵ� ����鿡�� ���� => �α����ϰ� �ִ� ��� 
						     messageAll(Function.LOGIN+"|"
						        +id/*+"|"+name+"|"+sex*/);
						     // ����
						      wait.add(this);
						     // ������ ������ ���� �޴´� 
						     messageTo(Function.MYLOG+"|");//  ���� ���� 
						     for(Client client:wait)
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
					    	
					     }
					     break;
					     
					}
				}
			}catch(Exception ex){}
		}
}
