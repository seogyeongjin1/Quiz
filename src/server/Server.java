package server;
import java.util.*;
import server.Room;

import common.Function;

import java.net.*;
import java.io.*;


//         �̰� ���� ��¥ ����



/*
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
 */
public class Server implements Runnable{
    // ���� ���� ���� 
   private ServerSocket ss;
   private final int PORT=7777;
   // Ŭ���̾�Ʈ ������ ���� 
   Vector<Client> waitVc=
            new Vector<Client>();
   Vector<Room> roomVc=new Vector<Room>();
   // Ŭ���̾�Ʈ�� IP,id....
   public Server()// ���α׷����� ���۰� ���� ���� : ������,main
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
      Server server=new Server();
        new Thread(server).start();
   }
   
   // ��� �غ� ==> ���� Ŭ���� 
    class Client extends Thread
    {
       // �α��ν� �����ϴ� ������ id,name
       String id;
       String pos;// �� ����
       String rn; //����
       int rmpos; //����ġ
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
       /*
            ����   =======> 
             �����͸� �޾Ƽ� ó��
             ����� �����ش�
            Ŭ���̾�Ʈ ====>
               ��û�� ���� �ʿ��� ������ ����
               �α��� : ID,PWD
               ������� �޾Ƽ� ȭ�鿡 ���  
        */
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
                int protocal=Integer.parseInt(st.nextToken());
                switch(protocal)
                {
                  case Function.LOGIN:// login.jsp
                  {
                    id=st.nextToken();
                    pos="����";
                    // ������ ��� ������� �α����� �˷��ش�(���̺� ���)
                    messageAll(Function.LOGIN+"|"+id+"|"+pos);
                    // ������ �߰����� �ʴ´� 
                    // ������ �߰� 
                    waitVc.addElement(this);
                    // 1. �α��� ==> ���Ƿ� ���� 
                    //messageTo(Function.MYLOG+"|"+id);
                    // 2. ������ ����� ���� 
                    for(Client client:waitVc)
                    {
                       messageTo(Function.MYLOG+"|"
                            +client.id+"|"
                            +client.pos);
                    }
                    
                    //������ ����
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
                  
                  
                  // �游���
                  case Function.MAKEROOM:
                  {

                     // ������ �ޱ�
                	  rn = st.nextToken(); //����
                      rmpos = Integer.parseInt(st.nextToken()); //����ġ
                     Room room=new Room(
                           rn,
                           rmpos,
                           Integer.parseInt(st.nextToken()));  //�� �ִ��ο�
                     room.userVC.addElement(this);  //�����̶�� ���� �߰�
                     pos="�����غ���";
                     roomVc.addElement(room); //�水ü �߰�
                     
                     //���������� ����� ��ο��� �������� ����
                     messageAll(Function.MAKEROOM+"|"   
                                +room.roomName+"|"
                                +room.roomPosition+"|"
                                +room.current+"/"+room.maxcount);
                     
                     
                     //���ӹ�� ����� ���������� �������� => ���������� ����鿡�� ����
                     messageAll(Function.WAITUPDATE+"|"
                           +id+"|"+pos);
                     
                     
                     // 2/6
                     // ���θ� �гα�ü
                     messageTo(Function.MYROOMIN+""/*"|"
                           +id+"|"+room.roomName+"/"+room.current*/);
                     
                     messageTo(Function.ROOMADD+"|"+id);
                  }
                  break;

                  
                  case Function.MYROOMIN:
                  {

                	 id=st.nextToken();  //���̵� 
                     rn=st.nextToken();  //������

                     int roompos=Integer.parseInt(st.nextToken()); //����ġ
                     for(int i=0;i<roomVc.size();i++)  //������� �游ŭ �ݺ�
                     {
                        Room room=roomVc.elementAt(i);
                        if(rn.equals(room.roomName))  //������ ������ ��ġ�ϴ��� Ȯ��
                        {
                           //������ ��ġ

                           
                           room.current++;  //�濡 �ִ� �����
                           pos="�����غ���"; 
                           // �濡 �ִ� ��� ó��
                           for(int j=0;j<room.userVC.size();j++) //�濡 �ִ� �ο���ŭ �ݺ�
                           {
                              Client user=room.userVC.elementAt(j);
                              
                              user.messageTo(Function.ROOMADD+"|"+id);//���� Ȯ���� ��ġ ���濡�� �����ϱ�
                              

                              System.out.println(id + "�� �����ߴٰ� ����ä��â�� ���");
                              user.messageTo(Function.GAMECHAT +"|"+"[�˸� ��]  " + id + "|"+"���� �����ϼ̽��ϴ�");//����޼���
                           }
                           
                           // �濡 ���� ��� ó��
                           room.userVC.add(this);  //���� ��,���������� �� ���� �߰�
                           messageTo(Function.MYROOMIN+""
                        		   /*+"|"   //ī�巹�̾ƿ� ��ü
                                 +id+"|"+name+"|"
                                 +room.roomName*/);
                           
                           System.out.println(id+"�� �����ߴ�");

                           for(int k=0;k<room.userVC.size();k++) //�濡 ������ ���� ����ŭ �ݺ�
                           {
                              Client user=room.userVC.elementAt(k); 
                              	//System.out.println("bbb���� ��������� �����ٰŴ�");
                                messageTo(Function.ROOMADD+"|" 
                                 +user.id);//�� ���̵�� ���� ���̵� �ٸ���� ROOMADD����
                           }

                           // ���ǿ� �ִ� ������� �� ���� �Ѹ���
                           messageAll(Function.WAITUPDATE+"|"
                                   +id+"|"+pos);
                           
                           // ����� ����
                           messageAll(Function.ROOMUP+"|"+roompos+"|"
                                   +room.current+"/"+room.maxcount);
                           
                           
                        //���� ��ġ if �� ��
                        }
                     }
                  }
                  break;
                  
                  case Function.GAMESTART: //���ӽ���
                  {
                     rn=st.nextToken(); //����� �޾ƿ���
                     int roompos=Integer.parseInt(st.nextToken()); //����ġ ����
                     pos = "������";
                     for(int i=0;i<roomVc.size();i++)
                     {                        
                        Room room=roomVc.elementAt(i);
                        
                        if(rn.equals(room.roomName))  //������ ��ġ�ϴ��� Ȯ��

                        {	
                           for(int j=0;j<room.userVC.size();j++) // �濡 �����ִ� �ο���ŭ �ݺ�
                           {
                              Client user=room.userVC.elementAt(j); 
                              user.messageTo(Function.GAMESTART+"|" //��ȿ� �ִ� �����鿡�Ը� ����
                                 +roompos+"|" +"===== ���� START!! =====");
                              /*user.messageTo(Function.GAMECHAT+"|"+id+"|"
                                    +"===== ���� START!! =====");*/
                              
                              messageAll(Function.WAITUPDATE+"|"
                                    +user.id+"|"+user.pos);
                           }
                           messageAll(Function.PLAYUP+"|"
                                   +roompos);
                        }                     
                     }
                  }
                 break;
                  
                  
                  // ä�� ��ûó��
                  case Function.WAITCHAT:
                  {
                     String id = st.nextToken();
                     String chat=st.nextToken();
                     messageAll(Function.WAITCHAT+"|["+id+"]| "+chat);
                  }
                  break;
                  
                  case Function.GAMECHAT:
                  {
                     id = st.nextToken();
                     String chat=st.nextToken();
                     System.out.println(id);
                     
                     for(int i=0;i<roomVc.size();i++)
                     {                        
                        Room room=roomVc.elementAt(i);
                        //System.out.println(room.roomName);
                        if(rn.equals(room.roomName))  //������ ��ġ�ϴ��� Ȯ��
                        {	
                           for(int j=0;j<room.userVC.size();j++) // �濡 �����ִ� �ο���ŭ �ݺ�
                           {
                              Client user=room.userVC.elementAt(j); 
                              user.messageTo(Function.GAMECHAT+"|["+id+"]| "+chat);
                           }
                        }                     
                     }
                  }
                  break;
                  
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
        *   ���� ==> Ŭ���̾�Ʈ ���� �޼��� 
        */
       //  ��ü �����ϴ� �޼���
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

