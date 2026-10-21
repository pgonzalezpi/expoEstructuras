
class Nodo {
    int valor;
    Nodo anterior;
    Nodo siguiente;

    Nodo(int valor) {
        this.valor = valor;
        this.anterior = null;
        this.siguiente = null;
    }
}

class ListaDoble {

    Nodo cabeza = null;
    Nodo cola = null;
    int tamano = 0;

    // Agregar al inicio
    void agregarInicio(int valor) {
        Nodo nuevo = new Nodo(valor);

        if (cabeza == null) {
            // Caso borde: lista vacía
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }

        tamano++;
    }

    // Agregar al final
    void agregarFinal(int valor) {
        Nodo nuevo = new Nodo(valor);

        if (cola == null) {
            // Caso borde: lista vacía, ya que no debería ser posible tener cabeza != null y cola == null
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }

        tamano++;
    }

    // Buscar un nodo por su valor
    Nodo buscar(int valor) {
        Nodo actual = cabeza;

        while (actual != null) {
            if (actual.valor == valor) {
                return actual;
            }
            actual = actual.siguiente;
        }

        // Caso borde: nodo inexistente
        return null;
    }

    // Eliminar nodo por su valor
    boolean eliminar(int valor) { // requiere casos especiales para eliminar cabeza y cola

        Nodo actual = buscar(valor);

        // Caso borde 1: lista vacía o nodo inexistente
        if (actual == null) {
            return false;
        }

        // Caso borde 2: único nodo
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        }

        // Caso borde 3: eliminar cabeza
        else if (actual == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }

        // Caso borde 4: eliminar cola
        else if (actual == cola) {
            cola = cola.anterior;
            cola.siguiente = null;
        }

        // Caso normal: eliminar nodo intermedio
        else {
            actual.anterior.siguiente = actual.siguiente;
            actual.siguiente.anterior = actual.anterior;
        }

        // Desconectar nodo eliminado
        actual.anterior = null;
        actual.siguiente = null;

        tamano--;
        return true;
    }

    // Recorrer de cabeza a cola
    void imprimirAdelante() {
        Nodo actual = cabeza;

        System.out.print("Adelante: ");

        while (actual != null) {
            System.out.print(actual.valor + " ");
            actual = actual.siguiente;
        }

        System.out.println();
    }

    // Recorrer de cola a cabeza
    void imprimirAtras() {
        Nodo actual = cola;

        System.out.print("Atras: ");

        while (actual != null) {
            System.out.print(actual.valor + " ");
            actual = actual.anterior;
        }

        System.out.println();
    }
}

// Clase principal
public class Main {

    public static void main(String[] args) {

        ListaDoble lista = new ListaDoble();

        // CASO 1: eliminar de una lista vacía
        System.out.println("Caso 1: lista vacia");
        System.out.println(lista.eliminar(10)); // false

        // CASO 2: insertar en lista vacía
        System.out.println("Caso 2: insertar primer nodo");
        lista.agregarInicio(10);
        lista.imprimirAdelante(); // 10

        // CASO 3: eliminar el único nodo
        System.out.println("Caso 3: eliminar unico nodo");
        lista.eliminar(10);
        lista.imprimirAdelante(); // vacía

        // Insertar varios nodos
        lista.agregarFinal(10);
        lista.agregarFinal(20);
        lista.agregarFinal(30);
        lista.agregarFinal(40);

        // Lista: 10 <-> 20 <-> 30 <-> 40
        lista.imprimirAdelante();

        // CASO 4: eliminar cabeza
        System.out.println("Caso 4: eliminar cabeza");
        lista.eliminar(10);
        lista.imprimirAdelante(); // 20 30 40

        // CASO 5: eliminar cola
        System.out.println("Caso 5: eliminar cola");
        lista.eliminar(40);
        lista.imprimirAdelante(); // 20 30

        // Agregar otro nodo
        lista.agregarFinal(50);

        // CASO 6: eliminar nodo intermedio
        System.out.println("Caso 6: eliminar nodo intermedio");
        lista.eliminar(30);
        lista.imprimirAdelante(); // 20 50

        // CASO 7: eliminar nodo inexistente
        System.out.println("Caso 7: nodo inexistente");
        System.out.println(lista.eliminar(99)); // false

        // CASO 8: recorrer hacia atrás
        System.out.println("Caso 8: recorrer al reves");
        lista.imprimirAtras(); // 50 20

        System.out.println("Tamano: " + lista.tamano);
    }
}


