package co.megadodo.mc;

public class Pair<K,V> {

	public K k;
	public V v;
	
	public Pair() {
		k=null;
		v=null;
	}
	
	public Pair(K k,V v) {
		this.k=k;
		this.v=v;
	}
	
	public Pair<K,V> setKey(K k) {
		this.k=k;
		return this;
	}
	
	public Pair<K,V> setValue(V v) {
		this.v=v;
		return this;
	}
	
	public K getKey() {
		return k;
	}
	
	public V getValue() {
		return v;
	}
	
}
