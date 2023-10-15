public class Util {
    /**
     * 分割字符串成多个字符串存入数组
     * @param str
     * @param split
     */
    public static void split(String str,String split) {
        String[] arr  = str.split(split);
        for(String s : arr) {
            System.out.println("Music.Music" + "('" + s + "',);");
        }
    }
    /**
     * String类型转char类型数组
     * @param str
     */
    public static void castChar (String str){
        char [] chars = str.toCharArray();
        for (char c : chars){
            System.out.println("Music.Music('" + c + "',);");
        }
    }
    /**
     * 替换字符串中指定的字符成目标字符
     * @param str
     * @param regex
     * @param replacement
     */
    public static void replaceString (String str,String regex,String replacement){
        System.out.println(str.replaceAll(regex,replacement));
    }
}
