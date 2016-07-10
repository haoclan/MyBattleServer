package src.Utill.Information;

import src.Entity.OnlinePlayersEntity;
import src.Utill.MyServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/7/8.
 */

public class PlayersInformationInput implements Runnable
{
    //需要socket
    //可能需要调用其他东西比如因此来个玩家
    public Socket playersocket;//为了方便单独把socket拿出来。
    public OnlinePlayersEntity playersEntity;//当前玩家的所有信息类
    public Object SynObject=null; //游戏对战时候需要和游戏进程同步


    //构造方法
    public PlayersInformationInput(OnlinePlayersEntity playersEntity){

        playersocket = playersEntity.socketToPlayer;
        this.playersEntity=playersEntity;
    }

    public void setSynObject(Object synObject) {
        SynObject = synObject;
    }

    @Override
    public void run() {
      //获得输入流，然后不断地获取信息。。。。

        //最好把下面的信息类别使用其他类来解析
        //信息解析类,

        try {

            InputStream is = playersocket.getInputStream();//  返回此套接字的输入流。因此是一个吧

            String[] words;

            while (true){

                byte[] buffer=new byte[200];

                int length= is.read(buffer);
                String word=new String(buffer,0,length);// 这里直接将字节转为String解析是不合理的,应该用protobuff


                System.out.println(word);//这里能不能正确输出到控制台

                //加入退出功能 #exit#yourname#
                words =word.split("#");

                if (words.length==3)//尾巴貌似不算。。 前面空格是算的
                {
                    if (words[1].equals("exit"))
                    {
                        String exitname=words[2];//通过名字来退出的

                        //现在使用list去保存的，之后应该改用map吧
                        for (Iterator iterator = MyServer.onlinePlayersEntitylist.iterator(); iterator.hasNext();)
                        {
                            OnlinePlayersEntity playersEntity =  (OnlinePlayersEntity)iterator.next();
                            if ( playersEntity.playerotherinformation.playersName.equals(exitname))
                            {
                                //做一些逻辑处理，比如讲玩家的游戏信息写回数据库等等

                                MyServer.onlinePlayersEntitylist.remove(playersEntity);//这样删除这个节点

                                if(!playersocket.isClosed()) {
                                    playersocket.close(); //这里关闭是不是对方已经关闭了？
                                    break;//跳出for循环
                                }
                            }

                        }

                        /*
                        //把当前的消息输入线程也要关闭
                        Thread.currentThread().interrupt();//中断此线程
                        //这句指令后竟然又来了一次socket的消息。
                        */
                        return;

                    }//if退出指令
                }//这是指令


            }



        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
