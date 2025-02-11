package ru.aston.ogurnoy_na.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayToolsTest {

    @Test
    public void testIsSorted() {
        int[] sortedArray = {1, 2, 3, 4, 5};
        int[] unsortedArray = {5, 3, 2, 4, 1};

        assertTrue(ArrayTools.isSorted(sortedArray));
        assertFalse(ArrayTools.isSorted(unsortedArray));
    }


    @Test
    public void testSwapFirstAndLast() {
        int[] array = {1, 2, 3, 4, 5};
        ArrayTools.swapFirstAndLast(array);
        assertEquals(5, array[0]); // первый элемент должен быть 5
        assertEquals(1, array[4]); // последний элемент должен быть 1
    }

    @Test
    public void testFindFirstUnique() {
        int[] array = {1, 2, 3, 1, 2, 4};
        assertEquals(3, ArrayTools.findFirstUnique(array)); // должно вернуть 3
    }

    @Test
    public void testMergeSort() {
        int[] array = {5, 3, 8, 4, 2};
        ArrayTools.mergeSort(array);
        assertArrayEquals(new int[]{2, 3, 4, 5, 8}, array); // массив должен быть отсортирован
    }

    @Test
    public void testFillAndSort() {
        int[] array = new int[10];
        ArrayTools.fillAndSort(array);
        assertTrue(ArrayTools.isSorted(array)); // массив должен быть отсортирован после fillAndSort
    }
}