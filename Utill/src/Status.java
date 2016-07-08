/**
 * Created by Administrator on 2016/7/8.
 */
public class Status {

    public static class PlayersStatus{
        public static final int isOnline=1;
        public static final int isMatching =2;
        public static final int isBattling=3;
        public static final int isOutline=4;
    }

    public static class MatchResult{
        public static final int MatchIsOk=1;
        public static final int MatchIsNotOk=2;
        public static final int MatchOut=3;

    }

    //这是用户连接服务器时候验证返回状态
    public static class LinkStatus{
        public static final int LinkisOk=1;
        public static final int LinkUseIdisError=2;
        public static final int LinkPassWordisError=3;
        public static final int LinkOtherError=4;
    }

    //收到的玩家信息的大体类型
    public static class InformationType{
        public static final int login=1;
        public static final int exit=2;
        public static final int startMatch=3;
        public static final int exitMatch=4;
        public static final int gameInformation=5;
    }

    //游戏中玩家的状态
    public static class PlayerInGameStatus{
        public static final int ;
    }

}
