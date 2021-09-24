package com.zkp.jwt.leecode;

public class t1 {
    public static void main(String[] args) {
        int[] arr = {-1,0,3,5,9,12};
        int[] x = {5};
        int t = 9;
        System.out.println(search(x,5));
    }
    static int search(int[] nums, int target) {
        int low =0;
        int hight = nums.length-1;
        while (low<=hight){
           int mid = (low+hight)/2;
           int num = nums[mid];
           if (num==target){
               return mid;
           }
           if (num<target){
               low = mid+1;
           }
           if (num>target){
               hight = mid-1;
           }
        }


        return -1;
    }
}
