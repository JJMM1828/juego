package estructuras;

import main.PanelDeJuego;
import java.util.Random;

/**
 * Clase ArbolDeNiveles
 *
 * Implementa un árbol binario de búsqueda balanceado para almacenar instancias de PanelDeJuego.
 * El árbol se construye a partir de una lista enlazada ordenada (ListaEnlazada) y se actualiza
 * cada vez que se inserta un nuevo nivel.
 */
public class ArbolDeNiveles {
    public NodoArbol raiz;         // Nodo raíz del árbol balanceado
    private ListaEnlazada niveles; // Lista enlazada que almacena los niveles ordenados
    private Random rand = new Random();  // Generador de números aleatorios para asignar identificadores

    public ArbolDeNiveles() {
        niveles = new ListaEnlazada();
    }

    /**
     * Inserta un nuevo PanelDeJuego en el árbol.
     * Se asigna un identificador aleatorio al nivel, se inserta ordenadamente en la lista,
     * y luego se reconstruye el árbol balanceado.
     * @param nuevo PanelDeJuego a insertar.
     */
    public void insertar(PanelDeJuego nuevo) {
        int randomId = rand.nextInt(10000);  // Genera un número aleatorio entre 0 y 9999
        nuevo.setNumeroIdentificacion(randomId);
        niveles.insertarOrdenado(nuevo);
        reconstruirArbol();
    }

    /**
     * Reconstruye el árbol balanceado a partir de la lista enlazada ordenada.
     */
    private void reconstruirArbol() {
        raiz = construirBalanceado(0, niveles.getTamaño() - 1);
    }

    /**
     * Construye recursivamente un árbol balanceado a partir de la lista enlazada ordenada.
     * @param inicio Índice de inicio en la lista.
     * @param fin Índice final en la lista.
     * @return NodoArbol que representa la raíz del subárbol balanceado.
     */
    private NodoArbol construirBalanceado(int inicio, int fin) {
        if (inicio > fin) return null;
        int medio = (inicio + fin) / 2;
        NodoLista medioNodo = niveles.obtenerNodoEnIndice(medio);
        if (medioNodo == null) return null;
        // Crear un nodo del árbol a partir del PanelDeJuego almacenado en el nodo de la lista
        NodoArbol nodo = new NodoArbol(medioNodo.nivel);
        // Recursivamente construir el subárbol izquierdo y derecho
        nodo.izquierda = construirBalanceado(inicio, medio - 1);
        nodo.derecha = construirBalanceado(medio + 1, fin);
        return nodo;
    }

    /**
     * Busca un PanelDeJuego por su identificador.
     * @param id Identificador a buscar.
     * @return PanelDeJuego encontrado o null si no existe.
     */
    public PanelDeJuego buscar(int id) {
        NodoArbol nodoEncontrado = buscarRec(raiz, id);
        return nodoEncontrado != null ? nodoEncontrado.nivel : null;
    }

    /**
     * Método recursivo de búsqueda en el árbol utilizando NodoArbol.
     * @param actual Nodo actual en la búsqueda.
     * @param id Identificador a buscar.
     * @return NodoArbol que contiene el PanelDeJuego o null si no se encuentra.
     */
    private NodoArbol buscarRec(NodoArbol actual, int id) {
        if (actual == null || actual.nivel.getNumeroIdentificacion() == id) {
            return actual;
        }
        if (id < actual.nivel.getNumeroIdentificacion()) {
            return buscarRec(actual.izquierda, id);
        } else {
            return buscarRec(actual.derecha, id);
        }
    }

    /**
     * Imprime el árbol en orden (izquierda - raíz - derecha).
     * Útil para depuración y para verificar la estructura del árbol.
     */
    public void imprimirEnOrden() {
        imprimirEnOrdenRec(raiz);
        System.out.println();
    }

    /**
     * Método recursivo para imprimir el árbol en orden utilizando NodoArbol.
     * @param actual Nodo actual a imprimir.
     */
    private void imprimirEnOrdenRec(NodoArbol actual) {
        if (actual != null) {
            imprimirEnOrdenRec(actual.izquierda);
            System.out.print(actual.nivel.getNumeroIdentificacion() + " ");
            imprimirEnOrdenRec(actual.derecha);
        }
    }
}
