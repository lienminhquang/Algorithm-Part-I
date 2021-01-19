import java.util.Random;

import javax.lang.model.util.ElementScanner6;

import edu.princeton.cs.algs4.StdOut;

public class QuickSort {

    public static void main(String[] args) {

        int numFailed = 0;
        Random rd = new Random();
        for (int i = 0; i < 1000; i++) {
            Integer[] arr = new Integer[rd.nextInt(100)];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = rd.nextInt(30);
            }
            Utilities.printArr(arr);
            sort(arr);
            Utilities.printArr(arr);
            boolean sorted = Utilities.isSorted(arr);
            StdOut.println("Is sorted: " + sorted);
            numFailed += sorted ? 0 : 1;
            // for(int k = 0; k < arr.length; k++)
            // {
            // StdOut.print(QuickSort.select(arr, k) + " ");
            // }
            StdOut.println();
        }
        StdOut.println("Num failed: " + numFailed);
    }

    private static int partition(Integer[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (a[++i] < a[lo])
                if (i == hi)
                    break;
            while (a[--j] > a[lo])
                if (j == lo)
                    break;
            if (j <= i) {
                break;
            } else {
                Utilities.exchange(a, i, j);
            }
        }
        Utilities.exchange(a, lo, j);
        return j;
    }

    public static Integer select(Integer[] arr, int k) {
        return quickSelect(arr, 0, arr.length - 1, k);
    }

    private static int quickSelect(Integer[] arr, int lo, int hi, int k) {
        if (lo >= hi) {
            // StdOut.println();
            // StdOut.println(lo + "-" + hi + "-" + k);
            return arr[k];
        }
        int mid = partition(arr, lo, hi);
        if (mid == k)
            return arr[k];
        else if (mid > k) {
            return quickSelect(arr, lo, mid - 1, k);
        } else {
            return quickSelect(arr, mid + 1, hi, k);
        }

    }

    private static void quickSort(Integer[] arr, int lo, int hi) {
        if (lo >= hi)
            return;
        int k = partition(arr, lo, hi);
        quickSort(arr, lo, k - 1);
        quickSort(arr, k + 1, hi);
    }

    public static void sort(Integer[] arr) {
        // quickSort(arr, 0, arr.length - 1);
        threeWayQuickSort(arr, 0, arr.length - 1);
    }

    private static void threeWayQuickSort(Integer[] arr, int lo, int hi) {
        if (lo >= hi)
            return;
        // int k = threeWayPartition(arr, lo, hi, newHi, newLo);
        int lt = lo;
        int i = lo;
        int gt = hi;
        Integer v = arr[lo];
        while (i <= gt) {
            if (arr[i] < v) {
                Utilities.exchange(arr, i++, lt++);
            } else if (arr[i] > v) {
                Utilities.exchange(arr, i, gt--);
            } else {
                ++i;
            }
        }
        threeWayQuickSort(arr, lo, lt - 1);
        threeWayQuickSort(arr, gt + 1, hi);
    }
}
