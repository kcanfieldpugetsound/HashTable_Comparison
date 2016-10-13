/**
 * This class is a Sequential-Search-Symbol-Table. It contains an array of keys and and array of values.
 * @author Kramer Canfield
 * @version 28 February 2014
 */

class SequentialTable<K,V> extends SymbolTable<K,V>
{

	//fields
	public Object[] keys; //the array of keys
	private Object[] values; //the array of values
	private int size; //the size of the table: the number of key-value pairs
	private int stepSize;
	//private final int _M = 997;//the m to modulo by

	//the constructor
	public SequentialTable()
	{
		keys = null;//delay instantiation of arrays until first put
		values = null;
		size=0;
		stepSize = 20;
	}

	// map key to value
	@SuppressWarnings("unchecked")
	public void put(K key, V value)
	{


		if(keys==null)
		{
			keys = new Object[30];
			values = new Object[30];
		}

		// System.out.println(key);

		boolean addingNewPair;//assume we're adding a new pair, unless the linear search produces otherwise
		

		int index = -1;

		if(get(key) != null)//if the key already exists
		{
			addingNewPair = false;

			for(int i=0; i<keys.length; i++)
			{
				if(keys[i] != null)
				{
					if(keys[i].equals(key))
					{
						index = i;
					}
				}
			}
		}
		else//if the key does not already exist
		{

			// System.out.println("the key does not exist");
			addingNewPair = true;

			for(int i=0; i<keys.length; i++)
			{
				if(keys[i] == null)
				{
					index = i;
					break;
				}
			}

		}

		if(addingNewPair==true)
		{
			size++;
		}

		// System.out.println("index:" + index);
		//overwrite the pair at that spot in the array
		keys[index] = key;
		values[index] = value;

		// System.out.println(keys[index]);
		// System.out.println(values[index]);


		//check for resize
		if(size+stepSize > keys.length)
		{
			Object[] newKeys = new Object[size+stepSize];
			Object[] newValues = new Object[size+stepSize];
			System.arraycopy(keys,0,newKeys,0,size);
			System.arraycopy(values,0,newValues,0,size);
			keys = newKeys;
			values = newValues;
		}
	}

    // return key's value, returns null if the key is not present
    @SuppressWarnings("unchecked")
    public V get(K key)
    {
     	//linear search through the key array
     	int index = -1;
    	for(int i=0; i<size; i++)
    	{
    		if(key.equals(keys[i]))
    		{
    			index = i;
    		}
    	}

    	//return the value if the key was found, otherwise, return null
     	if(index != -1)
     	{
     		Object theValueToReturn;
     		theValueToReturn = (V)(values[index]);//get the object 
     		return (V)(theValueToReturn);
     	}
     	else
     	{
     		return null;
     	}
     		
    }

    // remove key-value pair
    @SuppressWarnings("unchecked")
    public void delete(K key)
    {
    	
    	//linear search through the array to find the index of the pair to delete
    	int indexToRemove = -1;
    	for(int i=0; i<keys.length; i++)
    	{
    		if(keys[i] != null)//if the key is null, keep going
    		{
    			if(keys[i].equals(key))
    				indexToRemove = i;
    		}

    	}

    	//then set that entry in the arrays to be null and decrement the size
    	if(indexToRemove != -1)
    	{
    		keys[indexToRemove] = null;
			values[indexToRemove] = null;
			size--;
    	}

    }	

    // how many pairs?
    public int size()
    {
     	return size;
    }	







    
	//main method used for testing

	public static void main(String[] args)
	{
		SequentialTable<String,Integer> myST = new SequentialTable<String,Integer>();

		System.out.println("\n	size:  " + myST.size() + ";   0");
		
		System.out.println("NOW ADDING HELLO");

		myST.put(new String("hello"), new Integer(0));

		System.out.println(myST.keys[0]);
		System.out.println(myST.values[0]);
		System.out.println("\n	size:  " + myST.size() + ";   1");
		
		System.out.println("NOW ADDING WORLD");

		myST.put(new String("world"), new Integer(1));

		System.out.println(myST.keys[0] + ", "+ myST.keys[1]);
		System.out.println(myST.values[0] + ", "+ myST.values[1]);
		System.out.println("\n	size:  " + myST.size() + ";   2");



		myST.put("world", new Integer(17));

		System.out.println(myST.keys[0] + ", "+ myST.keys[1]);
		System.out.println(myST.values[0] + ", "+ myST.values[1]);
		System.out.println("\n	size:  " + myST.size() + ";   2");


		System.out.println("GETTING HELLO");
		System.out.println(myST.get("hello"));

		
		System.out.println("NOW REMOVING HELLO ");

		myST.delete("hello");

		System.out.println("HELLO REMOVED");


		System.out.println("GETTING HELLO");
		System.out.println(myST.get("hello"));


		System.out.println(myST.keys[0] + ", "+ myST.keys[1]);
		System.out.println(myST.values[0] + ", "+ myST.values[1]);
		System.out.println("\n	size:  " + myST.size() + ";   1");
		
		System.out.println("NOW REMOVING WORLD ");

		myST.delete("world");
		
		System.out.println(myST.keys[0] + ", "+ myST.keys[1]);
		System.out.println(myST.values[0] + ", "+ myST.values[1]);
		System.out.println("\n	size:  " + myST.size() + ";   0");

	}

	


}