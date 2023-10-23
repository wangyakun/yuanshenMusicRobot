import java.awt.event.KeyEvent;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/21 - 10 - 21 - 22:47
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class ExitHotKeyHandler implements HotKeyObverser{
    private static ExitHotKeyHandler INSTANCE = new ExitHotKeyHandler();
    public static ExitHotKeyHandler getInstance() {
        return INSTANCE;
    }
    private ExitHotKeyHandler() {
        System.out.println("ExitHotKeyHandler()");
    }

    @Override
    public void update(int keyCode) {
        if (keyCode == KeyEvent.VK_F11) {
            HotKey.getInstance().cleanUp();
            System.out.println("程序退出");
            System.exit(0);
        }
    }
}
