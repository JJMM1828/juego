package estructuras;

import main.PanelDeJuego;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafoNiveles {

    private Map<Integer, NodoGrafo> nodos;

    public GrafoNiveles() {
        nodos = new HashMap<>();
    }

    public void agregarNivel(int id, PanelDeJuego nivel) {
        if (!nodos.containsKey(id)) {
            NodoGrafo nuevoNodo = new NodoGrafo(id, nivel);
            nodos.put(id, nuevoNodo);
        }
    }

    public void conectarNiveles(int idOrigen, int idDestino, String llaveNecesaria) {
        NodoGrafo origen = nodos.get(idOrigen);
        NodoGrafo destino = nodos.get(idDestino);

        if (origen == null || destino == null) {
            System.out.println("Uno o ambos nodos no existen.");
            return;
        }

        // Evita conexiones duplicadas
        for (NodoGrafo.Conexion conexion : origen.getConexiones()) {
            if (conexion.getDestino().getId() == idDestino) {
                System.out.println("La conexión ya existe.");
                return;
            }
        }

        origen.añadirConexion(destino, llaveNecesaria);
    }

    public NodoGrafo obtenerNivel(int id) {
        return nodos.get(id);
    }

    public boolean puedeAvanzar(int idOrigen, int idDestino, List<String> llavesJugador) {
        NodoGrafo origen = nodos.get(idOrigen);
        if (origen != null) {
            for (NodoGrafo.Conexion conexion : origen.getConexiones()) {
                if (conexion.getDestino().getId() == idDestino) {
                    if (conexion.getLlaveNecesaria() == null || llavesJugador.contains(conexion.getLlaveNecesaria())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}