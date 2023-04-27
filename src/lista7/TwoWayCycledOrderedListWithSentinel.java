package lista7;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<E>> implements IList<E> {

	private class Element{
		public Element(E e) {
			this.object = e;
			this.next = null;
			this.prev = null;
		}
		public Element(E e, Element next, Element prev) {
			this.object = e;
			this.prev = prev;
			this.next = next;
		}
		// add element e after this
		public void addAfter(Element elem) {
			Element previousNext = next;
			next = elem;
			elem.prev = this;
			elem.next = previousNext;
			previousNext.prev = elem;
		}
		// assert it is NOT a sentinel
		public void remove() {
			if(this==sentinel){
				throw new NoSuchElementException();
			}
			size--;
			this.next.prev = this.prev;
			this.prev.next = this.next;
		}


		E object;
		Element next=null;
		Element prev=null;
	}


	Element sentinel;
	int size;

	private class InnerIterator implements Iterator<E> {
		Element current;

		public InnerIterator() {
			current = sentinel;
		}

		@Override
		public boolean hasNext() {
			return current.next != sentinel;
		}

		@Override
		public E next() {
			current = current.next;
			return current.object;
		}
	}

	private class InnerListIterator implements ListIterator<E> {
		Element current;

		public InnerListIterator() {
			current = sentinel;
		}

		@Override
		public boolean hasNext() {
			return current.next != sentinel;
		}

		@Override
		public E next() {
			current = current.next;
			return current.object;
		}

		@Override
		public void add(E arg0) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasPrevious() {
			return current != sentinel;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			E result = current.object;
			current = current.prev;

			return result;
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E arg0) {
			throw new UnsupportedOperationException();
		}
	}
	public TwoWayCycledOrderedListWithSentinel() {
		size = 0;
		this.sentinel = new Element(null);
		this.sentinel.next=sentinel;
		this.sentinel.prev=sentinel;
	}

	public boolean add(E e){
		Element newElement = new Element(e);
		Element currentElement = sentinel;
		while(currentElement.next != sentinel && currentElement.next.object.compareTo(e)<=0){
			currentElement = currentElement.next;
		}
		currentElement.addAfter(newElement);
		size++;
		return true;
	}

	private Element getElement(int index) {
		if(index < 0 || index >= this.size){
			return null;
		}
		Element currentElement = sentinel.next;
		for(int i = 0; i < index; i++){
			currentElement = currentElement.next;
		}
		return currentElement;
	}

	private Element getElement(E obj) {
		Element current = sentinel.next;

		while (current != sentinel && !current.object.equals(obj)) {
			current = current.next;
		}

		if (current == sentinel)
			throw new NoSuchElementException();

		return current;
	}


	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void clear() {
		sentinel = new Element(null);
		sentinel.next=sentinel;
		sentinel.prev=sentinel;
		size = 0;
	}

	@Override
	public boolean contains(E element) {
		try {
			getElement(element);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public E get(int index) {
		if(index < 0 || index >= this.size){
			throw new NoSuchElementException();
		}
		return getElement(index).object;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(E element) {
		Element currentElement =sentinel.next;
		for(int i=0;i<size;i++){
			if(currentElement.object.equals(element)){
				return i;
			}
			currentElement = currentElement.next;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Iterator<E> iterator() {
		return new InnerIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new InnerListIterator();
	}

	public E remove(int index) {
		if(index < 0 || index >= this.size){
			throw new NoSuchElementException();
		}
		Element elem=getElement(index);
		elem.remove();
		return elem.object;}
	public boolean remove(E value) {
		Element elem=getElement(value);
		if(elem==null)
			return false;
		elem.remove();
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
		if(this==other)
			return;
		if(this.isEmpty()){
			this.sentinel=other.sentinel;
			this.size=other.size;
			other.clear();
			return;
		}
		if(other.isEmpty())
			return;

		Element current = sentinel;

		for (E e : other) {
			while (current.next != sentinel && current.next.object.compareTo(e) <= 0) {
				current = current.next;
			}
			Element elem = new Element(e);
			current.addAfter(elem);
			size++;
		}
		other.clear();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAll(E e) {
		Element current = sentinel;
		while (current.next != sentinel) {
			if (current.next.object.equals(e)) {
				current.next.remove();
			} else {
				current = current.next;
			}
		}
	}

}

