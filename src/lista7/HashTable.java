package lista7;

import java.util.LinkedList;

public class HashTable<E extends IWithName>{
	LinkedList<E> arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	public HashTable(int initCapacity, double maxLF) {
		arr = new LinkedList[initCapacity];
		this.maxLoadFactor=maxLF;
	}

	private int hash(int hashCode) {
		return hashCode % arr.length;
	}

	public boolean add(E elem) {
		if (get(elem) != null)
			return false;
		if(((double)size+1)/arr.length>maxLoadFactor)
			doubleArray();
		int index = hash(elem.hashCode());
		if (arr[index] == null)
			arr[index] = new LinkedList<E>();
		arr[index].add(elem);
		size++;
		return true;
	}

	private void doubleArray() {
		LinkedList<E>[] oldArr=arr;
		arr=new LinkedList[oldArr.length*2];
		for(int i=0;i<oldArr.length;i++) {
			if(oldArr[i]!=null) {
				for(E elem: oldArr[i]) {
					add(elem);
				}
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder output= new StringBuilder();
		for(int i=0;i<arr.length;i++) {
			output.append(i).append(":");
			if(arr[i]!=null) {
				output.append(" ");
				for(E elem: arr[i]) {
					output.append(elem.getName()).append(", ");
				}
				output.delete(output.length()-2,output.length());
			}
			output.append("\n");
		}
		return output.toString();
	}

	public Object get(E toFind) {
		int index = hash(toFind.hashCode());
		if (arr[index] == null)
			return null;

		for (E elem : arr[index]) {
			if (elem.equals(toFind))
				return elem;
		}
		return null;
	}

}

