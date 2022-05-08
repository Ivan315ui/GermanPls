package tdaMapeo;

import Exceptions.InvalidKeyException;

public interface Map <K, V> {

	/**
	 * Consulta el tamaño del mapeo.
	 * @return Tamaño del mapeo.
	 */
	public int size();
	
	/**
	 * Consulta si la lista está vacía
	 * @return true si la lista está vacía, false en caso contrario.
	 */
	public boolean isEmpty();
	
	/**
	 * Si hay un mapeo con clave key en la lista, retorna su valor asociado, 
	 * sino retorna null.
	 * @param key Clave del mapeo a obtener.
	 * @return valor V del mapeo asociado a la clave key si se encuentra en 
	 * el mapeo, sino null.
	 */
	public V get(K key) throws InvalidKeyException;
	
	/**
	 * Agrega una entrada con clave key y valor value si no hay una entrada 
	 * con clave key en el mapeo y retorna null.
	 * Si ya hay una entrada con clave key, reemplaza su valor asociado con 
	 * value y retorna el valor anterior.
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key, V value) throws InvalidKeyException;
	
	/**
	 * Remueve del mapeo la entrada con clave key y retorna su valor 
	 * asociado. Si no hay un mapeo con clave key, no hace nada y retorna 
	 * null.
	 * @param key
	 * @return
	 */
	public V remove(K key) throws InvalidKeyException;
	
	/**
	 * Retorna una colección iterable de las claves en el mapeo.
	 * Una lista iterable de claves.
	 * @return
	 */
	public Iterable<K> keys();
	
	/**
	 * Retorna una lista iterable de los valores del mapeo.
	 * @return
	 */
	public Iterable<V> values();
	
	/**
	 * Retorna una lista iterable de las entradas del mapeo.
	 * @return
	 */
	public Iterable<Entry<K, V>> entries();
	
}
