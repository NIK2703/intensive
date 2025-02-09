package ru.aston.ogurnoy_na.strings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringToolsTest {

    @Test
    public void testFindLongestWord() {
        assertEquals("", StringTools.findLongestWord(""));
        assertEquals("", StringTools.findLongestWord(null));
        assertEquals("longest", StringTools.findLongestWord("This is a longest word example"));
        assertEquals("example", StringTools.findLongestWord("This is an example of words"));
    }

    @Test
    public void testIsPalindrom() {
        assertTrue(StringTools.isPalindrom("radar"));
        assertFalse(StringTools.isPalindrom("hello"));
        assertFalse(StringTools.isPalindrom(""));
        assertFalse(StringTools.isPalindrom(null));

    }

    @Test
    public void testCensor() {
        assertEquals("", StringTools.censor(""));
        assertEquals("", StringTools.censor(null));
        assertEquals("[вырезано цензурой] [вырезано цензурой] [вырезано цензурой]", StringTools.censor("бяка бяка бяка"));
        assertEquals("No curse words here", StringTools.censor("No curse words here"));
    }

    @Test
    public void testCountOccurrences() {
        assertEquals(0, StringTools.countOccurrences("", "a"));
        assertEquals(0, StringTools.countOccurrences(null, "a"));
        assertEquals(2, StringTools.countOccurrences("abacaba", "aba"));
        assertEquals(2, StringTools.countOccurrences("hello world", "o"));
    }

    @Test
    public void testInvertWords() {
        assertEquals("", StringTools.invertWords(""));
        assertEquals("", StringTools.invertWords(null));
        assertEquals("sihT si a esrever drow", StringTools.invertWords("This is a reverse word"));
        assertEquals("olleh dlrow", StringTools.invertWords("hello world"));
    }
}