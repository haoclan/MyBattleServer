package src.Entity;

/**
 * Created by Administrator on 2016/7/8.
 */

public class PlayersEntity {
    public String playersName;
    public int playersId;
    //more information ... 持久化的信息


    public PlayersEntity(String playersName,int playersId) {
        this.playersName = playersName;
        this.playersId= playersId;
        //以及不拉不拉
    }
}
