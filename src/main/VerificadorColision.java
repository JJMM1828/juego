package main;

import Entidad.Entidad;
import objetos.SuperObjeto;
import java.awt.Rectangle;

public class VerificadorColision {
    PanelDeJuego pj;
    SimpleHashMap<String, Boolean> mapaBaldosaColision = new SimpleHashMap<>();
    SimpleHashMap<String, SimpleArrayList<SuperObjeto>> mapaObjeto = new SimpleHashMap<>();
    SimpleHashMap<String, SimpleArrayList<Entidad>> mapaEntidad = new SimpleHashMap<>();

    public VerificadorColision(PanelDeJuego pj) {
        this.pj = pj;
    }

    private void construirMapaBaldosa() {
        if (pj.baldosaM == null) {
            System.out.println("Warning: baldosaM es null, no se puede construir el mapa de baldosas.");
            return;
        }
        mapaBaldosaColision.clear();
        for (int col = 0; col < pj.baldosaM.mapaBaldosaNumero.length; col++) {
            for (int row = 0; row < pj.baldosaM.mapaBaldosaNumero[col].length; row++) {
                int tileNum = pj.baldosaM.mapaBaldosaNumero[col][row];
                if (pj.baldosaM.baldosa[tileNum].colision) {
                    String clave = col + "," + row;
                    mapaBaldosaColision.put(clave, true);
                }
            }
        }
    }

    private String generarClaveCelda(int x, int y) {
        int col = x / pj.tamañoCuadro;
        int row = y / pj.tamañoCuadro;
        return col + "," + row;
    }

    public void verificarBaldosa(Entidad entidad) {
        if (mapaBaldosaColision.isEmpty() && pj.baldosaM != null) {
            construirMapaBaldosa();
        }
        int entidadIzquierdaX = entidad.x + entidad.areaSolida.x;
        int entidadDerechaX  = entidad.x + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArribaY   = entidad.y + entidad.areaSolida.y;
        int entidadAbajoY    = entidad.y + entidad.areaSolida.y + entidad.areaSolida.height;

        int entidadIzquierdaColumna = entidadIzquierdaX / pj.tamañoCuadro;
        int entidadDerechaColumna   = entidadDerechaX / pj.tamañoCuadro;
        int entidadArribaFila       = entidadArribaY / pj.tamañoCuadro;
        int entidadAbajoFila        = entidadAbajoY / pj.tamañoCuadro;

        switch (entidad.direccion) {
            case "arriba":
                entidadArribaFila = (entidadArribaY - entidad.velocidad) / pj.tamañoCuadro;
                if (mapaBaldosaColision.containsKey(entidadIzquierdaColumna + "," + entidadArribaFila) ||
                        mapaBaldosaColision.containsKey(entidadDerechaColumna + "," + entidadArribaFila)) {
                    entidad.colisionEncendida = true;
                }
                break;
            case "abajo":
                entidadAbajoFila = (entidadAbajoY + entidad.velocidad) / pj.tamañoCuadro;
                if (mapaBaldosaColision.containsKey(entidadIzquierdaColumna + "," + entidadAbajoFila) ||
                        mapaBaldosaColision.containsKey(entidadDerechaColumna + "," + entidadAbajoFila)) {
                    entidad.colisionEncendida = true;
                }
                break;
            case "derecha":
                entidadDerechaColumna = (entidadDerechaX + entidad.velocidad) / pj.tamañoCuadro;
                if (mapaBaldosaColision.containsKey(entidadDerechaColumna + "," + entidadArribaFila) ||
                        mapaBaldosaColision.containsKey(entidadDerechaColumna + "," + entidadAbajoFila)) {
                    entidad.colisionEncendida = true;
                }
                break;
            case "izquierda":
                entidadIzquierdaColumna = (entidadIzquierdaX - entidad.velocidad) / pj.tamañoCuadro;
                if (mapaBaldosaColision.containsKey(entidadIzquierdaColumna + "," + entidadArribaFila) ||
                        mapaBaldosaColision.containsKey(entidadIzquierdaColumna + "," + entidadAbajoFila)) {
                    entidad.colisionEncendida = true;
                }
                break;
        }
    }

    private void construirMapaObjetos() {
        mapaObjeto.clear();
        if (pj.obj == null) return;
        for (int i = 0; i < pj.obj.length; i++) {
            if (pj.obj[i] != null) {
                SuperObjeto obj = pj.obj[i];
                int x = obj.mundoX + obj.areaSolida.x;
                int y = obj.mundoY + obj.areaSolida.y;
                int width = obj.areaSolida.width;
                int height = obj.areaSolida.height;
                int startCol = x / pj.tamañoCuadro;
                int startRow = y / pj.tamañoCuadro;
                int endCol = (x + width) / pj.tamañoCuadro;
                int endRow = (y + height) / pj.tamañoCuadro;
                for (int col = startCol; col <= endCol; col++) {
                    for (int row = startRow; row <= endRow; row++) {
                        String key = col + "," + row;
                        SimpleArrayList<SuperObjeto> lista = mapaObjeto.get(key);
                        if (lista == null) {
                            lista = new SimpleArrayList<>();
                            mapaObjeto.put(key, lista);
                        }
                        lista.add(obj);
                    }
                }
            }
        }
    }

    private void construirMapaEntidades(Entidad[] objetivo) {
        mapaEntidad.clear();
        if (objetivo == null) return;
        for (int i = 0; i < objetivo.length; i++) {
            if (objetivo[i] != null) {
                Entidad ent = objetivo[i];
                int x = ent.x + ent.areaSolida.x;
                int y = ent.y + ent.areaSolida.y;
                int width = ent.areaSolida.width;
                int height = ent.areaSolida.height;
                int startCol = x / pj.tamañoCuadro;
                int startRow = y / pj.tamañoCuadro;
                int endCol = (x + width) / pj.tamañoCuadro;
                int endRow = (y + height) / pj.tamañoCuadro;
                for (int col = startCol; col <= endCol; col++) {
                    for (int row = startRow; row <= endRow; row++) {
                        String key = col + "," + row;
                        SimpleArrayList<Entidad> lista = mapaEntidad.get(key);
                        if (lista == null) {
                            lista = new SimpleArrayList<>();
                            mapaEntidad.put(key, lista);
                        }
                        lista.add(ent);
                    }
                }
            }
        }
    }

    public int verificarObjeto(Entidad entidad, boolean jugador) {
        int indice = 999;
        if (pj.obj == null) return indice;
        if (mapaObjeto.isEmpty()) {
            construirMapaObjetos();
        }

        int izquierda = entidad.x + entidad.areaSolida.x;
        int arriba = entidad.y + entidad.areaSolida.y;
        int derecha = izquierda + entidad.areaSolida.width;
        int abajo = arriba + entidad.areaSolida.height;

        switch (entidad.direccion) {
            case "arriba":    arriba -= entidad.velocidad; break;
            case "abajo":     abajo += entidad.velocidad; break;
            case "izquierda": izquierda -= entidad.velocidad; break;
            case "derecha":   derecha += entidad.velocidad; break;
        }

        int startCol = izquierda / pj.tamañoCuadro;
        int startRow = arriba / pj.tamañoCuadro;
        int endCol = derecha / pj.tamañoCuadro;
        int endRow = abajo / pj.tamañoCuadro;

        SimpleHashSet<SuperObjeto> objetosARevisar = new SimpleHashSet<>();
        for (int col = startCol; col <= endCol; col++) {
            for (int row = startRow; row <= endRow; row++) {
                String key = col + "," + row;
                SimpleArrayList<SuperObjeto> lista = mapaObjeto.get(key);
                if (lista != null) {
                    objetosARevisar.addAll(lista);
                }
            }
        }

        Rectangle entidadRect = new Rectangle(izquierda, arriba, entidad.areaSolida.width, entidad.areaSolida.height);
        for (int i = 0; i < objetosARevisar.size(); i++) {
            SuperObjeto obj = objetosARevisar.get(i);
            int objX = obj.mundoX + obj.areaSolida.x;
            int objY = obj.mundoY + obj.areaSolida.y;
            Rectangle objRect = new Rectangle(objX, objY, obj.areaSolida.width, obj.areaSolida.height);
            if (entidadRect.intersects(objRect)) {
                if (obj.colision) {
                    entidad.colisionEncendida = true;
                }
                if (jugador) {
                    for (int j = 0; j < pj.obj.length; j++) {
                        if (pj.obj[j] == obj) {
                            indice = j;
                            break;
                        }
                    }
                }
            }
        }
        return indice;
    }

    public int verificarEntidad(Entidad entidad, Entidad[] objetivo) {
        int indice = 999;
        if (objetivo == null) return indice;
        if (mapaEntidad.isEmpty()) {
            construirMapaEntidades(objetivo);
        }

        int izquierda = entidad.x + entidad.areaSolida.x;
        int arriba = entidad.y + entidad.areaSolida.y;
        int derecha = izquierda + entidad.areaSolida.width;
        int abajo = arriba + entidad.areaSolida.height;

        switch (entidad.direccion) {
            case "arriba":    arriba -= entidad.velocidad; break;
            case "abajo":     abajo += entidad.velocidad; break;
            case "izquierda": izquierda -= entidad.velocidad; break;
            case "derecha":   derecha += entidad.velocidad; break;
        }

        int startCol = izquierda / pj.tamañoCuadro;
        int startRow = arriba / pj.tamañoCuadro;
        int endCol = derecha / pj.tamañoCuadro;
        int endRow = abajo / pj.tamañoCuadro;

        SimpleHashSet<Entidad> entidadesARevisar = new SimpleHashSet<>();
        for (int col = startCol; col <= endCol; col++) {
            for (int row = startRow; row <= endRow; row++) {
                String key = col + "," + row;
                SimpleArrayList<Entidad> lista = mapaEntidad.get(key);
                if (lista != null) {
                    entidadesARevisar.addAll(lista);
                }
            }
        }

        Rectangle entidadRect = new Rectangle(izquierda, arriba, entidad.areaSolida.width, entidad.areaSolida.height);
        for (int i = 0; i < entidadesARevisar.size(); i++) {
            Entidad ent = entidadesARevisar.get(i);
            if (ent == entidad) continue;
            int entIzq = ent.x + ent.areaSolida.x;
            int entArriba = ent.y + ent.areaSolida.y;
            Rectangle entRect = new Rectangle(entIzq, entArriba, ent.areaSolida.width, ent.areaSolida.height);
            if (entidadRect.intersects(entRect)) {
                entidad.colisionEncendida = true;
                for (int j = 0; j < objetivo.length; j++) {
                    if (objetivo[j] == ent) {
                        indice = j;
                        break;
                    }
                }
                break;
            }
        }
        return indice;
    }

    public void verificarJugador(Entidad entidad) {
        entidad.areaSolida.x = entidad.x + entidad.areaSolida.x;
        entidad.areaSolida.y = entidad.y + entidad.areaSolida.y;
        pj.jugador.areaSolida.x = pj.jugador.x + pj.jugador.areaSolida.x;
        pj.jugador.areaSolida.y = pj.jugador.y + pj.jugador.areaSolida.y;

        switch (entidad.direccion) {
            case "arriba":
                entidad.areaSolida.y -= entidad.velocidad;
                if (entidad.areaSolida.intersects(pj.jugador.areaSolida)) {
                    entidad.colisionEncendida = true;
                    pj.jugador.colisionContraEnemigo = true;
                }
                break;
            case "abajo":
                entidad.areaSolida.y += entidad.velocidad;
                if (entidad.areaSolida.intersects(pj.jugador.areaSolida)) {
                    entidad.colisionEncendida = true;
                    pj.jugador.colisionContraEnemigo = true;
                }
                break;
            case "izquierda":
                entidad.areaSolida.x -= entidad.velocidad;
                if (entidad.areaSolida.intersects(pj.jugador.areaSolida)) {
                    entidad.colisionEncendida = true;
                    pj.jugador.colisionContraEnemigo = true;
                }
                break;
            case "derecha":
                entidad.areaSolida.x += entidad.velocidad;
                if (entidad.areaSolida.intersects(pj.jugador.areaSolida)) {
                    entidad.colisionEncendida = true;
                    pj.jugador.colisionContraEnemigo = true;
                }
                break;
        }
        entidad.areaSolida.x = entidad.areaSolidaDefectoX;
        entidad.areaSolida.y = entidad.areaSolidaDefectoY;
        pj.jugador.areaSolida.x = pj.jugador.areaSolidaDefectoX;
        pj.jugador.areaSolida.y = pj.jugador.areaSolidaDefectoY;
    }

    public static class SimpleHashMap<K, V> {
        private static class Node<K, V> {
            K key;
            V value;
            Node<K, V> next;
            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private Node<K, V>[] buckets;
        private int capacity;

        @SuppressWarnings("unchecked")
        public SimpleHashMap() {
            capacity = 16;
            buckets = (Node<K, V>[]) java.lang.reflect.Array.newInstance(Node.class, capacity);
        }

        private int hash(K key) {
            int h;
            if (key instanceof String) {
                String s = (String) key;
                h = 7;
                for (int i = 0; i < s.length(); i++) {
                    h = h * 31 + s.charAt(i);
                }
            } else {
                h = key.hashCode();
            }
            return Math.abs(h) % capacity;
        }

        public void put(K key, V value) {
            int index = hash(key);
            Node<K, V> node = buckets[index];
            if (node == null) {
                buckets[index] = new Node<>(key, value);
            } else {
                Node<K, V> current = node;
                while (true) {
                    if (current.key.equals(key)) {
                        current.value = value;
                        return;
                    }
                    if (current.next == null) break;
                    current = current.next;
                }
                current.next = new Node<>(key, value);
            }
        }

        public V get(K key) {
            int index = hash(key);
            Node<K, V> node = buckets[index];
            while (node != null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        }

        public boolean containsKey(K key) {
            return get(key) != null;
        }

        @SuppressWarnings("unchecked")
        public void clear() {
            buckets = (Node<K, V>[]) java.lang.reflect.Array.newInstance(Node.class, capacity);
        }

        public boolean isEmpty() {
            for (int i = 0; i < capacity; i++) {
                if (buckets[i] != null) return false;
            }
            return true;
        }
    }

    public static class SimpleArrayList<T> {
        private Object[] data;
        private int size;

        public SimpleArrayList() {
            data = new Object[10];
            size = 0;
        }

        public void add(T element) {
            if (size == data.length) {
                Object[] newData = new Object[data.length * 2];
                for (int i = 0; i < data.length; i++) {
                    newData[i] = data[i];
                }
                data = newData;
            }
            data[size++] = element;
        }

        @SuppressWarnings("unchecked")
        public T get(int index) {
            if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
            return (T) data[index];
        }

        public int size() {
            return size;
        }

        public void addAll(SimpleArrayList<T> other) {
            for (int i = 0; i < other.size(); i++) {
                this.add(other.get(i));
            }
        }
    }

    public static class SimpleHashSet<T> {
        private SimpleArrayList<T> elements;

        public SimpleHashSet() {
            elements = new SimpleArrayList<>();
        }

        public void add(T element) {
            if (!contains(element)) {
                elements.add(element);
            }
        }

        public void addAll(SimpleArrayList<T> list) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }

        public boolean contains(T element) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).equals(element)) {
                    return true;
                }
            }
            return false;
        }

        public int size() {
            return elements.size();
        }

        public T get(int index) {
            return elements.get(index);
        }
    }
}