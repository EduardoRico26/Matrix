package main.java.presentacion;

import javax.swing.SwingUtilities;

public class Matrix {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MenuGUI();
        });

    }
}
