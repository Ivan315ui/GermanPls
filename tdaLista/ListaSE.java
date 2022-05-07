package tdaLista;

import Exceptions.*;

import java.util.Iterator;

public class ListaSE <E> implements PositionList <E> {

	//atributos de instancia
	private NodoSE<E> head;		//primer nodo de la lista
	private int size;			//mantiene size en O(1)
	
	
	/* Constructor */
	/**
	 * Crea una nueva lista vac�a.
	 */
	public ListaSE() {}			//instanciaci�n de attrs por omisi�n.
	
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
			throw new EmptyListException("La lista est� vac�a, no tiene primer elemento.");
		
		//si no est� vac�a, head es el primero nodo
		return head;
	}
	
	public Position<E> last() throws EmptyListException {
		if (head == null)
			throw new EmptyListException("La lista est� vac�a, no tiene �ltimo elemento.");
		
		//si no est� vac�a:
		NodoSE <E> actual = head;			//desde el primer nodo

		//mientras el elemento actual tenga siguiente:
		while (actual.getNext() != null)
			//avanzo al siguiente elemento
			actual = actual.getNext();
		
		//cuando salgo del bucle estoy en el �ltimo elemento, as� que lo retorno
		return actual;
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		//verificar que sea una posici�n valida
		NodoSE <E> pos = checkPosition(p);
		
		//BoundaryViolationException p = head;
		if (pos == head)
			throw new BoundaryViolationException("No se puede obtener el elemento previo al primer elemento.");
		
		//si llegamos a este punto, entonces es una posici�n a la que se le puede encontrar el previo
		NodoSE <E> actual = head;			//iniciamos el cursor en el inicio
		/*
		 * mientras el nodo actual no tenga como siguiente al pasado por par�metro, 
		 * entonces no es el buscado y avanzamos al siguiente.
		 */
		while (actual.getNext() != pos)
			actual = actual.getNext();
		
		//al final del bucle anterior encontr� el previo, luego lo retorno
		return actual;		
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		//verifico que sea una posici�n v�lida
		NodoSE<E> pos = checkPosition(p);
		
		//si la posici�n es la �ltima, tiro error
		try {
			if (pos == last())
				throw new BoundaryViolationException("No se puede obtener el elemento siguiente de la �ltima posici�n.");
		//catch inalcanzable, se supone que si la posici�n es v�lida, hay al menos un elemento en la lista.
		} catch (EmptyListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//en este punto es una posici�n v�lida de la lista, as� que simplemente retorno el siguiente
		return pos.getNext();
	}
	
	
	/**
	 * Verifica si una posici�n es v�lida en la lista, es decir, es un nodo
	 * @param position Posici�n cuya validez se verificar�.
	 * @return Posici�n pasada por par�metro como nodo, si result� ser v�lida.
	 * @throws InvalidPositionException si la posici�n resulta ser inv�lida.
	 */
	private NodoSE<E> checkPosition(Position<E> position) throws InvalidPositionException {
		NodoSE <E> validPosition;
		
		//intentamos el casteo a nodo (ser� exitoso si era una posici�n v�lida
		try {
			validPosition = (NodoSE<E>) position;
			
			//puede suceder que sea una posici�n compatible con la lista pero nula, as� que vamos a verificar eso
			if (validPosition == null)
				//llegara a cumplirse, le mando error por no ser v�lida 
				throw new InvalidPositionException("La posici�n pasada por par�metro es nula.");
			
			/*
			 * TODO: preguntar si es necesario que verifique que no sea una posici�n previamente eliminada, es decir
			 * 		 validPosition.element() = null
			 */
			
			
		} catch (ClassCastException e) {
			//en caso de que no se pueda realizar el casteo, significa que no era una posici�n v�lida
			throw new InvalidPositionException("La posici�n pasada por par�metro no es v�lida.");
		}
		
		//si llegamos a este punto, significa que es una posici�n v�lida en la lista
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
		
		//aprovecho la operaci�n last() para obtener el �ltimo nodo de la lista
		try {
			//como es el last de esta lista, s� que me retorna un nodo y no cualquier otra posici�n
			NodoSE<E> last = (NodoSE<E>) last();
			//asigno newLast como siguiente del �ltimo actual
			last.setNext(newLast);
		//si me tira error, es porque la lista est� vac�a
		} catch (EmptyListException e) {
			//entonces asigno el nuevo elemento a head
			head = newLast;
		}
		
		//incremento la cantidad de nodos
		size++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		//verifico que la posici�n sea v�lida
		NodoSE<E> pos = checkPosition(p);
		
		//para este punto, se supone que la posici�n es v�lida, luego creo el nodo que encapsula element
		NodoSE<E> newPrev = new NodoSE<E>(element);
		
		//busco el elemento en el cual se debe insertar
		if (pos == head)	//si es el primero, simplemente puedo usar addFirst
			addFirst(element);
		else {
			//desde el inicio, busco el anterior a la posici�n pasada por par�metro
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
		//verifico que sea una posici�n v�lida de la lista
		NodoSE<E> pos = checkPosition(p);
		
		//creo el nodo que contiene element
		NodoSE<E> newNext = new NodoSE<E>(element);
		
		//actualizo las referencias como corresponde
		newNext.setNext(pos.getNext());				//vinculo el resto de la lista
		pos.setNext(newNext);						//pongo newNext justo despu�s de la posici�n del par�metro.
		
		//incremento la cantidad de nodos
		size++;
	}
	
	public E set(Position<E> p, E element) throws InvalidPositionException {
		//verifico que la posici�n sea v�lida
		NodoSE <E> pos = checkPosition(p);
		
		//guardo el elemento que estaba en pos
		E prevElement = pos.element();
		
		//fijo element en pos
		pos.setElement(element);
		
		//retorno el elemento que estaba antes
		return prevElement;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException {
		//verifico que sea v�lida
		NodoSE<E> pos = checkPosition(p);
		
		//guardo el elemento que estaba antes
		E posElem = pos.element();
		
		//si la posici�n a eliminar es la primera:
		if (pos == head)
			//head ahora es el elemento siguiente
			head = head.getNext();
		//sino, busco cu�l es el elemento anterior al que tengo que eliminar
		else {
			//desde head, me fijo si el siguiente es el que quiero eliminar
			NodoSE<E> actual = head;		//me paro sobre head
			
			//avanzo mientras el siguiente del elemento actual no sea el que quiero eliminar
			while (actual.getNext() != pos)
				actual = actual.getNext();
			
			//despu�s del while, tengo el anterior al que quiero eliminar, luego actualizo las referencias
			actual.setNext(pos.getNext());		//vinculo el anterior al que elimin� al resto de la lista
		}

		//inutilizo el nodo que elimin�
		pos.setNext(null);			//desvinculo el resto de la lista del nodo eliminado
		pos.setElement(null);		//elimino la informaci�n del nodo
		
		//decremento la cantidad de nodos
		size--;
		
		//retorno el elemento que estaba en la posici�n
		return posElem;
	}
	
	/* M�todos de la interfaz iterable */
	
	/* M�todo de la interfaz Iterable */
	public Iterator<E> iterator() {
		//derivo el comportamiento del iterador en la clase ElementIterator
		return new ElementIterator<E>(this);
	}
	
	/*
	 * Retorna una colecci�n iterable de posiciones de la lista.
	 * B�sicamente retorna una lista donde cada elemento tiene una posici�n
	 * de la lista a la que se le llama el m�todo.
	 */
	public PositionList<Position<E>> positions() {
		//creo la lista a retornar (vac�a en un principio)
		PositionList<Position<E>> positions = new ListaSE<Position<E>>();
		
		//si la lista no est� vac�a
		if (!isEmpty()) {
			/*cargo la lista de posiciones con las posiciones de la lista*/
			
			//desde el primer nodo de la lista
			NodoSE<E> actual = head;
			
			//mientras el elemento actual no sea una referencia nula
			while (actual != null) {
				//a�ado la posici�n a la lista de posiciones
				positions.addLast(actual);
				//paso a la siguiente posici�n
				actual = actual.getNext();
			}
		}
		
		return positions;
	}
	
}
