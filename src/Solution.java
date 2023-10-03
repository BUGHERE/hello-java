import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
        return Arrays.stream(restaurants).filter(restaurant -> ((restaurant[2] ^ 1) & veganFriendly) == 0)
                .filter(restaurant -> restaurant[3] <= maxPrice && restaurant[4] <= maxDistance)
                .sorted((a, b) -> a[1] == b[1] ? b[0] - a[0] : b[1] - a[1])
                .map(restaurant -> restaurant[0])
                .collect(Collectors.toList());
    }
}