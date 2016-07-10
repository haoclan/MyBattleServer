package src.Battle.Match;

import src.Battle.Entity.GameConfigEntity;
import src.Battle.OneBattleProcess;
import src.Entity.OnlinePlayersEntity;
import src.Entity.PlayersEntity;
import src.Utill.MyServer;
import src.Utill.Status;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/8.
 */

//匹配这进程是一开始就存在的
public class MatchHandle {
    public static ArrayList<OnlinePlayersEntity> matchingArray=new ArrayList<OnlinePlayersEntity>(); //正在匹配的玩家列表
    public static ArrayList<OnlinePlayersEntity> inGameArray=new ArrayList<OnlinePlayersEntity>(); //已经在对战的玩家列表,对战状态
    //貌似没用上面这个

    public static ArrayList<OneBattleProcess> BattlesArrat;//存在的战斗记录
    public static HashMap playerNameToOneBattleProcess=new HashMap();

    //在MyServer初始化一次就OK
    public MatchHandle()
    {
        matchingArray=new ArrayList<OnlinePlayersEntity>();
        inGameArray=new ArrayList<OnlinePlayersEntity>();

    }



    public static int match(OnlinePlayersEntity player) throws IOException  //这个匹配的逻辑不应该让玩家的通信进程执行。而是，每次匹配队列改变后，自动去执行这个go。
    {
        //进行匹配处理
        matchingArray.add(player);

        if (matchingArray.size()==1)//为什么没有到这里啊?
        {
            //告诉它,正在匹配,等着呗
            OutputStream os= player.socketToPlayer.getOutputStream();
            os.write("正在匹配".getBytes());

            return Status.MatchResult.MatchIsNotOk;

        }else{

            //匹配到了,加载游戏
            startGame(matchingArray.get(0),matchingArray.get(1),new GameConfigEntity());//游戏配置信息,包括地图



            return Status.MatchResult.MatchIsOk; //启动成功
        }







    }

    public static void startGame(OnlinePlayersEntity player1, OnlinePlayersEntity player2, GameConfigEntity config){
        //启动对战新线程 因此需要线程

        OneBattleProcess onebattle = new OneBattleProcess(player1,player2,new GameConfigEntity());
        //onebattle的锁赋予2个的通信进程
        player1.playersInformationInput.setlock(onebattle.oneGameLock);
        player2.playersInformationInput.setlock(onebattle.oneGameLock);

        playerNameToOneBattleProcess.put(player1.playerotherinformation.playersName,onebattle);
        playerNameToOneBattleProcess.put(player2.playerotherinformation.playersName,onebattle);


        MyServer.executorPool.submit(onebattle);
        //启动了游戏进程了


    }


}
