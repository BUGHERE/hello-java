import java.util.Arrays;

class Solution {
    public int minCount(int[] coins) {
        return Arrays.stream(coins).reduce(0, (r, a)->r+(a+1)/2);
    }
}