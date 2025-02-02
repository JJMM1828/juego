package main;
import baldosa.*;
import Entidad.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Juego extends JFrame implements Runnable{


    ArbolDeNiveles arbol;
    PanelDeJuego nivelActual;
    public Timer timer;
    public Thread hiloJuego;
    public boolean enEjecucion = false;

    public Juego(){
        arbol = new ArbolDeNiveles();

        PanelDeJuego panelDeJuego0 = new PanelDeJuego("/mapas/mapa01.txt/", 0, new int[]{}, new int[]{});
        PanelDeJuego panelDeJuego1 = new PanelDeJuego("/mapas/mapa01.txt/", 5, new int[]{5,4,6,7,8}, new int[]{5,4,9,6,3});
        PanelDeJuego panelDeJuego2 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego3 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego4 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego5 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego6 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});


        arbol.insertar(panelDeJuego0);
        arbol.insertar(panelDeJuego1);
        arbol.insertar(panelDeJuego2);
        arbol.insertar(panelDeJuego3);
        arbol.insertar(panelDeJuego4);
        arbol.insertar(panelDeJuego5);
        arbol.insertar(panelDeJuego6);

        nivelActual = arbol.raiz;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("ESCAPE");
        this.add(nivelActual);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        iniciarHiloJuego();

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nivelActual.eleccionPuerta.isEmpty()){
                    if(nivelActual.eleccionPuerta.equals("izquierda")){
                        cambiarNivel(nivelActual.izquierda);

                    }
                    if(nivelActual.eleccionPuerta.equals("derecha")){
                        cambiarNivel(nivelActual.derecha);

                    }
                }
            }
        });
        timer.start();
    }

    public void iniciarHiloJuego() {
        if (hiloJuego == null) {
            hiloJuego = new Thread(this);
            enEjecucion = true;
            hiloJuego.start();
        }
    }
    public void detenerHiloJuego() {
        enEjecucion = false;

    }
    public void cambiarNivel(PanelDeJuego siguiente) {
        if (siguiente != null) {
            this.remove(nivelActual);
            nivelActual = siguiente;
            this.add(nivelActual);
            this.revalidate();
            this.repaint();
            nivelActual.eleccionPuerta = "";
            nivelActual.requestFocusInWindow();
        } else {
            detenerHiloJuego();
            this.dispose();
        }
    }

    @Override
    public void run() {
        final int FPS = 60;
        double intervaloDibujo = 1000000000 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempoActual;

        while (enEjecucion) {
            tiempoActual = System.nanoTime();
            delta += (tiempoActual - ultimoTiempo) / intervaloDibujo;
            ultimoTiempo = tiempoActual;

            if (delta >= 1) {
                actualizarJuego();
                repaint();
                nivelActual.terminarNivel();
                delta--;
            }
        }
    }
    private void actualizarJuego() {
        nivelActual.actualizar();
        if (nivelActual.eleccionPuerta != null) {
            if (nivelActual.eleccionPuerta.equals("izquierda")) {
                cambiarNivel(nivelActual.izquierda);
            }
            if (nivelActual.eleccionPuerta.equals("derecha")) {
                cambiarNivel(nivelActual.derecha);
            }
        }
    }

}
