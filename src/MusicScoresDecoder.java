import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/23 - 10 - 23 - 23:00
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class MusicScoresDecoder {
    private String musicScoreFilePath = "resource/musicscores.txt";

    private ArrayList<String> getMusicScoresFromFile() {
        ArrayList<String> content = new ArrayList<>();
        final String CHARSET_NAME = "UTF-8";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(musicScoreFilePath), Charset.forName(CHARSET_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    content.add(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public MusicScores decodeFromFile() {
        ArrayList<String> content = getMusicScoresFromFile();
        MusicScores musicScores = new MusicScores();
        ListIterator<String> iterator = content.listIterator();
        //decode global settings
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.charAt(0) == '{' && line.charAt(line.length() - 1) == '}') {
                decodeLineToSetting(line, musicScores.global_settings);
            } else {
                iterator.previous();
                break;
            }
        }
        //decode one music
        MusicScores.MusicScore currentScore = new MusicScores.MusicScore();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.charAt(0) == '[' && line.charAt(line.length() - 1) == ']') {
                currentScore = new MusicScores.MusicScore();
                musicScores.scores.add(currentScore);
                currentScore.setName(line.substring(1, line.length() - 1));
            } else if(line.charAt(0) == '{' && line.charAt(line.length() - 1) == '}') {
                if (line.equals("{+8}") || line.equals("{-8}")) {
                    currentScore.score_parts.add(line);
                } else {
                    decodeLineToSetting(line, currentScore.settings);
                }
            } else if(line.charAt(0) == '#') {
                currentScore.score_parts.add("");
            } else {
                if (currentScore.score_parts.isEmpty()) {
                    currentScore.score_parts.add("");
                }
                currentScore.score_parts.set(currentScore.score_parts.size() - 1, currentScore.score_parts.get(currentScore.score_parts.size() - 1) + line);
            }
        }
        return musicScores;
    }

    private void decodeLineToSetting(String line, HashMap<String, String> settingsMap) {
        line = line.substring(1, line.length() - 1);
        String[] settingkv = line.split("=");
        settingsMap.put(settingkv[0].trim(), settingkv[1].trim());
    }

    public static void main(String[] args) {
        MusicScoresDecoder musicScoresDecoder = new MusicScoresDecoder();
        MusicScores musicScores = musicScoresDecoder.decodeFromFile();
        System.out.println(musicScores);
    }

}
