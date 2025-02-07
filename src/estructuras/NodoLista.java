package estructuras;

import main.PanelDeJuego;

/**
 * Clase NodoLista
 *
 * Representa un nodo en la lista enlazada que almacena una instancia de PanelDeJuego.
 */
public class NodoLista {

    // PanelDeJuego almacenado en este nodo
    public PanelDeJuego nivel;
    // Referencia al siguiente nodo en la lista
    public NodoLista siguiente;

    public NodoLista(PanelDeJuego nivel) {
        this.nivel = nivel;
        this.siguiente = null;
    }
}

