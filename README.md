# HuffmanEncoder
<h2> Notes </h2>
<ul>
  <li> Input file is read, HashMap stores frequencies, ArrayList encapsulates data in nodes then is turned into Huffman Tree which encodes characters. </li>
  <li> Created for Data Structures class in March 2020. </li>
  <li> The original file is the "truncated" Little Women file. It's named "truncated" because it's just the first 50 KB of the book, not the whole thing. </li>
  <li> The "encoded" Little Women file is the result of the huffman encoding. </li>
  <li> It's important to note the program doesn't actually perform sophisticated binary manipulation - rather it replaces each character with its huffman encoded binary equivalent (so a series of the characters "1" and "0"). This means the "encoded" file actually takes up more space than the original file. But, it's really the idea that counts in terms of generating the Huffman encoding. </li>
</ul>
 
