package tdaLista;

import Exceptions.*;


import java.util.Comparator;
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
	private NodoDE trail;			//último elemento
	
	public ListaDE() {
		/* Crea la lista vacía, instanciando todos los atributos por omisión */
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	/* Métodos de recorrido */
	
	public Position<E> first() throws EmptyListException {
		if (size == 0)
			throw new EmptyListException("La lista está vacía, no se puede obtener el primer elemento.");
		
		return head;
	}
	
	public Position<E> last() throws EmptyListException {
		if (size == 0)
			throw new EmptyListException("La lista está vacía, no se puede obtener el último elemento.");
		
		return trail;				//O(1), a diferencia de la implementación con nodos simplemente enlazados
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		/* Chequeamos la validez de la posición en la lista */
		NodoDE<E> pos = checkPosition(p);
		
		/* Si intento obtener el previo de la cabeza */
		if (pos == head)
			throw new BoundaryViolationException("No se puede obtener el elemento previo del primer elemento.");
		
		//en este punto ya se debería poder obtener el previo
		return pos.getPrev();				//O(1), a diferencia de la listaSE que era O(n)
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		/* Verifico que sea una posición de la lista */
		NodoDE<E> pos = checkPosition(p);
		
		/* Tiro error si quiero obtener el siguiente del último elemento */
		if (pos == trail)
			throw new BoundaryViolationException("No se puede obtener el elemento siguiente del último elemento.");
		
		//en este punto debería poder obtener el siguiente
		return pos.getNext();
	}
	
	/* Métodos de actualización */
	
	public E set(Position<E> p, E element) throws InvalidPositionException {
		/* Verifico que sea una posición de la lista */
		NodoDE<E> pos = checkPosition(p);
		
		//Obtengo el elemento que contenía previamente
		E prevElement = pos.element();
		
		//fijo su elemento al pasado por parámetro
		pos.setElement(element);
		
		//retorno el elemento que se encontraba antes en esa posición
		return prevElement;
	}
	
	public void addFirst(E element) {
		//Creo un nuevo nodo que encapsule a element
		NodoDE<E> newHead = new NodoDE<E>(element);
		
		//si la lista no está vacía
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
		
		//si la lista no está vacía
		if (head != null) {
			//vinculo toda la lista al nodo nuevo y añado el nodo al final
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
		//valido la posición como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//creo el nuevo nodo que encapsule al elemento
		NodoDE<E> newNode = new NodoDE<E>(element);
		
		//vinculo la sublista que empezaba con pos, después del nuevo nodo
		newNode.setNext(pos);
		//vinculo la sublista que estaba antes de pos, antes del nuevo nodo
		newNode.setPrev(pos);
		
		//actualizo al nuevo nodo como previo de pos
		pos.setPrev(newNode);					//queda O(1), a diferencia de ListaSE
		
		size++;
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		//valido la posición como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//creo el nuevo nodo que encapsule al elemento
		NodoDE<E> newNode = new NodoDE<E>(element);
		
		//vinculo desde el inicio hasta pos atrás del nuevo nodo
		newNode.setPrev(pos);
		
		//vinculo el resto de la lista después de pos al nuevo nodo
		newNode.setNext(pos.getNext());
		
		//fijo al nuevo nodo como el siguiente de pos
		pos.setNext(newNode);					//queda O(1), a diferencia de ListaSE
		
		size++;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException {
		//valido la posición como nodo de la lista
		NodoDE<E> pos = checkPosition(p);
		
		//obtengo el elemento de la posición a eliminar
		E removed = pos.element();
		
		//si la posición es el primer elemento
		if (pos == head) {
			//actualizo el nuevo primer elemento como el segundo
			head = head.getNext();
			//elimino el elemento previo del nuevo primer elemento
			head.setPrev(null);
		} else if (pos == trail) {
			//actualizo el último elemento
			trail = trail.getPrev();
			//desvinculo el último elemento de antes, de la lista
			trail.setNext(null);
		} else {
			/* Vinculo ambas partes de la lista con la respectiva otra */
			//vinculo la lista a derecha del nodo a eliminar con el anterior
			pos.getPrev().setNext(pos.getNext());
			//vinculo la lista a izquierda del nodo a eliminar con el siguiente
			pos.getNext().setPrev(pos.getPrev());
		}
		
		//inutilizo la posición del parámetro
		pos.setPrev(null);
		pos.setElement(null);
		pos.setNext(null);
		
		size--;
		
		return removed;
	}
	
	/* Métodos de la interfaz Iterable */
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}
	
	public PositionList<Position<E>> positions() {
		/* Para cada elemento de la lista, creo una posición y la añado a la lista
		 * de posiciones a retornar */
		//creo la lista de posiciones a retornar
		PositionList<Position<E>> positions = new ListaDE<Position<E>>();
		
		//si la lista no está vacía
		if (size != 0) {
			//creo un cursor que empiece en el primer elemento
			NodoDE<E> cursor = head;
			
			do {
				//agrego la posición actual a la lista de posiciones
				positions.addLast(cursor);
				//paso a la siguiente posicion (null si estoy en la última para cortar)
				cursor = cursor != trail ? cursor.getNext() : null;
			} while (cursor != null);
		}
		
		return positions;
	}
	
	/* Ejercicio 10 */
	/**
	 * eliminar(PositionList<E> L): void
	 *
	 * 1. Elimina de la lista receptora todas las apariciones de los elementos contenidos en L
	 * 2. Inserta al final de la lista receptora todos los elementos de L en orden inverso al que aparecen en L.
	 * 
	 * @param L
	 */
	public void eliminar(PositionList<E> L) {
		/* Razonamiento base, por cada elemento de la lista L, comparo con cada elemento de la lista para 
		 * ver si está presente, en caso de que sí, lo elimino.
		 * Luego de eliminar, con una lista auxiliar que se comporte como pila, paso los elementos de L 
		 * a la lista auxiliar y de la lista auxiliar a this. */
		
		/* Creo el comparador */
		Comparator<E> comparator = new DefaultComparator<E>();
		
		/* Eliminar L de this */
		// para cada elemento de L
		for (E element:L)
			//Para cada posicion de esta lista
			for (Position<E> position:positions())
				//si el elemento de la posicion actual coincide con el elemento actual de la lista L
				if (comparator.compare(element, position.element()) == 0)
					try {
						//lo elimino de la lista
						remove(position);
					/* catch inalcanzable, siempre trabajo con posiciones de la lista */
					} catch (InvalidPositionException e) {
						e.printStackTrace();
						return;
					}
		
		/* Insertar L**r en this al final */
		//creo la lista que se comporte como pila (patrón adaptador)
		ListaDE<E> pilaAux = new ListaDE<E>();
		
		//lleno la pila con los elementos de la lista L
		for (E element:L)
			pilaAux.addFirst(element);
		
		//por cada elemento de la pila, desapilo y lo paso a la lista this, invirtiendo
		for (E element:pilaAux)
			addLast(element);
		
	}
	
	
	/* Métodos privados */
	private NodoDE<E> checkPosition(Position<E> pos) throws InvalidPositionException {
		NodoDE<E> validPosition;
		
		/* Si la posición con la que se trabaja es una referencia nula o fue eliminada previamente, tiramos una excepción */
		if (pos == null || pos.element() == null)
			throw new InvalidPositionException("No es una posición válida en la lista.");
		
		try {
			//intentamos el casteo
			validPosition = (NodoDE<E>) pos;
			
		} catch (ClassCastException e) {
			//Si no se pudo realizar el casteo, no es una posición de la lista, entonces la propago como InvalidPositionException
			throw new InvalidPositionException("No es una posición compatible en la lista");
		}
		
		return validPosition;
	}
	
}


