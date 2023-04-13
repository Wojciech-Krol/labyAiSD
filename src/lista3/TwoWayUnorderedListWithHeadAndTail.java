package lista3;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{

    private class Element{
        public Element(E e) {
            this.object=e;
            next=null;
            prev=null;
        }
        public Element(E e, Element next, Element prev) {
            this.object=e;
            this.next=next;
            this.prev=prev;
        }
        public E getObject() { return object; }
        public void setObject(E object) { this.object = object; }
        public Element getNext() {return next;}
        public void setNext(Element next) {this.next = next;}
        public Element getPrev() {return prev;}
        public void setPrev(Element prev) {this.prev = prev;}
        E object;
        Element next=null;
        Element prev=null;
    }

    Element head;
    Element tail;
    // can be realization with the field size or without
    int size;

    private class InnerIterator implements Iterator<E>{
        private Element currentElement;

        public InnerIterator() {
            currentElement = head;
        }
        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E returnedElement = currentElement.object;
            currentElement = currentElement.next;
            return returnedElement;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element currentElement;

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();

        }

        @Override
        public boolean hasNext() {
            return currentElement.next != null;
        }

        public boolean hasPrevious(){
            return currentElement.prev != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Element returnedElement = currentElement.next;
            currentElement = returnedElement;
            return returnedElement.object;
        }

        public E previous() throws NoSuchElementException {
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            Element returnedElement = currentElement.prev;
            currentElement = returnedElement;
            return returnedElement.object;

        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
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
        public void set(E e) {
            currentElement.object = e;

        }
    }

    public TwoWayUnorderedListWithHeadAndTail() {
        size=0;
        head=null;
        tail=null;
    }

    @Override
    public boolean add(E e) {
        Element element=new Element(e);
        if(size==0){
            head=element;
            head.prev=null;
            tail=head;
            tail.next=null;
        }
        else{
            element.setPrev(tail);
            tail.setNext(element);
            tail=element;
        }
        size++;
        return true;
    }


    @Override
    public void add(int index, E element) {
        if(index<0 || index>size){
            throw new NoSuchElementException();
        }
        Element e=new Element(element);
        if(index==0){
            if(head==null){
                head=e;
                tail=e;
            }
            e.setNext(head);
            head.setPrev(e);
            head=e;
        }
        else if(index==size){
            tail.next=e;
            e.prev=tail;
            tail=e;
//            e.setPrev(tail);
//            tail.setNext(e);
//            tail=e;
        }
        else{
            Element prev=getElement(index-1);
            Element next=prev.getNext();
            e.setNext(next);
            e.setPrev(prev);
            prev.setNext(e);
            next.setPrev(e);
        }
        size++;
    }

    @Override
    public void clear() {
        head.setNext(null);
        tail.setPrev(null);
        head=null;
        tail=null;
        size=0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)!=-1;
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= this.size){
            throw new NoSuchElementException();
        }
        Element e=getElement(index);
        if(e.getObject()==null)
            throw new NoSuchElementException();
        return e.getObject();
    }

    private Element getElement(int index) {
        Element elem=head;
        for(int i=0;i<index;i++)
            elem=elem.next;
        return elem;
    }
    private Element getElement(E element) {
        Element e=head;
        while(e!=null && !e.getObject().equals(element))
            e=e.getNext();
        return e;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= this.size){
            throw new NoSuchElementException();
        }
        Element e=getElement(index);
        E ret=e.getObject();
        e.setObject(element);
        return ret;
    }

    @Override
    public int indexOf(E element) {
        int index=0;
        while(index<size)
            if(getElement(index).getObject().equals(element))
                return index;
            else
                index++;
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head==null;
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
    public E remove(int index) {
        if(index < 0 || index >= this.size){
            throw new NoSuchElementException();
        }
        if(size==0){
            throw new NoSuchElementException();
        }
        Element e=getElement(index);
        Element prev=e.getPrev();
        Element next=e.getNext();
        if(prev==null)
            head=next;
        else
            prev.setNext(next);
        if(next==null)
            tail=prev;
        else
            next.setPrev(prev);
        size--;
        return e.getObject();
    }

    @Override
    public boolean remove(E e) {
        if (head == null)
            return false;

        Element pos = head;

        while (pos != null) {
            if (pos.object.equals(e)) {
                removeElementInPlace(pos);
                return true;
            }
            pos = pos.next;
        }

        return false;
    }
//    @Override
//    public boolean remove(E element) {
//        if(size==0){
//            throw new NoSuchElementException();
//        }
//        Element e=getElement(element);
//        if(e==null)
//            return false;
//        if(size==1){
//            size--;
//            clear();
//            return true;
//        }
//        Element prev=e.getPrev();
//        Element next=e.getNext();
//        if(prev==null){
//            head=next;
//            head.setPrev(null);}
//        else{
//            prev.setNext(next);
//            prev.setPrev(null);
//        }
//        if(next==null)
//            tail=prev;
//        else
//            next.setPrev(prev);
//        size--;
//        return true;
//
//    }

    @Override
    public int size() {
        return size;
    }
    public String toStringReverse() {
        ListIterator<E> iter = new InnerListIterator();
        while(iter.hasNext())
            iter.next();
        String retStr="";
        while(iter.hasPrevious()){
            retStr += "\n" + iter.previous();
        }
        return retStr;
    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
        if(this==other)
            return;
        if (other.isEmpty())
            return;
        if(this.isEmpty()){
            head=new Element(other.head.object,other.head.next,other.head.prev);
            tail=new Element(other.tail.object,other.tail.next,other.tail.prev);
            size+=other.size();
            other.clear();
        }
        else{
            tail.setNext(new Element(other.head.object,other.head.next,tail));
            tail=new Element(other.tail.object,other.tail.next,other.tail.prev);
            size+=other.size();
            other.clear();
        }
    }
    private void removeElementInPlace(Element pos) {
        if (pos.prev != null)
            pos.prev.next = pos.next;

        if (pos.next != null)
            pos.next.prev = pos.prev;

        if (pos == head)
            head = pos.next;

        if (pos == tail)
            tail = pos.prev;

        size--;
    }
}
