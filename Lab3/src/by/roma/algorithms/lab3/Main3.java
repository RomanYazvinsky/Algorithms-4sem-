package by.roma.algorithms.lab3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Main3 {
    public static String[] strings = new String[]{
            "Hello, World!", "Algorithms are not very useful", "I love Meth"
    };

    public static void main(String[] args) {
        Integer[][] arrays = create(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), false);
        for (Integer[] arr : arrays) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(linearMerge(divideAndConquer(arrays))));
        System.out.println(bytesToString(linearMerge(divideAndConquer(toByteMatrix(strings)))));
        Integer[] array = create(20, 40, true);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(rearrange(array, countKeysLess(countKeysEqual(array, 40), 40), 40)));
    }

    public static Byte[][] toByteMatrix(String[] strings) {
        Byte[][] bytes = new Byte[strings.length][];
        for (int i = 0; i < strings.length; i++) {
            bytes[i] = toByteArray(strings[i]);
        }
        return bytes;
    }

    public static String[] toStringArray(Byte[][] bytes) {
        String[] strings = new String[bytes.length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = bytesToString(bytes[i]);
        }
        return strings;
    }

    public static Byte[] toByteArray(String string) {
        byte[] str = string.getBytes();
        Byte[] bytes = new Byte[string.length()];
        for (int i = 0; i < string.length(); i++) {
            bytes[i] = str[i];
        }
        bytes = divideAndConquer(bytes);
        return bytes;
    }

    public static String bytesToString(Byte[] array) {
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = array[i];
        }
        return new String(bytes);
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

    public static Integer[] create(int size, int randLimiter, boolean positive) {
        Random random = new Random();
        Integer[] result = new Integer[size];
        if (randLimiter <= 1) {
            for (int j = 0; j < size; j++) {
                result[j] = random.nextInt();
            }
        } else {
            for (int j = 0; j < size; j++) {
                result[j] = positive ? random.nextInt() % (randLimiter / 2) + randLimiter / 2 : random.nextInt() % randLimiter;
            }
        }
        return result;
    }

    public static Integer[][] create(int count, int size, int randLimiter, boolean positive) {
        Integer[][] arrays = new Integer[count][size];
        for (int i = 0; i < count; i++) {
            arrays[i] = create(size, randLimiter, positive);
        }
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = divideAndConquer(arrays[i]);
        }
        return arrays;
    }


    public static <T extends Comparable> T[] linearMerge(T[][] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            arrays[i + 1] = mergeSort(arrays[i], arrays[i + 1], false);
        }
        return arrays[arrays.length - 1];
    }

    public static <T extends Comparable> T[] mergeSort(T[] arr1, T[] arr2, boolean sortCheck) {
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
        T[] tempMergedArray = (T[]) Array.newInstance(arr1.getClass().getComponentType(), arr1.length + arr2.length);
        for (int j = 0, k = 0; j < arr1.length || k < arr2.length; ) {
            tempMergedArray[j + k] = (j < arr1.length) ? ((k < arr2.length) ? ((arr1[j].compareTo(arr2[k]) < 0) ? arr1[j++] : arr2[k++]) : arr1[j++]) : arr2[k++];
        }
        return tempMergedArray;
    }


    public static <T extends Comparable> T[] divideAndConquer(T[] array) {
        int len = array.length;
        T[] result;
        if (len % 2 == 0) {
            result = mergeSort(Arrays.copyOfRange(array, 0, len / 2), Arrays.copyOfRange(array, len / 2, len), true);
        } else {
            result = mergeSort(Arrays.copyOfRange(array, 0, len / 2 + 1), Arrays.copyOfRange(array, len / 2 + 1, len), true);
        }
        return result;
    }

    public static <T extends Comparable> T[][] divideAndConquer(T[][] arrays) {
        int len = arrays.length;
        if (len <= 2) {
            T[][] result = (T[][]) Array.newInstance(arrays.getClass().getComponentType(), 1);
            result[0] = linearMerge(arrays);
            return result;
        }
        T[][] result;
        if (len % 2 == 0) {
            result = concatenate(divideAndConquer(Arrays.copyOfRange(arrays, 0, len / 2)), divideAndConquer(Arrays.copyOfRange(arrays, len / 2, len)));
        } else {
            result = concatenate(divideAndConquer(Arrays.copyOfRange(arrays, 0, len / 2 + 1)), divideAndConquer(Arrays.copyOfRange(arrays, len / 2 + 1, len)));
        }
        return result;
    }


    public static Integer[] countKeysEqual(Integer[] array, Integer range) {
        Integer[] equal = new Integer[range];
        for (int i = 0; i < equal.length; i++) {
            equal[i] = 0;
        }
        for (Integer anArray : array) {
            equal[anArray]++;
        }
        return equal;
    }

    public static Integer[] countKeysLess(Integer[] equal, Integer range) {
        Integer[] less = new Integer[range];
        less[0] = 0;
        for (int i = 1; i < equal.length; i++) {
            less[i] = less[i - 1] + equal[i - 1];
        }
        return less;
    }

    public static Integer[] rearrange(Integer[] array, Integer[] less, Integer range) {
        Integer[] newArr = new Integer[array.length];
        Integer[] next = new Integer[range];
        for (int i = 0; i < range; i++) {
            next[i] = less[i];// + 1;
        }
        for (int i = 0; i < array.length; i++) {
            int key = array[i];
            int index = next[key];
            try {
                newArr[index] = array[i];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(key + " " + index + " " + i);
            }
            next[key]++;
        }
        return newArr;
    }

}
