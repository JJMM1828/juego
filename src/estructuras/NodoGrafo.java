package estructuras;

import main.PanelDeJuego;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nodo que representa un nivel del juego
public class NodoGrafo {
    private int id;
    private PanelDeJuego nivel;
    private List<Conexion> conexiones;

    public NodoGrafo(int id, PanelDeJuego nivel) {
        this.id = id;
        this.nivel = nivel;
        this.conexiones = new ArrayList<>();
    }

    public int getId() { return id; }
    public PanelDeJuego getNivel() { return nivel; }

    public void añadirConexion(NodoGrafo destino, String llaveNecesaria) {
        conexiones.add(new Conexion(destino, llaveNecesaria));
    }

    public List<Conexion> getConexiones() { return conexiones; }

    public class Conexion {
        private NodoGrafo destino;
        private String llaveNecesaria;

        public Conexion(NodoGrafo destino, String llaveNecesaria) {
            this.destino = destino;
            this.llaveNecesaria = llaveNecesaria;
        }

        public NodoGrafo getDestino() { return destino; }
        public String getLlaveNecesaria() { return llaveNecesaria; }
    }
}
