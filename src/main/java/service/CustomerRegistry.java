package service;

import db.UserDB;
import ds.CustomerHashTable;
import model.User;

public class CustomerRegistry {
    private CustomerHashTable table = new CustomerHashTable();
    private UserDB db = new UserDB();

    // Called by TravelSystem.loadAll() on startup. Loads every user, admin included.
    public void loadFromDatabase() {
        table.clear();
        User[] all = db.getAllUsers();
        for (int i = 0; i < all.length; i++) {
            table.insert(all[i]);
        }
    }

    // Returns false if the username is taken or the database fails.
    public boolean register(User u) {
        if (table.search(u.getUsername()) != null) {
            return false;
        }
        if (!db.addUser(u)) {
            return false;
        }
        table.insert(u);
        return true;
    }

    public User getByUsername(String username) {
        return table.search(username);
    }

    public boolean update(User u) {
        User old = getById(u.getUserId());
        if (old == null) {
            return false;
        }
        if (!old.getUsername().equals(u.getUsername()) && table.search(u.getUsername()) != null) {
            return false;
        }
        if (!db.updateUser(u)) {
            return false;
        }
        table.remove(old.getUsername());
        table.insert(u);
        return true;
    }

    // The admin account cannot be deleted.
    public boolean delete(int userId) {
        User old = getById(userId);
        if (old == null || old.getRole().equals("ADMIN")) {
            return false;
        }
        if (!db.deleteUser(userId)) {
            return false;
        }
        table.remove(old.getUsername());
        return true;
    }

    public User[] getAll() {
        return table.getAll();
    }

    private User getById(int userId) {
        User[] all = table.getAll();
        for (int i = 0; i < all.length; i++) {
            if (all[i].getUserId() == userId) {
                return all[i];
            }
        }
        return null;
    }
}
