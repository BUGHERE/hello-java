package Uses;

import java.util.*;

public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a", "apple");
        map.put("a", "b");
        map.put("b", "banana");
        map.put("C", "chip");
        map.remove("b");
        System.out.println(map.entrySet());
        System.out.println(map.get("a"));
        System.out.println(map.containsKey("a"));
        System.out.println(map.containsValue("apple"));
        System.out.println(map.size());
        System.out.println("LinkedHashMap: ");
        LinkedHashMap<Integer, Integer> lhm = new LinkedHashMap<>(2);
        lhm.put(1, 1);lhm.put(2, 2);lhm.put(3, 3);
        Iterator<Integer> iterator = lhm.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        lhm.remove(1);
        lhm.put(1, 1);
        iterator = lhm.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("HashMap: ");
        Map<Integer, Integer> hm = new HashMap<>(2);
        hm.put(1, 1);hm.put(2, 2);hm.put(3, 3);
        iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        hm.remove(1);
        hm.put(1, 1);
        iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
