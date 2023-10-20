import java.util.Arrays;
import java.util.HashMap;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/12 - 10 - 12 - 17:18
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
class OneTimeNote {
    public String[] notes = new String[3];
    public int nNotes = 0;
    public double times_beat = 1;

    private static HashMap<String, Character> note_key = new HashMap<>();
    static {
        note_key.put("+1",'Q');
        note_key.put("+2",'W');
        note_key.put("+3",'E');
        note_key.put("+4",'R');
        note_key.put("+5",'T');
        note_key.put("+6",'Y');
        note_key.put("+7",'U');
        note_key.put("1",'A');
        note_key.put("2",'S');
        note_key.put("3",'D');
        note_key.put("4",'F');
        note_key.put("5",'G');
        note_key.put("6",'H');
        note_key.put("7",'J');
        note_key.put("-1",'Z');
        note_key.put("-2",'X');
        note_key.put("-3",'C');
        note_key.put("-4",'V');
        note_key.put("-5",'B');
        note_key.put("-6",'N');
        note_key.put("-7",'M');
        note_key.put("0",'0');
    }

    public OneTimeNote(String[] notes, int nNotes, double times_beat) {
        for (int i = 0; i < nNotes; i++) {
            this.notes[i] = notes[i];
        }
        this.nNotes = nNotes;
        this.times_beat = times_beat;
    }

    public OneTimeNote() {

    }

    public OneTimeNote(OneTimeNote oneTimeNote) {
        this(oneTimeNote.notes, oneTimeNote.nNotes, oneTimeNote.times_beat);
    }

    public void addKey(String key) {
        if (nNotes >= 3) {
            try {
                throw new Exception("WARN:notes > 3, can't add");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (nNotes > 0 && key.equals("0")) {
            return;
        }
        if (nNotes == 1 && notes[0].equals("0")) {
            nNotes--;
        }
        notes[nNotes] = key;
        nNotes++;
    }

    public void clear() {
        nNotes = 0;
        times_beat = 1;
    }

    public void upOctave() {
        for (int i = 0; i < nNotes; i++) {
            switch (notes[i].charAt(0)) {
                case '0':
                case '+':
                    break;
                case '-':
                    notes[i] = notes[i].substring(1);
                    break;
                default:
                    notes[i] = '+' + notes[i];
                    break;
            }
        }
    }

    public void downOctave() {
        for (int i = 0; i < nNotes; i++) {
            switch (notes[i].charAt(0)) {
                case '0':
                case '-':
                    break;
                case '+':
                    notes[i] = notes[i].substring(1);
                    break;
                default:
                    notes[i] = '-' + notes[i];
                    break;
            }
        }
    }

    public static char noteToKey(String note) throws Exception {
        if (!note_key.containsKey(note)) {
            throw new Exception("ERR:no key match note '" + note + "'");
        }
        return note_key.get(note);
    }

    public static boolean isNote(String note) {
        return note_key.containsKey(note);
    }

    @Override
    public String toString() {
        return "OneTimeNote{" +
                "notes=" + Arrays.toString(notes) +
                ", nNotes=" + nNotes +
                ", times_beat=" + times_beat +
                '}';
    }
}
