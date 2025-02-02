package main;
public class ArbolDeNiveles {
    PanelDeJuego raiz;

    public void insertar(PanelDeJuego nuevo) {
        if (raiz == null) {
            raiz = nuevo;
        } else {
            insertarRec(raiz, nuevo);
        }
    }

    public void insertarRec(PanelDeJuego actual, PanelDeJuego nuevo) {
        if (nuevo.numeroIdentificacion < actual.numeroIdentificacion) {
            if (actual.izquierda == null) {
                actual.izquierda = nuevo;
            } else {
                insertarRec(actual.izquierda, nuevo);
            }
        } else {
            if (actual.derecha == null) {
                actual.derecha = nuevo;
            } else {
                insertarRec(actual.derecha, nuevo);
            }
        }
    }
}
