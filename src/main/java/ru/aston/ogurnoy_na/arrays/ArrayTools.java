package ru.aston.ogurnoy_na.arrays;

import java.util.Arrays;
import java.util.Random;

public class ArrayTools {
    //1.	Напишите метод, которая проверяет отсортирован ли массив по возрастанию. Если он отсортирован по возрастанию то выводится “OK”, если нет, то будет выводиться текст “Please, try again”
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {

                return false;
            }
        }
        return true;
    }

    //2.	Напишите метод, который меняет местами первый и последний элемент массива
    public static void swapFirstAndLast(int[] array) {
        int temp = array[0];
        array[0] = array[array.length - 1];
        array[array.length - 1] = temp;
    }

    //3.	Дан массив чисел. Найдите первое уникальное в этом массиве число.
    //Например, для массива [1, 2, 3, 1, 2, 4] это число 3
    public static int findFirstUnique(int[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean isUnique = true;
            for (int j = 0; j < array.length; j++) {
                if (i != j && array[i] == array[j]) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                return array[i];
            }
        }
        throw new IllegalArgumentException("No unique element found");
    }

    //метод сортировки массива слиянием
    public static void mergeSort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }

    private static void merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public static void fillAndSort(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100); // Заполнение случайными числами от 0 до 99
        }
        mergeSort(array);

    }
}
