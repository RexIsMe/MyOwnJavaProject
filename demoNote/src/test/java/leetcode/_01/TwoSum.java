package leetcode._01;

import org.junit.Test;

/**
 * @author Rex
 * @title: TwoSum
 * @projectName demoNote
 * @description: TODO
 * @date 2019/7/2315:12
 */
public class TwoSum {


    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] ints = twoSum(nums, target);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }


    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     *
     * 示例:
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int i1 = i + 1; i1 < nums.length; i1++) {
                if(nums[i1] + num == target){
                    return new int[]{i, i1};
                }
            }
        }

        return null;
    }

}
