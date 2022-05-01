package me.karunarathne.adfly.JSONParse;

public class FormatJSON {
    public static String formatJson(String jsonStr) {
        StringBuilder sb = new StringBuilder();
        int jsonStrLen = jsonStr.length();

        int indentLevel = 0;
        for (int i = 0; i < jsonStrLen; i++) {
            char c = jsonStr.charAt(i);

            if ('{' == c) {
                indentLevel++;
            } else if ('}' == c) {
                indentLevel--;
            }

            if ('{' == c || ',' == c) {
                sb.append(c);
                sb.append("\n");
                for (int j = 0; j < indentLevel; j++) {
                    sb.append("\t");
                }
            } else if ('}' == c) {
                sb.append("\n");
                for (int j = 0; j < indentLevel; j++) {
                    sb.append("\t");
                }
                sb.append(c);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
