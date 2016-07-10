package src.Utill.Information;

import src.Battle.Match.MatchHandle;
import src.Battle.OneBattleProcess;
import src.Entity.OnlinePlayersEntity;
import src.Utill.MyServer;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/7/8.
 */


public class PlayersInformationParse //解析返回返回类型和数据格式化？
{

    public static class InformationParse{

        public static void parse(byte[] bytes,int length) throws IOException {
            String word=new String(bytes,0,length);
            System.out.println(word);

            //解析到信息是匹配
            String[] words =word.split(":");
            String[] contents=words[1].split("#");
            String[] names=words[0].split("@");

            if(contents[0].equals("match")){

                System.out.println(names[0]+"要加入匹配");
                //根据名字找到OnlinePlayersEntity;
                //下面这个用个map改进下吧id和实体
                OnlinePlayersEntity player1=null;
                 for(Iterator iterator = MyServer.onlinePlayersEntitylist.iterator();iterator.hasNext();){
                     OnlinePlayersEntity temp=(OnlinePlayersEntity)iterator.next();
                    if (temp.playerotherinformation.playersName.equals(names[0])){
                        player1=temp;
                        break;
                    }
                 }
                //调用match类 传入palyer1
                MatchHandle.match(player1);

            }//解析到是匹配
            else if(contents[0].equals("gameinformation"))//游戏数据包
            {
                //传入专门解析游戏数据包的类contents[1],由OneBattleProcess计算,再发送结果到2个玩家
                //通过玩家找到它所在的OneBattleProcess对象
                GameInformationParse.parse(contents[1],(OneBattleProcess) MatchHandle.playerNameToOneBattleProcess.get(names[0]) );


            }


        }
    }

    public static class GameInformationParse{
        //解析后进行游戏逻辑处理，然后返回信息
        public static void parse(Object object, OneBattleProcess oneBattle)
        {
            //解析并调用游戏沙盘对象
            synchronized (oneBattle.oneGameLock) {
                System.out.println(object.toString());

                //由于object的信息,改变了oneBattle的 瞬间状态,从而需要通知那个进程
                oneBattle.update(object);

                System.out.println("通知的游戏线程的锁对象" + oneBattle.oneGameLock.hashCode());
                oneBattle.oneGameLock.notify(); //通知游戏线程干活。//这里必须首先在同步块里
            }


        }
    }

}
