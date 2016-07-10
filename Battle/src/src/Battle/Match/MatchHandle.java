package src.Battle.Match;

import src.Entity.OnlinePlayersEntity;
import src.Entity.PlayersEntity;
import src.Utill.Status;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/8.
 */

public class MatchHandle {
    public static ArrayList<OnlinePlayersEntity> array;


    public MatchHandle()
    {
        array=new ArrayList<OnlinePlayersEntity>();
    }


    //要匹配的时候加入到这里
    public void addtoArray(OnlinePlayersEntity player)
    {
        array.add(player);
    }





    public int match()  //这个匹配的逻辑不应该让玩家的通信进程执行。而是，每次匹配队列改变后，自动去执行这个go。
    {
        //进行匹配处理

        //若匹配到了

        //启动对战新线程 因此需要线程


        return Status.MatchResult.MatchIsOk; //启动成功
    }


}
