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
    String myid,msg,id,chat,location;
	public ClientMainForm()
	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		 setLayout(card);
		 
		 add("MV",mv); //�α���â
		 add("MF",wr); //����â
		 add("GR",gr); //����â
		 	 
		 
	     setSize(1600,900);
	     setVisible(true);
	     setResizable(false);
	     
	     mv.b1.addActionListener(this); //�α��ι�ư
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
	      
	      if(e.getSource()==mv.b1) //�α��ι�ư
	      {
	    	 myid = mv.tf.getText(); //�� ���̵� ����
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
		         out.write((Function.LOGIN+"|"+myid+"\n").getBytes()); //�����̵� �����ֱ�
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
	            out.write((Function.CHAT+"|"+myid+"|"+chat+"\n").getBytes()); //ä�� ������
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
					         wr.ta.append("["+id+"] ���� �����Ͽ����ϴ�.\n");
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
