package Uses;

import javax.xml.soap.Node;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommonTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        String join = String.join(", ", list);
        System.out.println("join = " + join);
        Integer[] arr = {1, 2, 3};
        Arrays.sort(arr, (a, b) -> b - a);
        BigInteger bi = new BigInteger("1");
        bi.intValue();
        Queue<Integer> queue = new LinkedList<>();
        queue.poll();
        Deque<Integer> stack = new LinkedList<>();

        System.out.println((int) 1e7);
        Integer[] test1 = new Integer[]{1, 2, 3};
        Integer[] test2 = new Integer[]{1, 2, 3};
        System.out.println("test1==test2 = " + test1.equals(test2));

        String t = "a", s = t + t;
        list.add(s);
        System.out.println("list = " + list);

        boolean test[] = new boolean[1];
        System.out.println("test = " + test[0]);
        Arrays.stream(arr).max(Integer::compareTo).get();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int intArr[] = new int[]{1, 2, 3, 1};
        Arrays.stream(intArr).max().getAsInt();
        int[] reverse1 = IntStream.range(0, intArr.length).map(i -> intArr[intArr.length - 1 - i]).toArray();
        System.out.println("reverse1 = " + Arrays.toString(reverse1));

        Integer integerArr[] = new Integer[]{1, 2, 3};
        Arrays.stream(integerArr).max(Integer::compareTo).get();
        int[] reverse2 = IntStream.range(0, integerArr.length).map(i -> integerArr[integerArr.length - 1 - i]).toArray();
        System.out.println("reverse2 = " + Arrays.toString(reverse2));
        Collections.reverse(Arrays.asList(intArr));
        System.out.println("intArr = " + Arrays.toString(intArr));
        Collections.reverse(Arrays.asList(integerArr));
        System.out.println("integerArr = " + Arrays.toString(integerArr));

        Queue<Integer> q = new LinkedList<>();
        Deque<Integer> deque = new LinkedList<>();
        Set<Integer> set = new HashSet<>();

        list.stream().mapToInt((i) -> Integer.valueOf(i));

        System.out.println("1 > 0 ? 1 : 0 + 1 = " + (1 > 0 ? 1 : 0 + 1));

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.computeIfAbsent(arr[i], x -> new ArrayList<>()).add(i);
            // 相当于
            // if (map.get(arr[i]) == null) {
            //    map.put(arr[i], new ArrayList<>());
            // }
            // map.get(arr[i]).add(i);
        }

        String hashtable = "";

        Set<Integer> collect = Arrays.stream(intArr).mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toSet());
        System.out.println("collect = " + collect);

        List<Integer> l = Arrays.asList(1,2,3);
        Integer reduce = l.stream().reduce(0, (r, a) -> r + a);
        System.out.println("reduce = " + reduce);
        Integer reduce2 = l.stream().reduce(0, (r, a) -> r + a, (r1,r2)->r1+r2);
        System.out.println("reduce2 = " + reduce2);
        Integer reduce3 = l.stream().reduce((r, a) -> r + a).get();
        System.out.println("reduce3 = " + reduce3);
        int[] ints = l.stream().mapToInt(i -> i).toArray();

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        

    }
}
