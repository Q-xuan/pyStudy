package com.py;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author py
 * @Date 2024/12/18
 */
public class BytedanceTest {

    @Test
    public void test01() {
        System.out.println(solution01(new int[]{1, 1, 2, 2, 3, 3, 4, 5, 5}));
        System.out.println(solution01(new int[]{1, 1, 2, 2, 3, 3, 4, 5, 5}) == 4);
        System.out.println(solution01(new int[]{0, 1, 0, 1, 2}) == 2);
    }

    public static int solution01(int[] cards) {
        // 初始化结果变量为0
        int result = 0;

        // 遍历数组，对每个元素进行异或运算
        for (int card : cards) {
            result ^= card;  // 异或运算
        }

        // 返回结果
        return result;
    }

    @Test
    public void test02() {
        System.out.println(solution02(5, 2, new int[]{1, 2, 3, 3, 2}) == 9);//TODO
    }

    public static int solution02(int n, int k, int[] data) {
        int[] dp = new int[n];
        dp[0] = data[0];
        // 遍历每一天，计算到每一天的最小花费
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            // 考虑从前面哪些天携带食物过来更划算
            for (int j = Math.max(0, i - k + 1); j < i; j++) {
                dp[i] = Math.min(dp[i], dp[j] + data[i]);
            }
            // 如果当前天数小于最大携带份数，可以直接每天买当天食物
            if (i < k) {
                dp[i] = Math.min(dp[i], (i + 1) * data[i]);
            }
        }
        return dp[n - 1];
    }

    @Test
    public void test03() {
        System.out.println(solution03("1294512.12412").equals("1,294,512.12412"));
        System.out.println(solution03("1294512.12412"));
        System.out.println(solution03("0000123456789.99").equals("123,456,789.99"));
        System.out.println(solution03("987654321").equals("987,654,321"));
    }

    public static String solution03(String s) {
        int index = s.indexOf('.');
        String temp = "";
        if (index != -1) {
            temp = s.substring(index);
            s = s.substring(0, index);
        }
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < length; i++) {
            if (s.charAt(i) == '0' && isFirst) {
                continue;
            } else {
                isFirst = false;
            }
            if (sb.length() > 0 && i > 0 && (length - i) % 3 == 0) {
                sb.append(",");
            }
            sb.append(s.charAt(i));
        }
        sb.append(temp);
        return sb.toString();
    }

    public void test04() {
        // You can add more test cases here
        System.out.println(solution04(new int[]{123, 456, 789}) == 14);
        System.out.println(solution04(new int[]{123456789}) == 4);
        System.out.println(solution04(new int[]{14329, 7568}) == 10);
    }

    public static int solution04(int[] numbers) {
        // Please write your code here
        // 将每个数字组的奇数和偶数分别存储
        List<List<Integer>> oddNumbers = new ArrayList<>();
        List<List<Integer>> evenNumbers = new ArrayList<>();

        for (int num : numbers) {
            List<Integer> odd = new ArrayList<>();
            List<Integer> even = new ArrayList<>();
            // 将数字组的每一位数字分类为奇数或偶数
            while (num > 0) {
                int digit = num % 10;
                if (digit % 2 == 0) {
                    even.add(digit);
                } else {
                    odd.add(digit);
                }
                num /= 10;
            }
            oddNumbers.add(odd);
            evenNumbers.add(even);
        }
        // 使用递归来尝试所有可能的组合
        return recursiveCombination(oddNumbers, evenNumbers, 0, 0);
    }

    // 递归函数来尝试所有可能的组合
    private static int recursiveCombination(List<List<Integer>> oddNumbers, List<List<Integer>> evenNumbers, int index, int currentSum) {
        // 如果已经遍历完所有数字组
        if (index == oddNumbers.size()) {
            // 判断当前和是否为偶数
            return currentSum % 2 == 0 ? 1 : 0;
        }

        // 当前数字组的奇数和偶数
        List<Integer> odd = oddNumbers.get(index);
        List<Integer> even = evenNumbers.get(index);

        int count = 0;

        // 尝试选择奇数
        for (int num : odd) {
            count += recursiveCombination(oddNumbers, evenNumbers, index + 1, currentSum + num);
        }

        // 尝试选择偶数
        for (int num : even) {
            count += recursiveCombination(oddNumbers, evenNumbers, index + 1, currentSum + num);
        }

        return count;
    }

    @Test
    public void test05() {
        String[] testTitles1 = {"adcdcefdfeffe", "adcdcefdfeff", "dcdcefdfeffe", "adcdcfe"};
        String[] testTitles2 = {"CLSomGhcQNvFuzENTAMLCqxBdj", "CLSomNvFuXTASzENTAMLCqxBdj", "CLSomFuXTASzExBdj", "CLSoQNvFuMLCqxBdj", "SovFuXTASzENTAMLCq", "mGhcQNvFuXTASzENTAMLCqx"};
        String[] testTitles3 = {"abcdefg", "abefg", "efg"};

        System.out.println(solution05(4, "ad{xyz}cdc{y}f{x}e", testTitles1).equals("True,False,False,True"));
        System.out.println(solution05(6, "{xxx}h{cQ}N{vF}u{XTA}S{NTA}MLCq{yyy}", testTitles2).equals("False,False,False,False,False,True"));
        System.out.println(solution05(3, "a{bdc}efg", testTitles3).equals("True,True,False"));
    }

    public static String solution05(int n, String template, String[] titles) {
        List<String> splitArr = split(template);

        StringBuilder result = new StringBuilder();
        for (String title : titles) {
            result.append(matchesTemplate(title, splitArr) ? "True" : "False").append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static List<String> split(String template) {
        List<String> arr = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{[^}]*\\}|[^\\{}]+");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            arr.add(matcher.group());
        }
        return arr;
    }

    public static boolean matchesTemplate(String title, List<String> tempList) {
        int titleIndex = 0;
        for (String temp : tempList) {
            if (temp.startsWith("{") && temp.endsWith("}")) {
                // 通配符部分，跳过通配符的长度
                int wildcardLength = temp.length() - 2; // 减去 {} 的长度
                if (titleIndex + wildcardLength > title.length()) {
                    return false; // 如果通配符部分的长度超过了标题的长度，返回 false
                }
                titleIndex += wildcardLength;
            } else {
                // 固定字符串部分，检查是否匹配
                if (!title.startsWith(temp, titleIndex)) {
                    return false;
                }
                titleIndex += temp.length();
            }
        }
        return titleIndex == title.length();
    }

    @Test
    public void test06() {
        // Add your test cases here

        System.out.println(solution06(new int[]{1, 3, 8, 2, 3, 1, 3, 3, 3, 8}) == 3);
    }

    public static int solution06(int[] array) {
        int candidate = 0;
        int count = 0;

        for (int num : array) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }

    @Test
    public void test07() {
        System.out.println(solution07(3, 4, "abc", "abcd") == 3);
        System.out.println(solution07(4, 2, "abbc", "bb") == 2);
        System.out.println(solution07(5, 4, "bcdea", "abcd") == 4);
        System.out.println(solution07(15, 15, "acabacccaabbcca", "tvbaecosivjdocp") == 4);

    }

    public static int solution07(int n, int m, String s, String c) {
        // write code here
        int count = 0;
        String[] split = s.split("");

        for (int i = 0; i < n; i++) {
            if (c.contains(split[i])) {
                count++;
                //去除掉c中的这个字符
                c = c.replaceFirst(split[i], "");
            }
        }
        return count;
    }

    @Test
    public void test08() {
        System.out.println(solution08(10, 1) == 10);
        System.out.println(solution08(10, 2) == 5);
        System.out.println(solution08(10, 3) == 4);
    }

    public static int solution08(int a, int b) {
        // write code here
        double num = (double) a / b;
        if (num != Math.floor(num)) {
            num += 1;
        }
        return (int) num;
    }

    @Test
    public void test09() {
        System.out.println(solution09(2, 2, 2, new int[]{1, 3}, new int[]{3, 1}, new int[]{3, 4}) == 0);
        System.out.println(solution09(3, 5, 5, new int[]{2, 1, 3}, new int[]{1, 3, 2}, new int[]{10, 7, 8}) == 18);
        System.out.println(solution09(1, 3, 3, new int[]{4}, new int[]{4}, new int[]{5}) == 0);
    }

    //01背包问题
    public static int solution09(int n, int T, int H, int[] t, int[] h, int[] a) {
        // PLEASE DO NOT MODIFY THE FUNCTION SIGNATURE
        // write code here
        int[][][] dp = new int[n + 1][T + 1][H + 1];

        // 事件
        for (int i = 1; i <= n; i++) {
            // 时间
            for (int j = 0; j <= T; j++) {
                // 精力
                for (int k = 0; k <= H; k++) {
                    if (j >= t[i - 1] // 时间对比
                            && k >= h[i - 1] // 精力对比
                    ) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - t[i - 1]][k - h[i - 1]] + a[i - 1]);
                    } else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
                }
            }
        }

        return dp[n][T][H];
    }


    @Test
    public void test10() {
        System.out.println(solution10(3, 1) == 6);
        System.out.println(solution10(2, 2) == 6);
        System.out.println(solution10(4, 3) == 30);
    }

    //14 数组元素之和最小化
    public static int solution10(int n, int k) {
        // PLEASE DO NOT MODIFY THE FUNCTION SIGNATURE
        // write code here
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i * k;
        }
        return sum;
    }


    @Test
    public void test11() {
        System.out.println(solution11("aba", "abb") == 1);
        System.out.println(solution11("abcd", "efg") == 4);
        System.out.println(solution11("xyz", "xy") == 1);
        System.out.println(solution11("hello", "helloworld") == 0);
        System.out.println(solution11("same", "same") == 0);
        System.out.println(solution11("bbbabaaaaa", "baaabaaabaaaba") == 3);
    }

    //15 最少前缀问题
    public static int solution11(String S, String T) {
        // PLEASE DO NOT MODIFY THE FUNCTION SIGNATURE
        // write code here
        // 找到最长公共前缀的长度
        int left = 0, right = 0;
        int operations = 0; // 初始化操作次数

        while (left < S.length() && right < T.length()) {
            if (S.charAt(left) == T.charAt(right)) {
                // 字符匹配，移动 right 指针
                right++;
            } else {
                // 字符不匹配，增加操作次数
                operations++;
                right++;
            }
            // 移动 left 指针
            left++;
        }

        // 处理剩余字符
        operations += S.length() - left;

        return operations;
    }


}
