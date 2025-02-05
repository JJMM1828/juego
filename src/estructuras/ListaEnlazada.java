package estructuras;

import main.PanelDeJuego;

public class ListaEnlazada {

        private NodoLista cabeza;
        private int tamaño;

        public ListaEnlazada() {
            cabeza = null;
            tamaño = 0;
        }

        public void insertarOrdenado(PanelDeJuego nuevo) {
            NodoLista nuevoNodo = new NodoLista(nuevo);
            if (cabeza == null || cabeza.nivel.getNumeroIdentificacion() > nuevo.getNumeroIdentificacion()) {
                nuevoNodo.siguiente = cabeza;
                cabeza = nuevoNodo;
            } else {
                NodoLista actual = cabeza;
                while (actual.siguiente != null && actual.siguiente.nivel.getNumeroIdentificacion() < nuevo.getNumeroIdentificacion()) {
                    actual = actual.siguiente;
                }
                nuevoNodo.siguiente = actual.siguiente;
                actual.siguiente = nuevoNodo;
            }
            tamaño++;
        }

        public NodoLista obtenerNodoEnIndice(int indice) {
            NodoLista actual = cabeza;
            for (int i = 0; i < indice && actual != null; i++) {
                actual = actual.siguiente;
            }
            return actual;
        }

        public int getTamaño() {
            return tamaño;
        }


}
