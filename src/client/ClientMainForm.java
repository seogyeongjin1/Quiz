package client;

import java.awt.CardLayout;
import javax.swing.JFrame;

public class ClientMainForm extends JFrame{
	CardLayout card = new CardLayout();
	MainView mv = new MainView();  //�α���â ����
	WaitRoom wr = new WaitRoom();  //���â ����
	GameRoom gr = new GameRoom();  //���ӷ� ����
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

	//������
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
