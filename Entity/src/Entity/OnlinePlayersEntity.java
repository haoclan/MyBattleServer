package src.Entity;

import src.Utill.Information.PlayersInformationInput;

import java.net.Socket;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/7/8.
 */

public class OnlinePlayersEntity {

    public int playerstatus; //
    public PlayersEntity playerotherinformation;//更多玩家持久化信息

    public Future playersinformationInputFuture=null;//玩家信息输入子线程的查询future
    public PlayersInformationInput playersInformationInput;

    public Socket socketToPlayer;//为了方便


    //玩家通信进程的引用，通信进程根本不听话的。它启动后就自己玩了，里面有逻辑会调用其他东西。
    //使用Future可以控下吧


    public OnlinePlayersEntity(int playerstatus, PlayersEntity playersEntity,Socket socket) {
        this.playerstatus = playerstatus;
        this.playerotherinformation = playersEntity;
        this.socketToPlayer=socket;
    }

    public void setPlayersinformationInputFuture(Future playersinformationInput) {
        this.playersinformationInputFuture = playersinformationInput;
    }

    public void setPlayersInformationInput(PlayersInformationInput playersInformationInput) {
        this.playersInformationInput = playersInformationInput;
    }
}
