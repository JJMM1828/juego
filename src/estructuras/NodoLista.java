package estructuras;

import main.PanelDeJuego;

public class NodoLista {

        public PanelDeJuego nivel;
        public NodoLista siguiente;

        public NodoLista(PanelDeJuego nivel) {
            this.nivel = nivel;
            this.siguiente = null;
        }
}
