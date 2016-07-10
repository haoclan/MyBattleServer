package src.Entity;

/**
 * Created by Administrator on 2016/7/8.
 */

//用于第一次连接后返回的信息一个是,是否可以登录的状态,一个是玩家信息实体
public class NewPlayersEntity
{
    public int newStatus;
    public PlayersEntity playersEntity=null;

    public NewPlayersEntity(int newStatus) {
        this.newStatus = newStatus;

    }

    public void setPlayersEntity(PlayersEntity playersEntity) {
        this.playersEntity = playersEntity;
    }
}
