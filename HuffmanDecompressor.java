import java.io.FileReader;
import java.util.HashMap;
import java.util.*;
import java.io.*;

public class HuffmanDecompressor{
  
  public static HashMap<String, Character> listCreator(String inputFileName) throws FileNotFoundException, IOException{
    
    FileReader inputFileReader = new FileReader(inputFileName);
    HashMap<String, Character> huffmanMap = new HashMap<String, Character>(108);
    int currentInt = inputFileReader.read();
    int colonCount = 0;
    boolean justSpaced = false;
    boolean nextLine = true;
    Character justHashed = ' ';
    StringBuilder huffCode = new StringBuilder("");
    
    while(currentInt != -1){
      Character currentCharacter = (char)currentInt;
      
      if(nextLine){
        justHashed = currentCharacter;
        nextLine = false;
      }
      
      if(currentCharacter == '\n'){
        huffmanMap.put(huffCode.toString(), justHashed);
        huffCode = new StringBuilder("");
        justSpaced = false;
        nextLine = true;
        colonCount = 0;
      }
      
      if(justSpaced){
        huffCode.append(currentCharacter);
      }
      
      if(currentCharacter == ':'){
        colonCount++;
      }
            
      if(currentCharacter == ' ' && colonCount == 2){
        justSpaced = true;
      }
      
      currentInt = inputFileReader.read();
    }
    
    //huffmanMap.put("00010", '\n');
    
    return huffmanMap;
  }
  
  public static void encodeFile(String inputFileName, String outputFileName, HashMap huffmanMap) throws FileNotFoundException, IOException{   
    
    /** Used to read the inputted file */
    FileReader inputFileReader = new FileReader(inputFileName);
    /** Used to write to the output file */
    PrintStream outputFile = new PrintStream(new FileOutputStream(outputFileName));
    /** Stores the int value of the current character being read */
    int currentInt = inputFileReader.read();
    StringBuilder huffCode = new StringBuilder("");

    /** 
     * Iterates until the whole file has been read. Each iteration writes to the new file using the huffman encoding
     * And keeps track of the amount of bits stored in the old and new files
     */
    while(currentInt != -1){
      /** Character representation of what is being read */
      Character currentCharacter = (char)currentInt;
      huffCode.append(currentCharacter);
      /** String representation of the huffman encoding */
      
      if(huffmanMap.containsKey(huffCode.toString())){
        Character currentHuffmanEncoding = (Character)huffmanMap.get(huffCode.toString());
        //System.out.println(currentHuffmanEncoding);
        outputFile.print(currentHuffmanEncoding);
        huffCode = new StringBuilder("");
      }
        currentInt = inputFileReader.read();
    }
    
  }
  // java HuffmanDecompressor encodingFile.txt encodedFile.txt decodedOutputFile.txt
  // encodingFile shows the huffman encoding of all the characters
  // encodedFile is the file trying to be decoded
  // decodedOutputFile is the decoded file you're outputting
  public static void main (String[] args) throws FileNotFoundException, IOException{
    HashMap<String, Character> mup = listCreator(args[0]);
    encodeFile(args[1], args[2], mup);
  }
}