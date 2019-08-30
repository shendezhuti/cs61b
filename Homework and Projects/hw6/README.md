> # CS 61B  Homework 6 Due noon Wednesday, March 19, 2014

> This homework will teach you about hash tables, hash codes, and compression
> functions.  This is an individual assignment; you may not share code with other
> students.

Part I  (6 points)
------------------
> Implement a class called HashTableChained, a hash table with chaining.
> HashTableChained implements an interface called Dictionary, which defines the
> set of methods that a dictionary needs.  Both files appear in the "dict"
> package.

> The methods you will implement:  insert() an entry (key + value) into a hash
> table, find() an entry with a specified key, remove() an entry with a specified
> key, return the size() of the hash table (in entries), and say whether the hash
> table isEmpty().  There is also a makeEmpty() method, which removes every entry
> from a hash table, and two HashTableChained constructors.  One constructor lets
> applications specify an estimate of the number of entries that will be stored
> in the hash table; the other uses a default size.  Both constructors should
> create a hash table that uses a prime number of buckets.  (Several methods for
> identifying prime numbers were discussed early in the semester.)  In the first
> constructor, shoot for a load factor between 0.5 and 1.  In the second
> constructor, shoot for around 100 buckets.  Descriptions of all the methods may
> be found in Dictionary.java and HashTableChained.java.

> Do not change Dictionary.java.  Do not change any prototypes in
> HashTableChained.java, or throw any checked exceptions.  Most of your solution
> should appear in HashTableChained.java, but other classes are permitted.  You
> will probably want to use a linked list code of your choice.  Note that even
> though the hash table is in the "dict" package, it can still use linked list
> code in a separate "list" package.  There’s no need to move the list code or
> the "list" package into the "dict" package, nor is it a good idea.
> Look up the hashCode method in the java.lang.Object API.  Assume that the
> objects used as keys to your hash table have a hashCode() method that returns a
> "good" hash code between Integer.MIN_VALUE and Integer.MAX_VALUE (that is,
> between -2147483648 and 2147483647).  Your hash table should use a compression
> function, as described in lecture, to map each key’s hash code to a bucket of
> the table.  Your compression function should be computed by the compFunction()
> helper method in HashTableChained.java (which has "package" protection so we
> can test it independently; DO NOT CHANGE ITS PROTECTION).  Your insert(),
> find(), and remove() should all use this compFunction() method.
> The methods find() and remove() should return (and in the latter case,
> remove) an entry whose key is equals() to the parameter "key".  Reference
> equality (==) is NOT required for a match.

本次作业让我们实现一个hashtable，建议上课好好听讲。废话不多说 直接看代码

hash table 属性定义如下

```java

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
public class HashTableChained implements Dictionary{
  /**
  *  Place any data fields here
  **/
  private List[]buckets;
  private int numberofBuckets;
  private int numberofEntry;
  private int collisions;
  
  
}
```

构造函数

```java
/** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/
   public HashTableChained(int sizeEstimate){
		numberofBuckets=returnClosesizeEstimateNumber(sizeEstimate);
     buckets=new SList[numberofBuckets];
     for(int i=0;i<numberofBuckets;i++){
				buckets[i]=new SList();
       
     }
     collisions=0;
     numberofEntrys=0;
   }

  public HashTableChained() {
    // Your solution here.
    numberOfBuckets=101;
     buckets=new SList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new SList();
        }
         collisions=0;
      numberOfEntrys=0;
  }
```

下面是returnClosesizeEstimate()方法 输出一个质数，该质数除以输入的数字最接近0.75

```java
 /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/
	
protected static int returnClosesizeEstimate(int sizeEstimate){
  int n=2*sizeEstimate;
  int primeNumber=0;
  double smallest=1.0;
  boolean [] prime=new boolean[n+1];
  	for(int i=2;i<=n;i++){
		prime[i]=true;//prime until proven composite
    }
		for(int divisor=2;divisor*divisor<=n;divisor++){
			if(prime[divisor]){
				for(int i=2*divisor;i<=n;i=i+divisor){
          prime[i]=false;
        }
      }
    }
  
  	for(int i=sizeEstimate;i++;i++){
      if(prime[i]){
        double result=Math.abs(((double)sizeEstimate/i)-0.75);
        if(result<smallest){
          primeNumber=i;
          smallest=result;
        }
      }
    }
  return primeNumber;
}
```



Compression functions
---------------------
> Besides the lecture notes, compression functions are also covered in Section
> 9.2.4 of Goodrich and Tamassia.  If you have an old edition (prior to the
> fifth), they make the erroneous claim that for a hash code i and an N-bucket
> hash table,
>
> h(i) = |ai + b| mod N
>
> is "a more sophisticated compression function" than
>
> Actually, the "more sophisticated" function causes _exactly_ the same
> collisions as the less sophisticated compression function; it just shuffles the
> buckets to different indices.  The better compression function (which they get
> right in the fifth edition) is
>
> h(i) = ((ai + b) mod p) mod N,
>
> where p is a large prime that’s substantially bigger than N.  (You can replace
> the parentheses with absolute values if you like; it doesn’t matter much.)
>
> For this homework, the simplest compression function might suffice.  The bottom
> line is whether you have too many collisions or not in Part II.  If so, you’ll
> need to improve your hash code or compression function or both.

根据讲义我们可以写compressFunction()

```java

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *  				
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  public int compFunction(int code) {
  	code=(23333*code+23333333)%16908799;
    if(code<0){
      code=(code+16908799)%16908799;
    }
    return code % numberOfBuckets;
  }
```



```java
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
```

```java
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
    SList hashNode=(SList)buckets[compFunction(key.hashCode())];
    if(!hashNode.isEmpty()){
      collisions++;
    }
    hashNode.insertFront(item);
    numberOfEntrys++;
    return item;
  }
```

```JAVA
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
 public Entry find(Object key){
		SList hashNode=(SList)buckets[compFunction(key.hashCode())];
   ListNode currentNode=hashNode.front();
 		while(currentNode.isValidNode()){
      try{
        if(((Entry)currentNode.item()).key().equals(key))
      }catch(InvalidNodeException e){
				e.printStackTrace();
      }
    }
 }
```

```java
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
   	SList hashNode=(SList)buckets[compFunction(key.hashCode())];
    ListNode currentNode=hashNode.front();
    while(currentNode.isValid()){
      try{
        if((Entry)currentNode.item().key().equals(key)){
          Entry entry=(Entry)currentNode.item();
          currentNode.remove();
          numberOfEntry--;
          return entry;
        }
      }catch (InvalidNodeException e){
        e.printStackTrace();
      }
    }
    
    return null;
  }
```

```java
/**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new SList();
        }
    collisions = 0;
    numberOfEntrys = 0;
  }
```



Part II  (4 points)
-------------------

> It is often useful to hash data structures other than strings or integers.  For
> example, game tree search can sometimes be sped by saving game boards and their
> evaluation functions, so that if the same game board can be reached by several
> different sequences of moves, it will only have to be evaluated once.  For this
> application each game board is a key, and the value returned by the minimax
> algorithm is the value stored alongside the key in the hash table.  If our
> search encounters the same game board again, we can look up its value in the
> dictionary, so we won’t have to run minimax on it twice.
>
> The class SimpleBoard represents an 8x8 checkerboard.  Each position has one of
> three values:  0, 1, or 2.  Your job is to fill in two missing methods:
> equals() and hashCode().  The equals() operation should be true whenever the
> boards have the same pieces in the same locations.  The hashCode() function
> should satisfy the specifications described in the java.lang.Object API.  In
> particular, if two SimpleBoards are equals(), they have the same hash code.
>
> You will be graded on how "good" your hash code and compression function are.
> By "good" we mean that, regardless of the table size, the hash code and
> compression function evenly distribute SimpleBoards throughout the hash table.
> Your solution will be graded in part on how well it distributes a set of
> randomly constructed boards.  Hence, the sum of all the cells is not a good
> hash code, because it does not change if cells are swapped.  The product of all
> cells is even worse, because it’s usually zero.  What’s better?  One idea is to
> think of each cell as a digit of a base-3 number (with 64 digits), and convert
> that base-3 number to a single int.  (Be careful not to use floating-point
> numbers for this purpose, because they round off the least significant digits,
> which is the opposite of what you want.  Better to round off the most
> significant digits, which is what happens when an int gets too big.)
>
> Do not change any prototypes in SimpleBoard.java, or throw any checked
> exceptions.  The file Homework6Test.java is provided to help you test your
> HashTableChained and your SimpleBoard together.  Note that Homework6Test.java
> does NOT test all the methods of HashTableChained; you should write additional
> tests of your own.  Moreover, you will need to write a test to see if your
> hash code is doing a good job of distributing SimpleBoards evenly through the
> table.  Our autograder will do extensive tests on that.

判断相等的代码逻辑非常简单，如下

```java
 /**
   *  Returns true if "this" SimpleBoard and "board" have identical values in
   *    every cell.
   *  @param board is the second SimpleBoard.
   *  @return true if the boards are equal, false otherwise.
   */

  public boolean equals(Object board) {
    // Replace the following line with your solution.  Be sure to return false
    //   (rather than throwing a ClassCastException) if "board" is not
    //   a SimpleBoard.
    if(board instanceof SimpleBoard){
      for(int i=0;i<DIMENSION;i++){
        for(int j=0;j<DIMENSION;j++){
          if(((SimpleBoard)board).grid[i][j]!=grid[i][j]){
            return false;
          }
        }
      }
      return true;
    }else{
    return false;
  }
  }
```

写这个hashCode()我感觉有点晕了，没思路，好吧是我自己这次写的时候没看上面的提示（One idea is to
think of each cell as a digit of a base-3 number (with 64 digits), and convert
that base-3 number to a single int.)

```java
 /**
   *  Returns a hash code for this SimpleBoard.
   *  @return a number between Integer.MIN_VALUE and Integer.MAX_VALUE.
   */

  public int hashCode() {
    // Replace the following line with your solution.
    int hash = 0;
    for (int i = 0; i < DIMENSION; ++i) {
      for (int j = 0; j < DIMENSION; ++j) {
        int index = (i * DIMENSION) + j;
        hash += grid[i][j] *(long) Math.pow(3, index);
      }
    }
    return hash;
  }
```

A tutorial on collision probability
-----------------------------------
> Students are always surprised when they find out how many collisions occur in
> a working hash table.  You might have the misimpression that there won’t be
> many collisions at all until the table is nearly full.  Let’s analyze how many
> collisions you should expect to see if your hash code and compression function
> are good.  Here, we define a "collision" to be the event where a newly inserted
> key has to share its bucket with one or more previously inserted keys.  (We
> count that as only one collision, regardless of how many keys are already in
> the bucket.)
>
> If you have N buckets and a good (pseudorandom) hash function, the probability
> of any two keys colliding is 1/N.  So when you have i keys in the table and
> insert key i + 1, the probability that the new key does NOT collide with any
> old key is (1 - 1/N)^i.  If you insert n distinct items, the expected number
> that WON’T collide with any previous item is
>
> n-1
> sum (1 - 1/N)^i = N - N (1 - 1/N)^n,
> i=0
>
> so the expected number of collisions is
>
>   n - N + N (1 - 1/N)^n.
>
> Now, for any n and N you test, you can just plug them into this formula and see
> if the number of collisions you’re getting is in the ballpark of what you
> should expect to get.  For example, if you have N = 100 buckets and n = 100
> keys, expect about 36.6 collisions.
