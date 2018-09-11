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
	MainView mv = new MainView();  //로그인창 생성
	WaitRoom wr = new WaitRoom();  //대기창 생성
	GameRoom gr = new GameRoom();  //게임룸 생성
	Socket s;
    BufferedReader in;//  서버
    OutputStream out;// 서버 
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
					    	 // 로그인 => 입력받는다 
						     id=st.nextToken();
						     
						     // 이미 접속된 사람들에게 전송 => 로그인하고 있는 사람 
						     messageAll(Function.LOGIN+"|"
						        +id/*+"|"+name+"|"+sex*/);
						     // 저장
						      wait.add(this);
						     // 상대방의 정보를 본인 받는다 
						     messageTo(Function.MYLOG+"|");//  대기실 갱신 
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
