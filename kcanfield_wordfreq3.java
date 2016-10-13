import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.util.Arrays;
/**
 * This is a program that counts the frequencies of words and also compares a separate chaining hash table with a standard linear probing hashtable with a double hashing hashtable.
 *
 * @author Kramer Canfield
 * @version 14 May 2014
 */
class wordfreq3
{
	public static void main(String[] args)
	{

		Scanner sc = null;//make a scanner from the file
		try
		{
			sc = new Scanner(new File(args[0]));
		}
		catch(IOException e)
		{
	       	System.err.println("Error reading file: " + args[0]);//handle a possible exception
	       	System.err.println("The program will now exit.");
	       	System.exit(1);
	    }

	    if(args.length != 1)
		{
			System.err.println("ERROR: Please only use one argument: the name of the text file. The program will now exit.");
			System.exit(1);
		}

		//make the HashTable, choose the type to use by commenting out the other two lines
		//HashTable<String,Integer> hashTable = new HashTable<String,Integer>();
		LPHashTable<String,Integer> hashTable = new LPHashTable<String,Integer>();
		//DHHashTable<String,Integer> hashTable = new DHHashTable<String,Integer>();

		//run through the text file line-by-line, adding words as we go
		long start = System.currentTimeMillis();
		String line = null;
		while(sc.hasNextLine())
		{		
			line = sc.nextLine();//the reason to store the line as a String is to avoid problems with the scanner's carriage placement
			if(line.equals(""))//if we happen to hit a blank line of text, move to the next line
				continue;
			line = line.toLowerCase();//convert to lower-case because the program should be case-insensitive

			String[] words = line.split("\\W+"); //use regex to split into an array of words (breaking on any number of non-word letters)

			for(int i=0; i<words.length; i++)//for each word in a line, add it to the HashTable
			{
				//if the word already exists, increment the value, otherwise, put it in the HashTable with a value of 1
				Integer value = hashTable.get(words[i]);
				if(value != null)
					hashTable.put(words[i], (value+1));
				else
					hashTable.put(words[i], 1);
			}
		}
		long stop = System.currentTimeMillis();
		sc.close();//close the file scanner


		//PROGRAM RESULTS
		System.out.println("----------\nThis text contains " + hashTable.size() + " distinct words.");
		System.out.println("Time to add all words: "+ (stop-start) + " milliseconds");
		System.out.println("Please enter a word to get its frequency, use a dash'-' before the word to remove it, or hit enter to leave.");


		//USER INTERFACE AND INTERACTION
		Scanner userScanner = new Scanner(System.in);
		while(true)//while the program is running
		{
			System.out.print(">");

			String input = userScanner.nextLine();
			
			if(input.equals(""))//if the user typed nothing, then exit
			{
				userScanner.close();
				break;
			}

			char[] inputChars = input.toCharArray();

			//check if the user is trying to delete a word or get the words frequency
			if(inputChars[0] == '-')
			{	
				String toRemove = "";
				for(int c=1; c<inputChars.length; c++)
					toRemove+=inputChars[c];

				hashTable.delete(toRemove);
				System.out.print("\""+toRemove+"\"");
				System.out.println(" has been deleted.");
			}
			else
			{
				Integer num = hashTable.get(input);
				if(num != null)
				{
					System.out.print("\""+input+"\"");
					System.out.println(" appears "+ num +" times.");
				}
				else
				{
					System.out.print("\""+input+"\"");
					System.out.println(" does not appear.");
				}	
			}
		}
		System.out.println("Goodbye!");
		System.exit(0);

	}
}