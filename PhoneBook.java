/**
 * The Abstract Data Type Phone Book that is implemented through an arraylist and a linkedlist 
 * @author Jeffrey Kozik
 */
public interface PhoneBook{
  int size();
  /**
   * Inserts a person before a desired location in the phone book
   * Has a worst case running time of O(n)
   * @param i The position to insert the person before
   * @param person The person to be inserted
   */
  void insert(int i, Person person);
  /**
   * Removes the Person from the desired location
   * Has a worst case running time of O(n)
   * @param i The location at which to remove the Person from
   */
  Person remove(int i);
  /**
   * Checks what Person is at the given position
   * Has a worst case running time of O(n)
   * @param i The position at which to check the Person
   * @return The Person at the given position
   */
  Person lookup(int i);
  /**
   * Returns an instance of PhBIterator for an instance of Phone Book implemented using an array
   * Has a worst case running time of O(1)
   * @return An instance of PhBIterator
   */
  PhBIterator phBIterator();
  /**
   * Prints out the contents of the phone book in a readable format
   * Has a worst case running time of O(n)
   */
  default void printPhoneBook(){
    /* An iterator for the phone book */
    PhBIterator phBIt = phBIterator();
    /* Goes through the entire phone book. Each iteration a Person's data is printed out */
    while(phBIt.hasNext()){
      /* Temporarily stores each element */
      Person temp = phBIt.next();
      System.out.println("Person ID: " + temp.getPersonID() + " Phone Number: " + temp.getPhoneNum());
    }
  }
}