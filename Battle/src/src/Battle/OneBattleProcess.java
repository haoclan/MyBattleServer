package src.Battle;

import src.Battle.Entity.GameConfigEntity;
import src.Battle.Entity.GameMapEntity;
import src.Battle.Entity.GameResultEntity;
import src.Battle.Entity.InGameMomentStatusEntity;
import src.Entity.OnlinePlayersEntity;

import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/7/8.
 */

public class OneBattleProcess implements Callable  //实际上差不多吧
{
    public GameConfigEntity gameconfig=null; //游戏配置
    public InGameMomentStatusEntity inGameMomentStatusEntity=null; //游戏时刻状态
    public Object oneGameLock=new Object(); //同步锁 另外当玩家进入游戏后,通信线程里也会有锁了

    public OnlinePlayersEntity player1;
    public OnlinePlayersEntity player2;
    public int isOverFlag=0;

    public Object changeStatus= new Object();


    public OneBattleProcess(OnlinePlayersEntity player1,OnlinePlayersEntity player2,GameConfigEntity gameconfig)
    {
        this.player1=player1;
        this.player2=player2;
        this.gameconfig=gameconfig;
        inGameMomentStatusEntity=new InGameMomentStatusEntity();//游戏状态初始化。
    }

    public void update(Object object){
        changeStatus = object;
    }

    public void logiCompute()
    {
        //根据changeStatus
        System.out.println("进行了逻辑计算");

    }

    @Override
    public GameResultEntity call() throws Exception {
    //线程启动了
        while(true){


            synchronized (this.oneGameLock){
                System.out.println("oneGame进程启动了,且获得了锁哦");

                //逻辑处理
                logiCompute();
                //因为此时游戏状态已经被改变了

                //消息返回,通过socket
                OutputStream os1 = player1.socketToPlayer.getOutputStream();
                OutputStream os2 = player2.socketToPlayer.getOutputStream();
                os1.write("服务器发来的游戏信息".getBytes());
                os2.write("服务器发来的游戏信息".getBytes());

                //如果判定游戏结束,则break;


                //等待被唤醒再次执行
                System.out.println("等待的游戏线程的锁对象"+oneGameLock.hashCode());
                oneGameLock.wait();

            }
            if (isOverFlag==1)
            {
                break;
            }
        }

        //当判断后就返回这里了
        GameResultEntity result = new GameResultEntity();
        return result;
    }







}
