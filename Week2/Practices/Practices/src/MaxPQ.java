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

    public static void main(String[] args) {
        MaxPQ<Integer> test = new MaxPQ<>(10);
        Random rd = new Random();
        for(int i = 0; i < 10; i++)
        {
            test.insert(rd.nextInt(10));
        }
        Utilities.printArr(test._keys);
        StdOut.println(test.size());
        while(!test.isEmpty())
        {
            StdOut.print(test.delMax() + "-");
        }
    }
}
