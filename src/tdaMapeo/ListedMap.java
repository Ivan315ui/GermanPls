package tdaMapeo;

import tdaLista.*;

import java.util.Iterator;

import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

import java.util.Comparator;

public class ListedMap <K, V> implements Map <K, V> {

	private PositionList<Entrada<K, V>> S;
	
	public ListedMap() {
		S = new ListaDE<Entrada<K, V>>();
	}
	
	public int size() {
		return S.size();
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public V get(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("La clave es nula");
		
		//Creo una variable para guardar el valor a retornar
		V value = null;
		
		/* Busco en toda la lista de entradas una con la clave que busco */
		//creo el iterador de elementos del mapeo
		Iterator<Entrada <K, V>> entradas = S.iterator();
		
		//creo el comparador para las claves de la lista de entradas
		Comparator<K> comparator = new DefaultComparator<K>();
		
		//variable aux para guardar la entrada actual
		Entrada<K, V> actual;
		
		/* recorro la lista de entradas hasta llegar al final o encontrar 
		 * la entrada con la misma clave */
		while (entradas.hasNext() && value == null) {
			//guardo la entrada actual
			actual = entradas.next();
			
			//si la clave de la entrada actual es la misma que la que busco
			if (comparator.compare(key, actual.getKey()) == 0)
				//actualizo el valor
				value = actual.getValue();
		}
		
		return value;
	}
	
	public V put(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("La clave es nula");
		
		//creo la variable para guardar el valor a retornar
		V prevValue = null;
		
		/* Itero sobre las posiciones de la lista buscando una entrada 
		 * con la misma clave */
		//creo el iterador
		Iterator<Position<Entrada<K, V>>> entradas = S.positions().iterator();
		
		//variable auxiliar para almacenar la posicion actual
		Position<Entrada<K, V>> actual = null;
		
		//creo el comparador de claves
		Comparator<K> comparator = new DefaultComparator<K>();
		
		while (entradas.hasNext() && prevValue == null) {
			//guardo la posicion actual
			actual = entradas.next();
			
			//si la clave de la entrada actual coincide con la que quiero insertar
			if (comparator.compare(key, actual.element().getKey()) == 0)
				//actualizo el valor a retornar
				prevValue = actual.element().getValue();
		}
		
		/* Si prevValue tomó un valor, entonces actualizo, sino inserto */
		if (prevValue != null)
			//actualizo
			actual.element().setValue(value);
		else
			//inserto
			S.addLast(new Entrada<K, V>(key, value));
		
		return prevValue;
	}
	
	public V remove(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("La clave es nula");
		
		//no lo voy a intentar hacer estructurado
		
		//creo el comparador para claves
		Comparator<K> comparator = new DefaultComparator<K>();
		
		/* Recorro las posiciones de la lista buscando un elemento con la 
		 * misma clave y si la encuentro, la elimino de la lista de entradas
		 * y retorno su valor*/
		for(Position<Entrada<K, V>> pos: S.positions())
			//si la clave actual coincide
			if (comparator.compare(pos.element().getKey(), key) == 0) {
				//guardo el valor asociado
				V value = pos.element().getValue();
				
				//elimino la posicion de la lista
				try {
					S.remove(pos);
					
					//y termino retornando el valor que tenía asociado
					return value;
				//catch inalcanzable, trabajo con posiciones de la lista
				} catch (InvalidPositionException e) {
					e.printStackTrace();
					System.err.println("Mal al eliminar, pete.");
					return null;
				}
				
			}
		
		
		//si sali del for, entonces no estaba en la lista, retorno nulo.
		return null;
	}
	
	public Iterable<K> keys() {
		/* Por cada entrada de la lista, añado una clave a la lista 
		 * de claves */
		//Creo la lista de claves a retornar
		PositionList<K> keys = new ListaDE<K>();
		
		//para cada entrada del mapeo
		for(Entrada<K, V> entry: S)
			//inserto en el listado de claves la clave de la entrada actual
			keys.addLast(entry.getKey());
		
		return keys;
	}
	
	public Iterable<V> values() {
		/* Por cada entrada de la lista, añado un valor a la lista de 
		 * valores */
		//creo la lista de valores
		PositionList<V> values = new ListaDE<V>();
		
		//para cada entrada del mapeo
		for(Entrada <K, V> entry: S)
			//inserto el valor de la entrada actual en el listado de valores
			values.addLast(entry.getValue());
		
		return values;
	}
	
	public Iterable<Entry<K, V>> entries() {
		/* Por cada entrada de la lista, añaddo la entrada a la lista de
		 * entradas a retornar como iterable */
		PositionList<Entry<K, V>> entries = new ListaDE<Entry<K, V>>();
		
		for (Entrada<K, V> entry:S)
			entries.addLast(entry);
		
		return entries;
	}
	
}
