package com.study.java8;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class StreamsTest {
    public static void main(String[] args) {
        System.out.println("Using Java 7:");
        List<String> stringList = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        System.out.println("List: " + stringList);
        long count = getCountEmptyStringUsingJava7(stringList);
        System.out.println("Empty Strings : " + count);
        count = getCountLength3UsingJava7(stringList);
        System.out.println("Strings of length 3:" + count);

        // eliminate empyt string
        List<String> filtered = deleteEmptyStringsUsingJava7(stringList);
        System.out.println("Filtered List:" + filtered);

        // Eliminate empty string and join using comma
        String mergedString = getMergedStringUsingJava7(stringList, ", ");
        System.out.println("Merged String:" + mergedString);


        List<Integer> numbers = Arrays.asList(3,2,2,3,7,3,5);
        // get list of square of distinct numbers
        List<Integer> squaresList = getSquares(numbers);
        System.out.println("Squares List:" + squaresList);

        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);

        System.out.println("List:" + integers);
        System.out.println("Highest number in List: " + getMax(integers));
        System.out.println("Lowest number in List: " + getMin(integers));
        System.out.println("Sum of all numbers: " + getSum(integers));
        System.out.println("Average of all numbers: " + getAverage(integers));

        System.out.println("Random Numbers:");
        Random random = new Random();
        for (int i = 0; i < 10; i ++) {
            System.out.println(random.nextInt());
        }

        System.out.println("Using Java 8:");
        System.out.println("List:" + stringList);
        count = stringList.stream().filter(string->string.isEmpty()).count();
        System.out.println("Empty Strings:" + count);

        count = stringList.stream().filter(string->string.length() == 3).count();
        System.out.println("String of length 3:" + count);

        filtered = stringList.stream().filter(string->!string.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered list :" + filtered);

        mergedString = stringList.stream().filter(string->!string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged string:" + mergedString);

        squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List:" + squaresList);



        IntSummaryStatistics stats = integers.stream().mapToInt(x->x).summaryStatistics();
        System.out.println("List:" + integers);
        System.out.println("Highest number in List: " + stats.getMax());
        System.out.println("Lowest number in List: " + stats.getMin());
        System.out.println("Sum of all numbers: " + stats.getSum());
        System.out.println("Average of all numbers: " + stats.getAverage());
        System.out.println("Random Numbers:");
        random.ints().limit(10).sorted().forEach(System.out::println);

        // paralleled processing
        count = stringList.parallelStream().filter(string->string.isEmpty()).count();
        System.out.println("Empty String:" + count);
    }

    private static int getCountEmptyStringUsingJava7(List<String> stringList) {
        int count = 0;
        for (String str : stringList) {
            if (null == str || str.isEmpty()) {
                count ++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> stringList) {
        int count = 0;
        for (String str : stringList) {
            if (null != str && str.length() == 3) {
                count ++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> stringList) {
        List<String> filteredList = new ArrayList<>(stringList.size());
        for (String str : stringList) {
            if (!str.isEmpty()) {
                filteredList.add(str);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> stringList, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String str : stringList) {
            if (!str.isEmpty()) {
                sb.append(str);
                sb.append(separator);
            }
        }
        String mergedString = sb.toString();
        return mergedString.substring(0, mergedString.length() - separator.length());
    }

    private static List<Integer> getSquares(List<Integer> numbers) {
        List<Integer> squaresList = new ArrayList<>(numbers.size());
        for (Integer number : numbers) {
            Integer square = new Integer(number.intValue() * number.intValue());
            if (!squaresList.contains(square)) {
                squaresList.add(square);
            }
        }
        return squaresList;
    }
    private static int getMax(List<Integer> numbers) {
        int max = numbers.get(0);
        for (int i = 1; i < numbers.size(); i ++) {
            Integer number = numbers.get(i);
            if (number.intValue() > max) {
                max = number.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers) {
        int min = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            Integer number = numbers.get(i);
            if (number.intValue() < min) {
                min = number.intValue();
            }
        }
        return min;
    }

    private static int getSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer i : numbers) {
            sum += i.intValue();
        }
        return sum;
    }
    private static int getAverage(List<Integer> numbers) {
        return getSum(numbers) / numbers.size();
    }
}
