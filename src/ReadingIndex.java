/***********************************************************
 * Program: Assignment 4 - Reading Index
 * Course: CSCI 200 - 001
 * Author: Tanner Crocker
 * Date: April 12, 2015
 * Description:
 * 	A program that reads through a file to determine
 * 		the number of sentences, words, and syllables
 * 		and calculate the reading index of the file.
***********************************************************/
import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.*;

public class ReadingIndex 
{

	public static void main(String[] args) throws IOException
	{
		//initial declaration of variables and objects
		double rIndex;
		int countWord = 0, countSyll = 0, countSentence = 0;
		String fileName = "", word = "", line = "";
		Scanner scan = new Scanner(System.in);
		Scanner wordScan;
		DecimalFormat readIndexFmt = new DecimalFormat("0.##");
		
		//get the file name from the user
		System.out.print("Please enter the name of the file (including the extension): ");
		fileName = scan.next();
		Scanner fileScan = new Scanner( new File(fileName) );
		System.out.println();
		
		//do while the document has another statement
		do
		{
			line = fileScan.nextLine();
			wordScan = new Scanner (line);
			
			//while the line of text has another word
			while (wordScan.hasNext())
			{
				word = wordScan.next();
				countWord++;
				
				if  (word.endsWith(".") || word.endsWith("?") || word.endsWith("!") || word.endsWith(":") || word.endsWith(";"))
				{
					countSentence++;
				}
				
				countSyll += ReadingIndex.countSyllables(word);
			}
		}
		while(fileScan.hasNext());
		
		//calculate reading index
		rIndex = 206.835 - 84.6 * ((double) countSyll/countWord) - 1.015 * ((double) countWord/countSentence);
		
		//Print out the stats & reading level of the document
		System.out.println(fileName + " Statistics:" + "\n\t" +
				"Total Words:\t\t" + countWord + "\n\t" +
				"Total Syllables:\t" + countSyll + "\n\t" +
				"Total Sentences:\t" + countSentence + "\n\t" +
				"Index Computed:\t\t" + readIndexFmt.format(rIndex));
		System.out.println();
		System.out.println("The document's reading level is aimed for " + 
				( ((rIndex >= 66) && (rIndex <= 70) ) ? "an" : "a" ) +  " " + ReadingIndex.calculateEduLevel(rIndex) );
		
		
		//Scanner closure
		scan.close();
		fileScan.close();
		wordScan.close();
	}
	
	/***************************************************
	 * 					Is Vowel					   *
	 ***************************************************/
	//used to determine number of syllables
	private static boolean isVowel(char c)
	{
		c = Character.toLowerCase(c);
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y' )
			return true;
		else
			return false;
	}
	
	/***************************************************
	 * 				Count Syllables					   *
	 ***************************************************/
	//used to get the number of syllables in a word
	private static int countSyllables(String a)
	{
		//remove punctuation and send to lower case
		if (a.endsWith(".") || a.endsWith("?") || a.endsWith("!") || a.endsWith(":") || a.endsWith(";") || a.endsWith(",") || a.endsWith("\'") || a.endsWith(")") )
		{
			a = a.substring(0, a.length()-1);
		}
		a = a.toLowerCase();
		
		int num = 0;
		//reads all letters of the word
		for (int x = 0; x < a.length(); x++)
		{
			if (ReadingIndex.isVowel(a.charAt(x)))
			{
				if (!(a.endsWith("e") /*|| a.endsWith("es")*/))
				{	
					//increment num by 1 when x is not the last character and the next character is not a vowel 
					if ( (x < a.length()-1) && !(ReadingIndex.isVowel(a.charAt(x+1))) )
					{
						num++;
					}
					//IF x is last index AND a vowel(not e)
					if ( (x == a.length()-1) && (a.endsWith("a") || a.endsWith("i") || a.endsWith("o") || a.endsWith("u") || a.endsWith("y")) )
					{
						num++;
					}
				}
				else
				{
					//IF 'x' is not the last index AND the next vowel is NOT a vowel
					if ( (x < a.length()-1) && !(ReadingIndex.isVowel(a.charAt(x+1))) )
					{
						num++;
					}
					//IF there are multiple vowels at the end of the word AND there is an ending e
					if ( (x == a.length()-2) && (ReadingIndex.isVowel(a.charAt(x))) )
					{
						num++;
					}
				}
			}
		}
		
		//If word has no syllables
		if (num <= 0)
		{
			num = 1;
		}
		
		return num; 
	}
	
	/***************************************************
	 * 				Calculate Edu Level				   *
	 ***************************************************/
	//determines the string equivalent of the reading index double
	private static String calculateEduLevel(double ri)
	{
		if (ri > 100)
			return "Preschooler to Fourth Grader";
		else 
		{
			if (ri >= 91)
				return "Fifth Grader";
			else
			{
				if (ri >= 81)
					return "Sixth Grader";
				else
				{
					if (ri >= 71)
						return "Seventh Grader";
					else
					{
						if (ri >= 66)
							return "Eighth Grader";
						else
						{
							if (ri >= 61)
								return "Ninth Grader";
							else
							{
								if (ri >= 51)
									return "High School Student";
								else
								{
									if (ri >= 31)
										return "College Student";
									else
									{
										if (ri >= 0)
											return "College Graduate";
										else
											return "Law School Graduate";
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
