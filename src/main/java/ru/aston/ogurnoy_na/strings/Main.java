package ru.aston.ogurnoy_na.strings;

public class Main {
    public static void main(String[] args) {
        String text = "Найти самое длинное слово в этом тексте.";
        String result = StringTools.findLongestWord(text);
        System.out.println("Самое длинное слово: " + result);
        System.out.println();

    }
}
