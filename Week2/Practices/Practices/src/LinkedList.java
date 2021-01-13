import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class LinkedList<Item> implements Iterable<Item> {
    public class Node {
        Item value;
        Node next;
    }

    private class LinkedListIterator implements Iterator<Item>
    {
        private Node _current;
        public LinkedListIterator(Node first) {
            super();
            _current = first;
        }

        @Override
        public boolean hasNext() {
            return _current != null;
        }

        @Override
        public Item next() {
            Item value = _current.value;
            _current = _current.next;
            return value;
        }
        
    }

    public boolean isExist(Item item)
    {
        Node cur = _first;
        while(cur != null)
        {
            if(cur.value.equals(item))
            {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public void delete(int k)
    {
        Node itr = _first;
        Node prev = null;
        if(k == 0)
        {
            if(_first != null) _first = _first.next;
            return;
        }
        for(int i = 1; i <= k; i++)
        {
            if(itr != null)
            {
                prev = itr;
                itr = itr.next;
            }
            else{
                return;
            }
        }
        prev.next = itr.next;
    }

    public void push(Item item){
        Node node = new Node();
        node.value = item;
        Node old = _first;
        _first = node;
        _first.next = old;
    }



    @Override
	public Iterator<Item> iterator() {
		return new LinkedListIterator(_first);
    }

    private void removeAfter(Node node)
    {
        if(node != null)
        {
            if(node.next != null)
            {
                node.next = node.next.next;
            }
        }
    }

    private void insertAfter(Node node , Node toInsert){
        toInsert.next = node.next;
        node.next = toInsert;
    }

    public void remove(Item key)
    {
        Node cur = _first;
        Node pre = null;
        while(cur != null)
        {
            if(cur.value.equals(key))
            {
                if(pre == null)
                {
                    _first = cur.next;
                    cur = cur.next;
                    continue;
                }else{
                    pre.next = cur.next;
                    cur = cur.next;
                    continue;
                }
            }
            pre = cur;
            cur = cur.next;
        }
    }
    
    private Node _first;

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(3);
        list.push(4);
        list.push(3);
        list.push(5);
        list.push(6);
        list.push(3);
        list.push(7);
        list.remove(3);
        for (Integer integer : list) {
            StdOut.print(integer);
        }
        StdOut.println(list.isExist(4));
    }
}