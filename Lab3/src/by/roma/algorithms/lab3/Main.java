package by.roma.algorithms.lab3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        var arrays = create(3, 6, 100);
        for (var arr : arrays) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(merge(divideAndConquer(arrays))));
        System.out.println(Arrays.toString(merge(arrays)));
    }

    public static <T> T[] concatenate(T[] A, T[] B) {
        int aLen = A.length;
        int bLen = B.length;

        @SuppressWarnings("unchecked")
        T[] C = (T[]) Array.newInstance(A.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);

        return C;
    }

    public static int[][] create(int count, int size, int randLimiter) {
        var arrays = new int[count][size];
        Random random = new Random();
        if (randLimiter <= 1) {
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < size; j++) {
                    arrays[i][j] = random.nextInt();
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < size; j++) {
                    arrays[i][j] = random.nextInt() % randLimiter;
                }
            }
        }
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = divideAndConquer(arrays[i]);
        }
        return arrays;
    }

    public static int[][] divideAndConquer(int[][] arrays) {
        int len = arrays.length;
        if (len <= 2) {
            return new int[][]{merge(arrays)};
        }
        int[][] result;
        if (len % 2 == 0) {
            result = concatenate(divideAndConquer(Arrays.copyOfRange(arrays, 0, len / 2)), divideAndConquer(Arrays.copyOfRange(arrays, len / 2, len)));
        } else {
            result = concatenate(divideAndConquer(Arrays.copyOfRange(arrays, 0, len / 2 + 1)), divideAndConquer(Arrays.copyOfRange(arrays, len / 2 + 1, len)));
        }
        return result;
    }

    public static int[] divideAndConquer(int[] array) {
        int len = array.length;
        int[] result;
        if (len % 2 == 0) {
            result = mergeSort(Arrays.copyOfRange(array, 0, len / 2), Arrays.copyOfRange(array, len / 2, len), true);
        } else {
            result = mergeSort(Arrays.copyOfRange(array, 0, len / 2 + 1), Arrays.copyOfRange(array, len / 2 + 1, len), true);
        }
        return result;
    }

    public static int[] merge(int[][] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            arrays[i + 1] = mergeSort(arrays[i], arrays[i + 1], false);
        }
        return arrays[arrays.length - 1];
    }

    public static int[] mergeSort(int[] arr1, int[] arr2, boolean sortCheck) {
        if (sortCheck) {
            if (arr1.length > 2) {
                arr1 = divideAndConquer(arr1);
            } else {
                Arrays.sort(arr1);
            }
            if (arr2.length > 2) {
                arr2 = divideAndConquer(arr2);
            } else {
                Arrays.sort(arr2);
            }
        }
        var tempMergedArray = new int[arr1.length + arr2.length];
        for (int j = 0, k = 0; j < arr1.length || k < arr2.length; ) {
            tempMergedArray[j + k] = (j < arr1.length) ? ((k < arr2.length) ? ((arr1[j] < arr2[k]) ? arr1[j++] : arr2[k++]) : arr1[j++]) : arr2[k++];
        }
        return tempMergedArray;
    }

}