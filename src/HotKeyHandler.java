import java.awt.event.KeyEvent;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/21 - 10 - 21 - 22:47
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class HotKeyHandler {
    public boolean handle(int markCode) {
        boolean handled = true;
        if (markCode == KeyEvent.VK_F5) {
            System.out.println("检测到按下F5。");
            System.out.println(Player.getInstance().hashCode());
            if (Player.getInstance().isPlaying()) {
                System.out.println("--停止播放。");
                Player.getInstance().stop();
            } else {
                System.out.println("--开始播放。");
                new Thread(() -> {
                    try {
                        Player.getInstance().playMusic(600, MusicScore.Pal_DieLian, MusicScore.Pal_DieLian_low);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } else if (markCode == KeyEvent.VK_F11) {
            HotKey.getInstance().cleanUp();
            System.out.println("检测到按下F11，程序退出。");
            System.exit(0);
        }
        return handled;
    }

    public void registerHotKeys(HotKey hotKey) {
        hotKey.registerHotKey(KeyEvent.VK_F5);
        hotKey.registerHotKey(KeyEvent.VK_F11);
    }
}
