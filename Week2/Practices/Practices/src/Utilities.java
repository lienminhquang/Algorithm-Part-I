import edu.princeton.cs.algs4.StdOut;

public class Utilities {
    public static void exchange(Object[] arr, int a, int b)
    {
        Object c = arr[a];
        arr[a] = arr[b];
        arr[b] = c;
    }

    public static boolean isSorted(Integer[] arr) {
        for(int i = 1; i < arr.length; i++)
        {
            if(arr[i] < arr[i - 1])
            {
                return false;
            }
        }
        return true;
    }

    public static void printArr(Object[] a)
    {
        for (Object object : a) {
            StdOut.print(object + " ");
        }
        StdOut.println();
    }
}
