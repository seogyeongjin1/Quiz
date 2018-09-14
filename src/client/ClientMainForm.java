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
   MainView mv = new MainView();  //로그인창 생성
   WaitRoom wr = new WaitRoom();  //대기창 생성
   GameRoom gr = new GameRoom();  //게임룸 생성
   WaitRoom_NewRoom nr=new WaitRoom_NewRoom();
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
         
         if(e.getSource()==mv.b1) //로그인버튼
         {
           myid = mv.tf.getText(); //내 아이디 저장
               if(myid.trim().length()<1)
                   return;
               
            try
            {
            	s=new Socket("211.238.142.60", 7777);


               in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                  // byte ==> 2byte
               out=s.getOutputStream();
               out.write((Function.LOGIN+"|"+myid+"\n").getBytes()); //내아이디 날려주기
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
               out.write((Function.WAITCHAT+"|"+myid+"|"+chat+"\n").getBytes()); //채팅 날리기
               wr.tf.setText("");
               wr.tf.requestFocus(); // focusS
            }catch(Exception ex) {}
            
         }
         else if(e.getSource()==wr.b7)
         {
        	 nr.wnp.roomName.setText("");
 			
 			
 			nr.wnp.la3.setVisible(false);
 			
 			// 다시 방만들기를 할 때마다 메모리 초기화
 			
 			nr.setLocation(250, 190);// 방만들기 창이 뜨는 위치 설정
 			nr.setSize(600, 500);
 			nr.setVisible(true);
         }
         // 실제 방 만들기
         else if(e.getSource()==nr.wnp.noButton)
         {
        	 nr.setVisible(false);
         }
         else if(e.getSource()==nr.wnp.okButton)
         {
        	 {
     			// 입력된 방 정보를 읽기
     			String rname=nr.wnp.roomName.getText();
     			if(rname.trim().length()<1)
     			{
     				// 입력이 안된 상태
     				JOptionPane.showMessageDialog(this, "방이름을 입력하세요"); 
     				nr.wnp.roomName.requestFocus();
     				return;
     			}
     			
     			String temp="";
     			for(int i=0;i<wr.model1.getRowCount();i++)
     			{
     				temp=wr.model1.getValueAt(i, 0).toString();
     				if(temp.equals(rname))
     				{
     					JOptionPane.showMessageDialog(this, "이미 존재하는 방입니다\n다시 입력하세요");// 중복체크 
     					nr.wnp.roomName.setText("");
     					nr.wnp.roomName.requestFocus();
     					return;
     				}
     			}
        	 
        	// 서버로 전송
 			try
 			{
 				out.write((Function.MAKEROOM+"|"+rname+"|"+"\n").getBytes());
 			}catch(Exception ex) {}
 			
 			nr.setVisible(false);// 서버로 값을 보내고 사라지게 만든다
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
                        wr.ta.append("※ ["+id+"]"+" 님이 입장하였습니다.\n");
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
       						st.nextToken()//location // 대기실
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



