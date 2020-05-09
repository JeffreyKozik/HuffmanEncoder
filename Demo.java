/**
 * Implements the removeDuplicates method and main method to show 
 * That the Phone Book Abstract Data Type works as designed
 * @author Jeffrey Kozik
 */
public class Demo{
  /**
   * Removes the elements that are shared in common by two phonebooks
   * Has a worst case running time of O(n^3) - there are two iterators and a 
   * @param phB1 The first phone book to check duplicates with
   * @param phB2 The second phone book to check duplicates with
   */
  public static void removeDuplicates(PhoneBook phB1, PhoneBook phB2){
    /* An iterator for the first phonebook */
    PhBIterator phB1It = phB1.phBIterator();
    /* 
     * Loop runs through all of phB1's contents. Each iteration the element from phB1 is compared to the 
     * elements in phB2 and if phB2 contains a repeat, that element is removed from phB2
     */
      while(phB1It.hasNext()){
        /* Temporary storing of each element from phB1 */
        Person temp1 = phB1It.next();
        /* An iterator for the second phonebook */
        PhBIterator phB2It = phB2.phBIterator();
        /*
         * Loop runs through all of phB2's contents. Each iteration the element from phB2 is compared to the 
         * element from phB1
         */
        while(phB2It.hasNext()){
          /* Temporary storing of each element from phB2 */
          Person temp2 = phB2It.next();
          /* If the element from phB1 and phB2 have the same Person ID, the element is removed from phB2 */
          if(temp1.getPersonID().equals(temp2.getPersonID())){
            //System.out.println(temp2.getPersonID());
            phB2It.iteratorRemove();
          }
        }
      }
  }
  
  /**
   * Processes the arguments the user inputs. Used to show that the Phone Book ADT and removeDuplicates work 
   * Has a worst case running time of O(n^3)
   * @param args The arguments inputted
   */
  public static void main(String[] args){
    /* A phone book using the array list implementation with an initial capacity of 10 */
    PhBArrayList al = new PhBArrayList(10);
    /* A phone book using the linked list implementation */
    PhBLinkedList ll = new PhBLinkedList();
    /* 
     * Various people to insert into the two phonebook implementations
     * nathanA and helioA are going to be included in al and nathanB and helioB are going to be included in ll
     * This way by printing the respective lists before and after removeDuplicates is called, it will be shown
     * That removeDuplicates works as nathanA and nathanB share the same person ID and one of them will be removed
     * From their phonebook and that helioA and helioB share the same person ID and one of them will be removed
     */
    Person joe = new Person("joe", "111-111-1111");
    Person scott = new Person("scott", "222-222-2222");
    Person bill = new Person("bill", "333-333-3333");
    Person marina = new Person("marina", "343-3434-343");
    Person joemama = new Person("joemama", "444-444-4444");
    Person guapo = new Person("guapo", "454-4545-454");
    Person percy = new Person("percy", "555-555-5555");
    Person me = new Person("me", "666-666-6666");
    /*
     * The following lines insert the people from above into al and ll
     * The people inserted into al are always inserted into a position after the last current person
     * Because this will ensure efficiency as the array list won't have to shift elements to accomodate the new person
     * The people inserted into ll are always inserted into the front of the linked list
     * Because this way the list won't have to be inefficiently iterated through to the spot of insertion
     */
    al.insert(0, joemama);
    al.insert(1, scott);
    al.insert(2, bill);
    al.insert(3, guapo);
    al.insert(4, percy);
    al.insert(5, me);
    ll.insert(0, joe);
    ll.insert(0, scott);
    ll.insert(0, bill);
    ll.insert(0, marina);
    ll.insert(0, percy);
    System.out.println("Before Removing Duplicates:");
    System.out.println("Array List:");
    al.printPhoneBook();
    System.out.println("Linked List:");
    ll.printPhoneBook();
    Demo.removeDuplicates(al, ll);
    System.out.println("After Removing Duplicates:");
    System.out.println("Array List:");
    al.printPhoneBook();
    System.out.println("Linked List:");
    ll.printPhoneBook();
  }
}
