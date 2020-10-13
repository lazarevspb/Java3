package lesson7;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Homework for lesson #7
 *
 * @author Valeriy Lazarev
 * @since 14.10.2020
 */
public class TestClass {

    @BeforeSuite(priority = 0)
    public void methodBefore() {
        System.out.println("methodBefore");
    }


    @Test(priority = 1)
    public void methodTest1() {
        System.out.println("methodTest1");
    }

    @Test(priority = 5)
    public void methodTest2() {
        System.out.println("methodTest2");
    }

    @AfterSuite(priority = 10)
    public void methodAfter() {
        System.out.println("methodAfter");
    }
}

class Main {

    public static void start(Class<?> name) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object obj;
        Map<Method, Integer> map = new HashMap<>();
        Method[] methods = name.getDeclaredMethods();
        int beforeMethod = 0, afterMethod = 0;
        int priority;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeMethod++;
                if (beforeMethod > 1) throw new RuntimeException();

                else {
                    map.put(method, method.getAnnotation(BeforeSuite.class).priority());
                }
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterMethod++;
                if (afterMethod > 1) throw new RuntimeException();
                else {
                    map.put(method, method.getAnnotation(AfterSuite.class).priority());
                }
            } else if (method.isAnnotationPresent(Test.class)) {
                priority = method.getAnnotation(Test.class).priority();
                map.put(method, priority);
            }
        }
        obj = name.newInstance();
        for (Map.Entry<Method, Integer> entry : map.entrySet()) {
            entry.getKey().setAccessible(true);
            entry.getKey().invoke(obj);
        }


        map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(methodIntegerEntry ->
                        System.out.println(methodIntegerEntry.getValue() + " " + methodIntegerEntry.getKey()));

    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        start(TestClass.class);
    }
}
