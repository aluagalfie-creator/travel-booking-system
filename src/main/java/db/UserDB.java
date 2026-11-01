/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customerhashtable;

/**
 *
 * @author cedie
 */

public class UserDB {
    private CustomerHashTable hashTable;

    public UserDB() {
        hashTable = new CustomerHashTable();
    }

    public boolean registerUser(String username, String password, String fullName) {
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            return false;
        }
        return hashTable.insert(username, password, fullName);
    }

    public boolean authenticateUser(String username, String password) {
        return hashTable.search(username, password);
    }
}
