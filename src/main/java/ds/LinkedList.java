package ds;

/**
 * Simple singly linked list built from Node. SHARED FILE: if the group or the
 * professor already has a LinkedList class, delete this one and use theirs.
 */
public class LinkedList {
    private Node head;
    private int size;

    public void add(Object data) {
        Node n = new Node(data);
        if (head == null) {
            head = n;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = n;
        }
        size++;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        size = 0;
    }
}
