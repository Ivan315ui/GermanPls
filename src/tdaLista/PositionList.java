package tdaLista;

import Exceptions.*;

/* Extender a la interfaz Iterable permite que la lista sea iterable, esto
 * es, hereda el m�todo iterator de la interfaz iterable y debe 
 * implementarse en todas las clases que implementen a esta interfaz */
public interface PositionList <E> extends java.lang.Iterable<E> {

	// Conjunto de operaciones del tda lista
	
	/* Operaciones comunes a todas las estructuras */
	
	/**
	 * Consulta la cantidad de elementos en la lista.
	 * @return Cantidad de elementos en la lista.
	 */
	public int size();
	
	/**
	 * Consulta si la lista est� vac�a.
	 * @return true si la lista est� vac�a, false en caso contrario.
	 */
	public boolean isEmpty();
	
	/* Operaciones de recorrido */
	
	/**
	 * Retorna la posici�n del primer elemento de la lista.
	 * @return Posici�n del primer elemento de la lista.
	 * @throws EmptyStackException si la lista est� vac�a.
	 */
	public Position<E> first() throws EmptyListException;
	
	/**
	 * Retorna la posici�n del �ltimo elemento de la lista.
	 * @return Posici�n del �ltimo elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 */
	public Position<E> last() throws EmptyListException;
	
	/**
	 * Retorna la posici�n previa a la de la posici�n p pasada por par�metro.
	 * @param p Posici�n de la que se quiere la posici�n previa.
	 * @return Posici�n previa a la posici�n p pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n p es inv�lida.
	 * @throws BoundaryViolationException si la posici�n p pasada por par�metro es la primera.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	
	/**
	 * Retorna la posici�n del elemento que sigue al elemento en la posici�n p.
	 * @param p Posici�n de la que se quiere recuperar la posici�n siguiente.
	 * @return Posici�n siguiente a la de la posici�n p pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida.
	 * @throws BoundaryViolationException si la posici�n p pasada por par�metro es la �ltima.
	 */
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	
	/* Operaciones de actualizaci�n */

	/**
	 * Reemplaza al elemento en la posici�n p con element y retorna el elemento que estaba antes 
	 * en la posici�n p.
	 * @param p Posici�n en la que se fijar� el elemento element.
	 * @param element Elemento a fijar en la posici�n p.
	 * @return Elemento almacenado previamente en la posici�n p
	 * @throws InvalidPositionException si la posici�n es inv�lida.
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * Inserta un nuevo elemento element como primer elemento (al inicio de la lista).
	 * @param element Elemento a insertar al inicio de la lista.
	 */
	public void addFirst(E element);
	
	/**
	 * Inserta un nuevo elemento element como �ltimo elemento (al final de la lista).
	 * @param element Elemento a insertar al final de la lista.
	 */
	public void addLast(E element);
	
	/**
	 * A�ade un elemento element justo antes de la posici�n p pasada por par�metro.
	 * @param p Posici�n de referencia para insertar element justo antes.
	 * @param element Elemento a insertar justo antes de p.
	 * @throws InvalidPositionException si la posici�n p pasada por par�metro es inv�lida.
	 */
	public void addBefore(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * A�ade un elemento element justo despu�s de la posici�n p pasada por par�metro.
	 * @param p Posici�n de referencia para insertar element justo despu�s.
	 * @param element Elemento a insertar justo despu�s de p.
	 * @throws InvalidPositionException si la posici�n no es v�lida.
	 */
	public void addAfter(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * Elimina y retorna el elemento en la posici�n p, invalidando la posici�n p.
	 * @param p Posici�n a eliminar de la lista.
	 * @return Elemento encontrado en la posici�n p a eliminar.
	 * @throws InvalidPositionException si la posici�n es inv�lida.
	 */
	public E remove(Position<E> p) throws InvalidPositionException;
	
	/*Operaciones de iterables*/
	/* no hace falta declarar la operaci�n iterator():Iterator<E> porque est�
	 * declarada en la interfaz iterable */
	
	/**
	 * Retorna un objeto iterable que contiene las posiciones asociadas 
	 * a los elementos almacenados en la lista.
	 * En otras palabras, retorna una lista donde cada elemento es una 
	 * posici�n de esta lista. En vez de una lista de elementos, una 
	 * lista de posiciones.
	 * @return
	 */
	public PositionList<Position<E>> positions();
}
