package lesson6;

import java.util.ArrayList;
import java.util.List;

/**
 * Homework for lesson #6
 *
 * @author Valeriy Lazarev
 * @since 10.10.2020
 */
public class Main {

    public static void main(String[] args) {
    }

    /*Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
    иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода
    (по 3-4 варианта входных данных).Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].*/
    public static int[] makeAnArrayAfterFour(int[] array) {
        int count = 0;
        List<Integer> integers = new ArrayList<>();

        if (array.length > 0) {
            for (int i = array.length - 1; i >= 0; i--) {
                if (array[i] == 4) {
                    for (int j = i + 1; j < array.length; j++) {
                        integers.add(array[j]);
                        count++;
                    }
                    if (integers.size() == 0) return array;
                    else return integers.stream().mapToInt(k -> k).toArray();
                }
            }
            if (count == 0) {
                throw new RuntimeException("RuntimeException");
            }
        }
        return array;
    }

    /*Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
    то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).*/
    public static boolean lookingForOneOrFour(int[] array) {
        for (int j : array) {
            if (j == 1 || j == 4) return true;
        }
        return false;
    }

}
