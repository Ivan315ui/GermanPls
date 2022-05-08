package tdaLista;

import Exceptions.*;

import java.util.Iterator;

public class ListaSE <E> implements PositionList <E> {

	//atributos de instancia
	private NodoSE<E> head;		//primer nodo de la lista
	private int size;			//mantiene size en O(1)
	
	
	/* Constructor */
	/**
	 * Crea una nueva lista vacía.
	 */
	public ListaSE() {}			//instanciación de attrs por omisión.
	
	/* Operaciones de la interfaz */
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return head == null;	//si head es null, no hay nodos
	}
	
	/* Operaciones de recorrido */
	public Position<E> first() throws EmptyListException {
		if (head == null)
			throw new EmptyListException("La lista está vacía, no tiene primer elemento.");
		
		//si no está vacía, head es el primero nodo
		return head;
	}
	
	public Position<E> last() throws EmptyListException {
		if (head == null)
			throw new EmptyListException("La lista está vacía, no tiene último elemento.");
		
		//si no está vacía:
		NodoSE <E> actual = head;			//desde el primer nodo

		//mientras el elemento actual tenga siguiente:
		while (actual.getNext() != null)
			//avanzo al siguiente elemento
			actual = actual.getNext();
		
		//cuando salgo del bucle estoy en el último elemento, así que lo retorno
		return actual;
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		//verificar que sea una posición valida
		NodoSE <E> pos = checkPosition(p);
		
		//BoundaryViolationException p = head;
		if (pos == head)
			throw new BoundaryViolationException("No se puede obtener el elemento previo al primer elemento.");
		
		//si llegamos a este punto, entonces es una posición a la que se le puede encontrar el previo
		NodoSE <E> actual = head;			//iniciamos el cursor en el inicio
		/*
		 * mientras el nodo actual no tenga como siguiente al pasado por parámetro, 
		 * entonces no es el buscado y avanzamos al siguiente.
		 */
		while (actual.getNext() != pos)
			actual = actual.getNext();
		
		//al final del bucle anterior encontré el previo, luego lo retorno
		return actual;		
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		//verifico que sea una posición válida
		NodoSE<E> pos = checkPosition(p);
		
		//si la posición es la última, tiro error
		try {
			if (pos == last())
				throw new BoundaryViolationException("No se puede obtener el elemento siguiente de la última posición.");
		//catch inalcanzable, se supone que si la posición es válida, hay al menos un elemento en la lista.
		} catch (EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//en este punto es una posición válida de la lista, así que simplemente retorno el siguiente
		return pos.getNext();
	}
	
	
	/**
	 * Verifica si una posición es válida en la lista, es decir, es un nodo
	 * @param position Posición cuya validez se verificará.
	 * @return Posición pasada por parámetro como nodo, si resultó ser válida.
	 * @throws InvalidPositionException si la posición resulta ser inválida.
	 */
	private NodoSE<E> checkPosition(Position<E> position) throws InvalidPositionException {
		NodoSE <E> validPosition;
		
		//intentamos el casteo a nodo (será exitoso si era una posición válida
		try {
			validPosition = (NodoSE<E>) position;
			
			//puede suceder que sea una posición compatible con la lista pero nula, así que vamos a verificar eso
			if (validPosition == null)
				//llegara a cumplirse, le mando error por no ser válida 
				throw new InvalidPositionException("La posición pasada por parámetro es nula.");
			
			/*
			 * TODO: preguntar si es necesario que verifique que no sea una posición previamente eliminada, es decir
			 * 		 validPosition.element() = null
			 */
			
			
		} catch (ClassCastException e) {
			//en caso de que no se pueda realizar el casteo, significa que no era una posición válida
			throw new InvalidPositionException("La posición pasada por parámetro no es válida.");
		}
		
		//si llegamos a este punto, significa que es una posición válida en la lista
		return validPosition; 
	}
	
	public void addFirst(E element) {
		//creo el nodo que encapsula el elemento
		NodoSE<E> newHead = new NodoSE<E>(element);
		
		//pongo head como el siguiente del nuevo primer elemento
		newHead.setNext(head);
		//actualizo la referencia de head al nuevo primer elemento
		head = newHead;
		//incremento la cantidad de nodos
		size++;
	}
	
	public void addLast(E element) {
		//creo el nodo que encapsule el elemento
		NodoSE<E> newLast = new NodoSE<E>(element);
		
		//aprovecho la operación last() para obtener el último nodo de la lista
		try {
			//como es el last de esta lista, sé que me retorna un nodo y no cualquier otra posición
			NodoSE<E> last = (NodoSE<E>) last();
			//asigno newLast como siguiente del último actual
			last.setNext(newLast);
		//si me tira error, es porque la lista está vacía
		} catch (EmptyListException e) {
			//entonces asigno el nuevo elemento a head
			head = newLast;
		}
		
		//incremento la cantidad de nodos
		size++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		//verifico que la posición sea válida
		NodoSE<E> pos = checkPosition(p);
		
		//para este punto, se supone que la posición es válida, luego creo el nodo que encapsula element
		NodoSE<E> newPrev = new NodoSE<E>(element);
		
		//busco el elemento en el cual se debe insertar
		if (pos == head)	//si es el primero, simplemente puedo usar addFirst
			addFirst(element);
		else {
			//desde el inicio, busco el anterior a la posición pasada por parámetro
			NodoSE<E> previo = head;
			
			while (previo.getNext() != pos)		//mientras no sea el buscado			
				previo = previo.getNext();		//paso al siguiente
			
			//actualizo las referencias como corresponde
			previo.setNext(newPrev);			//pongo newPrev justo antes de pos
			newPrev.setNext(pos);				//vinculo el resto de la lista al nuevo nodo
			
		}
		
		//incremento la cantidad de nodos
		size++;
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		//verifico que sea una posición válida de la lista
		NodoSE<E> pos = checkPosition(p);
		
		//creo el nodo que contiene element
		NodoSE<E> newNext = new NodoSE<E>(element);
		
		//actualizo las referencias como corresponde
		newNext.setNext(pos.getNext());				//vinculo el resto de la lista
		pos.setNext(newNext);						//pongo newNext justo después de la posición del parámetro.
		
		//incremento la cantidad de nodos
		size++;
	}
	
	public E set(Position<E> p, E element) throws InvalidPositionException {
		//verifico que la posición sea válida
		NodoSE <E> pos = checkPosition(p);
		
		//guardo el elemento que estaba en pos
		E prevElement = pos.element();
		
		//fijo element en pos
		pos.setElement(element);
		
		//retorno el elemento que estaba antes
		return prevElement;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException {
		//verifico que sea válida
		NodoSE<E> pos = checkPosition(p);
		
		//guardo el elemento que estaba antes
		E posElem = pos.element();
		
		//si la posición a eliminar es la primera:
		if (pos == head)
			//head ahora es el elemento siguiente
			head = head.getNext();
		//sino, busco cuál es el elemento anterior al que tengo que eliminar
		else {
			//desde head, me fijo si el siguiente es el que quiero eliminar
			NodoSE<E> actual = head;		//me paro sobre head
			
			//avanzo mientras el siguiente del elemento actual no sea el que quiero eliminar
			while (actual.getNext() != pos)
				actual = actual.getNext();
			
			//después del while, tengo el anterior al que quiero eliminar, luego actualizo las referencias
			actual.setNext(pos.getNext());		//vinculo el anterior al que eliminé al resto de la lista
		}

		//inutilizo el nodo que eliminé
		pos.setNext(null);			//desvinculo el resto de la lista del nodo eliminado
		pos.setElement(null);		//elimino la información del nodo
		
		//decremento la cantidad de nodos
		size--;
		
		//retorno el elemento que estaba en la posición
		return posElem;
	}
	
	/* Métodos de la interfaz iterable */
	
	/* Método de la interfaz Iterable */
	public Iterator<E> iterator() {
		//derivo el comportamiento del iterador en la clase ElementIterator
		return new ElementIterator<E>(this);
	}
	
	/*
	 * Retorna una colección iterable de posiciones de la lista.
	 * Básicamente retorna una lista donde cada elemento tiene una posición
	 * de la lista a la que se le llama el método.
	 */
	public PositionList<Position<E>> positions() {
		//creo la lista a retornar (vacía en un principio)
		PositionList<Position<E>> positions = new ListaSE<Position<E>>();
		
		//si la lista no está vacía
		if (!isEmpty()) {
			/*cargo la lista de posiciones con las posiciones de la lista*/
			
			//desde el primer nodo de la lista
			NodoSE<E> actual = head;
			
			//mientras el elemento actual no sea una referencia nula
			while (actual != null) {
				//añado la posición a la lista de posiciones
				positions.addLast(actual);
				//paso a la siguiente posición
				actual = actual.getNext();
			}
		}
		
		return positions;
	}
	
}
