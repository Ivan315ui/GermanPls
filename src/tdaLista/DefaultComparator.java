package tdaLista;

/**
 * Clase comparador que deriva las comparaciones entre elementos al método compareTo de la clase de los elementos que compara.
 * @author ivane
 *
 * @param <E> Tipo de elementos que compara
 */
public class DefaultComparator <E> implements java.util.Comparator <E> {

	public int compare(E element1, E element2) {
		//retorno la comparación por compareTo implementada en la clase E
		return ((Comparable <E>)element1).compareTo(element2);
	}
	
}
