package guru.qa.niffler.test.web;

import org.junit.jupiter.api.Test;

import java.util.*;

public class JavaTest {

    @Test
    void tests() {
        List<String> list = new ArrayList<>() {{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
        }};

        List<String> list1 = List.of("1", "2", "3");


        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("В списке осталось элементов: " + list1.size());

        LinkedList<String> s  = new LinkedList<>(){{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
        }};


    }
}
