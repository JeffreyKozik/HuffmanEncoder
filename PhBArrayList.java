/**
 * An implementation of the Phone Book ADT using an array
 * @author Jeffrey Kozik
 */
public class PhBArrayList implements PhoneBook{
  
  /* An array of Persons in the phonebook */
  private Person[] personArray;
  /* The initial length of the personArray */
  private int capacity;
  /* The amount of Persons in the phonebook */
  private int size;
  
  /**
   * Creates an instance of the Phone Book ADT using an array
   * Has a worst case running time of O(1)
   * @param capacity The initial length of the personArray
   */
  public PhBArrayList(int capacity){
    this.personArray = new Person[capacity];
    this.size = 0;
  }
  
  /** 
   * Returns the number of Persons in the phonebook
   * Has a worst case running time of O(1)
   * @return size The number of Persons in the phonebook
   */
  public int size(){
    return size;
  }
  
  /**
   * Has a worst case running time of O(1)
   * @param size The new number of Persons in the phoneBook
   */
  public void setSize(int size){
    this.size = size;
  } 
  
  /**
   * Has a worst case running time of O(1)
   * @return personArray.length The capacity of the personArray
   */
  public int getCapacity(){
    return personArray.length;
  }
  
  /**
   * Increases or decreases the length of the personArray by creating a new array of the desired size and
   * Transferring the persons in the same order to the new array. Then the old array is set equal to the new array
   * Has a worst case running time of ?(n) [Theta(n) - the theta symbol may not be clear or may turn into a ?]
   * @param change The amount to change the length of the array by
   */
  public void changeCapacity(int change){
    /* Temporarily creates a new array of the desired length */
    Person[] tempArray = new Person[getCapacity() + change];
    /* 
     * Iterates through the entire section of the old personArray that
     * Stores Persons. Each iteration the Persons are put onto the temporary array in the same order
     */
    for(int i = 0; i < size(); i++){
      tempArray[i] = personArray[i];
    }
    personArray = tempArray;
  }
  
  public void insert(int i, Person person){
    /*
     * If the size of the array has reached the capacity of the array list
     * The capacity of the array must be increased before a new person can be added
     */
    if(size() == getCapacity()){
        changeCapacity(size() + 1);
    }
    /*
     * If a person is being inserted at a location larger than or equal to the size of the array
     * That person must be inserted sequentially to the last person in the array 
     */
    if(i >= size()){
        personArray[size()] = person;
    }
    /*
     * If the person is not being inserted at the end of the array
     * The elements in the array must be shifted to accomodate
     */
    else{
      /*
       * Iterates from the position the last person in the array is currently stored at
       * And stops after the position the person is being inserted into has been iterated through.
       * Each iteration the persons are shifted down one in the array to make room for the new person
       */
      for(int j = size() - 1; j >= i; j--){
        personArray[i+1] = personArray[i];
      }
      personArray[i] = person;
    }
    setSize(size() + 1);
  }
  
  public Person remove(int i){
    /* If the user tries to remove a Person from a position that isn't storing a Person, null is returned */
    if(i > size() - 1){
      return null;
    }
    /* If the user would like to remove a Person from a valid position, that is done */
    else{
      /* Temporarily stores the person to be removed */
      Person temp = personArray[i];
      personArray[i] = null;
      /* 
       * Starting at the point at which the Person is to be removed, it iterates until the end of the segment of
       * personArray which contains Persons. Each iteration each Person is slided down to accomodate the removal
       */
      for(; i < size() - 1; i++){
        personArray[i] = personArray[i+1];
      }
      setSize(size() - 1);
      /*
       * If the number of Persons in the array after removing an element is less than or equal to a quarter of the
       * Total length of the array, the capacity of the array is changed to half of its current capacity rounded up.
       * This way if a user isn't much of the array, memory isn't wasted storing nothing
       */
      if(size() <= (getCapacity() * 0.25)){
        changeCapacity((int)java.lang.Math.ceil(size() * -0.5));
      }
      return temp;
    }
  }
  
  public Person lookup(int i){
    /* If the user wants to see what Person is stored at a location not storing a Person, an exception is thrown */
    if(i > size() - 1){
      throw new IndexOutOfBoundsException();
    }
    /* Otherwise, the Person at the given position is returned */
    else{
      return personArray[i];
    }  
  }
  
  public PhBIterator phBIterator(){
    return new PhBIterator(){
      
      /* The current position in the array that the pointer is at */
      int position = 0;
      
      /*
       * Determines if there is another element in the phone book to iterate through
       * Has a worst case running time of O(1)
       * @return True if there are more elements, false if not
       */
      public boolean hasNext(){
        return position < size();
      }
      
      /*
       * Determines the Person at the current position of the pointer and increments the pointer
       * Has a worst case running time of O(1)
       * @return The Person at the pointer position
       */
      public Person next(){
        position++;
        return personArray[position - 1];
      }
      
      /*
       * Determines the Person last called by the next method and removes them from the phone book
       * Has a worst case running time of O(n)
       * @return True if there is a Person to remove, false if the next method hasn't been called yet
       */
      public boolean iteratorRemove(){
        /*
         * If the next method has been called, the most recent Person returned by that method is removed and 
         * the pointer is decremented as the size of the arrayList has decreased meaning all elements slide over
         * If the pointer wasn't decremented, a Person would be skipped
         */
        if(position > 0){
          position--;
          remove(position);
          return true;
        }
        return false;
      }
    };
  }
}
        
      
    