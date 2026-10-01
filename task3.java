import java.util.Arrays;

public class task3 {
    float getMid(float[] nums) throws Exception {
        if(nums.length != 3) {
            throw new Exception();
        }
        Arrays.sort(nums);
        return nums[1];
    }
}
