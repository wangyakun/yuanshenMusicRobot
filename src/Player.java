import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
    int oneBeatMS = 500; // speed
    int startDalay = 2000;
    KeyboardControler keyboardControler = new KeyboardControler();
    ArrayList<OneTimeNote> notes;

    Player() {
    }

    Player(int startDalay) {
        this.startDalay = startDalay;
    }


    public void playMusic(String musicScore) throws Exception {
        keyboardControler.KeyboardControl(startDalay);
        decode(musicScore);
        for (OneTimeNote oneTimeNote : notes) {
            PlayOne(oneTimeNote);
        }
    }
    public void playMusic(int speed, String musicScore) throws Exception {
        oneBeatMS = speed;
        playMusic(musicScore);
    }

    public void playMusic(String musicScore1, String musicScore2) throws Exception {
        keyboardControler.KeyboardControl(startDalay);
        decode(musicScore1, musicScore2);
        for (OneTimeNote oneTimeNote : notes) {
            PlayOne(oneTimeNote);
        }
    }
    public void playMusic(int speed, String musicScore1, String musicScore2) throws Exception {
        oneBeatMS = speed;
        playMusic(musicScore1, musicScore2);
    }

    public void PlayOne(OneTimeNote oneTimeNote) throws Exception {
        judgeAndDoStop();

        int delay = (int)(oneBeatMS * oneTimeNote.times_beat);
        switch (oneTimeNote.nNotes) {
            case 1:
                char note = OneTimeNote.noteToKey(oneTimeNote.notes[0]);
                if (note == '0') {
                    keyboardControler.KeyboardControl(delay);
                } else {
                    keyboardControler.KeyboardControl(note, delay);
                }
                break;
            case 2: {
                char note1 = OneTimeNote.noteToKey(oneTimeNote.notes[0]);
                char note2 = OneTimeNote.noteToKey(oneTimeNote.notes[1]);
                keyboardControler.KeyboardControl(note1, note2, delay);
            }
                break;
            case 3: {
                char note1 = OneTimeNote.noteToKey(oneTimeNote.notes[0]);
                char note2 = OneTimeNote.noteToKey(oneTimeNote.notes[1]);
                char note3 = OneTimeNote.noteToKey(oneTimeNote.notes[2]);
                keyboardControler.KeyboardControl(note1, note2, note3, delay);
            }
                break;
            default:
                try {
                    throw new Exception("nNotes is " + oneTimeNote.nNotes + ", can't paly");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private void judgeAndDoStop() throws Exception {
        if (HotKey.getInstance().isNewKeyPressed(KeyEvent.VK_F5)) {
            HotKey.getInstance().cleanUp();
            System.out.println("检测到按下F5，直接结束。");
            System.exit(0);
        }
    }

    Decoder decoder = new Decoder();
    private void decode(String musicScore) throws Exception {
        notes = new ArrayList<>();
        decoder.decode(musicScore, notes);
    }

    private void decode(String musicScore1, String musicScore2) throws Exception {
        notes = new ArrayList<>();
        ArrayList<OneTimeNote> notes_high = new ArrayList<>();
        decoder.decode(musicScore1, notes_high);
        ArrayList<OneTimeNote> notes_low = new ArrayList<>();
        decoder.decode(musicScore2, notes_low);
        merge(notes, notes_high, notes_low);
    }

    private void merge(ArrayList<OneTimeNote> out, ArrayList<OneTimeNote> notes1, ArrayList<OneTimeNote> notes2) {
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
                for (int i = 0; i < note2.nNotes; i++) {
                    newNote.addKey(note2.notes[i]);
                }
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
    }

    private void mergeOneNote(ArrayList<OneTimeNote> out, ArrayList<OneTimeNote> smallerNotes1, double smallerTimeline, double biggerTimeline, int smallerIndex1) {
        OneTimeNote smallerNote = smallerNotes1.get(smallerIndex1);
        OneTimeNote newNote = new OneTimeNote(smallerNote);
        if (smallerTimeline + smallerNote.times_beat > biggerTimeline) {
            newNote.times_beat = biggerTimeline - smallerTimeline;
        }
        out.add(newNote);
    }

}