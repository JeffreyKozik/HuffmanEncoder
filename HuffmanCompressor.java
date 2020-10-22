/**
/** @author Jeffrey Kozik */
/*
 * Resources referenced:
 * https://canvas.case.edu/courses/21069/files/folder/Lecture%20Notes?preview=2310412 -> Data Structures Lecture Notes on Huffman Encoding
 * https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html -> Java API on ArrayList
 * https://canvas.case.edu/courses/18665/files/2019317?fd_cookie_set=1 -> Intro to Java Lab on File Reading
 * https://docs.oracle.com/javase/8/docs/api/java/io/FileReader.html -> Java API on FileReader
 * https://docs.oracle.com/javase/8/docs/api/java/io/InputStreamReader.html#read-- -> Java API on InputStreamReader
 * https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html -> Java API on HashMap
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html -> Java API on Character
 * https://repl.it/repls/ReflectingMealyObjectdatabase -> to test code until I got my Dr Java working
 * https://canvas.case.edu/courses/18665/files/2093370?module_item_id=732510 -> Intro to Java Lecture Notes on Lambda Expressions
 * https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#forEach-java.util.function.BiConsumer- -> Java API on Map Interface
 * https://canvas.case.edu/courses/18665/files/2070092?module_item_id=729195 -> Intro to Java Lecture Notes on Error Handling
 * https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html -> Java API on Comparator Interface
 * https://canvas.case.edu/courses/18665/files/2058212?module_item_id=727861 -> Intro to Java Lecture Notes of Comparator Interface
 * https://canvas.case.edu/courses/18665/files/2082426?module_item_id=730623 -> Intro to Java Lecture Notes on Nested and Anonymous Classes
 * https://docs.oracle.com/javase/8/docs/api/java/io/FileNotFoundException.html -> Java API on FileNotFoundException
 * https://docs.oracle.com/javase/8/docs/api/java/io/IOException.html -> Java API on IOException
 * https://canvas.case.edu/courses/18665/files/2093373?module_item_id=732512 -> Intro to Java Employee Class
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html -> Java API on Comparable Interface
 * https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html -> Java API on StringBuilder
 * https://docs.oracle.com/javase/8/docs/api/java/io/PrintStream.html -> Java API on PrintStream
 * Neo Huang's escapeSpecialCharacter Method
 * https://stackoverflow.com/questions/25085054/error-unchecked-call-to-putk-v-as-a-member-of-raw-type-java-util-hashmap/41034580 -> Blog on Unchecked Conversions for Hashmaps
 */

import java.io.FileReader;
import java.util.HashMap;
import java.util.*;
import java.io.*;
  
/** A class using mock Huffman Encoding to compress the size the of a file */
public class HuffmanCompressor{
  
  /** Neo Huang's code to display special characters more accurately */
  public static String escapeSpecialCharacter(String x) {
    StringBuilder sb = new StringBuilder();
    for (char c : x.toCharArray()) {
      if (c >= 32 && c < 127) sb.append(c);
      else sb.append(" [0x" + Integer.toOctalString(c) + "]");
    }
    return sb.toString();
  }
  
  /** 
   * Class to create an array list of nodes representing characters in the inputted file and their respective frequencies
   * I chose an array list over a linked list because the number of different characters in a file tends to stay relatively 
   * the same (there's only so many letters of the alphabet and numbers and so on). Array lists work well when they don't
   * have to be resized constantly because they provide random access to all elements in constant time.
   * @param inputFileName File path of file desired to be compressed
   * @return ArrayList<HuffmanNode> An array list of nodes representing characters and their frequencies
   * @throws FileNotFoundException If the filepath given isn't correct
   * @throws IOException If something goes wrong with the reading of the file
   */
  public static ArrayList<HuffmanNode> listCreator(String inputFileName) throws FileNotFoundException, IOException{
    
    /** To read through the inputted file */
    FileReader inputFileReader = new FileReader(inputFileName);
    /** 
     * HashMap to store characters and their frequencies. 
     * Has an initial size of 108 because the recommended load factor is 0.75 and from testing with "Little Women" 
     * there are around 75-80 different characters so making the arraylist have a reasonable capacity of 81 
     * and doing 81/0.75 obtains a hashmap with a size of 108
     */
    HashMap<Character, Integer> huffmanMap = new HashMap<Character, Integer>(108);
    /** The int value of the current char being read */
    int currentInt = inputFileReader.read();
    /** Stores frequency of the current character */
    Integer currentFrequency;
    /** Arraylist to store characters and frequencies wrapped in nodes*/
    ArrayList<HuffmanNode> huffmanList = new ArrayList<HuffmanNode>(81);
    
    /** Loop runs until whole file has been read. Each iteration adds a node to the array list */
    while(currentInt != -1){
      /** Character representation of what is currently being read */
      Character currentCharacter = (char)currentInt;
      currentFrequency = (Integer)huffmanMap.get(currentCharacter);
      
      /** 
       * If the key does exist in the hashmap, increase its frequency by one,
       * Otherwise put it on the hashmap with a starting frequency of one
       */ 
      if(currentFrequency != null){
        huffmanMap.put(currentCharacter, currentFrequency + 1);
      }
      else {
        huffmanMap.put(currentCharacter, 1);
        huffmanList.add(new HuffmanNode(currentCharacter));
      }
      
      currentInt = inputFileReader.read();
    }
    
    /** Sets each node's frequency of its respective character to what was computed and stored in huffmanMap */
    for(HuffmanNode node : huffmanList){
      node.setFrequency((Integer)huffmanMap.get(node.getInChar()));
    }
    
    return huffmanList;
  }
  
  /**
   * Merges two nodes into one - used when turning the arraylist into a binary search tree
   * @param node1 First node to be merged
   * @param node2 Other node to be merged
   * @return HuffmanNode The new node formed from the merger
   */
  public static HuffmanNode merge(HuffmanNode node1, HuffmanNode node2){
    return new HuffmanNode(null, node1.getFrequency() + node2.getFrequency(), node1, node2);
  }
  
  /**
   * Makes a binary search tree out of an arraylist
   * @param huffmanList The arraylist to turn into a tree
   * @return ArrayList<HuffmanNode> The returned tree (represented as an arraylist)
   */
  public static ArrayList<HuffmanNode> makeTree(ArrayList<HuffmanNode> huffmanList){
    
    /** 
     * Iterates until the array list consists of one node. Each iteration merges the 
     * Two nodes with the lowest frequencies
     */
    while(huffmanList.size() != 1){
      huffmanList.sort(HuffmanNode.compareByFrequency()); // figure out how to properly sort
      huffmanList.add(HuffmanCompressor.merge(huffmanList.get(0), huffmanList.get(1)));
      huffmanList.remove(0);
      huffmanList.remove(0);
      makeTree(huffmanList);
    }
    
    return huffmanList;
  }      
  
  /** 
   * Recursive method to traverse through a tree starting with the root and changing the huffmanEncoding
   * @param root The node that starts the tree off
   * @param huffmanMap The huffmanMap to store the huffman encodings into
   */
  public static void traverse(HuffmanNode root, HashMap<Character, String> huffmanMap){
    
    /** If the root equals null return the place in the code where the method was called */
    if(root == null){
      return;
    }
    /** If the current node is a leaf node, print out its frequency and huffman encoding */
    if(root.getInChar() != null){
      System.out.println(HuffmanCompressor.escapeSpecialCharacter(root.getInChar() + ": " + root.getFrequency() +  " : " + root.getHuffmanEncoding()));
      huffmanMap.put(root.getInChar(), root.getHuffmanEncoding());
    }
    /** 
     * If the current node's left child exists, change that child's huffmanEncoding to the current nodes plus "0" 
     * because it is to the left
     */
    if(root.getLeft() != null){
      root.getLeft().setHuffmanEncoding(root.getHuffmanEncoding() + "0");
    }
    traverse(root.getLeft(), huffmanMap);
    /** 
     * If the current node's right child exists, change that child's huffmanEncoding to the current nodes plus "1" 
     * because it is to the right
     */
    if(root.getRight() != null){
      root.getRight().setHuffmanEncoding(root.getHuffmanEncoding() + "1");
    }
    traverse(root.getRight(), huffmanMap);
  }
  
  /** Writes the encoded version of the inputted file into the outputted file using the provided hashmap
    * @param inputFileName The file to read from
    * @param outputFileName The file to write to
    * @param huffmanMap The hashmap to grab the huffman encoding data from
    * @throws FileNotFoundException If the file path is wrong
    * @throws IOException If something goes wrong with the reading and writing process
    */
  public static void encodeFile(String inputFileName, String outputFileName, HashMap huffmanMap) throws FileNotFoundException, IOException{   
    
    /** Used to read the inputted file */
    FileReader inputFileReader = new FileReader(inputFileName);
    /** Used to write to the output file */
    PrintStream outputFile = new PrintStream(new FileOutputStream(outputFileName));
    /** Stores the int value of the current character being read */
    int currentInt = inputFileReader.read();
    /** Stores how many bits the old file theoretically takes up */
    int previousBitCount = 0;
    /** Stores how many bits the new file will theoretically take up */
    int newBitCount = 0;

    /** 
     * Iterates until the whole file has been read. Each iteration writes to the new file using the huffman encoding
     * And keeps track of the amount of bits stored in the old and new files
     */
    while(currentInt != -1){
      previousBitCount += 8;
      /** Character representation of what is being read */
      Character currentCharacter = (char)currentInt;
      /** String representation of the huffman encoding */
      String currentHuffmanEncoding = (String)huffmanMap.get(currentCharacter);
      outputFile.print(currentHuffmanEncoding);
      newBitCount += currentHuffmanEncoding.length();
      currentInt = inputFileReader.read();
    }
    
    System.out.println("Previous bit count: " + previousBitCount + ", New bit count: " + newBitCount);
    System.out.println("Bits saved: " + (previousBitCount - newBitCount) + ", New file amount of bits as percentage of old file: " + (int)(((double)newBitCount/previousBitCount) * 100) + "%");
  }
    
  /** 
   * Method that uses all of the helper methods to read from the inputted file and write to the output file 
   * using huffman encoding
   * @param inputFileName The file being read
   * @param outputFileName The file being written to
   */
  public static String huffmanCoder(String inputFileName, String outputFileName){
    /** Tries to read and write, if it works return "OK" to signal it worked */
    try {
      /** Hashmap to store the characters' huffman encodings. Initial size of 108 see previous comment about load factor */
      HashMap<Character, String> huffmanMap = new HashMap<Character, String>(108); 
      /** Arraylist representation of huffman tree */
      ArrayList<HuffmanNode> huffmanList = HuffmanCompressor.makeTree(HuffmanCompressor.listCreator(inputFileName));
      HuffmanCompressor.traverse(huffmanList.get(0), huffmanMap);
      HuffmanCompressor.encodeFile(inputFileName, outputFileName, huffmanMap);
      return "OK";
    } 
    /** If an error occurs return "File Not Found" to indicate such */
    catch (FileNotFoundException e){
      return "File Not Found";
    }
    catch (IOException e){
      return "Input File Error";
    }
  }

  /** A nested class to hold data about the characters found in the inputted file */
  public static class HuffmanNode implements Comparator<HuffmanNode>{
    
    /** 
     * Fields of the HuffmanNode class to store the character, how much it appears in the file, 
     * its left and right children and its huffman encoding 
     */
    private Character inChar;
    private int frequency;
    private HuffmanNode left;
    private HuffmanNode right;
    private String huffmanEncoding;
    
    /** Constructs new instance of a HuffmanNode using all fields except huffmanEncoding */
    public HuffmanNode(Character inChar, int frequency, HuffmanNode left, HuffmanNode right){
      this.inChar = inChar;
      this.frequency = frequency;
      this.left = left;
      this.right = right;
      this.huffmanEncoding = "";
    }
    
    /** Constructs a new instance of a HuffmanNode using just the character it represents */
    public HuffmanNode(Character inChar){
      this.inChar = inChar;
      this.frequency = 1;
      this.left = null;
      this.right = null;
      this.huffmanEncoding = "";
    }
    
    /** Getters and setters for the various fields */
    public Character getInChar(){
      return this.inChar;
    }
    
    public void setInChar(Character inChar){
      this.inChar = inChar;
    }
    
    public int getFrequency(){
      return this.frequency;
    }
    
    public void setFrequency(int frequency){
      this.frequency = frequency;
    }
    
    public HuffmanNode getLeft(){
      return this.left;
    }
    
    public void setLeft(HuffmanNode left){
      this.left = left;
    }
    
    public HuffmanNode getRight(){
      return this.right;
    }
    
    public void setRight(HuffmanNode right){
      this.right = right;
    }
    
    public String getHuffmanEncoding(){
      return this.huffmanEncoding;
    }
    
    public void setHuffmanEncoding(String huffmanEncoding){
      this.huffmanEncoding = huffmanEncoding;
    }
    
    /** Overriding non default method in Comparator interface to compare two nodes by frequency */
    public int compare(HuffmanNode node1, HuffmanNode node2){
      return (int)(node1.getFrequency() - node2.getFrequency());
    }
    
    /** Method to return a Comparator that sorts by frequency from lowest to highest */
    public static Comparator<HuffmanNode> compareByFrequency(){
      return (a, b) -> (a.getFrequency() - b.getFrequency());
    }
  }
  
  /** 
   * Main method so that user can input the file they'd like to be compressed and where to output to 
   * @param args What the user types in
   */
  public static void main (String[] args){
    /** If the correct amount of arguments are inputted, method carries through */
    try{
      System.out.println(huffmanCoder(args[0], args[1]));
    }
    /** If the wrong amount of arguments are inputted that is communicated to the user */
    catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Please type the file name you'd like encoded and the file you'd like to output to.");
    }
  }
}