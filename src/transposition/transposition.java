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
	
	private void isKeyUnique()
	{
		if(key.length()>10 || key.length()<1)
		{
			System.err.println("your key is too long, please insert key that is below 10 characters and above 0 characters");
			System.exit(0);
		}
		
		for(int i=0; i<key.length();i++)
		{
			int count=0;
			for(int j=0; j<key.length();j++)
			{
				if(key.charAt(i) == key.charAt(j))
				{
					count++;
				}
			}
			if(count > 1)
			{
				System.err.println("your key is not unique, please try different key which is unique!!");
				System.exit(0);
			}
		}
	}
	
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
			if(modResult == 0)
			{
				do
				{
					randomResult = (r.nextInt(25) + (int)'a');
				}
				while((randomResult%2) == 1);
			}
			do
			{
				randomResult = (r.nextInt(25) + (int)'a');
			}
			while((randomResult%mod) == 0);
		}
		
		else
		{
			if(modResult == 0)
			{
				do
				{
					randomResult = (r.nextInt(25) + (int)'a');
				}
				while((randomResult%2) == 0);
			}
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
				int modResult = totalASCII%(MAXROWLENGTH+1);
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH+1,modResult,false));
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH+1,modResult,true));
			}
			else
			{
				totalASCII += (int)map.get(key.charAt(i)).get(MAXROWLENGTH-1);
				int modResult = totalASCII%(MAXROWLENGTH+1);
				map.get(key.charAt(i)).add((char)getRandomCharacter(MAXROWLENGTH+1,modResult,true));
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
		isKeyUnique();
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
				removeSpace();
				replacePaddingToSpace();
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
		final int MAXROWLENGTH = (map.get(key.charAt(0)).size())-1;
		
		
		for(int i=0; i<key.length(); i++)
		{
			int totalASCII=0;
			for(int j=0; j<MAXROWLENGTH; j++)
			{
				totalASCII += (int)map.get(key.charAt(i)).get(j);
				
			}
			
			if((totalASCII%(MAXROWLENGTH+1) ) == ((int)map.get(key.charAt(i)).get(MAXROWLENGTH) % (MAXROWLENGTH+1)))
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
		removePadding();
	}
	
	
	public void decryption(String key, String inputFileName, String outputFileName)
	{
		this.key=key;
		isKeyUnique();
		originalText = FileIO.readFile(inputFileName);
		putToMapDec();
		FileIO.printCipherToFile(outputFileName, generatePlainText());
	}
	
	public void encryptOrDecrypt(String option,String key, String inputFileName, String outputFileName)
	{
		if(option.equals("-e"))
		{
			encrypt(key, inputFileName, outputFileName);
		}
		else if(option.equals("-d"))
		{
			decryption(key, inputFileName, outputFileName);
		}
		else
		{
			System.err.println("wrogn input");
		}
	}
	
	

	public static void main(String[] args) {
		
		new transposition().encryptOrDecrypt(args[0], args[1], args[2], args[3]);

	}

}
