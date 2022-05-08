package tdaLista;

import Exceptions.*;

/* Extender a la interfaz Iterable permite que la lista sea iterable, esto
 * es, hereda el método iterator de la interfaz iterable y debe 
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
	 * Consulta si la lista está vacía.
	 * @return true si la lista está vacía, false en caso contrario.
	 */
	public boolean isEmpty();
	
	/* Operaciones de recorrido */
	
	/**
	 * Retorna la posición del primer elemento de la lista.
	 * @return Posición del primer elemento de la lista.
	 * @throws EmptyStackException si la lista está vacía.
	 */
	public Position<E> first() throws EmptyListException;
	
	/**
	 * Retorna la posición del último elemento de la lista.
	 * @return Posición del último elemento de la lista.
	 * @throws EmptyListException si la lista está vacía.
	 */
	public Position<E> last() throws EmptyListException;
	
	/**
	 * Retorna la posición previa a la de la posición p pasada por parámetro.
	 * @param p Posición de la que se quiere la posición previa.
	 * @return Posición previa a la posición p pasada por parámetro.
	 * @throws InvalidPositionException si la posición p es inválida.
	 * @throws BoundaryViolationException si la posición p pasada por parámetro es la primera.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	
	/**
	 * Retorna la posición del elemento que sigue al elemento en la posición p.
	 * @param p Posición de la que se quiere recuperar la posición siguiente.
	 * @return Posición siguiente a la de la posición p pasada por parámetro.
	 * @throws InvalidPositionException si la posición es inválida.
	 * @throws BoundaryViolationException si la posición p pasada por parámetro es la última.
	 */
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	
	/* Operaciones de actualización */

	/**
	 * Reemplaza al elemento en la posición p con element y retorna el elemento que estaba antes 
	 * en la posición p.
	 * @param p Posición en la que se fijará el elemento element.
	 * @param element Elemento a fijar en la posición p.
	 * @return Elemento almacenado previamente en la posición p
	 * @throws InvalidPositionException si la posición es inválida.
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * Inserta un nuevo elemento element como primer elemento (al inicio de la lista).
	 * @param element Elemento a insertar al inicio de la lista.
	 */
	public void addFirst(E element);
	
	/**
	 * Inserta un nuevo elemento element como último elemento (al final de la lista).
	 * @param element Elemento a insertar al final de la lista.
	 */
	public void addLast(E element);
	
	/**
	 * Añade un elemento element justo antes de la posición p pasada por parámetro.
	 * @param p Posición de referencia para insertar element justo antes.
	 * @param element Elemento a insertar justo antes de p.
	 * @throws InvalidPositionException si la posición p pasada por parámetro es inválida.
	 */
	public void addBefore(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * Añade un elemento element justo después de la posición p pasada por parámetro.
	 * @param p Posición de referencia para insertar element justo después.
	 * @param element Elemento a insertar justo después de p.
	 * @throws InvalidPositionException si la posición no es válida.
	 */
	public void addAfter(Position<E> p, E element) throws InvalidPositionException;
	
	/**
	 * Elimina y retorna el elemento en la posición p, invalidando la posición p.
	 * @param p Posición a eliminar de la lista.
	 * @return Elemento encontrado en la posición p a eliminar.
	 * @throws InvalidPositionException si la posición es inválida.
	 */
	public E remove(Position<E> p) throws InvalidPositionException;
	
	/*Operaciones de iterables*/
	/* no hace falta declarar la operación iterator():Iterator<E> porque está
	 * declarada en la interfaz iterable */
	
	/**
	 * Retorna un objeto iterable que contiene las posiciones asociadas 
	 * a los elementos almacenados en la lista.
	 * En otras palabras, retorna una lista donde cada elemento es una 
	 * posición de esta lista. En vez de una lista de elementos, una 
	 * lista de posiciones.
	 * @return
	 */
	public PositionList<Position<E>> positions();
}
