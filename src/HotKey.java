import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/20 - 10 - 20 - 11:50
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class HotKey extends HotKeySubject {
    private static HotKey INSTANCE = new HotKey();
    public static HotKey getInstance() {
        return INSTANCE;
    }

    private HotKey() {
        System.out.println("HotKey()");

        registerHotKey(KeyEvent.VK_F5);
        registerHotKey(KeyEvent.VK_F11);
        //第二步：添加热键监听器
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
            @Override
            public void onHotKey(int markCode) {
                System.out.println("检测到按下" + markCode + "。");
                notifyObservers(markCode);
            }
        });
    }

    public void registerHotKey(int keyCode) {
        //第一步：注册热键，第一个参数表示该热键的标识，第二个参数表示组合键，如果没有则为0，第三个参数为定义的主要热键
        JIntellitype.getInstance().registerHotKey(keyCode, 0, keyCode);
    }

    public void cleanUp() {
        JIntellitype.getInstance().cleanUp();
    }
}

