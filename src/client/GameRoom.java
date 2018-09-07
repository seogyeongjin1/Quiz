package client;
import javax.swing.*; // J~
import java.awt.*; // Color,Layout
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.*;
public class GameRoom extends JPanel implements Runnable{
   Munje mj = new Munje();
   JProgressBar pb1;
   JButton b1,b2,b3;
   JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9,la10,la11,la12,la13,la14,la15,po1,po2,po3,po4,po5,score;
   JTextField tf1,tf2,tf3,tf4,tf5;
   JTextArea ta;
   JTextField tf;
   JPanel gp;
   Image back,munje;
   
   GameRoom()
   {
      back=Toolkit.getDefaultToolkit().getImage("Image\\3.jpg");
      munje=Toolkit.getDefaultToolkit().getImage(mj.image[0]);
      // 초기값
         b1=new JButton(new ImageIcon("Image\\ready.png"));
         b2=new JButton(new ImageIcon("Image\\start.png"));
         b3=new JButton(new ImageIcon("Image\\wait_exit2.png")); // 클래스 초기화
      
           
         
      la1=new JLabel();
      la1.setOpaque(true);
      la1.setBackground(Color.black);
      la2=new JLabel();
      la2.setOpaque(true);
      la2.setBackground(Color.black);
      la3=new JLabel();
      la3.setOpaque(true);
      la3.setBackground(Color.black);
      la4=new JLabel();
      la4.setOpaque(true);
      la4.setBackground(Color.black);
      la5=new JLabel();
      la5.setOpaque(true);
      la5.setBackground(Color.black);
      la6=new JLabel();
      la6.setOpaque(true);
      la6.setBackground(Color.black);
      la7=new JLabel();
      la7.setOpaque(true);
      la7.setBackground(Color.black);
      la8=new JLabel();
      la8.setOpaque(true);
      la8.setBackground(Color.black);
      la9=new JLabel();
      la9.setOpaque(true);
      la9.setBackground(Color.black);
      la10=new JLabel();
      la10.setOpaque(true);
      la10.setBackground(Color.black);
      la11=new JLabel();
      la11.setOpaque(true);
      la11.setBackground(Color.black);
      la12=new JLabel();
      la12.setOpaque(true);
      la12.setBackground(Color.black);
      la13=new JLabel();
      la13.setOpaque(true);
      la13.setBackground(Color.black);
      la14=new JLabel();
      la14.setOpaque(true);
      la14.setBackground(Color.black);
      la15=new JLabel();
      la15.setOpaque(true);
      la15.setBackground(Color.black);
      po1=new JLabel(new ImageIcon("Image\\po1.png"));
      po1.setOpaque(true);
      po1.setBackground(Color.black);
      po2=new JLabel(new ImageIcon("Image\\po2.png"));
      po2.setOpaque(true);
      po2.setBackground(Color.black);
      po3=new JLabel(new ImageIcon("Image\\po3.png"));
      po3.setOpaque(true);
      po3.setBackground(Color.black);
      po4=new JLabel(new ImageIcon("Image\\po4.png"));
      po4.setOpaque(true);
      po4.setBackground(Color.black);
      po5=new JLabel(new ImageIcon("Image\\po5.png"));
      po5.setOpaque(true);
      po5.setBackground(Color.black);
      
      score=new JLabel("1000"); 
      score.setBackground(Color.black);
      score.setFont(new Font("휴먼매직체", Font.BOLD, 55));
      score.setForeground(Color.YELLOW);
      
      gp = new JPanel();
      
      tf1=new JTextField();
      tf2=new JTextField();
      tf3=new JTextField();
      tf4=new JTextField();
      tf5=new JTextField();
      
      ta=new JTextArea();
      JScrollPane js=new JScrollPane(ta);
      tf=new JTextField();
      
      
      
      // 배치
      setLayout(null);
      // null 직접 배치
      la1.setBounds(70, 115, 130,30);
      la2.setBounds(70, 162, 130,30);
      la3.setBounds(70, 211, 130,30);
     
      tf1.setBounds(650, 750, 275, 30);
      
      la4.setBounds(1385, 116, 130, 30);
      tf2.setBounds(1390, 60, 170, 30); 
      la5.setBounds(1385, 163, 130, 30);
      la6.setBounds(1385, 213, 130, 30);
      
      la7.setBounds(70, 346, 130, 30);
      tf3.setBounds(250, 330, 170, 30);
      la8.setBounds(70, 393, 130, 30);
      la9.setBounds(70, 441, 130, 30);
      
      la10.setBounds(1385, 346, 130, 30);
      tf4.setBounds(1390, 330, 170, 30);
      la11.setBounds(1385, 393, 130, 30);
      la12.setBounds(1385, 442, 130, 30);
      
      la13.setBounds(70, 586, 130, 30);
      tf5.setBounds(40, 800, 170, 30);
      la14.setBounds(70, 633, 130, 30);
      la15.setBounds(70, 681, 130, 30);
      
      po1.setBounds(230,90,150,150);
      po2.setBounds(1213,92,150,150);
      po3.setBounds(233,322,150,150);  
      po4.setBounds(1213,323,150,150);
      po5.setBounds(233,561,150,150);
      
      score.setBounds(855, 122, 150, 50);
      
      add(score);
      
      add(la1);
      add(tf1);
      
      add(la2);
      //add(tf2);
      
      add(la3);
      //add(tf3);
      
      add(la4);
      //add(tf4);
      
      add(la5);
      //add(tf5);
      add(la6);
      
      add(la7);
      add(la8);
      add(la9);
      
      add(la10);
      add(la11);
      add(la12);
      
      add(la13);
      add(la14);
      add(la15);
      
      add(po1);
      add(po2);
      add(po3);
      add(po4);
      add(po5);
      
      //게임 프로그래스바
      pb1 = new JProgressBar();
      pb1.setMaximum(100);
      pb1.setMinimum(0);
      pb1.setForeground(Color.ORANGE);
      pb1.setBackground(Color.WHITE);
      pb1.setStringPainted(true);
      pb1.setBounds(444,700, 701, 25);
      pb1.setFont(new Font("휴먼매직체", Font.BOLD, 20));
      //pb1.setValue(value);
      add(pb1);
      
      //채팅창 설정
      js.setBounds(1200, 540, 350, 200); // 채팅창
      add(js);
      // div {x:10 y:10}
      tf.setBounds(1200, 750, 350, 30); // 채팅
      add(tf);
      
      //시작하기 & 나가기 버튼
      JPanel p=new JPanel();
      p.setLayout(new GridLayout(1, 2, 7, 7)); // 버튼
      p.setOpaque(false);
      //p.add(b1);
      p.add(b2);
      p.add(b3);
      p.setBounds(1335, 23, 200, 25);
      add(p);
      
      //문제 창
      //gp.setBounds(445, 200, 700, 490);
      //gp.setOpaque(true);
      //add(gp);
      
      // 윈도우 크기 결정
      setSize(1600, 900);
      setVisible(true);
      
      //setDefaultCloseOperation(EXIT_ON_CLOSE); // 프로그램 종료
      new Thread(this).start();
   }
   


   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new GameRoom();
      
   }
   // 스킨입힐때
      protected void paintComponent(Graphics g) {
         g.drawImage(back, 0, 0, getWidth(),getHeight(),this);
         g.drawImage(munje, 445, 200, 700, 490, this);
      }

    //그림교체 화면
    public void setImage(int i)
    {
    	munje=Toolkit.getDefaultToolkit().getImage(mj.image[i]);
    	repaint();
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int a=0;
		int i=0;
		while(true)
		{
			pb1.setValue(a);
			try
			{
				Thread.sleep(50);
			}catch(Exception ex) {}
			if(a==100)
			{
				i++;
				a=0;
				setImage(i);
				
				if(i==10)
				{
					// 서버 점수 전송 
					break;
				}

			}
			a++;
			
		}
	}
}
      
      
      