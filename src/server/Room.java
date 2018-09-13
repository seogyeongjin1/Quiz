package server;
import java.util.*;

import server.Server1;
import server.Server1.Client;
public class Room {
    String roomName,roomState,roomPwd;
    int maxcount;
    int current;
    Vector<Server1.Client> userVc=new Vector<Server1.Client>();
    public Room(String rn,String rs,String rp,int max)
    {
    	roomName=rn;
    	roomState=rs;
    	roomPwd=rp;
    	maxcount=max;
    	current=1;
    }
}
