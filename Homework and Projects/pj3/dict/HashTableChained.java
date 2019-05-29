/* HashTableChained.java */

package dict;

import list.*;
import java.lang.Math;
import java.util.Random;


/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  private DList[]buckets;
  private int numberOfBuckets;
  private int numberOfEntrys;
  public  int collisions;
  private static final float MAX_LOADFACTOR = 1;
  private static final float MIN_LOADFACTOR = 0.25f;
  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    numberOfBuckets=returnClosesizeEstimateNumber(sizeEstimate);
     makeEmpty();
  }
  
  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/
  protected static int returnClosesizeEstimateNumber(int sizeEstimate){
    int n=2*sizeEstimate;
    int primeNumber=0;
    double smallest=1.0;
    boolean []prime= new boolean[n+1];
      for(int i=2;i<=n;i++){
       prime[i]=true; //prime until proven composite
      }
  
      for(int divisor=2;divisor*divisor<=n;divisor++){
       if(prime[divisor]){
        for(int i=2*divisor;i<=n;i=i+divisor){
      prime[i]=false;
      }
    }
    }

      for(int i=sizeEstimate;i<n;i++){
        if(prime[i]){
          double result=Math.abs(((double) sizeEstimate/i)-0.75);
          if(result<smallest){
            primeNumber=i;
            smallest=result;
          }
        }
      }
      return primeNumber;
  }

  public HashTableChained() {
    // Your solution here.
    numberOfBuckets=101;
     makeEmpty();
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  public int compFunction(int code) {
    // Replace the following line with your solution.

      code = (233333 * code + 23333333) % 16908799;
    if (code < 0) {
      code = (code + 16908799)  % 16908799;
    }
    return code % numberOfBuckets;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return numberOfBuckets;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return numberOfEntrys==0;
  }

  private float getLoadFactor(){
    return (float) numberOfEntrys/numberOfBuckets;
  }

  private void resize(float factor){
    int oldSize=numberOfBuckets;
    DList[]oldTable=buckets;
    numberOfBuckets=(int) (numberOfBuckets*factor);
    makeEmpty();
    for(int i=0;i<oldSize;i++){
      DList currList=oldTable[i];
      ListNode currNode=currList.front();
      while(currNode.isValidNode()){
        try{
        this.insert(((Entry) currNode.item()).key(),((Entry) currNode.item()).value());
          currNode=currNode.next();
        }catch(InvalidNodeException e){
          e.printStackTrace();
        }
      }
    }
  }
  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    Entry item=new Entry();
    item.key=key;
    item.value=value;
    DList hashNode=(DList)buckets[compFunction(key.hashCode())];
    if(!hashNode.isEmpty()){
      collisions++;
    }
    hashNode.insertBack(item);
    numberOfEntrys++;
    if(this.getLoadFactor()>=MAX_LOADFACTOR){
      resize(2);
    }
    return item;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    DList hashNode=(DList)buckets[compFunction(key.hashCode())];
    ListNode currentNode=hashNode.front();
    while(currentNode.isValidNode()){
        try{
            if(((Entry)currentNode.item()).key().equals(key)){
              return (Entry)currentNode.item();
            }else{
              currentNode=currentNode.next();
            }
        }catch(InvalidNodeException e){
          e.printStackTrace();
        }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    DList hashNode=(DList)buckets[compFunction(key.hashCode())];
    ListNode currentNode=hashNode.front();
    while(currentNode.isValidNode()){
      try{
          if(((Entry)currentNode.item()).key().equals(key)){
            Entry entry=(Entry)currentNode.item();
            currentNode.remove();
            numberOfEntrys--;
            if(this.getLoadFactor()<=MIN_LOADFACTOR){
              resize(0.5f);
            }
            return entry;
          }else{
            currentNode=currentNode.next();
          }
      }catch(InvalidNodeException e){
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
   buckets=new DList[numberOfBuckets];
    for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new DList();
        }
    collisions = 0;
    numberOfEntrys = 0;
  }

 public String[] String() {
    String[] res = new String[numberOfBuckets / 10 + 1];
    String s = "";
    int index = 0;
    for (int i = 0; i < numberOfBuckets; ++i) {
      if (i % 10 == 0) {
        res[index] = s;
        index++;
        s = "";
      }
      s += "[" + Integer.toString(buckets[i].length())+ "]";
    }
    return res;
  }

  public static double calExpCollision(int n,int N){
    return n-N+N*Math.pow(1-1/(double)N,n);
  }

  public static void main (String []args){
    HashTableChained table=new HashTableChained(100);
    // Random rand=new Random(1);
    // for(int i=20;i>0;i--){
    //   hash.insert(Math.abs(rand.nextInt()),i);
    // }
    // System.out.println(hash);
    // System.out.println(hash.collisions);
    // System.out.printf("%.2f", calExpCollision(20, 100));

     System.out.println("=====================size, isEmpty=========================");
        System.out.println("table's size is: " + table.size());
        System.out.println("table is Empty: " + table.isEmpty());
        
        System.out.println("=====================insert================================");
        table.insert("1", "The first one");
        table.insert("2", "The second one");
        table.insert("3", "The third one");
        table.insert("what", "nani?");
        table.insert("the","Eh-heng");
        table.insert("hell!","impolite");
        System.out.println("table's size is: " + table.size());
        System.out.println("table is Empty: " + table.isEmpty());
      
          String [] output = table.String();
          for(String s : output){
               if(s != null)    System.out.println(s);
          }
       
        
        System.out.println("====================find, remove===========================");
        Entry e1 = table.find("6");
        if(e1 != null)
                System.out.println("The item found is: [ " + e1.toString() + " ]");
        else
                System.out.println("The is no such item in the table to be found.");
        
        Entry e2 = table.remove("hell!");
        if(e2 != null)
                System.out.println("The item deleted is: [ " + e2.toString() + " ]");
        else
                System.out.println("The is no such item in the table to be deleted.");
        
      
                 output = table.String();
                for(String s : output){
                        if(s != null)        System.out.println(s);
                      }
        
        
        System.out.println("=====================makeEmpty=============================");
        table.makeEmpty();
       
                 output = table.String();
                for(String s : output){
                        if(s != null)        System.out.println(s);
                 }
           
  }
}
