import java.util.ArrayList;

/**
 * @Auther: 即兴幻想曲
 * @Date: 2023/10/13 - 10 - 13 - 10:22
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class OneMusicScoreDecoder {
    OneTimeNote oneTimeNote = new OneTimeNote();
    String note = "";
    boolean inBracket = false;
    boolean inSquareBracket = false;
    boolean inBrace = false;
    String calcOperator;
    int calcNum;
    class Settings {
        boolean haveSettings = false;
        String settings = "";

        public void setSettings(String settings) {
            this.settings = settings;
            haveSettings = true;
        }
    }
    Settings settings = new Settings();
    public void setKey(char key) {
        this.key = key;
    }
    char key = 'C';
    public void setOriginOffset(int originOffset) {
        this.originOffset = originOffset;
    }
    int originOffset = 0;

    OneMusicScoreDecoder() {
        decodeInit();
    }

    public ArrayList<OneTimeNote> decode(String musicScore) throws Exception {
        ArrayList<OneTimeNote> notes = new ArrayList<>();
        String tempSettings = "";
        for (char c : musicScore.toCharArray()) {
            switch (c) {
                case '{':
                    inBrace = true;
                    break;
                case '}':
                    settings.setSettings(tempSettings);
                    tempSettings = "";
                    inBrace = false;
                    break;
                case '+':
                case '-':
                    if (inBrace) {
                        tempSettings = tempSettings + c;
                        break;
                    }
                    checkAndUpdateOutput(notes);
                    note = note + c;
                    break;
                case '(':
                    checkAndUpdateOutput(notes);
                    inBracket = true;
                    break;
                case ')':
                    inBracket = false;
                    break;
                case '_':
                    oneTimeNote.times_beat += 1;
                    break;
                case '.':
                    oneTimeNote.times_beat *= 1.5;
                    break;
                case '/':
                    if (inSquareBracket) {
                        calcOperator = "/";
                    } else {
                        oneTimeNote.times_beat /= 2;
                    }
                    break;
                case '*':
                    if (inSquareBracket) {
                        calcOperator = "*";
                    } else {
                        throw new Exception("illegal char:'" + c + "'");
                    }
                    break;
                case '[':
                    inSquareBracket = true;
                    break;
                case ']':
                    if (calcOperator.equals("/")) {
                        oneTimeNote.times_beat /= calcNum;
                    } else if (calcOperator.equals("*")) {
                        oneTimeNote.times_beat *= calcNum;
                    } else {
                        throw new Exception("have no operator in []");
                    }

                    calcOperator = "";
                    calcNum = 0;
                    inSquareBracket = false;
                    break;
                case ' ':
                case '\n':
                    break; //ignore
                default:
                    if (inBrace) {
                        tempSettings = tempSettings + c;
                        break;
                    }
                    if (inSquareBracket) {
                        if (Character.isDigit(c)) {
                            calcNum = calcNum * 10 + Integer.parseInt(String.valueOf(c));
                        } else {
                            throw new Exception("illegal char:'" + c + "' in []");
                        }
                        break;
                    }
                    if (OneTimeNote.isNote(String.valueOf(c))) {
                        checkAndUpdateOutput(notes);
                        note = note + c;
                    } else {
                        throw new Exception("illegal char:'" + c + "'");
                    }
                    break;
            }
        }
        checkAndUpdateOutput(notes);
        checkAndDoSettings(notes);
        return notes;
    }

    void decodeInit() {
        oneTimeNote = new OneTimeNote();
        note = "";
        inBracket = false;
        inSquareBracket = false;
        inBrace = false;
        calcOperator = "";
        calcNum = 0;
        settings = new Settings();
        key = 'C';
        originOffset = 0;
    }

    private void checkAndUpdateOutput(ArrayList<OneTimeNote> notes) {
        if (!note.equals("") && OneTimeNote.isNote(note)) {
            oneTimeNote.addNote(note);
            note = "";
            if (!inBracket) {
                notes.add(oneTimeNote);
                oneTimeNote = new OneTimeNote();
            }
        }
    }

    private void checkAndDoSettings(ArrayList<OneTimeNote> notes) {
        int finalOffset = 0;
        finalOffset += key - 'C';
        finalOffset += originOffset == 0 ? 0 : (originOffset > 0 ? originOffset - 1 : originOffset + 1);
        if (settings.haveSettings && settings.settings.matches("[\\+-][0-9]+")) {
            int scorePartOffset = Integer.parseInt(settings.settings);
            finalOffset += scorePartOffset == 0 ? 0 : (scorePartOffset > 0 ? scorePartOffset - 1 : scorePartOffset + 1);
        }
        if (finalOffset != 0) {
            for (OneTimeNote note : notes) {
                note.modifyNote(finalOffset);
            }
        }
    }

}
