package transposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.*;

public class transposition {
	private String originalText;
	private ArrayList<Character> outputText = new ArrayList<Character>();
	private String key;
	private TreeMap <Character,ArrayList<Character>> map = new TreeMap<Character,ArrayList<Character>>();
	
	private void replaceSpace()
	{
		StringBuilder tempText = new StringBuilder(originalText);
		for(int i=0;i<originalText.length();i++)
		{
			if(originalText.charAt(i) == ' ')
			{
				tempText.setCharAt(i, '@');
			}
		}
		
		originalText = tempText.toString();
		//System.out.println(originalText);
	}
	
	private void addPadding()
	{
		
		replaceSpace();
		
	}
	
	
	private void printCipherToFile(String outputFileName)
	{
		FileWriter outputFile;
		try
		{
			outputFile = new FileWriter(outputFileName);
			BufferedReader bufferReader;
			
			try
			{
				for(Entry<Character, ArrayList<Character>> entry : map.entrySet()) 
				{
					  char currentKey = entry.getKey();
					  
					  for(int i=0; i<entry.getValue().size();i++)
					  {
						  outputFile.append(entry.getValue().get(i));
					  }
					  outputFile.append(' ');
				}
				
				outputFile.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private ArrayList<Character> putToRow(int index)
	{
		int counter=index;
		ArrayList<Character> temporary = new ArrayList<Character>();
		while(counter < originalText.length())
		{
			temporary.add(originalText.charAt(counter));
			counter += key.length();
		}
		
		
		return temporary;
	}
	
	private void putToMapEn()
	{
		for(int i=0; i<key.length();i++)
		{
			
			map.put(key.charAt(i), putToRow(i));
		}
	}
	
	
	public void encrypt(String key, String inputFileName, String outputFileName)
	{
		this.key=key;
		originalText = FileIO.readFile(inputFileName);
		addPadding();
		putToMapEn();
		printCipherToFile(outputFileName);
	}
	
	private void convertOutputToString()
	{
		StringBuilder temp = new StringBuilder(outputText.size());
		
		for (Character ch: outputText)
		{
			temp.append(ch);
		}
		
		originalText = temp.toString();
	}
	
	
	private void printPlainToFile(String outputFileName)
	{
		PrintWriter outputFile;
		try
		{
			outputFile = new PrintWriter(outputFileName);
			
			try
			{
				for(int i=0;i<map.get(key.charAt(0)).size();i++)
				{
					for(int j=0; j<key.length();j++)
					{
						outputText.add(map.get(key.charAt(j)).get(i));
						//outputFile.append(map.get(key.charAt(j)).get(i));
						
					}
				}
				
				convertOutputToString();
				removePadding();
				outputFile.print(originalText);
				outputFile.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeSpace()
	{
		StringBuilder tempText = new StringBuilder(originalText);
		for(int i=originalText.length()-1;i>=0;i--)
		{
			if(originalText.charAt(i) == ' ')
			{
				tempText.deleteCharAt(i);
			}
		}
		
		originalText = tempText.toString();
	}
	
	private void replacePaddingToSpace()
	{
		StringBuilder tempText = new StringBuilder(originalText);
		for(int i=0;i<originalText.length();i++)
		{
			if(originalText.charAt(i) == '@')
			{
				tempText.setCharAt(i, ' ');
			}
		}
		
		originalText = tempText.toString();
		//System.out.println(originalText);
	}
	
	private void removePadding()
	{
		removeSpace();
		replacePaddingToSpace();
	}
	
	private String shortKey(String key)
	{
		String sortedKey;
		char[] chars = key.toCharArray();
		Arrays.sort(chars);
		sortedKey = new String(chars);
		return sortedKey;
	}
	
	private void putToMapDec()
	{
		
		String sortedKey = shortKey(key);
		String[] aText = originalText.split(" ");
		for(int i=0; i<key.length();i++)
		{
			ArrayList<Character> unsortedText = new ArrayList<Character>();
			for(int j=0;j<aText[0].length();j++)
			{
				unsortedText.add(aText[i].charAt(j));
				
			}
			map.put(sortedKey.charAt(i), unsortedText);
		}
	}
	
	
	public void decryption(String key, String inputFileName, String outputFileName)
	{
		this.key=key;
		originalText = FileIO.readFile(inputFileName);
		putToMapDec();
		
		printPlainToFile(outputFileName);
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new transposition().decryption("4312567", "output.txt", "output1.txt");

	}

}
