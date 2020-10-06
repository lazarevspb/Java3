package lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


public class MainClass {
    public volatile static boolean isWin = false;
    public static final int CARS_COUNT = 4;
    final static CountDownLatch theRaceIsOverCountDownLatch = new CountDownLatch(CARS_COUNT);
        final  static CountDownLatch theRaceHasBegunCountDownLatch = new CountDownLatch(CARS_COUNT);
    static CyclicBarrier everyoneIsReadyCyclicBarrier = new CyclicBarrier(CARS_COUNT);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        theRaceHasBegunCountDownLatch.await();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        theRaceIsOverCountDownLatch.await();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}


class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));

            MainClass.everyoneIsReadyCyclicBarrier.await();

            System.out.println(this.name + " готов");

            MainClass.theRaceHasBegunCountDownLatch.countDown();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        MainClass.theRaceIsOverCountDownLatch.countDown();
    }
}

abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}

class Road extends Stage {
    Object lock = new Object();

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);

            System.out.println(c.getName() + " закончил этап: " + description);

      synchronized (lock)     {
                if (description.equals("Дорога 40 метров") && !MainClass.isWin) {
                    System.out.printf("%s WIN\n", c.getName());
                    MainClass.isWin = true;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Tunnel extends Stage {
    Semaphore semaphoreTunnel = new Semaphore(MainClass.CARS_COUNT / 2, true );

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);

                    semaphoreTunnel.acquire();

                System.out.println(c.getName() + " начал этап: " + description);

                Thread.sleep(length / c.getSpeed() * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);

                    semaphoreTunnel.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Race {
    private ArrayList<Stage> stages;
    public ArrayList<Stage> getStages() {
        return stages;
    }
    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}
