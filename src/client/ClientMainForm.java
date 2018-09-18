package client;
import java.awt.CardLayout;
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

public class ClientMainForm extends JFrame implements ActionListener,Runnable,MouseListener{
   CardLayout card = new CardLayout();
   MainView mv = new MainView();  //�α���â ����
   WaitRoom wr = new WaitRoom();  //���â ����
   GameRoom gr = new GameRoom();  //���ӷ� ����
   WaitRoom_NewRoom nr=new WaitRoom_NewRoom();
   Socket s;
    BufferedReader in;//  ����
    OutputStream out;// ���� 
    String myid,msg,id,chat,location,rname;
    int rmpos; //���ӷ� ������ġ
    boolean gms;
    int score;
   
   
   public ClientMainForm()
   {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
       setLayout(card);
       
       add("MV",mv); //�α���â
       add("MF",wr); //����â
       add("GR",gr); 
           
       
        setSize(1600,900);
        setVisible(true);
        setResizable(false);
        
        mv.b1.addActionListener(this); //�α��ι�ư
        wr.tf.addActionListener(this);
        wr.b7.addActionListener(this);
        wr.p1.addMouseListener(this);
        nr.wnp.noButton.addActionListener(this);
        nr.wnp.okButton.addActionListener(this);
        gr.b2.addActionListener(this);
        
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
               s=new Socket("211.238.142.63", 7777);

               in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                  // byte ==> 2byte
               out=s.getOutputStream();
               out.write((Function.LOGIN+"|"+myid+"\n").getBytes()); //�����̵� �����ֱ�
            }catch(Exception ex) {}
            new Thread(this).start();
            
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
	               wr.rmt = new JLabel[]{wr.latitle1,
	                                    wr.latitle2
	                                  /*lanum3.equals(""),
	                                lanum4.equals(""),
	                               lanum5.equals(""),
	                               lanum6.equals("")*/
	                                };
	               
	               
	              rname=nr.wnp.roomName.getText();
	              if(rname.trim().length()<1)
	              {
	                 // �Է��� �ȵ� ����
	                 JOptionPane.showMessageDialog(this, "���̸��� �Է��ϼ���"); 
	                 nr.wnp.roomName.setText("");
	                 nr.wnp.roomName.requestFocus();
	                 return;
	              }
	              
	              //�� �ߺ� �˻�
	              String temp="";
	              for(int i=0; i<wr.rmt.length ;i++)
	              {
	                 System.out.println(wr.rmt[i].getText());
	                 if((wr.rmt[i].getText()).equals(" ")) continue;  //����ġ Ȯ�� (0~5)
	                 
	                 temp=wr.rmt[i].getText();
	                 if(temp.equals(rname))
	                 {
	                    JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���Դϴ�\n�ٽ� �Է��ϼ���");// �ߺ�üũ 
	                    nr.wnp.roomName.setText("");
	                    nr.wnp.roomName.requestFocus();
	                    return;
	                 }
	              }
	              
	              int rmpos = 1;//�������� ���� ����ġ ����
	              for(int i=0; i<wr.rmt.length ;i++)
	              {
	                 if((wr.rmt[i].getText()).equals(" ")) break;//����ġ Ȯ�� (0~5)
	                 
	                 else
	                    rmpos++; //�������� ���� ����ġ ����
	              }
	            
	           
	           int inwon=nr.wnp.personnel_Combo.getSelectedIndex()+2;
	           // ������ ����
	          try
	          {
	             out.write((Function.MAKEROOM+"|"+rname+"|"+rmpos+"|"+inwon+"\n").getBytes());
	          }catch(Exception ex) {}
	          
	          nr.setVisible(false);// ������ ���� ������ ������� �����
	         }
         }
         else if(e.getSource()==gr.b2)
         {
        	 
        	 try
	          {
	             out.write((Function.GAMESTART+"|"+rname+"|"+rmpos+"\n").getBytes());
	          }catch(Exception ex) {} 
         }
         
        /* else if(e.getSource()==gr.tf1)
         {
        	 
         }*/
      }
       
      public void run()
      {
         try
         {
            while(true)
            {
               wr.rmt = new JLabel[]{
                     
                     wr.latitle1,
                        wr.latitle2
                     };
               wr.rmn = new JLabel[]{
                               wr.lanum1,
                                wr.lanum2};
               wr.rmw = new JLabel[]{wr.law1,
                              wr.law2};
               
               
               // 100|id|name|sex
               String msg=in.readLine();//out.write()
               int rmpos;
               String rname ,rmstate;
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
                       card.show(getContentPane(), "MF");
                    }
                       
                   break;
                   
                  case Function.MAKEROOM:
                  {
                     rname=st.nextToken();
                     rmpos=Integer.parseInt(st.nextToken());
                     rmstate = st.nextToken();
                     gr.la1.setText(myid);
                     
                     if(rmpos==1)
                     {
                        wr.rmt[rmpos-1].setText(rname);
                        wr.rmn[rmpos-1].setText(rmstate);
                        wr.rmw[rmpos-1].setText("WAITING");
                     }
                     else if(rmpos==2)
                     {
                        wr.rmt[rmpos-1].setText(rname);
                        wr.rmn[rmpos-1].setText(rmstate);
                        wr.rmw[rmpos-1].setText("WAITING");
                     }
                  }
                  break;
                  
                  case Function.MYROOMIN:
                  {
                     card.show(getContentPane(), "GR");
                  }
                  break;
                  
                  case Function.WAITUPDATE:
                  {
                     String id=st.nextToken();
                  String pos=st.nextToken();
                  String temp="";
                   for(int i=0;i<wr.model2.getRowCount();i++)
                   {
                      temp=wr.model2.getValueAt(i, 0).toString();
                      if(id.equals(temp))
                      {
                         wr.model2.setValueAt(pos, i, 2);
                         break;
                      }
                   }
                  }
                  break;
                  
                  //���ӽ���
                  case Function.GAMESTART:
                  {
                	  rmpos=Integer.parseInt(st.nextToken());
                	  chat=st.nextToken();
                	  if(rmpos==1)
                      {
                         wr.rmw[rmpos-1].setText("PLAYING");
                      }
                      else if(rmpos==2)
                      {
                         wr.rmw[rmpos-1].setText("PLAYING");
                      }
                	  score = 0;
                	  gr.ta.append(chat+"\n");
                	  gr.bar.setValue(gr.bar.getMaximum());
                	  new Thread(gr).start(); //���ӷ� ������ ����
                	  //gr.score.setText(score+"");
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
                  case Function.GAMECHAT:
                  {
                     id=st.nextToken();
                     chat=st.nextToken();
                     
                      gr.ta.append(id+" "+chat+"\n");
                      gr.bar.setValue(gr.bar.getMaximum());
                  }
                  break;
               }
            }
         }catch(Exception ex){}
      }



   @Override
   public void mouseClicked(MouseEvent e) {
      // TODO Auto-generated method stub
      if(e.getSource()==wr.p1 || e.getSource()==wr.p2 || e.getSource()==wr.p3 || e.getSource()==wr.p4 || e.getSource()==wr.p5 || e.getSource()==wr.p6)                     // ȭ��ѱ��
         {
            //if()
         }
   }



   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }



   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }



   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }



   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
   }
      
}


