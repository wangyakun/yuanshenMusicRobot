import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player implements HotKeyObverser{
    private static Player INSTANCE = new Player();
    public static Player getInstance() {
        return INSTANCE;
    }
    private Player() {
        System.out.println("Player()");
    }

    MusicScoresDecoder musicScoresDecoder = new MusicScoresDecoder();
    KeyboardControler keyboardControler = new KeyboardControler();
    private volatile boolean playing = false;
    private boolean shouldStop = false;

    public void stop() {
        shouldStop = true;
    }

    public void play() throws Exception {
        MusicScores musicScores = musicScoresDecoder.decodeFromFile();
        String play_mod = musicScores.global_settings.getOrDefault("play_mode", "single");
        if (play_mod.equals("single")) {
            System.out.println("进入单曲模式，播放1首曲子后退出程序");
            playMusicFromFile();
        } else if (play_mod.equals("hotkey")) {
            System.out.println("进入热键模式， --f5：开始/停止播放， --f11：退出程序");
            HotKey.getInstance().attach(Player.getInstance());
            HotKey.getInstance().attach(ExitHotKeyHandler.getInstance());
        }
    }

    public void playMusicFromFile() throws Exception {
        synchronized (INSTANCE) {
            playing = true;
            System.out.println("播放开始");
            MusicScores musicScores = musicScoresDecoder.decodeFromFile();
            int startDelay = Integer.parseInt(musicScores.global_settings.getOrDefault("start_delay", "2000"));
            if (startDelay > 0) {
                keyboardControler.KeyboardControl(startDelay);
            }
            for (MusicScores.MusicScore score : musicScores.scores) {
                if (musicScores.global_settings.getOrDefault("default_play", "true").equals("true")
                        && !score.settings.getOrDefault("play", "unset").equals("false")
                        || score.settings.getOrDefault("play", "unset").equals("true")) {
                    int oneBeatMS = Integer.parseInt(score.settings.getOrDefault("speed", "500"));
                    char key = score.settings.getOrDefault("1", "C").charAt(0);
                    int originOffset = Integer.parseInt(score.settings.getOrDefault("all", "0"));
                    System.out.println("正在播放：" + score.getName());
                    ArrayList<OneTimeNote> merged_notes = null;
                    for (String score_part : score.score_parts) {
                        ArrayList<OneTimeNote> notes = decodeScore(score_part, key, originOffset);
                        if (merged_notes == null) {
                            merged_notes = notes;
                        } else {
                            merged_notes = merge(merged_notes, notes);
                        }
                    }
                    if (merged_notes != null) {
                        for (OneTimeNote oneTimeNote : merged_notes) {
                            if (shouldStop) {
                                shouldStop = false;
                                break;
                            }
                            PlayOne(oneTimeNote, oneBeatMS);
                        }
                    }
                }
            }
            System.out.println("播放结束");
            playing = false;
        }
    }

    public void PlayOne(OneTimeNote oneTimeNote, int oneBeatMS) throws Exception {
//        System.out.println(oneTimeNote.toString());
        int delay = (int)(oneBeatMS * oneTimeNote.times_beat);
        keyboardControler.KeyboardControl(oneTimeNote.getKeys(), delay);
    }

    OneMusicScoreDecoder oneMusicScoreDecoder = new OneMusicScoreDecoder();
    private ArrayList<OneTimeNote> decodeScore(String musicScore, char key, int originOffset) throws Exception {
        oneMusicScoreDecoder.decodeInit();
        oneMusicScoreDecoder.setKey(key);
        oneMusicScoreDecoder.setOriginOffset(originOffset);
        return oneMusicScoreDecoder.decode(musicScore);
    }

    private ArrayList<OneTimeNote> merge(ArrayList<OneTimeNote> notes1, ArrayList<OneTimeNote> notes2) {
        ArrayList<OneTimeNote> out = new ArrayList<>();
        double timeline1 = 0;
        double timeline2 = 0;
        int index1 = 0;
        int index2 = 0;
        while (index1 < notes1.size() && index2 < notes2.size()) {
            if (Math.abs(timeline1 - timeline2) < 0.000001) { //处理因double引起的精度丢失问题。非最佳处理方式
                timeline2 = timeline1;
            }
            if (timeline1 < timeline2) {
                mergeOneNote(out, notes1, timeline1, timeline2, index1);
                timeline1 += notes1.get(index1).times_beat;
                index1++;
            } else if (timeline2 < timeline1) {
                mergeOneNote(out, notes2, timeline2, timeline1, index2);
                timeline2 += notes2.get(index2).times_beat;
                index2++;
            } else { //==
                OneTimeNote note1 = notes1.get(index1);
                OneTimeNote note2 = notes2.get(index2);
                OneTimeNote newNote = new OneTimeNote(note1);
                newNote.addNotes(note2.getNotes());
                newNote.times_beat = Math.min(note1.times_beat, note2.times_beat);
                out.add(newNote);
                timeline1 += note1.times_beat;
                index1++;
                timeline2 += note2.times_beat;
                index2++;
            }
        }

        while (index1 < notes1.size()) {
            out.add(new OneTimeNote(notes1.get(index1)));
            index1++;
        }

        while (index2 < notes2.size()) {
            out.add(new OneTimeNote(notes2.get(index2)));
            index2++;
        }

        return out;
    }

    private void mergeOneNote(ArrayList<OneTimeNote> out, ArrayList<OneTimeNote> smallerNotes1, double smallerTimeline, double biggerTimeline, int smallerIndex1) {
        OneTimeNote smallerNote = smallerNotes1.get(smallerIndex1);
        OneTimeNote newNote = new OneTimeNote(smallerNote);
        if (smallerTimeline + smallerNote.times_beat > biggerTimeline) {
            newNote.times_beat = biggerTimeline - smallerTimeline;
        }
        out.add(newNote);
    }

    @Override
    public void update(int keyCode) {
        if (keyCode == KeyEvent.VK_F5) {
            if (playing) {
                stop();
            } else {
                new Thread(() -> {
                    try {
                        playMusicFromFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}