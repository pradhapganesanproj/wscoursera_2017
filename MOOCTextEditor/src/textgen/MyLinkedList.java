package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null,null,null);
		tail = new LLNode<E>(null,null,head);				
		head.next = tail;
		size=0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if(null == element){
			throw new NullPointerException("null value can not be inserted");
		}
		if(head.next==tail || tail.prev==head){
			LLNode<E> newNode = new LLNode<E>(element, tail, head);
			tail.prev = newNode;
			head.next = newNode;
		}else{
			LLNode<E> prev = tail.prev;
			LLNode<E> newNode = new LLNode<E>(element, tail, prev);
			tail.prev = newNode;
			prev.next = newNode;
		}
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		validateIndex(index);
		
		LLNode<E> currentNode = getLLNode(index);
		
		if(null != currentNode){
			return currentNode.data;
		}else{
			return null;
		}
	}

	private LLNode<E> getLLNode(int index) {
		if(size==index){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> currentNode = head.next;
		int count = 0;
		while( currentNode != null && count < index){
			currentNode = currentNode.next;
			count++;
		}
		return currentNode;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		validateIndex(index);
		
		if(index==size){
			add(element);
		}else{
			LLNode<E> currentNode = getLLNode(index);
			if(null != currentNode){
				LLNode<E> prev = currentNode.prev;
				LLNode<E> newNode = new LLNode<E>(element, currentNode, prev);
				currentNode.prev = newNode;
				prev.next = newNode;
			}else{
				throw new NullPointerException("Could NOT add");
			}
			size++;
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		//throws IndexOutOfBoundsException if index > size || < 0
		validateIndex(index);

		LLNode<E> currentNode = getLLNode(index);
		LLNode<E> next = currentNode.next;
		LLNode<E> prev= currentNode.prev;
		
		if(next !=tail && prev != head){
			prev.next = next;
			next.prev = prev;
		}else if(next ==tail || prev == head){
			if(prev==head){
				head.next=next;
				next.prev=head;
			}
			if(next==tail){
				tail.prev = prev;
				prev.next=tail;
			}
		}else{
			throw new NullPointerException("Could not remove");
		}
		size--;
		return currentNode.data;
	}

	/*
	 * throws IndexOutOfBoundsException if index > size || < 0 
	 */
	private void validateIndex(int index) {
		if(index>size || index <0){
			throw new IndexOutOfBoundsException("Invalid index");
		}
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		validateIndex(index);
		
		if(element == null){
			throw new NullPointerException("Could not set");
		}
		
		LLNode<E> currentNode = getLLNode(index);
		E returnElem = null;
		if(null != currentNode){
			returnElem = currentNode.data;
			currentNode.data = element;
		}else{
			throw new NullPointerException("Could not set");
		}
		return returnElem;
	}
	
	public String toString(){
		LLNode<E> currentNode = head;
		int count = 0;
		StringBuilder strBuild = new StringBuilder();
		while( currentNode != null ){
			strBuild.append("\n-->"+count+" :\n"+" data: "+currentNode.data+"\n next-> "+(null != currentNode.next?currentNode.next.data:null)+"\n prev-> "+(null != currentNode.prev?currentNode.prev.data:null));
			currentNode = currentNode.next;
			count++;
		}
		return strBuild.toString();
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e,LLNode<E> next, LLNode<E> prev) 
	{
		this.data = e;
		this.next = next;
		this.prev = prev;
	}
	/*@Override
	public String toString(){
		return "\t data: "+this.data+"\n next: "+(this.next != null?this.next.toString():null)+"\n prev: "+(this.prev != null ?prev.toString():null)+"--->";
	}*/
}
