package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setTitle("ESCAPE");

        PanelDeJuego panelDeJuego = new PanelDeJuego();
        ventana.add(panelDeJuego);

        ventana.pack();

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        panelDeJuego.setupJuego();
        panelDeJuego.iniciarHiloJuego();

    }
}