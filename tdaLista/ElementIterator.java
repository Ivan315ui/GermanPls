package tdaLista;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Exceptions.*;


public class ElementIterator <E> implements Iterator<E> {
	
	/* Atributos de instancia */
	private PositionList<E> list;			//lista de iterador a retornar
	private Position<E> cursor;				//cursor (elemento actual)
	
	/* Constructor */
	public ElementIterator(PositionList<E> list) {
		this.list = list;
		if (list.isEmpty())
			cursor = null;
		else
			try {
				cursor = list.first();
			/* catch inalcanzable, ya controlé que no esté vacía */
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
	}
	
	/* Métodos de la interfaz */
	public boolean hasNext() {
		/* Solo da falso si estoy al final de la lista o si está vacía,
		 * en cualquiera de los casos, coincide que cursor = null */
		return cursor != null;
	}
	
	/**
	 * Retorna el siguiente elemento de la secuencia
	 */
	public E next() throws NoSuchElementException {

		//si ya recorrí la lista o está vacía, tiro la excepción
		if (cursor == null)
			throw new NoSuchElementException("No hay elemento siguiente");
		
		//creo el elemento a retornar
		E next = cursor.element();
		
		//paso al elemento siguiente
		try {
			cursor = cursor == list.last() ? null : list.next(cursor);
		/* catch inalcanzable, porque:
		 * - verifiqué que no esté vacía en el if
		 * - controlo no pasarme del último elemento al asignar en cursor
		 * - siempre trabajo con posiciones de la lista en next */
		} catch (EmptyListException | BoundaryViolationException |
				InvalidPositionException e){
			e.printStackTrace();
		}
		
		//retorno el elemento de la posición anterior
		return next;
	}
	
}
