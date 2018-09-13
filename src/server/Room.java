package server;
import java.util.*;

import server.Server1;
import server.Server1.Client;
public class Room {
    String roomName,roomState,roomPwd;
    int maxcount;
    int current;
    ArrayList<Server.Client> userList=new ArrayList<Server.Client>();
    public Room(String rn,int max)
    {
    	roomName=rn;
    	//roomState=rs;
    	//roomPwd=rp;
    	maxcount=max;
    	current=1;
    }
}
