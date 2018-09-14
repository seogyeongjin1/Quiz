package client;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
public class WaitRoom extends JPanel{
    JLabel la1,la2,lanum1,lanum2,lasoo1,lasoo2,law1,law2, latitle1, latitle2, la3;
    JTable table1,table2;
    DefaultTableModel model1,model2;
    JTextArea ta;
    JTextField tf,tf2;
    JButton b1,b2,b3,b4,b5,b6,b7,b8;
    Image back, wait;
    JScrollBar bar;
    JPanel p1,p2,p3,p4,p5,p6;
    
    
    WaitRoom()
    {
       
       setLayout(null);
       back=Toolkit.getDefaultToolkit().getImage("Image\\2.png");
       la1=new JLabel("방개설 정보");
       la2=new JLabel("채팅방");
       la3=new JLabel("접속자 정보");
       ta=new JTextArea();
       JScrollPane js3=new JScrollPane(ta);
       ta.setEditable(false);
       
       bar= js3.getVerticalScrollBar();
       tf=new JTextField();
       tf2=new JTextField();
       
       p1=new JPanel();
       p1.setOpaque(false);
       p2=new JPanel();
       p2.setOpaque(false);
       p3=new JPanel();
       p3.setOpaque(false);
       p4=new JPanel();
       p4.setOpaque(false);
       p5=new JPanel();
       p5.setOpaque(false);
       p6=new JPanel();
       p6.setOpaque(false);

       b7=new JButton(new ImageIcon("Image\\roommake.jpg"));
       b8=new JButton(new ImageIcon("Image\\wait_exit.png"));

       
       //table
       
       String[] col1={"아이디","닉네임","위치"};
       String[][] row1=new String[0][3];
       model2=new DefaultTableModel(row1, col1);
       table2=new JTable(model2)
       {
    	   @Override
			public boolean isCellEditable(int row, int column) 
    	   {
				// TODO Auto-generated method stub
				return false;
    	   }
       };
       table2.getTableHeader().setReorderingAllowed(false); // 셀 이동불가
       table2.getTableHeader().setResizingAllowed(false);   // 셀 크기 조절 불가


       JScrollPane js4=new JScrollPane(table2);
       js4.setOpaque(false);
       js4.getViewport().setOpaque(false);

       
       // 배치
       la1.setBounds(65, 25, 100, 30);
       la1.setForeground(Color.white);
       
       add(la1);
       //add(js1);
       
       la2.setBounds(65, 565, 100, 30);
       la2.setForeground(Color.white);
       //js2.setBounds(10, 395, 500, 150);
       
       add(la2);
       //add(js2);
       
       la3.setBounds(1050, 400, 120, 30);
       la3.setForeground(Color.white);
       js4.setBounds(1050, 430, 500, 240);
         add(la3);
       add(js4);
       
       tf2.setBounds(1050, 460, 500, 215);
       add(tf2);
       
       //채팅방
       Color color = new Color(0,0,0);
       js3.setBounds(65, 600, 870, 225);
       js3.setForeground(color);
       add(js3);
       tf.setBounds(65, 830, 870, 30);
       add(tf);
       
       //방입장 버튼 위 라벨 설정
       //la1,la2,lanum1,lanum2,lasoo1,lasoo2,law1,law2, latitle1, latitle2;
       int a=1;
       int b=5;
       lanum1= new JLabel("001");
       lanum2= new JLabel("002");
       lasoo1= new JLabel(a+"/"+b);
       lasoo2= new JLabel(a+"/"+b);
       law1 = new JLabel("WAITING");
       law2 = new JLabel("PLAYING");
       latitle1 = new JLabel("초보만 달려요 ㄱㄱ~");
       latitle2 = new JLabel("제발 고수만~ 못하면 오지마");

       
       lanum1.setBounds( 215, 100, 100, 30);
       lanum1.setForeground(Color.black);
       lanum1.setOpaque(false);
       lanum1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
       add(lanum1);
       
       lanum2.setBounds(670, 100, 100, 30);
       lanum2.setForeground(Color.black);
       add(lanum2);

       /*lasoo1.setBounds(65, 565, 100, 30);
       lasoo1.setForeground(Color.black);
       lasoo1.setOpaque(false);
       lasoo1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
       add(lasoo1);
       
       
       lasoo2.setBounds(65, 565, 100, 30);
       lasoo2.setForeground(Color.black);
       add(lasoo2);*/
       
       
       law1.setBounds(200, 150, 200, 50);
       law1.setForeground(Color.blue);
       law1.setOpaque(false);
       law1.setFont(new Font("Serif", Font.BOLD, 40));
       law1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
       add(law1);
       
       
       law2.setBounds(650, 150, 200, 50);
       law2.setFont(new Font("Serif", Font.BOLD, 40));
       law2.setForeground(Color.blue);
       add(law2);
       
       
       latitle1.setBounds( 275, 95, 250, 40);
       latitle1.setForeground(Color.black);
       latitle1.setOpaque(false);
       //latitle1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
       add(latitle1);
       
       
       latitle2.setBounds(730, 100, 250, 30);
       latitle2.setForeground(Color.black);
       add(latitle2);
       
       // 크기
       JPanel p=new JPanel();
       p.setOpaque(false);
       p.setLayout(new GridLayout(3, 2,10,10));
       p.add(p1);p.add(p2);
       p.add(p3);p.add(p4);
       p.add(p5);p.add(p6);
       
       //방입장관련
       p.setBounds(60,65,900, 480);
       add(p);
       
         b7.setOpaque(true);
         b7.setBorderPainted(false);
         b7.setFocusPainted(false);
         b7.setContentAreaFilled(false);
        //b7.setLayout(null);
         b8.setOpaque(true);
         b8.setBorderPainted(false);
         b8.setFocusPainted(false);
         b8.setContentAreaFilled(false);
         
         add(b7);
         add(b8);
         b7.setBounds(1060, 710, 220, 100);
         b8.setBounds(1320, 710, 220, 100);
         setSize(1600,900);
       setVisible(true);

    }
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      try
      {
    	  //UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
      }catch(Exception ex) {} //예외처리
        WaitRoom wr=new WaitRoom();
   }
     // 스킨입힐때
      /*@Override
      protected void paintComponent(Graphics g) {
         g.drawImage(back, 0, 0, getWidth(),getHeight(),this);
      }*/
   @Override
   protected void paintComponent(Graphics g) {
      g.drawImage(back, 0,0, getWidth(),getHeight(),this);

   }

}