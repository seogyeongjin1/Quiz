package client;
import javax.swing.*;
import java.awt.*; // Image
public class MainView extends JPanel{
   Image back;
   JLabel la1,la2;
   JTextField tf;
   JPasswordField pf;
   JButton b1,b2;
   MainView() {
       back=Toolkit.getDefaultToolkit().getImage("Image\\login.png");
       // 아이디& 비밀번호
       la1=new JLabel("아이디");
       la2=new JLabel("비밀번호");
       
       tf=new JTextField();
       pf=new JPasswordField();
       
       b1 = new JButton(new ImageIcon("Image\\btLogin_hud.png"));
       b2 = new JButton(new ImageIcon("Image\\btLogin_hud.png"));
       
       // 배치
       setLayout(null); //배치를 사용하지 않고 직접 배치
       
       b1.setBounds(745, 689, 104, 48);
       b1.setBorderPainted(false);
       b1.setFocusPainted(false);
       b1.setContentAreaFilled(false);
       
       b2.setBounds(745, 689, 104, 48);
       b2.setBorderPainted(false);
       b2.setFocusPainted(false);
       b2.setContentAreaFilled(false);
       
       tf.setBounds(731, 390, 280, 30);
       tf.setOpaque(false);
       tf.setForeground(Color.green);
       tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
       pf.setBounds(731, 510, 280, 30);
       pf.setOpaque(false);
       pf.setForeground(Color.green);
       pf.setBorder(javax.swing.BorderFactory.createEmptyBorder());

       JPanel p=new JPanel();// 버튼
       p.setOpaque(false);// 투명색으로 변
       p.setBounds(380, 340, 185, 35);
       add(b1);
       add(b2);
       add(tf);
       add(pf);
       add(p);
   }
    
    // 스킨입힐때
   @Override
   protected void paintComponent(Graphics g) {
      g.drawImage(back, 0, 0, getWidth(),getHeight(),this);
   }
    
}