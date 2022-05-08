package tdaLista;

/**
 * Clase nodo que modela nodos simplemente enlazados hacia adelante.
 * Los elementos que almacena son de tipo E.
 * @author ivane
 *
 * @param <E> Tipo gen�rico del elemento que almacena el nodo.
 */
public class NodoSE <E> implements Position <E>{

	private E element;
	private NodoSE <E> next;
	
	//Constructor
	/**
	 * Crea un nuevo nodo con el elemento pasado por par�metro.
	 * @param element Elemento a almacenar en el nodo.
	 */
	public NodoSE(E element) {
		this.element = element;
		//next null por omisi�n.
	}
	
	//operaci�n de la interface Position
	public E element() {
		return element;
	}
	
	//getters y setters
	
	/**
	 * Fija el elemento almacenado en el nodo al pasado por par�metro.
	 * @param element
	 */
	public void setElement(E element) {
		this.element = element;
	}
	
	/**
	 * Consulta por el siguiente nodo.
	 * @return nodo siguiente.
	 */
	public NodoSE<E> getNext() {
		return next;
	}
	
	/**
	 * Fija el nodo siguiente en el pasado por par�metro.
	 * @param next Nodo que ser� el nuevo siguiente.
	 */
	public void setNext(NodoSE<E> next) {
		this.next = next;
	}
	
	
}
