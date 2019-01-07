package com.gfc.example;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*1.两数之和*/
        //HashMap map不保证顺序的，之所以看起来排序，是因为刚好hash值有顺序
        int[] nums = {2 , 7 , 11 , 15};
        int target = 9;
        int[] result1 = twoSum1(nums,target);
        int[] result2 = twoSum2(nums,target);
        int[] result3 = twoSum3(nums,target);
        System.out.println(result1.toString());
        System.out.println(result2.toString());
        System.out.println(result3.toString());

        /*2.两数相加  TODO  链表*/
        /*3.无重复字符的最长子串  TODO 效率不高  复杂度O(2n)=O(n)*/
        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstring1(s));
        System.out.println(lengthOfLongestSubstring2(s));


        /*System.out.println(set.size());
        String s="12345";
        String str = "";

        for (int i = 0; i < s.length(); i++){
            str = s.substring(0,i);
        }
        System.out.println(str);*/
    }

    /**
     1.两数之和
     给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     示例:
         给定 nums = [2, 7, 11, 15], target = 9
         因为 nums[0] + nums[1] = 2 + 7 = 9
         所以返回 [0, 1]
     FIXME 推荐 一遍hash表 twoSum2
     */
    /*双重for循环*/
    public static int[] twoSum1(int[] nums,int target){
        int[] result = new int[2];
        boolean flag = false;
        for (int i = 0; i < nums.length - 1; i++){
            if(flag){
                break;
            }
            for (int j = i+1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    result[0] = i;
                    result[1] = j;
                    flag = true;
                    //直接return;
                    break;
                }
            }
        }
        return result;
    }
    /*一遍哈希表 FIXME 推荐*/
    public static int[] twoSum2(int[] nums,int target){
        int[] result = new int[2];
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if(map.containsKey(nums[i])){
                result[0] = map.get(nums[i]);
                result[1] = i;
                return result;
            }
            //存储差值key ， 下标未value  >> 加数，被加数
            map.put(target-nums[i],i);
        }
        return result;
    }
    /*两遍hash表*/
    public static int[] twoSum3(int[] nums,int target){
        int[] result = new int[2];
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            map.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++){
            int cha = target - nums[i];
            //TODO  &&map.get(cha) != i   如果 target 是8  nums 中有4  4+4  结果为同一个数字相加
            if(map.containsKey(cha)&&map.get(cha) != i){
                result[0] = i;
                result[1] = map.get(cha);
                return result;
            }
        }
        return result;
    }


    /**
     2.两数相加
     给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     示例：
         输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
         输出：7 -> 0 -> 8
         原因：342 + 465 = 807
     */
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode p = l1,q = l2,curr = result;
        int carry = 0;
        while (p != null || q != null){
            int x = p!=null?p.val:0;
            int y = q!=null?q.val:0;
            int sum = x + y;
            carry = sum/10;
            curr.next = new ListNode(sum%10);
            curr = curr.next;
            if(p!=null) p = p.next;
            if(q!=null) q = q.next;
        }
        if(carry > 0){
            curr.next = new ListNode(carry);
        }
        return result.next;
    }

    /**
     3.无重复字符的最长子串
     给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     示例 1:
         输入: "abcabcbb"
         输出: 3
         解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     示例 2:
         输入: "bbbbb"
         输出: 1
         解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     示例 3:
         输入: "pwwkew"
         输出: 3
         解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     abcdefaghijk  从b开始重算
     abcdefbghijk  从c开始重算
     abcdefcghijk  从d开始重算
     */
    public static int lengthOfLongestSubstring1(String s) {
        int result = 0;
        String str;
        int startIndex = 0;
        for (int i = 0; i < s.length(); i++){
            str = s.substring(startIndex,i+1);//截取字符串
            char[] chars = str.toCharArray();
            Map<Character,Integer> map = new HashMap();
            for (int j = 0; j < chars.length; j++) {
                if(!map.containsKey(chars[j])){//不包含key
                    map.put(chars[j],j);
                }else{
                    startIndex = startIndex + map.get(chars[j])+1;
                }
            }
            if(map.size()==str.length()&&str.length()>result){
                result = str.length();
            }
        }
        return result;
    }
    public static int lengthOfLongestSubstring2(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        Map<Character,Integer> map = new HashMap();
        for (int i = 0; i < chars.length; i++) {
            if(!map.containsKey(chars[i])){//不包含key
                map.put(chars[i],i);
            }else{
                result = map.size()>result?map.size():result;
                i = map.get(chars[i]);
                map = new HashMap<>();
            }
        }
        if(map.size()>result){
            result = map.size();
        }
        return result;
    }
    //TODO
    public static int lengthOfLongestSubstring3(String s) {
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(map.get(s.charAt(i)), j);
            }
            ans = Math.max(ans, i - j + 1);
            map.put(s.charAt(i), i + 1);
        }
        return ans;
    }
}
