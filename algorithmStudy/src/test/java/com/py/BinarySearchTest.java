package com.py;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Author py
 * @Date 2024/12/17
 */
public class BinarySearchTest {


    @Test
    public void test01() {
        System.out.println(search(new int[]{2, 5}, 0));
    }

    // 二分查找
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >> 1;
            int val = nums[mid];
            if (val < target) {
                left = mid + 1;
            } else if (val > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    //x 的平方根
    public int mySqrt(int x) {
        if (x < 2) return x;
        int left = 0, right = x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long) mid * mid <= x) {
                left = mid + 1;
            } else if ((long) mid * mid > x) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return right;
    }

    // 猜数字游戏
    public int guessNumber(int n) {
        int fromIndex = 0, toIndex = n;
        while (fromIndex <= toIndex) {
            int mid = (fromIndex + toIndex) >> 1;
            if (mid < n)
                fromIndex = mid + 1;
            else if (mid > n)
                toIndex = mid - 1;
            else
                return mid;
        }
        return -1;
    }


}
