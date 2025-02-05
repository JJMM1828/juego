package estructuras;

import main.PanelDeJuego;

public class NodoArbol {
    public PanelDeJuego nivel;
    public NodoArbol izquierda, derecha;

    public NodoArbol(PanelDeJuego nivel) {
        this.nivel = nivel;
        this.izquierda = null;
        this.derecha = null;
    }
}

