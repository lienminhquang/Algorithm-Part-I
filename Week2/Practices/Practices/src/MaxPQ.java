import java.util.Random;

import edu.princeton.cs.algs4.StdOut;

public class MaxPQ<Key extends Comparable<Key>> {
    private int _maxSize;
    private Key[] _keys;
    private int _size;
    public MaxPQ(int maxSize){
        _maxSize = maxSize;
        _keys = (Key[])new Comparable[_maxSize+1];
        _size = 0;
    }

    public MaxPQ(Key[] keys)
    {
        _maxSize = keys.length;
        _keys = (Key[])new Comparable[_maxSize+1];
        for(int i = 1; i < _keys.length; i++)
        {
            _keys[i] = keys[i-1];
        }
        _size = keys.length;

        for(int i = _size / 2; i >= 1; i--)
        {
            sink(i);
        }
    }

    public boolean isEmpty() {
        return _size == 0;
    }
    public int size() {
        return _size;
    }

    public void insert(Key key) {
        if(_size < _maxSize)
        {
            _keys[++_size] = key;
            swim(_size);
        }
    }

    public Key delMax() {
        exch(1, _size);
        Key key = _keys[_size];
        _keys[_size--] = null;
        sink(1);
        return key;
    }

    private boolean less(int i, int j) {
        return _keys[i].compareTo(_keys[j]) < 0;
    }
    private void exch(int i, int j) {
        Key tmp = _keys[i];
        _keys[i] = _keys[j];
        _keys[j] = tmp;
    }
    private void swim(int k) {
        while(k > 1) {
            if(less(k / 2, k)) {
                exch(k / 2, k);
                k = k / 2;
            }
            else {
                break;
            }
        }
    }
    private void sink(int k) {
        while(2*k <= _size)
        {
            int i = 2*k;
            if(i + 1 <= _size && less(i, i + 1)) {
                i++;
            }
            if(less(k, i))
            {
                exch(k, i);
                k = i;
            }
            else
            {
                break;
            }
        }
    }
    public void verify()
    {
        for(int i = _size / 2; i >= 1; i--)
        {
            if(less(i, 2 * i))
            {
                StdOut.println("Not a max-oriented heap");
                return;
            }
            else if(((2 * i + 1) <= _size) && less(i, 2 * i + 1))
            {
                StdOut.println("Not a max-oriented heap");
                return;
            }
        }
        StdOut.println("Max-oriented heap");
    }

    public static void main(String[] args) {
        for(int j = 0; j < 100; j++)
        {
            
            Random rd = new Random();
            Integer[] keys=new Integer[10];
            for(int i = 0; i < 10; i++)
            {
                //test.insert(rd.nextInt(10));
                keys[i] = rd.nextInt(10);
            }
            Utilities.printArr(keys);
            MaxPQ<Integer> test = new MaxPQ<>(keys);
            Utilities.printArr(test._keys);
            StdOut.println(test.size());
            test.verify();
            // while(!test.isEmpty())
            // {
            //     StdOut.print(test.delMax() + "-");
            // }
        }
        
    }
}
