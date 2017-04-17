/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	MyLinkedList<String> myList1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

		myList1 = new MyLinkedList<String>();
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		try{
			emptyList.remove(0);
			fail("Check out of bounds");
		}catch(IndexOutOfBoundsException e){
		}
		
		try{
			list1.remove(2);
			fail("Check out of bounds");
		}catch(IndexOutOfBoundsException e){
		}
		
		int last = list1.remove(1);
		assertEquals("Remove: check a is correct ",42, last);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 1, list1.size());
		
		int onlyOne = list1.remove(0);
		assertEquals("Remove: check a is correct ", 21, onlyOne);
		assertEquals("Remove: check size is correct ", 0, list1.size());
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		myList1.add("0");
		myList1.add("2");
		assertEquals("Invalid size ",2,myList1.size());
		
		myList1.add(1,"1");//add 4th index
		assertEquals("Invalid data ","2",myList1.get(myList1.size()-1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		myList1.add("0");
		assertEquals("Invalid size ",1,myList1.size());
		
		myList1.remove(0);
		assertEquals("Invalid size ",0,myList1.size());
		
		myList1.add(0,"0");
		myList1.add("1");
		myList1.add(2,"2");
		assertEquals("Invalid size ",3,myList1.size());
		assertEquals("Invalid data ","2",myList1.get(2));
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		myList1.add("2");
		myList1.add(0,"0");
		assertEquals("Invalid size ",2,myList1.size());

		myList1.add(1,"1");
		assertEquals("Invalid data at 1st Index ","1",myList1.get(1));
		
		assertEquals("Invalid size ",3,myList1.size());
		
		myList1.add(3,"3");//add 3 index
		assertEquals("Invalid data ","3",myList1.get(myList1.size()-1));
		
		try{
			myList1.add(5,"5");
			fail("check out of bound");
		}catch(IndexOutOfBoundsException e){
			
		}
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		try{
			list1.set(3,30);
			fail("check out of bound");
		}catch(IndexOutOfBoundsException e){
			
		}
		
		assertEquals("Invalid data ",(Integer)65,list1.set(0,50));
		assertEquals("Invalid data ",(Integer)42,list1.set(2,22));
	}
	
	
	// TODO: Optionally add more test methods.
	
}
