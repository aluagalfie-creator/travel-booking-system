package gui;

import service.TravelSystem;

/**
 *
 * @author aluag
 */
public class TravelBookSystem {

    public static void main(String[] args) {
        TravelSystem.loadAll(); //BE: load MySQL data into the data structures
        LoginFrame app = new LoginFrame();
        app.setVisible(true);
    }
}
