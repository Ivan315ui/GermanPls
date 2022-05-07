package tdaLista;

/**
 * Clase nodo que modela nodos simplemente enlazados hacia adelante.
 * Los elementos que almacena son de tipo E.
 * @author ivane
 *
 * @param <E> Tipo genérico del elemento que almacena el nodo.
 */
public class NodoSE <E> implements Position <E>{

	private E element;
	private NodoSE <E> next;
	
	//Constructor
	/**
	 * Crea un nuevo nodo con el elemento pasado por parámetro.
	 * @param element Elemento a almacenar en el nodo.
	 */
	public NodoSE(E element) {
		this.element = element;
		//next null por omisión.
	}
	
	//operación de la interface Position
	public E element() {
		return element;
	}
	
	//getters y setters
	
	/**
	 * Fija el elemento almacenado en el nodo al pasado por parámetro.
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
	 * Fija el nodo siguiente en el pasado por parámetro.
	 * @param next Nodo que será el nuevo siguiente.
	 */
	public void setNext(NodoSE<E> next) {
		this.next = next;
	}
	
	
}
