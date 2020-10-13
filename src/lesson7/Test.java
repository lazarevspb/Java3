package lesson7;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*@Target и @Retention – это метаанотации, то есть
аннотации, которые применяются к аннотации.
Значение ElementType.METHOD в @Target показывает, что наша аннотация @Test применима к методам,
то есть с помощью нее можно аннотировать только метод:*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    int priority();
}
