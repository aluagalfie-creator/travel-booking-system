package ds;

/**
 * Basic linked-list node. SHARED FILE: if the group or the professor already
 * has a Node class, delete this one and use theirs.
 */
public class Node {
    public Object data;
    public Node next;

    public Node(Object data) {
        this.data = data;
        this.next = null;
    }
}
