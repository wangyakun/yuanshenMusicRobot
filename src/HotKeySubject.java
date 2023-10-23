import java.util.LinkedList;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/23 - 10 - 23 - 17:38
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public abstract class HotKeySubject {
    private LinkedList<HotKeyObverser> obversers = new LinkedList<>();
    public void attach(HotKeyObverser obverser) {
        obversers.add(obverser);
    }
    public void detach(HotKeyObverser obverser) {
        obversers.remove(obverser);
    }
    public void notifyObservers(int KeyCode) {
        for (HotKeyObverser obverser : obversers) {
            obverser.update(KeyCode);
        }
    }
}

interface HotKeyObverser {
    void update(int keyCode);
}