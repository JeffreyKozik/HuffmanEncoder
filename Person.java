/**
 * Represents a person's info as stored in a phonebook
 * All methods in this class have a worst case running time of O(1).
 * @author Jeffrey Kozik
 */
public class Person{
  
  /* The name of the person */
  private String personID;
  /* The person's phone number */
  private String phoneNum;
  
  /**
   * Creates a new instance of a person
   * @param personID The name of the person
   * @param phoneNum The person's phone number
   */
  public Person(String personID, String phoneNum){
    this.personID = personID;
    this.phoneNum = phoneNum;
  }
  
  /** @return personID The name of the person */
  public String getPersonID(){
    return personID;
  }
  
  /** @param personID The name of the person */
  public void setPersonID(String personID){
    this.personID = personID;
  }
  
  /** @return phoneNum The person's phone number */
  public String getPhoneNum(){
    return phoneNum;
  }
  
  /** @param phoneNum The person's phone number */
  public void setPhoneNum(String phoneNum){
    this.phoneNum = phoneNum;
  }
}
  
  