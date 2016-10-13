import java.util.Arrays;
/**
 * This class represents a standard linear-probing hashtable using a step-size of 1.
 * It works with Objects and Java generics to be more flexible in the future. 
 *
 * @author Kramer Canfield
 * @version 14 May 2014
 */
class LPHashTable<K,V>
{
	//fields a.k.a. class variables
	private Object[] keys; //the array of keys
	private Object[] values; //the array of values
	private int size; //the size of the table: the number of key-value pairs
	private Object pairWasDeleted; //a special marker to use for deleted objects

	//constructor
	public LPHashTable()
	{
		keys = new Object[101];//initialize class variables, use prime number for array length to help maintain the uniform hashing assumption
		values = new Object[101];
		pairWasDeleted = new Object();
		size=0;
	}

	//puts a key-value pair into the table
	@SuppressWarnings("unchecked")
	public void put(K key, V value)
	{
		//find the index where the key should go
		int expected = findExpectedIndex(key);

		if(get(key)==null) { size++; }//if the key does not already exist, increase the size

		//if the spot is open, or the key is already there, then put it there
		if(keys[expected] == null || keys[expected].equals(key))
		{
			keys[expected] = key;
			values[expected] = value;
		}
		else//otherwise, find the next available location to put the pair
		{
			expected = findAvailableIndex(key, expected);
			keys[expected] = key;
			values[expected] = value;
		}
		
		//check to see if we need to rehash, if we do, then rehash
		if(checkRehash())
			rehash();		
	}

	//get the associated value from the given key, return null if the key does not exist
	@SuppressWarnings("unchecked")
	public V get(K key)
	{
		int expected = findExpectedIndex(key);
		if(keys[expected]!=null)
		{
			if(keys[expected].equals(key))//if the key is where it should be
				return (V)values[expected];//then return the value
		}
		int actual = findActualIndex(key,expected);
		if(actual != -1)
			return (V)values[actual];
		else
			return null;
	}

	//deletes a key-value pair from the table
	@SuppressWarnings("unchecked")
	public void delete(K key)
	{
		int expected = findExpectedIndex(key);
		if(keys[expected]!=null)
		{	
			if(keys[expected].equals(key))//if the key is where it should be
				deleteInfoAt(expected);//then delete the node
			else
			{
				int actual = findActualIndex(key,expected);//otherwise, find where it really is and delete it from there
				deleteInfoAt(actual);
			}
		}
	}
	//a helper method that removes data at the specified index and decrements size
	private void deleteInfoAt(int index)
	{
		keys[index] = null;
		values[index] = pairWasDeleted;//use this special Object as a marker so we can find things that were placed after it but still within its cluster
		size--;
	}

	//returns the total number of pairs in the entire HashTable
	@SuppressWarnings("unchecked")
	public int size()
	{
		return size;
	}

	//this method expands and rehashes the entire hashtable
	@SuppressWarnings("unchecked")
	private void rehash()
	{
		Object[] newKeys = new Object[keys.length*2];
		Object[] newValues = new Object[keys.length*2];

		Object[] oldKeys = keys;
		Object[] oldValues = values;

		keys = newKeys;
		values = newValues;
		size=0;//reset the size to 0 because rehashing uses put() which increments size
		for(int i=0; i<oldKeys.length; i++)
		{
			if(oldKeys[i]!=null)
			{
				put((K)oldKeys[i], (V)oldValues[i]);
			}
		}
	}

	//checks to see if we need to rehash, returns true if we do, returns false otherwise
	private boolean checkRehash()
	{
		double alpha = size/keys.length;//alpha = n/m where n is the number of pairs and m is the array length
		return (alpha >= 0.5);//we want to keep alpha close to 0.5
	}

	//finds the supposed or expected index of the given key
	@SuppressWarnings("unchecked")
	private int findExpectedIndex(K key)
	{
		int hashcode = Math.abs(key.hashCode());
		int hashAndMod = hashcode % keys.length;
		return hashAndMod;
	}
	//finds the actual index of the given key in the table
	@SuppressWarnings("unchecked")
	private int findActualIndex(K key, int startIndex)
	{
		int indexToReturn = startIndex;

		while(true)//while we are still looking
		{
			if(keys[indexToReturn] != null)//the key is not null
			{
				if(keys[indexToReturn].equals(key))//if it's the key we want
					return indexToReturn; // then return that index
				else//it's something else
				{	//increase the index to keep going
					if(indexToReturn+1 < keys.length)
						indexToReturn++;
					else
						indexToReturn=0;//the wrap around to the beginning
				}
			}	
			else//the key is null
			{
				if(values[indexToReturn]==null)
					break;//break because we hit the end of the cluster
				if(values[indexToReturn]==pairWasDeleted)//increase the index to keep going, there was something here
				{
					if(indexToReturn+1 < keys.length)
						indexToReturn++;
					else
						indexToReturn=0;//the wrap around to the beginning
				}
			}
		}
		return -1;//we did not find it
	}

	//finds the next available index in the table
	@SuppressWarnings("unchecked")
	private int findAvailableIndex(K key, int startIndex)
	{
		int indexToReturn = startIndex;//start where specified

		for(int i=0; i<keys.length; i++)
		{
			//if the spot is NOT free AND NOT taken by the key we want, go to the next entry
			if(keys[indexToReturn] != null && !(keys[indexToReturn].equals(key)))
			{
				if(indexToReturn+1 >= keys.length)
					indexToReturn=0;//wrap around to the beginning
				else
					indexToReturn++;

			}
			else//the spot is free OR it's the key we want
				return indexToReturn;
		}
		return indexToReturn;
	}


	@SuppressWarnings("unchecked")
	public String[] getKeys()
	{
		String[] s = new String[keys.length];

		for(int i=0; i<keys.length; i++)
			s[i] = (String) keys[i];

		return s;
	}

//a main method for testing
// public static void main(String[] args)
// {
// 	LPHashTable<String,Integer> l = new LPHashTable<String,Integer>();
// 	l.put(new String("hello"),new Integer(12));
// 	l.put(new String("world"),new Integer(12));
// 	l.put(new String("hello"),new Integer(14));
// 	l.put(new String("john"),new Integer(1));
// 	l.put(new String("paul"),new Integer(2));
// 	l.put(new String("george"),new Integer(3));
// 	l.put(new String("ringo"),new Integer(4));
// 	l.put(new String("ringo"),new Integer(2));
// 	l.put(new String("ringo"),new Integer(4));
// 	String d = "drowned";
// 	l.put(d,new Integer(4312421));
		
// 	System.out.println(Arrays.toString(l.keys));
// 	System.out.println("**************");
// 	System.out.println(Arrays.toString(l.values));
// 	System.out.println(l.get("hello"));
// 	System.out.println(l.get("paul"));
// 	System.out.println(l.get("ringo"));
// 	System.out.println(l.get("drowned"));

// 	System.out.println("expect 14, 2, 4, 4312421");
// 	System.out.println("hash and mod 'drowned':" + d.hashCode()%l.keys.length);
// 	System.out.println("size:(7) "+l.size());	
// }

}