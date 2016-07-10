package src.Entity;

/**
 * Created by Administrator on 2016/7/8.
 */
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
