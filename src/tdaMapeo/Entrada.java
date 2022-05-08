package tdaMapeo;

public class Entrada <K, V> implements Entry <K, V> {

	private K key;
	private V value;
	
	//Constructor
	public Entrada(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/* Getters */
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	/* Setters */
	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	
	public String toString() {
		return key + ": " + value;
	}
	
}
