package transposition;

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.*;

public class transposition {
	private String originalText;
	private String outputText;
	private String key;
	private TreeMap <Character,ArrayList<Character>> map = new TreeMap<Character,ArrayList<Character>>();
	
	private void readFile(String inputFileName)
	{
		FileReader inputFile;
		try
		{
			inputFile = new FileReader(inputFileName);
			BufferedReader bufferReader;
			
			try
			{
				bufferReader = new BufferedReader(inputFile);
				originalText = bufferReader.readLine();
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
		
		
		System.out.println(originalText);
	}
	
	
	private void addPadding()
	{
		
		
	}
	
	private void putToRow()
	{
		
	}
	
	private void putToMap()
	{
		
	}
	
	
	public void encrypt(String key, String inputFileName, String outputFileName)
	{
		readFile(inputFileName);
	}
	
	
	
	private void removePadding()
	{
		
	}
	
	
	public void decryption(String key, String inputFileName, String outputFileName)
	{
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new transposition().encrypt("123", "test.txt", "none");

	}

}
