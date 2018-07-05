package com.example.weiche.myapplication;

import android.util.Log;

/**
 * Created by chenjianing on 2018/4/2.
 */

public class TestSort {
    public TestSort(){

    }
    private static final String TAG = "Test";

    private int[] temp;

    public void testSort(){
        int[] data = {3,9,4,1,8,0,5,7,6,2};
        printData(data);
//        insertSort(data);
//        shellSort(data);
//        quickSort(data,0,data.length);
        temp = new int[data.length];
        gbSort(data,0,data.length-1);
        printData(data);
    }

    public void gbSort(int[] data,int lo, int hi){
        if (hi <= lo){
            return;
        }
        int middle = lo + (hi - lo)/2;
        gbSort(data,lo,middle);
        gbSort(data,middle+1,hi);
        mergeSort(data,lo,middle,hi);
    }

    private void mergeSort(int[] data, int lo, int middle, int hi) {
        int i = lo;
        int j = middle+1;
        for (int k = lo; k <= hi; k++){
            temp[k] = data[k];
        }

        for (int k = lo; k <= hi; k++){
            if (j > hi){
                data[k] = temp[i++];
            }else if (i > middle){
                data[k] = temp[j++];
            }else if (temp[i] < temp[j]){
                data[k] = temp[i++];
            }else {
                data[k] = temp[j++];
            }
        }
    }


    public void quickSort(int[] data,int lo,int hi){
        if (hi <= lo+1){
            return;
        }
        int j = probation(data,lo,hi);
        quickSort(data,lo,j);
        quickSort(data,j+1,hi);
    }

    private int probation(int[] data, int lo, int hi) {
        int i = lo;
        int j = hi;
        int key = data[lo];
        while (true){
            while (data[++i] < key){
                if (i == hi-1){
                    break;
                }
            }
            while (data[--j] > key){
                if (j == lo){
                    break;
                }
            }
            if (i >= j){
                break;
            }
            swap(data,i,j);

        }
        swap(data,lo,j);
        return j;
    }

    public void shellSort(int[] data){
        int h = 1;
        while (h < data.length/3){
            h = 3*h+1;
        }
        while (h > 0){
            for (int i = h ; i < data.length; i++){
                for (int j = i; j >= h; j -= h){
                    if (data[j] < data[j-h]){
                        swap(data,j,j-h);
                    }
                }
            }
            h = h/3;
        }
    }

    public void insertSort(int[] data){
        for (int i = 1 ; i < data.length; i++){
            for (int j = i; j > 0; j--){
                if (data[j] < data[j-1]){
                    swap(data,j,j-1);
                }
            }
        }
    }

    private void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public void printData(int[] data){
        StringBuffer sb = new StringBuffer();
        for (int i : data){
            sb.append(i);
        }
        Log.e(TAG,sb.toString());
    }
}
