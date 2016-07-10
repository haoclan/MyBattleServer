package src.Utill;

import src.Entity.NewPlayersEntity;
import src.Entity.OnlinePlayersEntity;
import src.Entity.PlayersEntity;
import src.Utill.Information.PlayersInformationInput;

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
        int port=5000;

        //创建线程池。用于玩家连入后（创建玩家在线实体和玩家通信进程）
        ThreadPoolExecutor executorinformationPool = new ThreadPoolExecutor(10,20,200,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));


        //向客户端发送信息则不用多线程

        //服务器端启动控制子线程，用于服务器维护方便
        ServerConsole serverConsole=new ServerConsole();
        executorinformationPool.submit(serverConsole);


        ServerSocket ss=new ServerSocket(port); //这里写port Integer.parseInt(args[0])

        while(true) //等待连接
        {
            Socket socket=ss.accept();


            //这里直接让这个函数返回一个结构new玩家实体
            NewPlayersEntity linkresult =MyServer.linketoServer(socket);


            if(linkresult.newStatus==Status.LinkStatus.LinkisOk) {

                //生成新的玩家在线实体
                OnlinePlayersEntity newplayersentity = new OnlinePlayersEntity(Status.PlayersStatus.isOnline,linkresult.playersEntity, socket);
                onlinePlayersEntitylist.add(newplayersentity);



                //有了玩家实体，创建新的玩家通信子进程
                PlayersInformationInput playerinput = new PlayersInformationInput(newplayersentity);
                //提交到进程池里进行执行
                Future future = executorinformationPool.submit(playerinput);


                newplayersentity.setPlayersinformationInput(future);


            }



        }


    }

    //收到一个socket的连接后,进行验证,然后返回一个new
    public static NewPlayersEntity linketoServer(Socket socket) throws IOException {
        //先收到一个连接，然后进行解析
        InputStream login= null;
        String words[]=null;

        try {


            login = socket.getInputStream();
            byte[] buffer=new byte[200];
            int length=login.read(buffer);//这里也会阻塞，等待流中的数据写入
            String word=new String(buffer,0,length);
            System.out.println(word);

            words=word.split("#"); //words[2]是name

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }


        //经过数据库验证判断等
        if(   canLoad(words[2])  ) //判断数据库等
        {
            OutputStream os = socket.getOutputStream();
            os.write("#1#".getBytes());
           // os.close();  //貌似是因为这里 就把socket关闭了。。。。
        }


        //经过逻辑判断，返回正确的类的对象
        NewPlayersEntity result=new NewPlayersEntity(Status.LinkStatus.LinkisOk);
        result.setPlayersEntity(new PlayersEntity(words[2],1));
        return  result;

    }


    //根据数据库，检查此用户是否可以登录，或者断线重连机制？
    public static boolean canLoad(String name) //暂时还只是一个name
    {
        return true;
    }


}
