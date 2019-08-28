# CS 61B  Homework 5 Due noon Wednesday, March 5, 2014

> This homework will teach you a more secure way to encapsulate lists than the
> method used in Homework 4, and give you practice using it to accomplish tasks
> quickly.  This is an individual assignment; you may not share code with other
> students.

> The list package contains encapsulated DList and SList classes (both of which
> inherit from an abstract List class).  These classes differ from those we have
> seen before in a critical way:  each ListNode knows which List it is in.  A new
> invariant in our Lists is that for every ListNode x in a List l, x.myList = l,
> UNLESS x is the sentinel.  For any sentinel node x, x.myList = null . Because
> every ListNode knows its List, we can move some of the methods from the List
> class to the ListNode class.

> Methods of List       								| Methods of ListNode
>
> ​								 			  					|
>
> public boolean isEmpty() 					  |public Object item()
>
> public int length() 				 				  |public void setItem(Obect item)
>
> public void insertFront(Object item)	|public ListNode next()
>
> public void insertBack(Object item)	 |public ListNode prev()
>
> public ListNode front()    					   |public void insertAfeter(Object item)
>
> public ListNode back()     					  |public  void insertBefore(Object item)
>
> ​																   |public void remove()
>
> ​																   |public boolean isValidNode()

> One innovation of these classes is the existence of "invalid nodes," which can
> be identified by the isValidNode() method.  In Homework 4, the methods next()
> and prev() return null when there is no node to return.  Here in Homework 5,
> they return an invalid node instead.  A node that has been removed from a List
> is also invalid.  With the exception of isValidNode(), any method called on an
> invalid node will throw an InvalidNodeException.

> The item field of ListNode is no longer public, to prevent applications from
> storing items in invalid nodes.

​	

> Recall that every ListNode knows what List it is in.  An invalid node is
> represented by any ListNode whose "myList" field is null.  In the DList
> implementation, the sentinel is an invalid node, which simplifies the
> implementations of front(), back(), next(), and prev().  (Take a look at
> the code for DListNode.isValidNode.)

Part I  (2 points)
------------------
> Complete the implementation of the DList and DListNode classes.

> In DList.java, you will need to implement insertFront(), insertBack(), and the
> DList() constructor.  You should be able to cut and paste your solutions from
> Homework 4 with just a small change.  The implementations of front() and back()
> are already provided; observe that they are simpler than in Homework 4 because
> we use sentinels as invalid nodes.

> In DListNode.java, you will need to implement insertAfter(), insertBefore(),
> and remove().  Your Homework 4 solutions will be a good start, but you’ll need
> to make changes to accommodate these methods’ move from DList to DListNode.
> The main() method of list.DList contains code to help test your work.

注意点：

1.构建DList()的时候head=newNode(item,null,null,null);  newNode的第二个参数应该是null !!即哨兵结点的myList 属性=null！

2.DListNode类中insertAfter()、insertBefore()、以及remove()方法都要注意调整表的 size 属性

```java
/**

- DList() constructs for an empty DList.
  **/
    public DList() {

// Your solution here.  Similar to Homework 4, but now you need to specify
//   the `list' field (second parameter) as well.
Object item=new Object();
head=newNode(item,null,null,null); //第一个坑 newNode的第二个参数应该是null
head.next=head;
head.prev=head;

  }
```

```java
/**
   *  insertFront() inserts an item at the front of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertFront(Object item) {
    // Your solution here.  Similar to Homework 4, but now you need to specify
    //   the `list' field (second parameter) as well.
    DListNode newhead=newNode(item,this,head,head.next);
    head.next.prev=newhead;
    head.next=newhead;
    size++;
  }
```

```java
/**
   *  insertBack() inserts an item at the back of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertBack(Object item) {
    // Your solution here.  Similar to Homework 4, but now you need to specify
    //   the `list' field (second parameter) as well.
    DListNode newtail=newNode(item,this,head.prev,head);
    head.prev.next=newtail;
    head.prev=newtail;
    size++;
  }
```

```java
/**
   *  remove() removes this node from its DList.  If this node is invalid,
   *  throws an exception.
   *
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void remove() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("remove() called on invalid node");
    }
    // Your solution here.  Will look something like your Homework 4 solution,
    //   but changes are necessary.  For instance, there is no need to check if
    //   "this" is null.  Remember that this node's "myList" field tells you
    //   what DList it's in.
    next.prev=prev;
    prev.next=next;
    myList.size--;
    // Make this node an invalid node, so it cannot be used to corrupt myList.
    myList = null;
    // Set other references to null to improve garbage collection.
    next = null;
    prev = null;
  }
```

Part II  (8 points)
-------------------
> Your main assignment is to implement a Set ADT in Set.java.  Your Set class
> must use a List to store the elements of the set.  Your Sets should behave like
> mathematical sets, which means they should not contain duplicate items.  To
> make set union and intersection operations run quickly, your Sets will contain
> only Comparable elements, and you will keep them sorted in order from least to
> greatest element.  (You will want to review the Comparable interface on the
> Java API Web page.)

> You will need to declare some fields and implement the following methods.
> public Set() 											  // Constructs an empty Set.
> public int cardinality()							// Number of elements in this Set.
> public void insert(Comparable c)		// Insert c into this Set.
> public void union(Set s)					    // Assign this = (this union s).
> public void intersect(Set s)			  	// Assign this = (this intersect s).
> public String toString()					 	// Express this Set as a String.

> Two items o1 and o2 are considered duplicates if o1.compareTo(o2) == 0.  By
> convention, Java classes are supposed to have o1.compareTo(o2) == 0 if and only
> if o1.equals(o2).  (Of course, it’s always possible for some idiot to break
> this convention, so it would be safest not to depend on equals() working.)

> Unlike most previous assignments, each method comes with prescribed time bounds
> that you must meet when your Set uses DLists (but not when it uses SLists).
> For example, union() and intersect() must run in time proportional to
> this.cardinality() + s.cardinality().  This means you do NOT have time to make
> a pass through "this" list for every element of s; that would take time
> proportional to this.cardinality() * s.cardinality().  To achieve this time
> bound, you must take advantage of the fact that Sets are sorted.  This time
> bound is one reason why Sets may not store duplicate items in their Lists.

> On the other hand, insert() need not run in constant time.  Since each Set uses
> a sorted representation, insert() may need time proportional to the cardinality
> of the Set to find the right place to insert a new element, and to ensure that
> the new element doesn’t duplicate an old one.

> Another constraint is that union() and intersect() may NOT change the Set s.
> Furthermore, intersect() may not construct any new ListNodes (it only needs to
> remove ListNodes from "this" List), and union() should reuse all the ListNodes
> in the Set "this", constructing new nodes only for elements of s that "this"
> List lacks.  We will deduct points for failing to meet the time bounds or
> failing to obey these constraints.

> Be sure to declare variables of static type List and ListNode in Set.java, not
> variables of type DList, DListNode, SList, or SListNode.  Set.java should be
> able to switch between using DLists and using SLists by changing one
> constructor call in the Set() constructor.  (In fact, you can use SList to help
> you debug Set if you have trouble getting DList working.  But be sure to use a
> DList in your final submission unless you can’t get it working.)

> Do not modify List.java, ListNode.java, SList.java, or SListNode.java.  Do not
> modify the prototypes in Set.Java, DList.java, or DListNode.java.

这节要求我们用List去实现一个Set，Set主要的性质就是不包含同样的元素

下面分别是 1.Set类属性中只需要一个DList 

2.Set构造方法  	3.cardinality()方法

```java
public class Set {
  /* Fill in the data fields here. */

  private DList list;
```

```java
/**
   * Set ADT invariants:
   *  1)  The Set's elements must be precisely the elements of the List.
   *  2)  The List must always contain Comparable elements, and those elements 
   *      must always be sorted in ascending order.
   *  3)  No two elements in the List may be equal according to compareTo().
   **/

  /**
   *  Constructs an empty Set. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public Set() { 
    // Your solution here.
    list=new DList();

  }
```

```java
 /**
   *  cardinality() returns the number of elements in this Set.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int cardinality() {
    // Replace the following line with your solution.
    return list.length();
  }
```

接下来的insert()、union()、intersect()相对就比较复杂了

```java
 /**
   *  insert() inserts a Comparable element into this Set.
   *
   *  Sets are maintained in sorted order.  The ordering is specified by the
   *  compareTo() method of the java.lang.Comparable interface.
   *
   *  Performance:  runs in O(this.cardinality()) time.
   **/
  public void insert(Comparable c) throws InvalidNodeException{
   //Your solution here
    if(cardinaality()==0){
      list.insertFront(c);
    }else{
      ListNode node=list.front();
  			try{
          while(c.compareTo(node.item())!=0){
            if(c.compareTo(node.item())>0){
              node=node.next();
            }else{
              node.insertBefore(c);
              break;
            }
            if(!node.isValidNode()){ //此时的node结点变为了哨兵结点
              list.back().insertAfter(c);
              break
            }
            
          }
        }catch(InvalidNodeException e){
          e.printStackTrace();
        }
    }
    
    
 }
```

```java
/**
   *  union() modifies this Set so that it contains all the elements it
   *  started with, plus all the elements of s.  The Set s is NOT modified.
   *  Make sure that duplicate elements are not created.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Your implementation should NOT copy elements of s or "this", though it
   *  will copy _references_ to the elements of s.  Your implementation will
   *  create new _nodes_ for the elements of s that are added to "this", but
   *  you should reuse the nodes that are already part of "this".
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
   **/
  public void union(Set s) throws InvalidNodeException{
  	if(s.cardinality()==0){
      return;
    }else if(cardinality()==0){
      ListNode cur=s.list.front();
      while(cur.isValidNode()){
        list.insertBack(cur.item());
        cur=cur.next();
      }
      return;
    }
    
    ListNode cur1=list.front();
    ListNode cur2=s.list.front();
    while(cur1.isValidNode()&&cur2.isValidNode()){
      if(((Comparable)cur2.item()).compareTo(cur1.item())<0){
        cur1.insertBefore(cur2.item());
        cur2=cur2.next();
      }else if(((Comparable)cur2.item()).compareTo(cur1.item())==0){
        cur1=cur1.next();
        cur2=cur2.next();
      }else if(((Comparable)cur2.item()).compareTo(cur1.item()) > 0){
        cur1=cur1.next();
      }
   
    }
    	if(cur2.isValidNode()){
        cur1=list.back();
        while(cur2.isValidNode()){
          cur1.insertAfter(cur2.item());
          cur2=cur2.next();
        }
      }
    
  }
```

```java
 /**
   *  intersect() modifies this Set so that it contains the intersection of
   *  its own elements and the elements of s.  The Set s is NOT modified.
   *
   *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
   *
   *  Do not construct any new ListNodes during the execution of intersect.
   *  Reuse the nodes of "this" that will be in the intersection.
   *
   *  DO NOT MODIFY THE SET s.
   *  DO NOT CONSTRUCT ANY NEW NODES.
   *  DO NOT ATTEMPT TO COPY ELEMENTS.
   **/
  public void intersect(Set s) throws InvalidNodeException{
  	if(s.cardinality()==0){
      list=new DList();
      return;
    }else if(cardinality()==0){
      return;
    }
    
    ListNode cur1=list.front();
    ListNode cur2=s.list.front();
    while(cur1.isValidNode()&&cur2.isValidNode()){
      if(((Comparable)cur1.item()).compareTo(cur2.item())<0){
        ListNode next=cur1.next();
        cur1.remove();
        cur1=next;
      }else if(((Comparable)cur1.item()).compareTo(cur2.item())==0){
        cur1=cur1.next();
        cur2=cur2.next();

      }else{
        cur2=cur2.next();
      }
    }
    	if(cur1.isValidNode()){
        while(cur1.isValidNode()){
          ListNode next=cur1.next();
          cur1.remove();
          cur1=next;
        }
      }
  }
```


