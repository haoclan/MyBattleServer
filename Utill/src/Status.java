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

    public static class LinkStatus{
        public static final int LinkisOk=1;
        public static final int LinkUseIdisError=2;
        public static final int LinkPassWordisError=3;
        public static final int LinkOtherError=4;

    }


}
