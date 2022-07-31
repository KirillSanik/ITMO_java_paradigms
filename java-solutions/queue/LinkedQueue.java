package queue;

public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;
    private Node headNext;

    protected void enqueueImpl(Object element) {
        Node temp = tail;
        tail = new Node(element, null);
        if (size == 0) {
            head = tail;
        } else {
            temp.next = tail;
        }
    }

    public Object elementImpl() {
        return head.value;
    }

    public void remove() {
        head = head.next;
    }

    protected void initNext() {
        headNext = head;
    }

    protected Object next() {
        Object result = headNext.value;
        headNext = headNext.next;
        return result;
    }

    public void clear() {
        head = tail;
        size = 0;
    }

    private static class Node {
        private Object value;
        private Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}
