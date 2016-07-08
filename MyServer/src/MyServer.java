import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/7/8.
 */

public class MyServer {
    public static ArrayList<OnlinePlayersEntity> onlinePlayersEntitylist=new ArrayList<OnlinePlayersEntity>();//维护一个在线用户列表


    public static void main(String[] args)throws Exception
    {
        int port=5001;

        //创建线程池。用于玩家连入后（创建玩家在线实体和玩家通信进程）
        ThreadPoolExecutor executorinformationPool = new ThreadPoolExecutor(5,10,200,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));


        //（需要和玩家连接的socket，得到流，循环接收方可）用于接收来自客户端的信息，
        //向客户端发送信息则不用多线程

        //服务器端启动控制子线程，用于服务器维护方便
        ServerConsole serverConsole=new ServerConsole();
        executorinformationPool.submit(serverConsole);


        ServerSocket ss=new ServerSocket(port); //这里写port Integer.parseInt(args[0])

        while(true) //等待连接
        {
            Socket socket=ss.accept();


            int linkresult =MyServer.linketoServer(socket);


            if(linkresult==Status.LinkStatus.LinkisOk) {

                //生成新的玩家实体
                OnlinePlayersEntity newplayersentity = new OnlinePlayersEntity(Status.PlayersStatus.isOnline, new PlayersEntity("tom", 1), socket);
                onlinePlayersEntitylist.add(newplayersentity);


                //有了玩家实体，创建新的玩家通信子进程
                PlayersInformationInput playerinput = new PlayersInformationInput(newplayersentity);
                Future future = executorinformationPool.submit(playerinput);



                newplayersentity.setPlayersinformationInput(future);

            }



        }




    }

    public static int linketoServer(Socket socket) throws IOException {
        //先收到一个连接，然后进行解析
        InputStream login= null;
        String words[]=null;
        try {

            login = socket.getInputStream();
            byte[] buffer=new byte[200];
            int length=login.read(buffer);//这里也会阻塞，等待流中的数据写入
            String word=new String(buffer,0,length);

            words=word.split("#"); //words[2]是name

        } catch (IOException e) {
            e.printStackTrace();
        }finally {


        }


        //返回给用户结果
        if(   canLoad(words[2])  ) //判断数据库等
        {
            OutputStream os = socket.getOutputStream(); //这里socket已经关了。。
            os.write("#1#".getBytes());
            os.close();
        }


        return Status.LinkStatus.LinkisOk;
    }


    //根据数据库，检查此用户是否可以登录，或者断线重连机制？
    public static boolean canLoad(String name) //暂时还只是一个name
    {
        return true;
    }


}
