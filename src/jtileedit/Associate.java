package jtileedit;

public class Associate<K,V> {
	private K k;
	private V v;
	
	Associate(){
		setKV(null,null);
	}
	
	Associate(K k,V v){
		setKV(k,v);
	}
	
	public K getK(){
		return k;
	}
	
	public V getV(){
		return v;
	}
	
	public void setK(K k){
		this.k=k;
	}
	
	public void setV(V v){
		this.v=v;
	}
	
	public void setKV(K k,V v){
		this.k=k;
		this.v=v;
	}
}
