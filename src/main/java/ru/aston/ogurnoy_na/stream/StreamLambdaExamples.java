package ru.aston.ogurnoy_na.stream;

import ru.aston.ogurnoy_na.task1.User;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamLambdaExamples {
    public static void main(String[] args) {
        Map<String, Runnable> tasks = new LinkedHashMap<>();
        tasks.put("Вывести все четные числа в диапазоне от 1 до 100",
                () -> IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0).forEach(System.out::println));

        tasks.put("Умножить каждое число в массиве [1, 2, 3, 4, 5] на 2",
                () -> IntStream.of(1, 2, 3, 4, 5).map(n -> n * 2).forEach(System.out::println));

        tasks.put("Посчитать сумму чисел в массиве [1, 2, 3, 4, 5], используя reduce()",
                () -> System.out.println("Сумма: " + IntStream.of(1, 2, 3, 4, 5).reduce(0, Integer::sum)));

        tasks.put("Вывести числа в диапазоне от 1 до 50 с шагом 2",
                () -> IntStream.rangeClosed(1, 50).filter(n -> n % 2 == 0).forEach(System.out::println));

        tasks.put("Найти первый четный элемент в списке [1, 2, 3, 4, 5]",
                () -> System.out.println("Первый четный элемент: " + IntStream.of(1, 2, 3, 4, 5).filter(n -> n % 2 == 0).findFirst().orElse(-1)));

        tasks.put("Отсортировать элементы массива [7, 9, 1, 3, 5] по возрастанию",
                () -> System.out.println(Arrays.toString(IntStream.of(7, 9, 1, 3, 5).sorted().toArray())));

        tasks.put("Вывести первые 10 чисел в списке [0, 1, 2, … 99]",
                () -> IntStream.range(0, 100).limit(10).forEach(System.out::println));

        tasks.put("Пропустить первые 10 элементов списка [0, 1, 2,.., 99] и начать выводить с 11-го элемента, выводя каждый 10-й элемент",
                () -> IntStream.range(0, 100).skip(10).filter(n -> n % 10 == 0).forEach(System.out::println));

        tasks.put("Выведите на экран все числа в диапазоне от 1 до 100, которые делятся на 3",
                () -> IntStream.range(1, 100).filter(n -> n % 3 == 0).forEach(System.out::println));

        tasks.put("Выведите все нечетные числа в заданном массиве [1, 2, 3, 4, 5]",
                () -> Arrays.stream(new int[]{1, 2, 3, 4, 5}).filter(n -> n % 2 != 0).forEach(System.out::println));

        tasks.put("Выведите на экран только те элементы списка (1, 2, 3, 4, 5), которые больше 3",
                () -> Stream.of(1, 2, 3, 4, 5).filter(n -> n > 3).forEach(System.out::println));

        tasks.put("Отфильтруйте все элементы списка, которые меньше 0",
                () -> Stream.of(1, -2, 3, -4, 5).filter(n -> n > 0).forEach(System.out::println));

        tasks.put("Выведите на экран строки из списка apple, banana, cherry, которые начинаются с заданной подстроки a",
                () -> Stream.of("apple", "banana", "cherry").filter(s -> s.startsWith("a")).forEach(System.out::println));

        tasks.put("Выведите все числа в списке (1, 2, 3, 4, 5), которые не кратны 2",
                () -> Stream.of(1, 2, 3, 4, 5).filter(n -> n % 2 != 0).forEach(System.out::println));

        tasks.put("Отфильтруйте объекты по определенному свойству, например, выведите все записи из базы данных, у которых значение поля равно 1",
                () -> Arrays.asList(
                        new User(25, "John", "Doe", true, false),
                        new User(30, "Jane", "Smith", false, true),
                        new User(25, "Bob", "Johnson", true, false),
                        new User(30, "Alice", "Brown", false, true)
                ).stream().filter(user -> user.getAge() == 25).forEach(System.out::println));

        tasks.put("Выведите только те элементы коллекции, которые удовлетворяют определенному условию, например, являются уникальными или имеют определенный формат",
                () -> Stream.of("apple", "apple", "banana", "orange", "orange", "pear", "kiwi").distinct().forEach(System.out::println));

        tasks.put("Отфильтруйте элементы массива, которые не являются числами",
                () -> Stream.of("apple", 123, "banana", true, 456).filter(obj -> obj instanceof Integer).forEach(System.out::println));

        tasks.put("Выведите на экран элементы списка, которые не входят в другой список",
                () -> {
                    List<String> list1 = Arrays.asList("apple", "banana", "orange");
                    List<String> list2 = Arrays.asList("banana", "kiwi", "pear");
                    list1.stream().filter(item -> !list2.contains(item)).forEach(System.out::println);
                });

        tasks.put("Проверить, все ли числа в массиве [0, 1, …, 19] являются четными",
                () -> System.out.println("Все числа четные: " + IntStream.range(0, 20).allMatch(num -> num % 2 == 0)));

        tasks.put("Проверить, есть ли четное число в списке [0, 1, 2, 3]",
                () -> System.out.println("Список содержит четное число: " + Stream.of(0, 1, 2, 3).anyMatch(num -> num % 2 == 0)));

        tasks.put("Собрать все уникальные элементы Stream в список и отсортировать их",
                () -> System.out.println(Stream.of(1, 3, 3, 1, 2, 4, 5).distinct().sorted().collect(Collectors.toList())));

        tasks.put("Собрать элементы Stream в карту, где ключом будет первая буква каждого слова, а значением — само слово. Отсортировать ключи в алфавитном порядке",
                () -> System.out.println(Stream.of("apple", "banana", "cherry", "date")
                        .collect(Collectors.toMap(word -> word.charAt(0), Function.identity(), (a, b) -> a))));

        tasks.put("Собрать числа в Stream в список сумм цифр каждого числа",
                () -> System.out.println(Stream.of(123, 456, 789)
                        .map(number -> String.valueOf(number).chars().map(Character::getNumericValue).sum())
                        .collect(Collectors.toList())));

        tasks.put("Собрать даты в Stream в список, где каждый элемент — это количество дат в каждом месяце года",
                () -> System.out.println(Stream.of(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 2, 1),
                        LocalDate.of(2023, 3, 1)
                ).collect(Collectors.groupingBy(LocalDate::getMonthValue, Collectors.counting()))));

        tasks.put("Собрать файлы в Stream в список, где каждый элемент — количество файлов с определенным расширением",
                () -> System.out.println(Stream.of(new File("file1.txt"), new File("file2.txt"), new File("file3.doc")).collect(Collectors.groupingBy(file -> file.getName().split("\\.")[1], Collectors.counting()))));

        tasks.put("Создайте Stream из массива чисел, выведите на экран числа, кратные 3 и 5 одновременно",
                () -> Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).filter(n -> n % 3 == 0 && n % 5 == 0).forEach(System.out::println));

        tasks.put("Создайте два Stream-а: один из массива чисел 1…5, второй из массива 5…10. Объедините эти два Stream-а в один и выведите на экран",
                () -> IntStream.concat(Arrays.stream(new int[]{1, 2, 3, 4, 5}), Arrays.stream(new int[]{6, 7, 8, 9, 10})).forEach(System.out::println));

        tasks.put("Создайте Stream-у чисел от 1 до 20. Создайте новый Stream, который будет выводить на экран только четные числа и числа, кратные 3. Затем объедините эти два Stream-a в один и выведите результирующий Stream",
                () -> IntStream.concat(IntStream.range(1, 20), IntStream.range(1, 20).filter(n -> n % 2 == 0 || n % 3 == 0)).forEach(System.out::println));

        tasks.put("Создайте Stream чисел от 2 до 10. Умножьте их на 2 и выведите результат на экран, ограничьтесь первыми десятью результатами",
                () -> IntStream.range(2, 10).map(n -> n * 2).limit(10).forEach(System.out::println));

        tasks.put("Создайте три Stream-а из массивов чисел 1…10, 5…20 и 10…30 соответственно. Объедините их в один Stream и выведите числа, которые кратны 3 или 5",
                () -> IntStream.concat(IntStream.range(1, 10), IntStream.concat(IntStream.rangeClosed(5, 20), IntStream.rangeClosed(10, 30)))
                        .filter(n -> n % 3 == 0 || n % 5 == 0).forEach(System.out::println));

        tasks.put("Создайте Stream, который выводит на экран четные числа от 2 до 30",
                () -> IntStream.rangeClosed(1, 30).filter(n -> n % 2 == 0).forEach(System.out::println));

        tasks.put("Создать стрим из массива чисел и вывести на экран только числа, которые больше 10 и меньше 20",
                () -> Arrays.stream(new int[]{5, 10, 15, 20, 25}).filter(n -> n > 10 && n < 20).forEach(System.out::println));

        tasks.put("Создать два стрима: один из чисел от 0 до 10, другой из чисел от 10 до 20. Объединить их в один стрим и вывести на экран числа больше 10",
                () -> IntStream.concat(IntStream.range(0, 11), IntStream.rangeClosed(10, 20)).filter(n -> n > 10).forEach(System.out::println));

        tasks.put("Создать стрим чисел от 0 до 100. Умножить их на 2 и вывести на экран результат, ограничиться первыми 10 результатами",
                () -> IntStream.range(0, 101).map(n -> n * 2).limit(10).forEach(System.out::println));

        tasks.put("Создать стрим, который выводит числа от 1 до 10. Найти среднее арифметическое этих чисел и вывести его на экран",
                () -> System.out.println(IntStream.rangeClosed(1, 10).average().getAsDouble()));

        tasks.put("Создать три стрима из массивов чисел от 1 до 10, от 10 до 20 и от 20 до 30 соответственно. Объединить их в один стрим и вывести числа, которые кратны 5",
                () -> IntStream.concat(IntStream.range(1, 11), IntStream.concat(IntStream.rangeClosed(10, 20), IntStream.rangeClosed(20, 30)))
                        .filter(x -> x % 5 == 0).forEach(System.out::println));

        tasks.put("Создать стрим четных чисел от 2 до 40 и вывести на экран количество элементов в этом стриме",
                () -> System.out.println(IntStream.rangeClosed(2, 40).filter(x -> x % 2 == 0).count()));

        tasks.put("Разделить элементы Stream на две группы: четные и нечетные, вывести результаты",
                () -> IntStream.rangeClosed(10, 20).boxed().collect(Collectors.partitioningBy(x -> x % 2 == 0))
                        .forEach((k, v) -> System.out.println("Четные: " + k + ", Значения: " + v)));

        tasks.put("Разделить слова в Stream на две группы по первой букве: гласные и согласные, посчитать количество слов в каждой группе",
                () -> {
                    String[] words = {"apple", "banana", "orange", "pear", "grape"};
                    Map<Boolean, Long> map = Arrays.stream(words)
                            .collect(Collectors.partitioningBy(word -> word.matches("[aeiou].*"), Collectors.counting()));
                    System.out.println("Гласные: " + map.get(true));
                    System.out.println("Согласные: " + map.get(false));
                });

        tasks.put("Разделить числа в Stream на три группы по остатку от деления на 3 (0, 1, 2), посчитать сумму чисел в каждой группе",
                () -> {
                    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    Map<Integer, Integer> map = Arrays.stream(numbers).boxed()
                            .collect(Collectors.groupingBy(number -> number % 3, Collectors.summingInt(Integer::intValue)));
                    System.out.println("Группа 0: " + map.get(0));
                    System.out.println("Группа 1: " + map.get(1));
                    System.out.println("Группа 2: " + map.get(2));
                });

        tasks.put("Разделить даты в Stream на четыре группы по временам года, посчитать количество событий в каждом времени года",
                () -> {
                    List<LocalDate> dates = Arrays.asList(
                            LocalDate.of(2023, 1, 1),
                            LocalDate.of(2023, 4, 15),
                            LocalDate.of(2023, 7, 30),
                            LocalDate.of(2023, 10, 10),
                            LocalDate.of(2023, 12, 25)
                    );
                    Map<String, Long> seasonCount = dates.stream().collect(Collectors.groupingBy(date -> {
                        if (date.getMonthValue() >= 3 && date.getMonthValue() <= 5) {
                            return "Весна";
                        } else if (date.getMonthValue() >= 6 && date.getMonthValue() <= 8) {
                            return "Лето";
                        } else if (date.getMonthValue() >= 9 && date.getMonthValue() <= 11) {
                            return "Осень";
                        } else {
                            return "Зима";
                        }
                    }, Collectors.counting()));
                    System.out.println(seasonCount);
                });

        tasks.put("Разделить файлы в Stream на два списка: с расширением .txt и с расширением .doc, посчитать количество файлов в каждом списке",
                () -> {
                    List<String> files = Arrays.asList("file1.txt", "file2.doc", "file3.txt", "file4.doc");
                    Map<String, Long> countFiles = files.stream()
                            .collect(Collectors.groupingBy(s -> s.substring(s.indexOf(".") + 1), Collectors.counting()));
                    System.out.println(countFiles);
                });

        tasks.put("Разделить продукты в Stream на несколько групп по категориям, посчитать стоимость продуктов в каждой группе",
                () -> Arrays.asList(
                                new Product("Молоко", "Молочные продукты", 50),
                                new Product("Сыр", "Молочные продукты", 120),
                                new Product("Хлеб", "Выпечка", 30),
                                new Product("Яблоки", "Фрукты", 80),
                                new Product("Мясо", "Мясные продукты", 250),
                                new Product("Курица", "Мясные продукты", 180),
                                new Product("Помидор", "Овощи", 60),
                                new Product("Огурец", "Овощи", 45),
                                new Product("Картофель", "Овощи", 30)
                        ).stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.averagingDouble(Product::getPrice)))
                        .forEach((category, averagePrice) -> System.out.println("Средняя цена категории " + category + ": " + averagePrice)));

        tasks.put("Создание Stream из массива целых чисел и вывод на экран всех простых чисел в диапазоне от 2 до 100",
                () -> Arrays.stream(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10})
                        .filter(number -> number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(divisor -> number % divisor == 0))
                        .forEach(System.out::println));

        tasks.put("Создание двух Stream из массивов целых чисел и объединение их в один, затем вывод на экран суммы квадратов элементов этого Stream",
                () -> System.out.format("Сумма квадратов: %d", IntStream.concat(IntStream.of(new int[]{1, 2, 3}), IntStream.of(new int[]{4, 5, 6}))
                        .map(number -> number * number).sum()));


        tasks.put("Создание трех Stream из массивов и объединение их в один. Затем вывод на экран среднего геометрического значения элементов этого Stream",
                () -> IntStream.concat(IntStream.concat(IntStream.of(new int[]{1, 2, 3}), IntStream.of(new int[]{4, 5, 6})), IntStream.of(new int[]{7, 8, 9}))
                        .average()
                        .ifPresentOrElse(System.out::println, () -> System.out.println("Нет значений")));

        tasks.put("Создание Stream символов и поиск всех строк, состоящих из строчных букв и цифр",
                () -> {
                    String[] strings = {"hello", "1234567890", "World", "!@#$%^&*()_+"};
                    Arrays.stream(strings)
                            .filter(s -> s.matches("[a-z0-9]+"))
                            .forEach(System.out::println);
                });

        tasks.put("Создание Stream дат и поиск всех дат, которые попадают в определенный диапазон",
                () -> {
                    LocalDate start = LocalDate.of(2023, 1, 1);
                    LocalDate end = LocalDate.of(2023, 12, 31);
                    LocalDate[] dates = {
                            LocalDate.of(2023, 4, 1),
                            LocalDate.of(2023, 7, 15),
                            LocalDate.of(2023, 9, 30),
                            LocalDate.of(2022, 12, 25),
                            LocalDate.of(2022, 10, 1),
                            LocalDate.of(2023, 6, 15)
                    };
                    Arrays.stream(dates)
                            .filter(d -> d.isAfter(start) && d.isBefore(end))
                            .forEach(System.out::println);
                });

        tasks.put("Создание Stream целых чисел и поиск всех чисел, у которых сумма цифр равна заданному числу",
                () -> {
                    int sum = 12;
                    List<Integer> numbers = Arrays.asList(100, 561, 9834, 12345, 534);
                    numbers.stream()
                            .filter(n -> String.valueOf(n).chars().map(Character::getNumericValue).sum() == sum)
                            .forEach(System.out::println);
                });

        tasks.put("Создание Stream строк и поиск всех строк, у которых длина равна заданному числу и которые состоят из определенного набора символов",
                () -> {
                    int length = 5;
                    String chars = "abc";
                    String[] strings = {"a", "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg", "abcbc", "cbaba"};
                    Arrays.stream(strings)
                            .filter(s -> s.length() == length && s.chars().allMatch(c -> chars.contains(Character.toString((char) c))))
                            .forEach(System.out::println);
                });

        tasks.put("Создание Stream пар чисел и поиск всех пар, у которых произведение равно заданному числу или которые удовлетворяют другому условию",
                () -> {
                    int[] numbers = {1, 2, 3, 4, 5, 6};
                    int target = 6;
                    Arrays.stream(numbers)
                            .mapToObj(i -> IntStream.of(numbers).mapToObj(j -> new int[]{i, j}))
                            .flatMap(Function.identity())
                            .filter(pair -> pair[0] * pair[1] == target)
                            .forEach(pair -> System.out.println(Arrays.toString(pair)));
                });

        tasks.put("Создание Stream коллекций и поиск всех коллекций, которые содержат определенный элемент или удовлетворяют другому условию",
                () -> {
                    List<List<Integer>> collections = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9));
                    Integer target = 5;
                    collections.stream()
                            .filter(collection -> collection.contains(target))
                            .forEach(System.out::println);
                }
        );

        tasks.put("Сгруппировать элементы Stream по их чётности, посчитать размер каждой группы и вывести результаты",
                () -> {
                   List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                   numbers.stream()
                           .collect(Collectors.groupingBy(number -> number % 2 == 0))
                           .forEach((even, numbersList) -> System.out.println(even + ": " + numbersList));
                }
        );

        tasks.put("Сгруппировать слова в Stream по первой букве, посчитать количество слов в каждой группе и вывести результаты в виде словаря, где ключ — первая буква слова, а значение — количество слов, начинающихся на эту букву",
                () -> {
                  List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "blueberry", "ananas");
                  words.stream()
                          .collect(Collectors.groupingBy(word -> word.charAt(0), Collectors.counting()))
                          .forEach((firstLetter, count) -> System.out.println(firstLetter + ": " + count));
                }
        );

        tasks.put("Сгруппировать числа в Stream по остатку от деления на 3, посчитать сумму чисел в каждой группе и вывести результаты",
                () -> {
                 List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                 numbers.stream()
                         .collect(Collectors.groupingBy(num -> num % 3, Collectors.summingInt(Integer::intValue)))
                         .forEach((remainder, sum) -> System.out.println(remainder + ": " + sum));
                }
        );

        tasks.put("Сгруппировать даты в Stream по месяцу, посчитать количество дат в каждом месяце и вывести результаты в виде календаря с количеством событий в каждом месяце",
                () -> {
                   List<LocalDate> dates = Arrays.asList(
                           LocalDate.of(2023, 1, 1), LocalDate.of(2023, 2, 15), LocalDate.of(2023, 3, 10),
                           LocalDate.of(2022, 1, 5), LocalDate.of(2022, 3, 1), LocalDate.of(2023, 3, 15));
                    Map<Month, Long> result = dates.stream()
                            .collect(Collectors.groupingBy(LocalDate::getMonth, Collectors.counting()));
                    System.out.println(result);
                }
        );

        tasks.put("Сгруппировать файлы в Stream по расширению, посчитать количество файлов с каждым расширением и вывести результаты в виде списка, где ключ — расширение файла, а значение — количество файлов с таким расширением",
                () -> {
                   List<String> files = Arrays.asList("file1.txt", "file2.docx", "file3.pdf", "file4.jpg", "file5.txt", "file6.docx");
                   Map<String, Long> result = files.stream()
                           .collect(Collectors.groupingBy(file -> file.substring(file.lastIndexOf('.')), Collectors.counting()));
                   System.out.println(result);
                }
        );

        tasks.forEach((description, task) -> {
            System.out.println("Задача: " + description);
            task.run();
            System.out.println();
        });
    }
}