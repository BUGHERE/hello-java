import java.util.ArrayList;
import java.util.List;

public class CopilotTest {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        // 用Stream流找出第一个大于3的数
        int first = java.util.Arrays.stream(arr).filter(x -> x > 3).findFirst().getAsInt();
        System.out.println("first = " + first);
        // 用Stream流找出所有大于3的数
        List<Integer> list = new ArrayList<>();
        java.util.Arrays.stream(arr).filter(x -> x > 3).forEach(x -> list.add(x));
        System.out.println("list = " + list);
        // 用Stream流找出所有大于3的数的和
        int sum = java.util.Arrays.stream(arr).filter(x -> x > 3).sum();
        System.out.println("sum = " + sum);
        // 英国和中国的时差是多少
        java.time.ZoneId china = java.time.ZoneId.of("Asia/Shanghai");
        java.time.ZoneId uk = java.time.ZoneId.of("Europe/London");
        java.time.ZonedDateTime now = java.time.ZonedDateTime.now();
        java.time.ZonedDateTime chinaTime = now.withZoneSameInstant(china);
        System.out.println("chinaTime = " + chinaTime);
        java.time.ZonedDateTime ukTime = now.withZoneSameInstant(uk);
        System.out.println("ukTime = " + ukTime);
        java.time.Duration duration = java.time.Duration.between(chinaTime, ukTime);
        System.out.println("duration = " + duration);
        // 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。注意：答案中不可以包含重复的三元组。
        int[] nums = {-1, 0, 1, 2, -1, -4};
        java.util.Arrays.sort(nums);
        System.out.println("nums = " + java.util.Arrays.toString(nums));
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum1 = nums[i] + nums[l] + nums[r];
                if (sum1 == 0) {
                    res.add(java.util.Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l] == nums[l + 1]) ++l;
                    while (l < r && nums[r] == nums[r - 1]) --r;
                    ++l; --r;
                } else if (sum1 < 0) ++l;
                else --r;
            }
        }
        System.out.println("res = " + res);

    }
}
