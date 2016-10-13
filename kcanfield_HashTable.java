/**
 * This is the HashTable class, which is an array of SequentialTable objects.
 * This HashTable implementation uses chaining, rather than linear probing.
 * @author Kramer Canfield
 * @version 28 February 2014
 */


class HashTable<K,V>
{
	
	//fields
	private Object[] tableArray; //the array of SequentialTables
	private final int _M = 997;//the m to modulo by

	// the constructor for the HashTable, the constructor's main job is to set up the array of symbol-table Objects
	public HashTable()
	{
		tableArray = new Object[1000];
		for(int i=0; i<tableArray.length; i++)
		{
			tableArray[i] = new SequentialTable<K,V>();
		}
	} 

	//puts a key-value pair into the table
	@SuppressWarnings("unchecked")
	public void put(K key, V value)
	{
		int theTableIndex = indexFinder(key);

		//put the key-value pair into the sequential table at the correct location
		
		((SequentialTable)tableArray[theTableIndex]).put(key,value);
	}

	//get the associated value from the given key
	@SuppressWarnings("unchecked")
	public V get(K key)
	{
		int theTableIndex = indexFinder(key);

		return ((SequentialTable<K,V>)tableArray[theTableIndex]).get(key);
	}

	//deletes a key-value pair from the table
	@SuppressWarnings("unchecked")
	public void delete(K key)
	{
		int theTableIndex = indexFinder(key);

		((SequentialTable<K,V>)tableArray[theTableIndex]).delete(key);
	}

	//returns the total number of pairs in the entire HashTable
	@SuppressWarnings("unchecked")
	public int size()
	{
		int totalSize = 0;
		for(int i=0; i<tableArray.length; i++)
		{
			totalSize = totalSize + ( (SequentialTable<K,V>) tableArray[i]).size();//sum the sizes of the sequential tables
		}
		return totalSize;
	}

	//a helper method to get the index of the sequential table by hashing then using modulo by m
	private int indexFinder(K key)
	{
		return Math.abs((((Object)key).hashCode())%_M);//use Math.abs to avoid negative indices
	}

	//main method used for testing
	
	// @SuppressWarnings("unchecked")
	// public static void main(String[] args)
	// {

	// 	HashTable<String,Integer> h = new HashTable<String,Integer>();

	// 	System.out.println("hello, value: 9,  hashes to " + ((new String("hello")).hashCode())%997);

	// 	h.put(new String("hello"), new Integer(9));

		
	// 	System.out.println("world, value: 1234,  hashes to " + ((new String("world")).hashCode())%997);

	// 	h.put(new String("world"), new Integer(2));
	// 	h.put(new String("world"), new Integer(1234));
	// 	System.out.println("get hello then world, expect 9 and 1234");
	// 	System.out.println(((SequentialTable)h.tableArray[702]).get("hello"));
	// 	System.out.println(h.get("hello"));

	// 	System.out.println(((SequentialTable)h.tableArray[779]).get("world"));

	// 	System.out.println("remove hello and next print line expect null");
	// 	h.delete("hello");
	// 	System.out.println(((SequentialTable)h.tableArray[702]).get("hello"));

	// 	System.out.println(h.get("hello"));


	// }


}