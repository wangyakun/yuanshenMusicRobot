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
}
