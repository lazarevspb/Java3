package lesson1;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Homework for lesson #1
 *
 * @author Valeriy Lazarev
 * @since 23.09.2020
 */
public class Task1<T> {
    /*1. Написать метод, который меняет два элемента массива местами.
    /(массив может быть любого ссылочного типа);*/
    public void swapsTwoElementsOfAnArray(T[] arrays) {
        if (arrays.length > 0 && arrays[0] != null && arrays[1] != null) {
            T tmp = arrays[0];
            arrays[0] = arrays[1];
            arrays[1] = tmp;
        }
    }

    /*2. Написать метод, который преобразует массив в ArrayList;*/
    public ArrayList<T> convertsArrayToArrayList(T[] arrays) {
        return new ArrayList<>(Arrays.asList(arrays));
    }

}
