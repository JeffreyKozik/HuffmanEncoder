/**
 * Implements methods to be utilized by PhBIterators regardless of 
 * If the phone book they are used by uses the arraylist or linkedlist implementation
 * @author Jeffrey Kozik
 */
public interface PhBIterator{
  Person next();
  boolean hasNext();
  boolean iteratorRemove();
}
  
  