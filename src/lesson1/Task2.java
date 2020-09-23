package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {}
}

/*a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)*/
abstract class Fruit {
    float weight;

    public Fruit(float weight) {
        this.weight = weight;
    }
}

class Apple extends Fruit {
    public Apple() {
        super(1.0f);
    }
}

class Orange extends Fruit {
    public Orange() {
        super(1.5f);
    }
}

/*b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
поэтому в одну коробку нельзя сложить и яблоки, и апельсины;*/
class Box<T extends Fruit> {
    /*c. Для хранения фруктов внутри коробки можете использовать ArrayList;*/
    private List<T> list = new ArrayList<>();

    /*f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
    (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
    соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты,
    которые были в этой коробке;*/
    public void pourOutOfTheBox(Box<T> box) {
        for (int i = 0; i <= list.size(); i++) {
            box.addingFruitToTheBox(takeOutOfTheBox());
        }
        list.clear();
    }

    /*g. Не забываем про метод добавления фрукта в коробку.*/
    public void addingFruitToTheBox(T t) {
        list.add(t);
    }

    /*e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той,
    которую подадут в compare в качестве параметра, true - если их веса равны,
     false в противном случае (коробки с яблоками мы можем сравнивать с коробками с апельсинами);*/
    public boolean compare(Box<? extends Fruit> box) {
        return (this.getWeight() - box.getWeight()) == 0;
    }

    public T takeOutOfTheBox() {
        T tmp = this.list.get(this.list.size()-1);
        this.list.remove(this.list.size()-1);
        return tmp;
    }

    /*d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и
вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);*/
    public double getWeight() {
        double sum = 0.0;
        for (T value : this.list) {
            sum += value.weight;
        }
        return sum;
    }
}

