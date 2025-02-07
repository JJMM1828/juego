package main;

import estructuras.ArbolDeNiveles;
import estructuras.NodoArbol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase Juego:
 * Esta clase es la ventana principal del juego. Administra el árbol de niveles,
 * el bucle de actualización (en un hilo) y un Timer que detecta la elección de puerta para cambiar de nivel.
 * Además, verifica si el jugador ha colisionado con un NPC (usando la bandera colisionContraEnemigo del jugador)
 * y, en ese caso, termina el juego mostrando un panel final con el mensaje "PERDISTE".
 * Si el juego se termina porque ya no existen niveles (al intentar cambiar a la izquierda o derecha),
 * se muestra un panel con "¡Felicidades, ganaste!".
 */
public class Juego extends JFrame implements Runnable {

    public ArbolDeNiveles arbol;          // Árbol de niveles balanceado
    private NodoArbol nivelActualNodo;    // Nodo actual del árbol (estructura completa)
    private PanelDeJuego nivelActual;     // Panel de juego actual (extraído del nodo actual)
    public Timer timer;                   // Timer para detectar cambios de nivel
    public Thread hiloJuego;              // Hilo principal para el bucle de actualización
    public boolean enEjecucion = false;   // Controla la ejecución del bucle

    /**
     * Constructor de Juego.
     * Se crean las instancias de PanelDeJuego, se insertan en el árbol, se configura la ventana,
     * se inicia el hilo y se configura el Timer para detectar la elección de puerta.
     */
    public Juego() {
        // Inicializar el árbol de niveles
        arbol = new ArbolDeNiveles();

        // Crear instancias de PanelDeJuego (cada nivel con su mapa, enemigos, etc.)
        PanelDeJuego panelDeJuego0 = new PanelDeJuego("/mapas/mapa01.txt/", 0, new int[]{}, new int[]{});
        PanelDeJuego panelDeJuego1 = new PanelDeJuego("/mapas/mapa01.txt/", 5, new int[]{5,4,6,7,8}, new int[]{5,4,9,6,3});
        PanelDeJuego panelDeJuego2 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego3 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego4 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego5 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});
        PanelDeJuego panelDeJuego6 = new PanelDeJuego("/mapas/mapa02.txt/", 9, new int[]{4,5,6,7,8,9,11,4,6}, new int[]{5,6,7,8,9,10,4,6,8});

        // Insertar cada nivel en el árbol. El árbol se reconstruye balanceado internamente.
        arbol.insertar(panelDeJuego0);
        arbol.insertar(panelDeJuego1);
        arbol.insertar(panelDeJuego2);
        arbol.insertar(panelDeJuego3);
        arbol.insertar(panelDeJuego4);
        arbol.insertar(panelDeJuego5);
        arbol.insertar(panelDeJuego6);

        // Asignar el nodo raíz del árbol como nivel actual
        nivelActualNodo = arbol.raiz;
        nivelActual = nivelActualNodo.nivel;

        // Configurar la ventana principal (JFrame)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("ESCAPE");
        this.add(nivelActual); // Se añade el panel del nivel actual
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Iniciar el hilo principal del juego
        iniciarHiloJuego();

        // Configurar un Timer que cada 100 ms detecta la elección de puerta para cambiar de nivel.
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si la elección de puerta no está vacía, se procede a cambiar de nivel.
                if (!nivelActual.eleccionPuerta.isEmpty()) {
                    if (nivelActual.eleccionPuerta.equals("izquierda")) {
                        if (nivelActualNodo.izquierda != null) {
                            cambiarNivel(nivelActualNodo.izquierda);
                        } else {
                            // Si no hay nivel a la izquierda, se considera que el jugador completó el juego.
                            System.out.println("Fin del juego (No hay nivel a la izquierda).");
                            enEjecucion = false;
                            timer.stop();
                            terminarJuego("ganaste");
                        }
                    } else if (nivelActual.eleccionPuerta.equals("derecha")) {
                        if (nivelActualNodo.derecha != null) {
                            cambiarNivel(nivelActualNodo.derecha);
                        } else {
                            // Si no hay nivel a la derecha, se considera que el jugador completó el juego.
                            System.out.println("Fin del juego (No hay nivel a la derecha).");
                            enEjecucion = false;
                            timer.stop();
                            terminarJuego("ganaste");
                        }
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Inicia el hilo principal de actualización del juego.
     */
    public void iniciarHiloJuego() {
        if (hiloJuego == null) {
            hiloJuego = new Thread(this);
            enEjecucion = true;
            hiloJuego.start();
        }
    }

    /**
     * Detiene el hilo principal del juego.
     */
    public void detenerHiloJuego() {
        enEjecucion = false;
    }

    /**
     * Cambia el nivel actual utilizando el nuevo nodo del árbol.
     * Se actualiza el JFrame removiendo el panel actual y agregando el nuevo.
     *
     * @param nuevoNodo El nodo del árbol que contiene el siguiente nivel.
     */
    private void cambiarNivel(NodoArbol nuevoNodo) {
        // Remover el panel actual del JFrame
        this.remove(nivelActual);
        // Actualizar el nodo actual
        nivelActualNodo = nuevoNodo;
        // Extraer el PanelDeJuego del nodo actual
        nivelActual = nivelActualNodo.nivel;
        // Agregar el nuevo panel a la ventana
        this.add(nivelActual);
        this.revalidate();
        this.repaint();
        // Reiniciar la elección de puerta en el nuevo nivel
        nivelActual.eleccionPuerta = "";
        // Solicitar el foco para que se capturen los eventos de teclado
        nivelActual.requestFocusInWindow();
    }

    /**
     * Bucle principal del juego ejecutado en un hilo a 60 FPS.
     * Actualiza la lógica del juego, redibuja el nivel y verifica las condiciones de fin del juego.
     */
    @Override
    public void run() {
        final int FPS = 60;
        double intervaloDibujo = 1000000000 / FPS; // Intervalo en nanosegundos
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
                // Se llama a terminarNivel() para que el nivel actual determine si debe cambiar de nivel
                nivelActual.terminarNivel();
                delta--;
            }
        }
    }

    /**
     * Actualiza la lógica del juego.
     * Llama a actualizar() del nivel actual y verifica si se ha producido una colisión fatal.
     * Si el jugador colisiona con un NPC, se termina el juego mostrando el panel "PERDISTE".
     * También se verifica la elección de puerta para cambiar de nivel.
     */
    public void actualizarJuego() {
        // Actualiza la lógica interna del nivel actual (movimientos, colisiones, etc.)
        nivelActual.actualizar();

        // Verifica si el jugador colisionó con un NPC (la bandera colisionContraEnemigo se activa en la clase Jugador)
        if (nivelActual.jugador.colisionContraEnemigo) {
            System.out.println("Colisión con NPC detectada. Terminando el juego (PERDISTE).");
            enEjecucion = false;
            timer.stop();
            terminarJuego("perdiste");
            return; // Salir del método para evitar actualizaciones adicionales
        }

        // Verifica si el jugador ha hecho una elección de puerta para cambiar de nivel
        if (!nivelActual.eleccionPuerta.isEmpty()) {
            if (nivelActual.eleccionPuerta.equals("izquierda")) {
                if (nivelActualNodo.izquierda != null) {
                    cambiarNivel(nivelActualNodo.izquierda);
                } else {
                    System.out.println("Fin del juego (No hay nivel a la izquierda).");
                    enEjecucion = false;
                    timer.stop();
                    terminarJuego("ganaste");
                }
            } else if (nivelActual.eleccionPuerta.equals("derecha")) {
                if (nivelActualNodo.derecha != null) {
                    cambiarNivel(nivelActualNodo.derecha);
                } else {
                    System.out.println("Fin del juego (No hay nivel a la derecha).");
                    enEjecucion = false;
                    timer.stop();
                    terminarJuego("ganaste");
                }
            }
        }
    }

    /**
     * Termina el juego mostrando un panel final.
     * Si el resultado es "perdiste", se muestra "PERDISTE".
     * Si el resultado es "ganaste", se muestra "¡Felicidades, ganaste!".
     * Ambos paneles incluyen un botón "Volver a jugar" que reinicia el juego.
     *
     * @param resultado Resultado del juego ("perdiste" o "ganaste").
     */
    public void terminarJuego(String resultado) {
        // Detener la ejecución del juego
        enEjecucion = false;
        if (timer != null) {
            timer.stop();
        }
        // Actualizar la interfaz en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Remover el nivel actual del JFrame
                Juego.this.remove(nivelActual);
                // Crear un panel final para mostrar el mensaje
                JPanel finPanel = new JPanel(new BorderLayout());
                finPanel.setBackground(Color.BLACK);
                finPanel.setPreferredSize(new Dimension(nivelActual.anchoPantalla, nivelActual.alturaPantalla));

                // Crear un JLabel para mostrar el mensaje de fin de juego
                JLabel mensaje = new JLabel("", SwingConstants.CENTER);
                mensaje.setFont(new Font("Arial", Font.BOLD, 36));
                mensaje.setForeground(Color.WHITE);
                if (resultado.equalsIgnoreCase("perdiste")) {
                    mensaje.setText("PERDISTE");
                } else if (resultado.equalsIgnoreCase("ganaste")) {
                    mensaje.setText("¡Felicidades, ganaste!");
                }

                // Crear un botón "Volver a jugar"
                JButton volverButton = new JButton("Volver a jugar");
                volverButton.setFont(new Font("Arial", Font.BOLD, 24));
                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Cerrar la ventana actual y reiniciar el juego creando una nueva instancia de Juego
                        Juego.this.dispose();
                        new Juego();
                    }
                });

                // Agregar el mensaje en el centro y el botón en la parte inferior del panel
                finPanel.add(mensaje, BorderLayout.CENTER);
                finPanel.add(volverButton, BorderLayout.SOUTH);

                // Agregar el panel final al JFrame y actualizar la interfaz
                Juego.this.add(finPanel);
                Juego.this.revalidate();
                Juego.this.repaint();
            }
        });
    }


}

