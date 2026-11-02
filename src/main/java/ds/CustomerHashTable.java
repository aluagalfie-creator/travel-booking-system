/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.customerhashtable;

/**
 *
 * @author cedie
 */
public class CustomerHashTable {
    private static final int CAPACITY = 10;
    private HashNode[] buckets;

    private static class HashNode {
        String username;
        String password;
        String fullName;
        HashNode next;

        public HashNode(String username, String password, String fullName) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
        }
    }

    public CustomerHashTable() {
        buckets = new HashNode[CAPACITY];
    }

    private int getIndex(String key) {
        return Math.abs(key.hashCode()) % CAPACITY;
    }

    public boolean insert(String username, String password, String fullName) {
        int index = getIndex(username);
        HashNode head = buckets[index];

        // Check if username already exists
        HashNode current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return false; // Duplicate user
            }
            current = current.next;
        }

        // Insert new node at the front
        HashNode newNode = new HashNode(username, password, fullName);
        newNode.next = head;
        buckets[index] = newNode;
        return true;
    }

    public boolean search(String username, String password) {
        int index = getIndex(username);
        HashNode current = buckets[index];

        while (current != null) {
            if (current.username.equals(username) && current.password.equals(password)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
