import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean single_mod = true;
        if (single_mod) {
            System.out.println("进入单曲模式，播放1首曲子后退出程序");
            Player.getInstance().setStartDalay(2000);
            Player.getInstance().playMusic(600, MusicScore.Pal_DieLian, MusicScore.Pal_DieLian_low);
        } else {
            System.out.println("进入热键模式， --f5：开始/停止播放， --f11：退出程序");
            HotKey.getInstance();
        }
//        Player player = Player.getInstance();
//        player.playMusic(MusicScore.score1_HuanLeSong);
//        player.playMusic(MusicScore.score2_pal4);
//        player.playMusic(500, MusicScore.score2_pal4, MusicScore.score2_pal4_low);
//        player.playMusic(250, MusicScore.score3_osusanna, MusicScore.score3_osusanna_low);
//        player.playMusic(400, MusicScore.saierda_kasiwa, MusicScore.saierda_kasiwa_low);
//        player.playMusic(300, MusicScore.SheXiangFuRen, MusicScore.SheXiangFuRen_low);
//        player.playMusic(500, MusicScore.MengYa, MusicScore.MengYa_low);
//        player.playMusic(500, MusicScore.GeChangZuGuo);
    }
}
