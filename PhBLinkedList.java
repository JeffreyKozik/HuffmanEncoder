/**
 * An implementation of the Phone Book ADT using a singly linked list
 * @author Jeffrey Kozik
 */
public class PhBLinkedList implements PhoneBook{
  
  /* The first node */
  private PhBNode head;
  /* The number of Persons in the phone book */
  private int size;
  
  /**
   * Creates a new instance of the Phone Book ADT using a singly linked list
   * Has a worst case running time of O(1)
   */
  public PhBLinkedList(){
    this.head = null;
    this.size = 0;
  }
  
  /**
   * Has a worst case running time of O(1)
   * @return The first node 
   */
  public PhBNode getFront(){
    return head;
  }
  
  /**
   * Has a worst case running time of O(1)
   * @param head The first node
   */
  public void setFront(PhBNode head){
    this.head = head;
  }
  
  /**
   * Has a worst case running time of O(1)
   * @return The number of Persons in the phone book
   */
  public int size(){
    return size;
  }
  
   /**
   * Has a worst case running time of O(1)
   * @param The new number of Persons in the phone book
   */
  public void setSize(int size){
   this.size = size;
  }
  
  public void insert(int i, Person person){
    /**
     * If i is greater than the size of the list
     * i is set equal to the size of the list so it will be inserted at the end
     */
    if(i > size()){
      i = size();
    }
    /**
     * If i is equal to zero
     * A node representing the person is inserted at the front of the list
     */
    if(i == 0){
      setFront(new PhBNode(person, getFront()));
    }
    /* Otherwise the node will be inserted either in the middle or end of the phone book */
    else{
      /* A pointer representing the first node */
      PhBNode nodeptr = getFront();
      /* 
       * Iterates through the entire phone book starting at the second element stopping right before the 
       * Position at which the element is to be inserted. Each iteration the nodeptr becomes the next node
       */
      for(int j = 1; j < i; j++){
        nodeptr = nodeptr.getNext();
      }
      nodeptr.setNext(new PhBNode(person, nodeptr.getNext()));
    }
    setSize(size() + 1);
  }
  
  public Person remove(int i){
    /* If an element is trying to be removed that doesn't exist, null is returned */
    if(i > (size() - 1)){
      return null;
    }
    /* If the first node is being removed, that node is removed and the next node becomes the new first node */
    if(i == 0){
      /* Temporarily stores the Person at the front (to be removed) */
      Person temp = getFront().getData();
      setFront(getFront().getNext());
      return temp;
    }
    /* A pointer storing the first node */
    PhBNode nodeptr = getFront();
    /* Starting at the 2nd node and stopping before the removal position. Each iteration the ptr is incremented */
    for(int j = 1; j < i; j++){
      nodeptr = nodeptr.getNext();
    }
    /* Temporarily stores the the Person to be removed */
    Person temp = nodeptr.getNext().getData();
    nodeptr.setNext(nodeptr.getNext().getNext());
    setSize(size() - 1);
    return temp;
  }
  
  public Person lookup(int i){
    /* If a Person is looked up that doesn't exist an exception is thrown */
    if(i >= size()){
      throw new IndexOutOfBoundsException();
    }
    /* A ptr representing the front of the phone book */
    PhBNode nodeptr = getFront();
    /* Starting at the first node and stopping before the lookup position. Each iteration the ptr is incremented */
    for(int j = 0; j < i; j++){
      nodeptr = nodeptr.getNext();
    }
    return nodeptr.getData();
  } 
  
  public PhBIterator phBIterator(){
    return new PhBIterator(){
      /* Represents the first node */
      PhBNode nodeptr = getFront();
      /* Represents the position right before the nodeptr */
      int position = -1;
      
      /*
       * Determines whether or not there is another Person in the phone book
       * Has a worst case running time of O(1)
       * @return True if there is another Person, false if not
       */
      public boolean hasNext(){
        return nodeptr != null;
      }
      
      /*
       * Determines the next Person and increments the ptr and person
       * Has a worst case running time of O(1)
       * @return The next Person
       */
      public Person next(){
        position++;
        Person temp = nodeptr.getData();
        nodeptr = nodeptr.getNext();
        return temp;
      }
      
      /*
       * Determines the Person last called by the next method and removes them from the phone book
       * Has a worst case running time of O(n)
       * @return True if there is a Person to remove, false if the next method hasn't been called yet
       */
      public boolean iteratorRemove(){
        /* If the next method has been called then whatever was returned by that method is removed */
        if(position > -1){
          remove(position);
          position--;
          /* Temporarily stores the first node */
          PhBNode temp = getFront();
          /* Runs until reaching the position the element you just removed was at. Each iteration increments temp */
          for(int j = 0; j < position; j++){
            temp = temp.getNext();
          }
          nodeptr = temp;
          return true;
        }
        return false;
      }
    };
  }
  
  /** Represents the elements in the linked list implementation of phone book 
    * All methods contained within have a worst case running time of O(1)
    */
  private class PhBNode{
    
    /* The Person contained within the node */
    private Person data;
    /* The node after this node */
    private PhBNode next;
    
    /**
     * Creates a new instance of PhBNode
     * @param data The Person contained within the node
     * @param next The node after this node
     */
    public PhBNode(Person data, PhBNode next){
      this.data = data;
      this.next = next;
    }
    
    /**
     * @return The Person contained within the node
     */
    public Person getData(){
      return data;
    }
    
    /**
     * @param data The new Person contained within the node
     */
    public void setData(Person data){
      this.data = data;
    }
    
    /**
     * @return The node after this node
     */
    public PhBNode getNext(){
      return next;
    }
    
    /**
     * @param next The new next node after this node
     */
    public void setNext(PhBNode next){
      this.next = next;
    }
  }
}