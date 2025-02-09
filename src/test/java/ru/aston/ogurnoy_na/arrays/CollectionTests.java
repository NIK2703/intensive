package ru.aston.ogurnoy_na.arrays;

import org.junit.jupiter.api.Test;
import java.util.*;

public class CollectionTests {

    @Test
    public void testArrayList() {
        // Создание ArrayList с помощью конструктора по умолчанию
        List<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Hello");
        arrayList1.add("World");
        assertArrayEquals(new String[]{"Hello", "World"}, arrayList1.toArray());

        // Создание ArrayList с начальной емкостью
        List<Integer> arrayList2 = new ArrayList<>(5);
        arrayList2.add(10);
        arrayList2.add(20);
        assertArrayEquals(new Integer[]{10, 20}, arrayList2.toArray());

        // Создание ArrayList из другой коллекции
        List<Integer> sourceList = Arrays.asList(30, 40, 50);
        List<Integer> arrayList3 = new ArrayList<>(sourceList);
        assertArrayEquals(new Integer[]{30, 40, 50}, arrayList3.toArray());
    }

    @Test
    public void testHashMap() {
        // Создание HashMap с помощью конструктора по умолчанию
        Map<String, Integer> hashMap1 = new HashMap<>();
        hashMap1.put("One", 1);
        hashMap1.put("Two", 2);
        assertEquals(1, hashMap1.get("One"));
        assertEquals(2, hashMap1.get("Two"));

        // Создание HashMap с начальной емкостью
        Map<String, String> hashMap2 = new HashMap<>(16);
        hashMap2.put("Key1", "Value1");
        hashMap2.put("Key2", "Value2");
        assertEquals("Value1", hashMap2.get("Key1"));
        assertEquals("Value2", hashMap2.get("Key2"));

        // Создание HashMap из другой коллекции
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put("Three", 3);
        sourceMap.put("Four", 4);
        Map<String, Integer> hashMap3 = new HashMap<>(sourceMap);
        assertEquals(3, hashMap3.get("Three"));
        assertEquals(4, hashMap3.get("Four"));
    }

    @Test
    public void testTreeSet() {
        // Создание TreeSet с помощью конструктора по умолчанию
        Set<String> treeSet1 = new TreeSet<>();
        treeSet1.add("Apple");
        treeSet1.add("Banana");
        Iterator<String> iterator1 = treeSet1.iterator();
        assertEquals("Apple", iterator1.next());
        assertEquals("Banana", iterator1.next());

        // Создание TreeSet с компаратором
        Set<Integer> treeSet2 = new TreeSet<>(Comparator.reverseOrder());
        treeSet2.add(10);
        treeSet2.add(20);
        Iterator<Integer> iterator2 = treeSet2.iterator();
        assertEquals(20, iterator2.next());
        assertEquals(10, iterator2.next());
    }

    @Test
    public void testCollectionsMethods() {
        List<String> list = new ArrayList<>();
        list.add("c");
        list.add("b");
        list.add("a");

        // Проверка размера коллекции
        assertEquals(3, list.size());

        // Поиск максимального элемента
        String maxElement = Collections.max(list);
        assertEquals("c", maxElement);

        // Сортировка списка
        Collections.sort(list);
        assertArrayEquals(new String[]{"a", "b", "c"}, list.toArray());
    }

    private void assertArrayEquals(Object[] expected, Object[] actual) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError("Expected: " + Arrays.toString(expected) + ", but was: " + Arrays.toString(actual));
        }
    }

    private void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }
}