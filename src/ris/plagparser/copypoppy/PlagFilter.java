package ris.plagparser.copypoppy;

import org.apache.commons.lang3.StringUtils;

public class PlagFilter {

    public static String JavaProcessContent(String content) {
        content = content.replaceAll("//.*|/\\*(?s:.*?)\\*/|(\"(?:(?<!\\\\)(?:\\\\\\\\)*\\\\\"|[^\r\n\"])*\")", "$1");
        content = StringUtils.stripStart(content,null);
        content = StringUtils.stripEnd(content, null);
        return content;
    }

    public static int JavaFilter(String content, boolean commentFlag){

        if (commentFlag) {
            if (content.contains("*/")) {
                return 0;
            }
            else {
                return 4;
            }
        }
        else if (content.isBlank() ||
                content.startsWith("import ") ||
                content.startsWith("//") ||
                (content.startsWith("/*") && content.contains("*/")) ||
                StringUtils.containsOnly(content, "}".toCharArray()) ||
                StringUtils.containsOnly(content, "}{else".toCharArray()) ||
                StringUtils.containsOnly(content, "} {else".toCharArray()) ||
                content.startsWith("public static void main") ||
                content.startsWith("@Override") ||
                content.startsWith("public void run") ||
                content.startsWith("try") ||
                content.startsWith("break;") ||
                content.startsWith("default:") ||
                content.contains("printStackTrace") ||
                (content.contains("catch") && content.contains("IOException")) ||
                StringUtils.containsOnly(content, "{".toCharArray())) {
            return 1;
        }
        else if (content.startsWith("/*")) {
                return 2;
        }
        else {
            return 3;
        }
    }
}
