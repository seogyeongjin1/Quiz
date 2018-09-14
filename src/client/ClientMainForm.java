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

import common.Function;

public class ClientMainForm extends JFrame implements ActionListener,Runnable{
   CardLayout card = new CardLayout();
   MainView mv = new MainView();  //�α���â ����
   WaitRoom wr = new WaitRoom();  //���â ����
   GameRoom gr = new GameRoom();  //���ӷ� ����
   WaitRoom_NewRoom nr=new WaitRoom_NewRoom();
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
        wr.b7.addActionListener(this);
        nr.wnp.noButton.addActionListener(this);
        nr.wnp.okButton.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
   
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new ClientMainForm().setLocationRelativeTo(null);
   }

   @Override
      public void actionPerformed(ActionEvent e) {
         // TODO Auto-generated method stub
         
         if(e.getSource()==mv.b1) //�α��ι�ư
         {
           myid = mv.tf.getText(); //�� ���̵� ����
               if(myid.trim().length()<1)
                   return;
               
            try
            {
            	s=new Socket("211.238.142.60", 7777);


               in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                  // byte ==> 2byte
               out=s.getOutputStream();
               out.write((Function.LOGIN+"|"+myid+"\n").getBytes()); //�����̵� �����ֱ�
            }catch(Exception ex) {}
            new Thread(this).start();
            card.show(getContentPane(), "MF");
         }
         
         if(e.getSource()==wr.tf)
         {
            chat=wr.tf.getText();
            if(chat.trim().length()<1)
            {
               wr.tf.setText("");
               return;
            }
            
            try
            {
               out.write((Function.WAITCHAT+"|"+myid+"|"+chat+"\n").getBytes()); //ä�� ������
               wr.tf.setText("");
               wr.tf.requestFocus(); // focusS
            }catch(Exception ex) {}
            
         }
         else if(e.getSource()==wr.b7)
         {
        	 nr.wnp.roomName.setText("");
 			
 			
 			nr.wnp.la3.setVisible(false);
 			
 			// �ٽ� �游��⸦ �� ������ �޸� �ʱ�ȭ
 			
 			nr.setLocation(250, 190);// �游��� â�� �ߴ� ��ġ ����
 			nr.setSize(600, 500);
 			nr.setVisible(true);
         }
         // ���� �� �����
         else if(e.getSource()==nr.wnp.noButton)
         {
        	 nr.setVisible(false);
         }
         else if(e.getSource()==nr.wnp.okButton)
         {
        	 {
     			// �Էµ� �� ������ �б�
     			String rname=nr.wnp.roomName.getText();
     			if(rname.trim().length()<1)
     			{
     				// �Է��� �ȵ� ����
     				JOptionPane.showMessageDialog(this, "���̸��� �Է��ϼ���"); 
     				nr.wnp.roomName.requestFocus();
     				return;
     			}
     			
     			String temp="";
     			for(int i=0;i<wr.model1.getRowCount();i++)
     			{
     				temp=wr.model1.getValueAt(i, 0).toString();
     				if(temp.equals(rname))
     				{
     					JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���Դϴ�\n�ٽ� �Է��ϼ���");// �ߺ�üũ 
     					nr.wnp.roomName.setText("");
     					nr.wnp.roomName.requestFocus();
     					return;
     				}
     			}
        	 
        	// ������ ����
 			try
 			{
 				out.write((Function.MAKEROOM+"|"+rname+"|"+"\n").getBytes());
 			}catch(Exception ex) {}
 			
 			nr.setVisible(false);// ������ ���� ������ ������� �����
         }
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
               
               switch(protocol) // MYLOG
               {
                  case Function.LOGIN:
                    {
                       id=st.nextToken();
                       location = st.nextToken();
                        wr.ta.append("�� ["+id+"]"+" ���� �����Ͽ����ϴ�.\n");
                        String[] data= {id,id,location};
                        wr.model2.addRow(data);
                    }
                   break;
                  case Function.MYLOG:
                    {
                    	id = st.nextToken(); // aaa
                       String[] data={
       						id,
       						id,//ID
       						st.nextToken()//location // ����
       					 };
       					 wr.model2.addRow(data);
                    }
                       
                   break;
                  case Function.WAITCHAT:
                    {
                       id=st.nextToken();
                       chat=st.nextToken();
                       
                        wr.ta.append(id+" "+chat+"\n");
                        wr.bar.setValue(wr.bar.getMaximum());
                    }
                    break;
                    
               }
            }
         }catch(Exception ex){}
      }
      
}



