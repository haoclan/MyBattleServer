/**
 * Created by Administrator on 2016/7/8.
 */
public class NewPlayersEntity
{
    int newStatus;
    PlayersEntity playersEntity=null;

    public NewPlayersEntity(int newStatus) {
        this.newStatus = newStatus;

    }

    public void setPlayersEntity(PlayersEntity playersEntity) {
        this.playersEntity = playersEntity;
    }
}
