package by.roma.algorithms.lab2;

import java.util.Random;

public class Main {
    public static int[] arr1 = {
            -7, 1, 3, 3, 4, 7, 11, 13
    };
    public static int[] arr2 = {
            -7, 2, 2, 3, 4, 7, 8, 11, 13
    };
    public static int[] arr3 = {
            -7, 1, 2, 3, 5, 7, 10, 13
    };

    public static void main(String[] args) {
        int[] data1 = new int[500];
        int[] data2 = new int[1000];
        int[] data3 = new int[5000];
        Random random = new Random();
        for (int i = 0; i < data1.length; i++) {
            data1[i] = random.nextInt();
        }
        for (int i = 0; i < data2.length; i++) {
            data2[i] = random.nextInt();
        }
        for (int i = 0; i < data3.length; i++) {
            data3[i] = random.nextInt();
        }
        int value = random.nextInt();
        System.out.println(getRunningTime(() -> recursiveLinearSearch(data3, value, 0))+"ns");
        //System.out.println(binarySearchInsertion(arr1, 10));


    }

    public static int betterLinearSearch(int[] data, int value) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == value) return i;
        }
        return -1;
    }

    public static long getRunningTime(Time algo) {
        for (int i = 0; i<5000; i++){
            algo.run();
        }
        long startTime = System.nanoTime();
        algo.run();
        return System.nanoTime() - startTime;
    }

    public static int binarySearch(int[] data, int value) {
        int left = 0, right = data.length;
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (data[middle] <= value)
                left = middle;
            else
                right = middle;
        }
        if (data[left] == value)
            return left;
        return -1;
    }

    public static int binarySearchInsertion(int[] data, int value) {
        int left = 0, right = data.length;
        int middle = 0;
        while (right - left > 1) {
            middle = (left + right) / 2;
            if (data[middle] <= value)
                left = middle;
            else
                right = middle;
        }
        if (data[left] == value)
            return left;
        else {
            if (data[left] < value) return -left - 2;
            return -left - 1;
        }
    }

    public static int sentinelLinearSearch(int[] data, int value) {
        int last = data[data.length - 1];
        int i = 0;
        int n = data.length - 1;
        data[n] = value;
        for (; data[i] != value; i++) ;
        data[n] = last;
        if (i < n || data[n] == value) {
            return i;
        } else {
            return -1;
        }
    }

    public static int recursiveLinearSearch(int[] data, int value, int i) {
        int n = data.length - 1;
        if (i > n) {
            return -1;
        }
        if (data[i] == value) {
            return i;
        }
        return recursiveLinearSearch(data, value, ++i);
    }

    public interface Time {
        int run();
    }
}
