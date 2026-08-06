/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tiendita;

import view.MenuPrincipal;

/**
 *
 * @author aldo
 */
public class Tiendita {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Establecer Look and Feel Nimbus para una mejor apariencia
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("No se pudo establecer el Look and Feel: " + ex.getMessage());
        }

        // Lanzar el Menú Principal en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
