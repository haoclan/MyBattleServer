package src.Utill;

import src.Entity.OnlinePlayersEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/7/8.
 */

public class ServerConsole implements Runnable
{

    @Override
    public void run() {
        System.out.println("服务器控制子线程启动");

        while(true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String line = reader.readLine();
                if (line.equals("show players")){
                    //显示玩家
                    System.out.println("当前在线玩家如下");

                    for (Iterator iterator = MyServer.onlinePlayersEntitylist.iterator(); iterator.hasNext();)
                    {
                        OnlinePlayersEntity localentity = (OnlinePlayersEntity) iterator.next();
                        System.out.println( localentity.playerotherinformation.playersName);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
