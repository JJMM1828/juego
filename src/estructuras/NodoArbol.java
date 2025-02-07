package estructuras;

import main.PanelDeJuego;

/**
 * Clase NodoArbol
 *
 * Representa un nodo en el árbol binario de niveles.
 * Cada nodo almacena:
 * - Una instancia de PanelDeJuego (el nivel asociado).
 * - Referencias a los nodos hijos izquierdo y derecho.
 */
public class NodoArbol {
    public PanelDeJuego nivel; // PanelDeJuego asociado a este nodo
    public NodoArbol izquierda, derecha; // Hijos izquierdo y derecho

    /**
     * Constructor que recibe un PanelDeJuego y lo asigna al nodo.
     * @param nivel PanelDeJuego que se almacenará en este nodo.
     */
    public NodoArbol(PanelDeJuego nivel) {
        this.nivel = nivel;
        this.izquierda = null;
        this.derecha = null;
    }
}


