package src.Battle.Entity;

/**
 * Created by lion on 16/7/10.
 */
public class GameConfigEntity {

    GameMapEntity map=null;
    int otherConfig;

    public GameConfigEntity(GameMapEntity map, int otherConfig) {
        this.map = map;
        this.otherConfig = otherConfig;
    }
    public GameConfigEntity()
    {

    }
}
