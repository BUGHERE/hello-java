package Algorightm;

/**
 * 现有一种使用英语字母的外星文语言，这门语言的字母顺序与英语顺序不同。
 *
 * 给定一个字符串列表 words ，作为这门语言的词典，words 中的字符串已经 按这门新语言的字母顺序进行了排序 。
 *
 * 请你根据该词典还原出此语言中已知的字母顺序，并 按字母递增顺序 排列。若不存在合法字母顺序，返回 "" 。若存在多种可能的合法字母顺序，返回其中 任意一种 顺序即可。
 *
 * 字符串 s 字典顺序小于 字符串 t 有两种情况：
 *
 * 在第一个不同字母处，如果 s 中的字母在这门外星语言的字母顺序中位于 t 中字母之前，那么s 的字典顺序小于 t 。
 * 如果前面 min(s.length, t.length) 字母都相同，那么 s.length < t.length 时，s 的字典顺序也小于 t 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/Jf1JuT
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

import java.util.*;

class TopoSortBfs {  // 拓扑排序+bfs
    HashMap<Character, List<Character>> map = new HashMap<>();  // 存储有向边
    HashMap<Character, Integer> inter = new HashMap<>();  // 存储每个点入度（如果有）
    public String alienOrder(String[] words) {
        int len = words.length;
        String ans = "";
        for (String word : words) {  // 将所有出现的char存入map（防止最后结果不完整）
            for (char c : word.toCharArray()) {
                map.putIfAbsent(c, new ArrayList<>());
            }
        }
        for (int i = 1; i < len; i++) {  // 建图（map和inter：边和入度）
            String last = words[i-1];
            String cur = words[i];
            int minLen = Math.min(last.length(), cur.length());
            int index = 0;
            while (index < minLen) {
                if (last.charAt(index) != cur.charAt(index)) {
                    map.get(last.charAt(index)).add(cur.charAt(index));
                    inter.put(cur.charAt(index), inter.getOrDefault(cur.charAt(index), 0)+1);
                    break;
                }
                ++index;
            }
            if (index == minLen && last.length() > cur.length())
                return "";
        }
        Queue<Character> q = new ArrayDeque<>();
        Set<Character> keys = map.keySet();
        for (Character key : keys) {  // 把所有入度为0的点先拿出来，放入queue
            if (!inter.containsKey(key))
                q.offer(key);
        }
        while (!q.isEmpty()) {  // 不断地从queue拿取首部
            Character poll = q.poll();
            ans += poll;
            // System.out.println(poll);
            List<Character> cs = map.get(poll);  // 该点的边对应的所有终点
//            if (cs == null) continue;
            for (int i = 0; i < cs.size(); i++) {  // 把所有终点的入度都-1
                Character c = cs.get(i);
                inter.put(c, inter.get(c)-1);
                if (inter.get(c) == 0)  // 如果入度为0，则加入queue
                    q.offer(c);
            }
        }
        return ans.length() == map.size() ? ans : "";
    }
}

class TopoSortDfs {  // 拓扑排序+dfs
    int VISITING = 1, VISITED = 2;
    HashMap<Character, List<Character>> map = new HashMap<>();
    HashMap<Character, Integer> state = new HashMap<>();
    String ans = "";
    public String alienOrder(String[] words) {
        int len = words.length;
        for (String word : words) {
            for (char c : word.toCharArray()) {
                map.putIfAbsent(c, new ArrayList<>());
                state.put(c, 0);
            }
        }
        for (int i = 1; i < len; i++) {
            String last = words[i-1];
            String cur = words[i];
            int minLen = Math.min(last.length(), cur.length());
            int index = 0;
            while (index < minLen) {
                if (last.charAt(index) != cur.charAt(index)) {
                    map.get(last.charAt(index)).add(cur.charAt(index));
                    break;
                }
                ++index;
            }
            if (index == minLen && last.length() > cur.length())
                return "";
        }
        Set<Character> keys = map.keySet();
        for (Character key : keys) {
            if (state.get(key) == 0)  // 如果未访问该点，那么dfs
                dfs(key);
        }
        return ans.length() == map.size() ? new StringBuilder(ans).reverse().toString() : "";
    }
    public void dfs(Character cur) {
        state.put(cur, VISITING);  // 设置当前节点为正在访问
        List<Character> cs = map.get(cur);  // 获取边的所有终点
        for (int i = 0; i < cs.size(); i++) {
            Character c = cs.get(i);
            if (state.get(c) == 0) {  // 如果未访问该终点，那么dfs
                dfs(c);
            }
            else if (state.get(c) == VISITING) {  // 如果正在访问说明，存在环，直接返回
                return;
            }
            // 还有一种情况是等于VISITED，已访问过该终点，说明已经加入到ans中，则不用管即可
        }
        ans += cur;  // ans不是最终的结果，最后需要reverse
        state.put(cur, VISITED);
    }
}
