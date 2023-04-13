package lista2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayList<E> implements IList<E>{
    private int size;
    private class Element{
        private E object;
        private Element next=null;
        public void setNext(Element next) {
            this.next = next;
        }
        public Element getNext() {
            return next;
        }
        public void setValue(E object) {
            this.object = object;
        }
        public E getValue() {
            return object;
        }
        public Element(E e) {
            this.object=e;
        }
    }
    Element sentinel=null;

    private class InnerIterator implements Iterator<E>{
        Element actElem;
        public InnerIterator() {
            actElem=sentinel;
        }
        @Override
        public boolean hasNext() {
            return actElem.next!=null;
        }

        @Override
        public E next() {
            E object=actElem.getValue();
            actElem=actElem.getNext();
            return object;
        }
    }

    public OneWayList() {
        sentinel=new Element(null);
        size=0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        Element newElem=new Element(e);
        Element tail=sentinel;
        while(tail.getNext()!=null){
            tail=tail.getNext();
        }
        tail.setNext(newElem);
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        if(index<0 || index>size)
            throw new IndexOutOfBoundsException();
        Element newElem=new Element(element);
        if(index==0){
            newElem.setNext(sentinel.getNext());
            sentinel.setNext(newElem);
            size++;
            return;
        }
        Element actElem=getElement(index-1);
        newElem.setNext(actElem.getNext());
        actElem.setNext(newElem);
        size++;
    }

    @Override
    public void clear() {
        sentinel.setNext(null);
        size=0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)>=0;
    }

    public Element getElement(int index) throws IndexOutOfBoundsException {
        if(index<0 || index>=size)
            throw new IndexOutOfBoundsException();
        Element actElem=sentinel.next;
        while(index>0 && actElem.next!=null) {
            actElem=actElem.getNext();
            index--;
        }
        if(actElem==null){
            throw new IndexOutOfBoundsException();
        }
        return actElem;
    }
    @Override
    public E get(int index) throws NoSuchElementException{
        if(index<0 || index>=size)
            throw new NoSuchElementException();
        Element actElem=getElement(index);
        if(actElem.getValue()==null)
            throw new NoSuchElementException();
        return actElem.getValue();
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        if(index<0 || index>=size)
            throw new NoSuchElementException();
        Element actElem=getElement(index);
        E old=actElem.getValue();
        actElem.setValue(element);
        return old;
    }

    @Override
    public int indexOf(E element) {
        int pos=0;
        Element actElem=sentinel.next;
        if(actElem==null)
            return -1;
        while(actElem.next!=null) {
            if(actElem.getValue().equals(element))
                return pos;
            pos++;
            actElem=actElem.getNext();
            if(actElem==null)
                return -1;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next==null;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if(index<0 || sentinel.next==null) throw new IndexOutOfBoundsException();
        if(index==0){
            E retValue= sentinel.getNext().getValue();
            sentinel=sentinel.getNext();
            size--;
            return retValue;}
        Element actElem=getElement(index-1);
        if(actElem.getNext()==null)
            throw new IndexOutOfBoundsException();
        E retValue=actElem.getNext().getValue();
        actElem.setNext(actElem.getNext().getNext());
        size--;
        return retValue;
    }

    @Override
    public boolean remove(E e) {
        if(sentinel.next==null)
            return false;
        if(sentinel.next.getValue().equals(e)){
            sentinel=sentinel.getNext();
            size--;
            return true;
        }
        Element actElem=sentinel.next;
        while(actElem.getNext()!=null && !actElem.getNext().getValue().equals(e))
            actElem=actElem.getNext();
        if(actElem.getNext()==null)
            return false;
        actElem.setNext(actElem.getNext().getNext());
        size--;
        return true;
    }

    @Override
    public int size() {
        int pos=0;
        Element actElem=sentinel;
        while(actElem.next!=null) {
            pos++;
            actElem=actElem.getNext();
        }
        return pos;
    }

    public boolean removeEvens(){
        if(sentinel.next==null)
            return false;
        Element actElem=sentinel;
        actElem.setNext(actElem.getNext());
        while(actElem.getNext().getNext()!=null){
            actElem.setNext(actElem.getNext().getNext());
            size--;
            actElem=actElem.next;
            if(actElem.getNext()==null){
                return true;
            }
        }
        if(actElem.next!=null){
            size--;
            actElem.next=null;
        }
        return true;
    }

}