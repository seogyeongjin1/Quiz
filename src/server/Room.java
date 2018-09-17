package server;
import java.util.*;

public class Room {
    String roomName,roomState,roomPwd,roomPosition;
    int maxcount;
    int current;
    Vector<Server.Client> userVC=new Vector<Server.Client>();
    public Room(String rn,String rmp, int max)
    {
    	roomName=rn;
    	roomPosition=rmp;
    	//roomPwd=rp;
    	maxcount=max;
    	current=1;
    }
}
