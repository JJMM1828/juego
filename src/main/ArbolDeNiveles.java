package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArbolDeNiveles {
    public PanelDeJuego raiz;
    private List<PanelDeJuego> niveles;
    private Random rand = new Random();

    public ArbolDeNiveles() {
        niveles = new ArrayList<>();
    }

    // Inserta un nivel asignándole un identificador aleatorio y reconstruye el árbol balanceado
    public void insertar(PanelDeJuego nuevo) {
        int randomId = rand.nextInt(10000);  // Genera un número aleatorio entre 0 y 9999
        nuevo.setNumeroIdentificacion(randomId);
        niveles.add(nuevo);
        reconstruirArbol();
    }

    // Ordena la lista de niveles y reconstruye un árbol balanceado
    private void reconstruirArbol() {
        Collections.sort(niveles, (a, b) -> Integer.compare(a.getNumeroIdentificacion(), b.getNumeroIdentificacion()));
        raiz = construirBalanceado(0, niveles.size() - 1);
    }

    // Construye recursivamente un árbol balanceado a partir de la lista ordenada
    private PanelDeJuego construirBalanceado(int inicio, int fin) {
        if (inicio > fin) return null;
        int medio = (inicio + fin) / 2;
        PanelDeJuego nodo = niveles.get(medio);
        nodo.izquierda = construirBalanceado(inicio, medio - 1);
        nodo.derecha = construirBalanceado(medio + 1, fin);
        return nodo;
    }

    // Método opcional: búsqueda por identificador
    public PanelDeJuego buscar(int id) {
        return buscarRec(raiz, id);
    }

    private PanelDeJuego buscarRec(PanelDeJuego actual, int id) {
        if (actual == null || actual.getNumeroIdentificacion() == id) {
            return actual;
        }
        if (id < actual.getNumeroIdentificacion()) {
            return buscarRec(actual.izquierda, id);
        } else {
            return buscarRec(actual.derecha, id);
        }
    }

    // Método opcional: imprimir el árbol en orden
    public void imprimirEnOrden() {
        imprimirEnOrdenRec(raiz);
        System.out.println();
    }

    private void imprimirEnOrdenRec(PanelDeJuego actual) {
        if (actual != null) {
            imprimirEnOrdenRec(actual.izquierda);
            System.out.print(actual.getNumeroIdentificacion() + " ");
            imprimirEnOrdenRec(actual.derecha);
        }
    }
}
