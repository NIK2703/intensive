package ru.aston.ogurnoy_na.strings;

public class StringTools {
    public static String findLongestWord(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        String[] words = text.split("\\s+");
        String longestWord = "";

        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return longestWord;
    }

    public static boolean isPalindrom(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String reversedStr = new StringBuilder(str).reverse().toString();
        return str.equals(reversedStr);
    }

    public static String censor(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return text.replaceAll("бяка", "[вырезано цензурой]");
    }

    public static int countOccurrences(String text, String substring) {
        if (text == null || substring == null || substring.isEmpty()) {
            return 0;
        }
        int count = 0;

        int index = text.indexOf(substring);
        while (index != -1) {
            count++;
            index = text.indexOf(substring, index + 1);
        }
        return count;
    }

    public static String invertWords(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        String[] words = text.split("\\s+");
        StringBuilder invertedText = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int length = word.length();
            for (int j = length - 1; j >= 0; j--) {
                invertedText.append(word.charAt(j));
            }
            if (i < words.length - 1) {
                invertedText.append(" ");
            }
        }
        return invertedText.toString();
    }


}
