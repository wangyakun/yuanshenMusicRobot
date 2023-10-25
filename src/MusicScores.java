import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/12 - 10 - 12 - 11:33
 * @Description:
 * file:
 * {setting=xxx}
 * {default_play=false}
 *
 * [马戏团]
 * {play=true}
 * # 主旋律
 * 5671...
 * # 伴奏
 * 1515...
 *
 * in one score:  +-  /._[/*]   ()  {shift8}
 * @version: 1.0
 */
public class MusicScores {
    static class MusicScore {
        String name;
        HashMap<String, String> settings = new HashMap<>();
        ArrayList<String> score_parts = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    HashMap<String, String> global_settings = new HashMap<>();
    ArrayList<MusicScore> scores = new ArrayList<>();
}
