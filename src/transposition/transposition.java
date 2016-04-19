package transposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.*;
import java.util.Random;

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
		
	}
	
	private int getRandomCharacter(int mod, int modResult, boolean isSameMod)
	{
		
		
		Random r = new Random();
		int randomResult=0;
		
		if(!isSameMod)
		{
			do
			{
				randomResult = (r.nextInt(25) + (int)'a');
			}
			while((randomResult%mod) == 0);
		}
		
		else
		{
			do
			{
				randomResult = (r.nextInt(25) + (int)'a');
			}
			while((randomResult%mod) != modResult);
		}
		
		return randomResult;
	}
	
	private void addPadding()
	{
		final int MAXROWLENGTH = map.get(key.charAt(0)).size();
		
		for(int i=0; i<key.length(); i++)
		{
			int totalASCII=0;
			for(int j=0; j<MAXROWLENGTH-1; j++)
			{
				totalASCII += (int)map.get(key.charAt(i)).get(j);
			}
			
			if(map.get(key.charAt(i)).size() != MAXROWLENGTH)
			{
				int modResult = totalASCII%MAXROWLENGTH;
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH,modResult,false));
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH,modResult,true));
			}
			else
			{
				totalASCII += (int)map.get(key.charAt(i)).get(MAXROWLENGTH-1);
				int modResult = totalASCII%MAXROWLENGTH;
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH,modResult,true));
			}
		}
		
		
		
		replaceSpace();
		
		
	}
	
	
	private String generateChiperText()
	{
		
		StringBuilder textHolder = new StringBuilder();
		
				for(Entry<Character, ArrayList<Character>> entry : map.entrySet()) 
				{
					  
					  
					  for(int i=0; i<entry.getValue().size();i++)
					  {
						  
						  textHolder.append(entry.getValue().get(i));
						  
					  }
					  textHolder.append(' ');
				}
				
		return textHolder.toString();
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
		putToMapEn();
		addPadding();
		FileIO.printCipherToFile(outputFileName, generateChiperText());
		
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
	
	
	private String generatePlainText()
	{
				for(int i=0;i<map.get(key.charAt(0)).size();i++)
				{
					for(int j=0; j<key.length();j++)
					{
						if(map.get(key.charAt(j)).size()==map.get(key.charAt(0)).size())
							outputText.add(map.get(key.charAt(j)).get(i));
					}
					
				}
				convertOutputToString();
				removePadding();
				return originalText;
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
	}
	
	private void removePadding()
	{
		
		removeSpace();
		replacePaddingToSpace();
	}
	
	private void removePadingg()
	{
		final int MAXROWLENGTH = (map.get(key.charAt(0)).size())-1;
		
		
		for(int i=0; i<key.length(); i++)
		{
			int totalASCII=0;
			for(int j=0; j<MAXROWLENGTH; j++)
			{
				totalASCII += (int)map.get(key.charAt(i)).get(j);
				
			}
			
			
			if((totalASCII%MAXROWLENGTH ) == ((int)map.get(key.charAt(i)).get(MAXROWLENGTH) % MAXROWLENGTH))
			{
				map.get(key.charAt(i)).set(MAXROWLENGTH, ' ');
				
			}
			
			else
			{
				map.get(key.charAt(i)).set(MAXROWLENGTH, ' ');
				map.get(key.charAt(i)).set(MAXROWLENGTH-1, ' ');
				
			}
			
		}
		
		
		
		replaceSpace();
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
		removePadingg();
	}
	
	
	public void decryption(String key, String inputFileName, String outputFileName)
	{
		this.key=key;
		originalText = FileIO.readFile(inputFileName);
		putToMapDec();
		FileIO.printCipherToFile(outputFileName, generatePlainText());
	}
	
	

	public static void main(String[] args) {
		
		new transposition().encrypt("erwin", "test.txt", "output.txt");
		new transposition().decryption("erwin", "output.txt", "output1.txt");

	}

}
