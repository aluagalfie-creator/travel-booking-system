package ds;

import model.User;

/**
 * Hash table of customers keyed by username, with separate chaining.
 * No HashMap and no built-in sorting.
 */
public class CustomerHashTable {
    private static final int CAPACITY = 31;
    private HashNode[] buckets;
    private int size;

    private static class HashNode {
        User user;
        HashNode next;

        HashNode(User user) {
            this.user = user;
        }
    }

    public CustomerHashTable() {
        buckets = new HashNode[CAPACITY];
        size = 0;
    }

    private int getIndex(String key) {
        return (key.hashCode() & 0x7fffffff) % CAPACITY;
    }

    // Returns false if the username already exists.
    public boolean insert(User user) {
        int index = getIndex(user.getUsername());
        HashNode current = buckets[index];
        while (current != null) {
            if (current.user.getUsername().equals(user.getUsername())) {
                return false;
            }
            current = current.next;
        }
        HashNode newNode = new HashNode(user);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        return true;
    }

    // Returns the user, or null if not found.
    public User search(String username) {
        HashNode current = buckets[getIndex(username)];
        while (current != null) {
            if (current.user.getUsername().equals(username)) {
                return current.user;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String username) {
        int index = getIndex(username);
        HashNode current = buckets[index];
        HashNode previous = null;
        while (current != null) {
            if (current.user.getUsername().equals(username)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public User[] getAll() {
        User[] result = new User[size];
        int k = 0;
        for (int i = 0; i < CAPACITY; i++) {
            HashNode current = buckets[i];
            while (current != null) {
                result[k] = current.user;
                k++;
                current = current.next;
            }
        }
        return result;
    }

    public int size() {
        return size;
    }

    public void clear() {
        buckets = new HashNode[CAPACITY];
        size = 0;
    }
}
