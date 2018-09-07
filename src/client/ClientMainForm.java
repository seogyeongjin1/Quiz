package client;

import java.awt.CardLayout;
import javax.swing.JFrame;

public class ClientMainForm extends JFrame{
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

	//쓰레드
	/*@Override
    public void run() {
        for(int i=0; i<=100; i++)
        {
            value++;
            try {
                Thread.sleep(10);
                progress.setValue(i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }*/
}
