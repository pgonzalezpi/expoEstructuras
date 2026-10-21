public class CasosBorde {

    static class Node {
        String clave;
        int valor;
        Node ant, sig;

        Node(String clave, int valor) { this.clave = clave; this.valor = valor; }
    }

    static class DoublyLinkedList {
        private Node head = null;
        private Node tail = null;
        private int tamano = 0;

        int size() { return tamano; }

        boolean isEmpty() { return head == null; }

        Node first() { return head; }

        void addFirst(Node nodo) {
            nodo.ant = null;
            nodo.sig = head;
            if (head != null) {
                head.ant = nodo;
            } else {
                tail = nodo;
            }
            head = nodo;
            tamano++;
        }

        void eliminar(Node nodo) {
            if (nodo.ant != null) {
                nodo.ant.sig = nodo.sig;
            } else {
                head = nodo.sig;
            }
            if (nodo.sig != null) {
                nodo.sig.ant = nodo.ant;
            } else {
                tail = nodo.ant;
            }
            nodo.ant = nodo.sig = null;
            tamano--;
        }

        void moverAlFrente(Node nodo) {
            eliminar(nodo);
            addFirst(nodo);
        }

        Node eliminarUltm() {
            if (tail == null) return null;
            Node ultm = tail;
            eliminar(ultm);
            return ultm;
        }

        @Override
        public String toString() {
            if (isEmpty()) return "head -> (vacia) <- tail";
            StringBuilder cadena = new StringBuilder("head -> ");
            for (Node nodo = head; nodo != null; nodo = nodo.sig) {
                cadena.append("[").append(nodo.clave).append(":").append(nodo.valor).append("]");
                if (nodo.sig != null) cadena.append(" <-> ");
            }
            return cadena.append(" <- tail").toString();
        }
    }

    static class CacheLRU {
        static final int MISS = -1;

        private final int capacidad;
        private final java.util.Map<String, Node> mapa = new java.util.HashMap<>();
        private final DoublyLinkedList lista = new DoublyLinkedList();

        CacheLRU(int capacidad) { this.capacidad = capacidad; }

        int get(String clave) {
            Node nodo = mapa.get(clave);
            if (nodo == null) return MISS;
            if (nodo != lista.first()) lista.moverAlFrente(nodo);
            return nodo.valor;
        }

        void put(String clave, int valor) {
            Node existente = mapa.get(clave);
            if (existente != null) {
                existente.valor = valor;
                if (existente != lista.first()) lista.moverAlFrente(existente);
                return;
            }

            Node nodo = new Node(clave, valor);
            mapa.put(clave, nodo);
            lista.addFirst(nodo);

            if (mapa.size() > capacidad) {
                Node victima = lista.eliminarUltm();
                mapa.remove(victima.clave);
            }
        }

        int size() { return lista.size(); }

        @Override
        public String toString() { return lista.toString(); }
    }

    public static void main(String[] args) {
        CacheLRU cache = new CacheLRU(3);

        System.out.println("Inicio:        " + cache);

        cache.put("A", 10);
        System.out.println("put(A,10):     " + cache);

        cache.put("B", 20);
        System.out.println("put(B,20):     " + cache);

        cache.put("C", 30);
        System.out.println("put(C,30):     " + cache);

        int a = cache.get("A");
        System.out.println("get(A) = " + a + ":    " + cache);

        cache.put("D", 40);
        System.out.println("put(D,40):     " + cache);

        int b = cache.get("B");
        System.out.println("get(B) = " + (b == CacheLRU.MISS ? "MISS" : b) + ": " + cache);
    }
}