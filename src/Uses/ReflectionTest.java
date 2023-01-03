package Uses;

import java.lang.reflect.Field;

public class ReflectionTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Person p = new Person();
        Class personClass = p.getClass();
        System.out.println(personClass);
        personClass = Person.class;
        System.out.println(personClass);
        personClass = Class.forName("Uses.Person");
        System.out.println(personClass);

        String className = personClass.getName();
        System.out.println(className);
        Field[] fields = personClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }
}
