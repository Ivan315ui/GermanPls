package tdaLista;

public class NodoDE <E> implements Position <E> {

	private E element;
	private NodoDE next;
	private NodoDE prev;
	
	public NodoDE(E element) {
		this.element = element;
		//next y prev por omisión
	}
	
	/*Consulta de interfaz */
	public E element() {
		return element;
	}
	
	/* Getters y Setters de nodos */
	public NodoDE getNext() {
		return next;
	}
	
	public NodoDE getPrev() {
		return prev;
	}
	
	public void setElement(E element) {
		this.element = element;
	}
	
	public void setNext(NodoDE next) {
		this.next = next;
	}
	
	public void setPrev(NodoDE prev) {
		this.prev = prev;
	}
	
}
