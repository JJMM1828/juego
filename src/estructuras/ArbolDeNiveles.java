package estructuras;

import main.PanelDeJuego;
import java.util.Random;

public class ArbolDeNiveles {
    public NodoArbol raiz;
    private ListaEnlazada niveles;
    private Random rand = new Random();

    public ArbolDeNiveles() {
        niveles = new ListaEnlazada();
    }

    // Inserta un nivel asignándole un identificador aleatorio y reconstruye el árbol balanceado
    public void insertar(PanelDeJuego nuevo) {
        int randomId = rand.nextInt(10000);  // Número aleatorio entre 0 y 9999
        nuevo.setNumeroIdentificacion(randomId);
        niveles.insertarOrdenado(nuevo);
        reconstruirArbol();
    }

    // Reconstruye el árbol balanceado a partir de la lista ordenada
    private void reconstruirArbol() {
        raiz = construirBalanceado(0, niveles.getTamaño() - 1);
    }

    // Construye recursivamente un árbol balanceado a partir de la lista enlazada ordenada
    private NodoArbol construirBalanceado(int inicio, int fin) {
        if (inicio > fin) return null;
        int medio = (inicio + fin) / 2;
        NodoLista medioNodo = niveles.obtenerNodoEnIndice(medio);
        if (medioNodo == null) return null;
        NodoArbol nodo = new NodoArbol(medioNodo.nivel);
        nodo.izquierda = construirBalanceado(inicio, medio - 1);
        nodo.derecha = construirBalanceado(medio + 1, fin);
        return nodo;
    }

    // Método para buscar un nivel por su identificador
    public PanelDeJuego buscar(int id) {
        NodoArbol nodoEncontrado = buscarRec(raiz, id);
        return nodoEncontrado != null ? nodoEncontrado.nivel : null;
    }

    // Método recursivo de búsqueda utilizando NodoArbol
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

    // Método para imprimir el árbol en orden (izquierda - raíz - derecha)
    public void imprimirEnOrden() {
        imprimirEnOrdenRec(raiz);
        System.out.println();
    }

    // Método recursivo para imprimir el árbol en orden utilizando NodoArbol
    private void imprimirEnOrdenRec(NodoArbol actual) {
        if (actual != null) {
            imprimirEnOrdenRec(actual.izquierda);
            System.out.print(actual.nivel.getNumeroIdentificacion() + " ");
            imprimirEnOrdenRec(actual.derecha);
        }
    }
}

