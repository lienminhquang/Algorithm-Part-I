import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Stack<T> implements Iterable<T> {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        while(!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            if(!item.equals("-"))
            {
                stack.push(item);
            }
            else if(!stack.isEmpty()){
                StdOut.print(stack.pop() + " ");
            }
        }

        StdOut.println("(" + stack.size() + " left on the stack)");
    }

    class StackIterator implements Iterator<T>{
        Node node;
        @Override
        public boolean hasNext() {
            return node.next != null;
        }

        @Override
        public T next() {
           node = node.next;
           return node.value;
        }

    }

    public int size()
    {
        return m_size;
    }

    public boolean isEmpty()
    {
        return m_head == null;
    }

    public void push(T item)
    {
        Node node = new Node();
        node.value = item;
        Node oldHead = m_head;
        m_head = node;
        m_head.next = oldHead;
        m_size++;
    }

    public T pop()
    {
        if(m_head != null)
        {
            Node head = m_head;
            m_head = head.next;
            m_size--;
            return head.value;
        }
        return null;
    }

    private class Node
    {
        T value;
        Node next;
    };

    @Override
    public Iterator<T> iterator() {
        StackIterator iterator = new StackIterator();
        iterator.node = m_head;
        return iterator;
    }
    
    private Node m_head;
    private int m_size = 0;
}
