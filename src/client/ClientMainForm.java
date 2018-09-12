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
    String myid,msg,id,chat,location;
	public ClientMainForm()
	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		 setLayout(card);
		 
		 add("MV",mv); //로그인창
		 add("MF",wr); //대기실창
		 add("GR",gr); //게임창
		 	 
		 
	     setSize(1600,900);
	     setVisible(true);
	     setResizable(false);
	     
	     mv.b1.addActionListener(this); //로그인버튼
	     wr.tf.addActionListener(this);
	     
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClientMainForm().setLocationRelativeTo(null);
	}

	@Override
	   public void actionPerformed(ActionEvent e) {
	      // TODO Auto-generated method stub
	      /*if(e.getSource()==gr.tf1)
	      {
	         String s = gr.tf1.getText();
	         gr.tf1.setText("");
	      }*/
	      
	      if(e.getSource()==mv.b1) //로그인버튼
	      {
	    	 myid = mv.tf.getText(); //내 아이디 저장
	        	 if(myid.trim().length()<1)
	 	            return;
	        	 
	         try
	         {
	            s=new Socket("211.238.142.63", 7777);
	            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
	               // byte ==> 2byte
	            out=s.getOutputStream();
	         }catch(Exception ex) {}
	         new Thread(this).start();
 
        	 
	         try
	         {
		         out.write((Function.LOGIN+"|"+myid+"\n").getBytes()); //내아이디 날려주기
	         }catch(Exception ex) {}
	         card.show(getContentPane(), "MF");
	      }
	      
	      if(e.getSource()==wr.tf)
	      {
	         chat=wr.tf.getText();
	         if(chat.trim().length()<1)
	            return;
	         
	         try
	         {
	            out.write((Function.CHAT+"|"+myid+"|"+chat+"\n").getBytes()); //채팅 날리기
	         }catch(Exception ex) {}
	         wr.tf.setText("");
	         //wr.tf.requestFocus();
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
					    	 id=st.nextToken();
					    	 location = st.nextToken();
					         wr.ta.append("["+id+"] 님이 입장하였습니다.\n");
					         String[] data= {id,id,location};
					         wr.model2.addRow(data);
					     }
						 break;
						case Function.MYLOG:
					     {
					    	 id=st.nextToken();
					    	 if(myid.equals(id)) 
					    		 break;
					    	 location = st.nextToken();
			    			 String[] data= {id,id,location};
			    			 wr.model2.addRow(data);
					     }
					     	
						 break;
						case Function.CHAT:
					     {
					    	 id=st.nextToken();
					    	 chat=st.nextToken();
					         wr.ta.append("["+id+"]  "+chat+"\n");
					     }
					     break;
					     
					}
				}
			}catch(Exception ex){}
		}
		
}
