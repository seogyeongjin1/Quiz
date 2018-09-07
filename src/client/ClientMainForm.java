package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ClientMainForm extends JFrame implements ActionListener{
	CardLayout card = new CardLayout();
	MainView mv = new MainView();  //로그인창 생성
	WaitRoom wr = new WaitRoom();  //대기창 생성
	GameRoom gr = new GameRoom();  //게임룸 생성
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
	      
	      
	      
	   }
}
