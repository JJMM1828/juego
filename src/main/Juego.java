package main;

import estructuras.ArbolDeNiveles;
import estructuras.NodoArbol;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Juego extends JFrame implements Runnable {

    public ArbolDeNiveles arbol;
    private NodoArbol nivelActualNodo; // Nodo actual en el árbol
    private PanelDeJuego nivelActual;  // Panel que se muestra (extraído del nodo actual)
    public Timer timer;
    public Thread hiloJuego;
    public boolean enEjecucion = false;

    public Juego(){
        arbol = new ArbolDeNiveles();

        // Crear instancias de PanelDeJuego (usa el constructor parametrizado para niveles completos)
        PanelDeJuego panelDeJuego0 = new PanelDeJuego("/mapas/mapa01.txt/", 0, new int[]{}, new int[]{});
        PanelDeJuego panelDeJuego1 = new PanelDeJuego("/mapas/mapa01.txt/", 5, new int[]{5,4,6,7,8}, new int[]{5,4,9,6,3});
        PanelDeJuego panelDeJuego2 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego3 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego4 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego5 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego6 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});

        // Insertar niveles en el árbol
        arbol.insertar(panelDeJuego0);
        arbol.insertar(panelDeJuego1);
        arbol.insertar(panelDeJuego2);
        arbol.insertar(panelDeJuego3);
        arbol.insertar(panelDeJuego4);
        arbol.insertar(panelDeJuego5);
        arbol.insertar(panelDeJuego6);

        // Asignamos el nodo raíz del árbol como nivel actual
        nivelActualNodo = arbol.raiz;
        nivelActual = nivelActualNodo.nivel;

        // Configurar la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("ESCAPE");
        this.add(nivelActual);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        iniciarHiloJuego();

        // Timer para detectar la elección de puerta y cambiar de nivel
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nivelActual.eleccionPuerta.isEmpty()) {
                    if (nivelActual.eleccionPuerta.equals("izquierda")) {
                        if (nivelActualNodo.izquierda != null) {
                            cambiarNivel(nivelActualNodo.izquierda);
                        } else {
                            System.out.println("Fin del juego (No hay nivel a la izquierda).");
                            enEjecucion = false;
                            timer.stop();
                        }
                    } else if (nivelActual.eleccionPuerta.equals("derecha")) {
                        if (nivelActualNodo.derecha != null) {
                            cambiarNivel(nivelActualNodo.derecha);
                        } else {
                            System.out.println("Fin del juego (No hay nivel a la derecha).");
                            enEjecucion = false;
                            timer.stop();
                        }
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

    // Actualiza la variable de nivel actual usando el nodo del árbol
    private void cambiarNivel(NodoArbol nuevoNodo) {
        // Remueve el nivel actual de la ventana
        this.remove(nivelActual);
        // Actualiza el nodo actual
        nivelActualNodo = nuevoNodo;
        // Extrae el PanelDeJuego del nodo actual
        nivelActual = nivelActualNodo.nivel;
        // Agrega el nuevo nivel a la ventana
        this.add(nivelActual);
        this.revalidate();
        this.repaint();
        // Reinicia la elección de puerta
        nivelActual.eleccionPuerta = "";
        // Solicita el foco para que se capturen los eventos de teclado
        nivelActual.requestFocusInWindow();
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
                // Llamamos a terminarNivel() para que el nivel establezca su elección si corresponde
                nivelActual.terminarNivel();
                delta--;
            }
        }
    }

    public void actualizarJuego() {
        nivelActual.actualizar();
        // La lógica de cambio de nivel se maneja en el Timer, pero también se verifica aquí
        if (!nivelActual.eleccionPuerta.isEmpty()) {
            if (nivelActual.eleccionPuerta.equals("izquierda")) {
                if (nivelActualNodo.izquierda != null) {
                    cambiarNivel(nivelActualNodo.izquierda);
                } else {
                    System.out.println("Fin del juego (No hay nivel a la izquierda).");
                    enEjecucion = false;
                }
            } else if (nivelActual.eleccionPuerta.equals("derecha")) {
                if (nivelActualNodo.derecha != null) {
                    cambiarNivel(nivelActualNodo.derecha);
                } else {
                    System.out.println("Fin del juego (No hay nivel a la derecha).");
                    enEjecucion = false;
                }
            }
        }
    }

}
