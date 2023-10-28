import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/12 - 10 - 12 - 17:18
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
class OneTimeNote {
    private HashSet<String> notes = new HashSet<>();
    public double times_beat = 1;

    private static HashMap<String, Character> note_key = new HashMap<>();
    static {
//        String values = "QWERTYU";
//        for (int i = 0; i < 7; i++) {
//            note_key.put("+" + (i + 1), values.charAt(i));
//        }
//        values = "ASDFGHJ";
//        for (int i = 0; i < 7; i++) {
//            note_key.put("" + (i + 1), values.charAt(i));
//        }
//        values = "ZXCVBNM";
//        for (int i = 0; i < 7; i++) {
//            note_key.put("-" + (i + 1), values.charAt(i));
//        }

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

    public OneTimeNote(HashSet<String> notes, double times_beat) {
        this.notes = (HashSet<String>) notes.clone();
        this.times_beat = times_beat;
    }

    public OneTimeNote() {

    }

    public OneTimeNote(OneTimeNote oneTimeNote) {
        this(oneTimeNote.notes, oneTimeNote.times_beat);
    }

    public void addNote(String newNote) {
        notes.add(newNote);
    }

    public void addNotes(HashSet<String> newNotes) {
        notes.addAll(newNotes);
    }

    public HashSet<String> getNotes() {
        return notes;
    }

    public String getKeys() throws Exception {
        StringBuilder keys = new StringBuilder();
        for (String note : notes) {
            keys.append(noteToKey(note));
        }
        return keys.toString();
    }

    public void clear() {
        notes.clear();
        times_beat = 1;
    }

    static class ModifyToneHelper {
        static HashMap<String, Integer> note_int = new HashMap<>();
        static HashMap<Integer, String> int_note = new HashMap<>();
        static {
            note_int.put("+1",15);
            note_int.put("+2",16);
            note_int.put("+3",17);
            note_int.put("+4",18);
            note_int.put("+5",19);
            note_int.put("+6",20);
            note_int.put("+7",21);
            note_int.put("1",8);
            note_int.put("2",9);
            note_int.put("3",10);
            note_int.put("4",11);
            note_int.put("5",12);
            note_int.put("6",13);
            note_int.put("7",14);
            note_int.put("-1",1);
            note_int.put("-2",2);
            note_int.put("-3",3);
            note_int.put("-4",4);
            note_int.put("-5",5);
            note_int.put("-6",6);
            note_int.put("-7",7);
            note_int.put("0",0);
            note_int.forEach((key, value) -> { int_note.put(value, key); });
        }
        static String modifyNote(String note, int offset) {
            if (note.equals("0")) {
                return note;
            }
            int i = note_int.get(note);
            int j = i + offset;
            if ( j > 21) {
                j = 14 + (j % 7);
            } else if (j <= 0) {
                j = j % 7 + 7;
            }
            return int_note.get(j);
        }
        static String modifyNote(String note, char key) throws Exception {
            key = Character.toUpperCase(key);
            if (key < 'A' || key > 'G') {
                throw new Exception("illegal tone key:" + key);
            }
            return modifyNote(note, key - 'C');
        }
    }

    public void modifyNote(int offset) {
        HashSet<String> newNotes = new HashSet<>();
        Iterator<String> iterator = notes.iterator();
        while (iterator.hasNext()){
            String note = iterator.next();
            newNotes.add(ModifyToneHelper.modifyNote(note, offset));
        }
        notes = newNotes;
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
                "notes=" + notes +
                ", times_beat=" + times_beat +
                '}';
    }

}
