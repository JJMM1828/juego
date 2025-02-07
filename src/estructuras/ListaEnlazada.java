package estructuras;

import main.PanelDeJuego;

/**
 * Clase ListaEnlazada
 *
 * Implementa manualmente una lista enlazada ordenada que almacena instancias de PanelDeJuego.
 * Esta lista se utiliza para reconstruir el árbol balanceado de niveles.
 */
public class ListaEnlazada {

    // Nodo cabeza de la lista
    private NodoLista cabeza;
    // Número de nodos en la lista
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * Inserta un PanelDeJuego de forma ordenada en la lista.
     * La ordenación se basa en el identificador del nivel.
     * @param nuevo PanelDeJuego a insertar.
     */
    public void insertarOrdenado(PanelDeJuego nuevo) {
        NodoLista nuevoNodo = new NodoLista(nuevo);
        // Si la lista está vacía o el primer nodo tiene un identificador mayor
        if (cabeza == null || cabeza.nivel.getNumeroIdentificacion() > nuevo.getNumeroIdentificacion()) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
        } else {
            // Recorrer la lista para encontrar la posición adecuada
            NodoLista actual = cabeza;
            while (actual.siguiente != null && actual.siguiente.nivel.getNumeroIdentificacion() < nuevo.getNumeroIdentificacion()) {
                actual = actual.siguiente;
            }
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
        tamaño++;
    }

    /**
     * Retorna el nodo en la posición dada (índice).
     * @param indice Posición en la lista (0-based).
     * @return NodoLista en ese índice o null si no existe.
     */
    public NodoLista obtenerNodoEnIndice(int indice) {
        NodoLista actual = cabeza;
        for (int i = 0; i < indice && actual != null; i++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    /**
     * Retorna el tamaño (número de nodos) de la lista.
     * @return Tamaño de la lista.
     */
    public int getTamaño() {
        return tamaño;
    }
}
