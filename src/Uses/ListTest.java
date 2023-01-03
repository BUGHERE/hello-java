package Uses;

import jdk.internal.dynalink.linker.ConversionComparator;

import java.util.*;

public class ListTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1); list.add(3); list.add(2);
        // list.sort((a, b)->a - b);

//        list.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(list.get(i));
        }
        Deque<Integer> deque = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
    }
}
