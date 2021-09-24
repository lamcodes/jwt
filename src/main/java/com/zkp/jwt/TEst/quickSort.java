package com.zkp.jwt.TEst;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class quickSort {
    public static void main(String[] args) {
        int[] arr = {1,3,5,7,2,4,6,8};

        quickSort(arr,0, arr.length-1);
        System.out.println(Arrays.toString(arr));

        System.out.println(Arrays.toString(smallestK(arr,4)));
    }
    public static int[] smallestK(int[] arr, int k) {
       quickSort(arr,0,arr.length-1);
        int[] ints = new int[k];
        for (int i =0;i<k;i++){
            ints[i]=arr[i];
        }
        return ints;
    }
    static void quickSort(int[] num, int left, int right) {
        if(left >= right){
            return;
        }
        //选取基准数
        Random random = new Random();
        int pivotIndex  = left +random.nextInt(right-left+1);
        //讲基准数放到数组最右边，现在right对应的数组值是基准值了
        swap(num,pivotIndex,right);

        int i = left ;
        int j = right-1;
        while(i<=j){
            //数组左边的小于基准值，左指针向后移动
            if (num[i]<=num[right]){
                i++;
            }else {
                //左指针对应数组值大于基准值，则与右边的交换，并右指针左移
                swap(num,i,j);
                j--;
            }
        }
        swap(num, i, right);
        quickSort(num, left, i-1);
        quickSort(num, i+1, right);
    }
   static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }
    /**
     * 构建最小堆，从最后一个节点的父节点开始调整（也可以对数组中某段连续数据即下标从firstIndex -> endIndex进行建堆）
     * @param firstIndex 起始下标
     * @param enIndex    结束下标
     */
    public void buildMinHeap(List list,int firstIndex, int enIndex) {
        for (int i = enIndex/2; i >= firstIndex; i--) {
            adjustDown(list,i,enIndex);
        }
    }
    /**
     * 调整当前子树，越小的数据往上移动，注意调整的该节点还有子节点的情况，所以需要递归调整。
     * @param parentIndex  父节点的下标
     */
    private void adjustDown(List<Integer> arr,int parentIndex,int endIndex) {
        int left = 2 * parentIndex + 1;
        int right = 2 * parentIndex + 2;
        //最小值的下标
        int minIndex = parentIndex;
        if (left < endIndex && arr.get(left) < arr.get(minIndex)) {
            minIndex = left;
        }
        if (right < endIndex && arr.get(right) < arr.get(minIndex)) {
            minIndex = right;
        }
        if(minIndex == parentIndex){
            return;
        }
        //交换元素
        swaplist(arr,parentIndex,minIndex);
        //递归调整
        adjustDown(arr,minIndex,endIndex);
    }

    void swaplist(List<Integer> list,int x ,int y){
        int temp = list.get(x);
        list.set(x,list.get(y));
        list.set(y,temp);
    }

}
