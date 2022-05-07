package tdaLista;

import Exceptions.*;

import java.util.Iterator;

/**
 * Clase que modela la estructura de datos Lista, implementada con nodos doblemente enlazados sin centinelas.
 * @author ivane
 *
 * @param <E> Tipo de dato de los elementos de la lista
 */
public class ListaDE <E> implements PositionList<E> {

	/* Atributos de instancia */
	private int size; 				//mantiene size() en O(1)
	private NodoDE head;			//primer elemento
	private NodoDE trail;			//�ltimo elemento
	
	public ListaDE() {
		/* Crea la lista vac�a, instanciando todos los atributos por omisi�n */
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	/* M�todos de recorrido */
	
	public Position<E> first() throws EmptyListException {
		if (size == 0)
			throw new EmptyListException("La lista est� vac�a, no se puede obtener el primer elemento.");
		
		return head;
	}
	
	public Position<E> last() throws EmptyListException {
		if (size == 0)
			throw new EmptyListException("La lista est� vac�a, no se puede obtener el �ltimo elemento.");
		
		return trail;				//O(1), a diferencia de la implementaci�n con nodos simplemente enlazados
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		/* Chequeamos la validez de la posici�n en la lista */
		NodoDE<E> pos = checkPosition(p);
		
		/* Si intento obtener el previo de la cabeza */
		if (pos == head)
			throw new BoundaryViolationException("No se puede obtener el elemento previo del primer elemento.");
		
		//en este punto ya se deber�a poder obtener el previo
		return pos.getPrev();				//O(1), a diferencia de la listaSE que era O(n)
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		/* Verifico que sea una posici�n de la lista */
		NodoDE<E> pos = checkPosition(p);
		
		/* Tiro error si quiero obtener el siguiente del �ltimo elemento */
		if (pos == trail)
			throw new BoundaryViolationException("No se puede obtener el elemento siguiente del �ltimo elemento.");
		
		//en este punto deber�a poder obtener el siguiente
		return pos.getNext();
	}
	
	/* M�todos de actualizaci�n */
	
	public E set(Position<E> p, E element) throws InvalidPositionException {
		/* Verifico que sea una posici�n de la lista */
		NodoDE<E> pos = checkPosition(p);
		
		//Obtengo el elemento que conten�a previamente
		E prevElement = pos.element();
		
		//fijo su elemento al pasado por par�metro
		pos.setElement(element);
		
		//retorno el elemento que se encontraba antes en esa posici�n
		return prevElement;
	}
	
	public void addFirst(E element) {
		//Creo un nuevo nodo que encapsule a element
		NodoDE<E> newHead = new NodoDE<E>(element);
		
		//si la lista no est� vac�a
		if (head != null) {
			//vinculo al resto de la lista con el nuevo primer nodo
			newHead.setNext(head);
			head.setPrev(newHead);
		} else {
			trail = newHead;
		}
		
		//hago del nuevo elemento la cabeza
		head = newHead;							//queda O(1), a diferencia de listaSE
		
		size++;
	}
	
	public void addLast(E element) {
		//Creo un nuevo nodo para encapsular al elemento
		NodoDE<E> newTrail = new NodoDE<E>(element);
		
		//si la lista no est� vac�a
		if (head != null) {
			//vinculo toda la lista al nodo nuevo y a�ado el nodo al final
			trail.setNext(newTrail);
			newTrail.setPrev(trail);
		} else {
			head = newTrail;
		}
		
		//cambio la referencia de last
		trail = newTrail;						//queda O(1), a diferencia de listaSE
		
		size++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		//valido la posici�n como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//creo el nuevo nodo que encapsule al elemento
		NodoDE<E> newNode = new NodoDE<E>(element);
		
		//vinculo la sublista que empezaba con pos, despu�s del nuevo nodo
		newNode.setNext(pos);
		//vinculo la sublista que estaba antes de pos, antes del nuevo nodo
		newNode.setPrev(pos);
		
		//actualizo al nuevo nodo como previo de pos
		pos.setPrev(newNode);					//queda O(1), a diferencia de ListaSE
		
		size++;
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		//valido la posici�n como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//creo el nuevo nodo que encapsule al elemento
		NodoDE<E> newNode = new NodoDE<E>(element);
		
		//vinculo desde el inicio hasta pos atr�s del nuevo nodo
		newNode.setPrev(pos);
		
		//vinculo el resto de la lista despu�s de pos al nuevo nodo
		newNode.setNext(pos.getNext());
		
		//fijo al nuevo nodo como el siguiente de pos
		pos.setNext(newNode);					//queda O(1), a diferencia de ListaSE
		
		size++;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException {
		//valido la posici�n como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//obtengo el elemento de la posici�n a eliminar
		E removed = pos.element();
		
		//si la posici�n es el primer elemento
		if (pos == head) {
			//actualizo el nuevo primer elemento como el segundo
			head = head.getNext();
			//elimino el elemento previo del nuevo primer elemento
			head.setPrev(null);
		} else if (pos == trail) {
			//actualizo el �ltimo elemento
			trail = trail.getPrev();
			//desvinculo el �ltimo elemento de antes, de la lista
			trail.setNext(null);
		} else {
			/* Vinculo ambas partes de la lista con la respectiva otra */
			//vinculo la lista a derecha del nodo a eliminar con el anterior
			pos.getPrev().setNext(pos.getNext());
			//vinculo la lista a izquierda del nodo a eliminar con el siguiente
			pos.getNext().setPrev(pos.getPrev());
		}
		
		//inutilizo la posici�n del par�metro
		pos.setPrev(null);
		pos.setElement(null);
		pos.setNext(null);
		
		size--;
		
		return removed;
	}
	
	/* M�todos de la interfaz Iterable */
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}
	
	public PositionList<Position<E>> positions() {
		/* Para cada elemento de la lista, creo una posici�n y la a�ado a la lista
		 * de posiciones a retornar */
		//creo la lista de posiciones a retornar
		PositionList<Position<E>> positions = new ListaDE<Position<E>>();
		
		//si la lista no est� vac�a
		if (size != 0) {
			//creo un cursor que empiece en el primer elemento
			NodoDE<E> cursor = head;
			
			do {
				//agrego la posici�n actual a la lista de posiciones
				positions.addLast(cursor);
				//paso a la siguiente posicion (null si estoy en la �ltima para cortar)
				cursor = cursor != trail ? cursor.getNext() : null;
			} while (cursor != null);
		}
		
		return positions;
	}
	
	/* M�todos privados */
	private NodoDE<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		NodoDE<E> validPosition;
		
		/* Si la posici�n con la que se trabaja es una referencia nula o fue eliminada previamente, tiramos una excepci�n */
		if (pos == null || pos.element() == null)
			throw new InvalidPositionException("No es una posici�n v�lida en la lista.");
		
		try {
			//intentamos el casteo
			validPosition = (NodoDE<E>) pos;
			
		} catch (ClassCastException e) {
			//Si no se pudo realizar el casteo, no es una posici�n de la lista, entonces la propago como InvalidPositionException
			throw new InvalidPositionException("No es una posici�n compatible en la lista");
		}
		
		return validPosition;
	}
	
}


