import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/7/8.
 */

public class PlayersInformationInput implements Runnable
{
    //需要socket
    //可能需要调用其他东西比如因此来个玩家
    Socket playersocket;//为了方便单独把socket拿出来。
    OnlinePlayersEntity playersEntity;


    public PlayersInformationInput(OnlinePlayersEntity playersEntity){

        playersocket = playersEntity.socketToPlayer;
        this.playersEntity=playersEntity;
    }


    @Override
    public void run() {

      //获得输入流，然后不断地获取信息。。。。


        try {
            System.out.println("下面是从socket再次获得输入流");

            if (playersocket.getKeepAlive()==false){
                System.out.println("socket已经断开了");
            }


            InputStream is = playersocket.getInputStream();
            System.out.println("上面是从socket再次获得输入流");



            while (true){

                byte[] buffer=new byte[200];

                int length= is.read(buffer);

                String word=new String(buffer,0,length);

                System.out.println(word);//这里能不能正确输出到控制台

            }



        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
