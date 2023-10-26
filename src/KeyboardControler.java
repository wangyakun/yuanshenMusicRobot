import java.awt.*;
public class KeyboardControler {
    static Robot singer;

    static {
        try {
            singer = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    // 以下代码实现同时按下多个键，最多为同时按下3个

    public void KeyboardControl(int delay) throws AWTException, InterruptedException {
        synchronized (singer) {
            singer.wait(delay);
        }
    }

    public void KeyboardControl(String keys, int delay) throws AWTException, InterruptedException {
        for (int i = 0; i < keys.length(); i++) {
            singer.keyPress(keys.charAt(i));
        }
        for (int i = 0; i < keys.length(); i++) {
            singer.keyRelease(keys.charAt(i));
        }
        synchronized (singer) {
            singer.wait(delay);
        }
    }

    public void KeyboardControl(char a, int delay) throws AWTException, InterruptedException {
        singer.keyPress(a);
        singer.keyRelease(a);
        synchronized (singer) {
            singer.wait(delay);
        }
    }

    public void KeyboardControl(char a, char b, int delay) throws AWTException, InterruptedException {
        singer.keyPress(a);
        singer.keyPress(b);
        singer.keyRelease(a);
        singer.keyRelease(b);
        synchronized (singer) {
            singer.wait(delay);
        }
    }

    public void KeyboardControl(char a, char b, char c, int delay) throws AWTException, InterruptedException {
        singer.keyPress(a);
        singer.keyPress(b);
        singer.keyPress(c);
        singer.keyRelease(a);
        singer.keyRelease(b);
        singer.keyRelease(c);
        synchronized (singer) {
            singer.wait(delay);
        }
    }

    public static void main(String[] args) throws AWTException {
        pressMultiKey(1);
        pressMultiKey(2);
        pressMultiKey(3);
        pressMultiKey(4);
        pressMultiKey(5);
        pressMultiKey(6);
        pressMultiKey(7);
        pressMultiKey(8);
        pressMultiKey(9);
        pressMultiKey(10);
        pressMultiKey(11);
        pressMultiKey(12);
        pressMultiKey(13);
        pressMultiKey(14);
        pressMultiKey(15);
        pressMultiKey(16);
        pressMultiKey(17);
        pressMultiKey(18);
        pressMultiKey(19);
        pressMultiKey(20);
        pressMultiKey(21);
    }

    private static void pressMultiKey(int nKeys) throws AWTException {
        Robot robot = new Robot();
        robot.delay(1000);
        String keyboard = "QWERTYUASDFGHJZXCVBNM";
        for (int i = 0; i < nKeys; i++) {
            robot.keyPress(keyboard.charAt(i));
        }
//        robot.delay(2000);
        for (int i = 0; i < nKeys; i++) {
            robot.keyRelease(keyboard.charAt(i));
        }
    }
}
