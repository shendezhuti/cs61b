# CS 61B Homework 4 Due noon Wednesday, February 26

> goal: This homework will give you practice with writing doubly-linked lists and using
> subclasses.  This is an individual assignment; you may not share code with
> other students.
>
> When you did Project 1, you probably noticed that the SList ADT doesn’t allow
> you to walk through an SList and process each node as you go.  Either you must
> violate the ADT by manipulating the SListNode pointers directly from your
> RunLengthEncoding class, or you must use the slow nth() method to access each
> successive element, thereby obtaining a toPixImage() method that runs in time
> proportional to N^2, where N is the size of the list.  Because we didn’t know
> about Java packages, we were unable to develop a really satisfying list ADT

> In this homework, you will implement a doubly-linked list ADT that allows an
> application to holdlist nodes and hop from node to node quickly.  How do we
> make the list an ADT if applications can get access to list nodes?  It’s easy:
> we put all the list code in a package called "list", and we declare the fields
> of DListNode protected--except the "item" field, which is public.  Applications
> can’t access the "prev" or "next" fields of a DListNode, so they can’t violate
> any DList invariants.

> I’ve chosen to make the "item" field public because it doesn’t take part in any
> invariants, so it does no harm to make it public.  Applications may read and
> change "item" as they please.  In fact, no method is provided for reading the
> "item" field indirectly.

Part I  (6 points)
------------------

> list/DList.java contains a skeleton of a doubly-linked list class.  Fill in the
> method implementations.

> Your DList should be circularly-linked, and its head should be a sentinel node
> (which holds no item) as described in Lecture 8.  An empty DList is signified
> by a sentinel node that points to itself.  Some DList methods return
> DListNodes; they should NEVER return the sentinel under any circumstances.
> Your DList should satisfy the following invariants.

> 1)  For any DList d, d.head != null.
> 2)  For any DListNode x in a DList, x.next != null.
> 3)  For any DListNode x in a DList, x.prev != null.
> 4)  For any DListNode x in a DList, if x.next == y,then y.prev=x
> 5)  For any DListNode x in a DList, if x.prev == y,then y.next=x
> 6)  For any DList d, the field d.size is the number of DListNodes,
> NOT COUNTING the sentinel, that can be accessed from the sentinel
> (d.head) by a sequence of "next" references.

> The DList class includes a newNode() method whose sole purpose is to call the
> DListNode constructor.  All of your methods that insert a new node should call
> this method; they should not call the DListNode constructor directly.  This
> will help minimize the number of methods you need to override in Part III.

> The DList class includes a newNode() method whose sole purpose is to call the
> DListNode constructor.  All of your methods that insert a new node should call
> this method; they should not call the DListNode constructor directly.  This
> will help minimize the number of methods you need to override in Part III.
> Do not change any of the method prototypes; as usual, our test code expects you
> to adhere to the interface we provide.  Do not change the fields of DList or
> DListNode.  You may add private/package helper methods as you please.
> You are welcome to create a main() method with test code.  It will not be
> graded.  We’ll be testing your DList class, so you should too.

下面的代码我也写了不少时间，写的时候有很多bug产生，需要花时间仔细调试

```java
public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  /**
   *  newNode() calls the DListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
    Object item=new Object();
    head=newNode(item,null,null);
    head.next=head;
    head.prev=head;
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.
    DListNode newhead=newNode(item,head,head.next);
    head.next.prev=newhead;
    head.next=newhead;
    size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    DListNode newtail=newNode(item,head.prev,head);
    head.prev.next=newtail;
    head.prev=newtail;
    size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
    return head.next;
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
    return head.prev;
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
    if(node==null||node==back())return null;
    
    return node.next;
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
    if(node==null||node==front())return null;
    return node.prev;
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
    if(node==null)return ;
    DListNode oldnodeAfter=node.next;
    node.next=newNode(item,node,oldnodeAfter);
    oldnodeAfter.prev=node.next;
    size++;
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
    if(node==null)return ;
        DListNode oldnodeBefore=node.prev;
        node.prev=newNode(item,oldnodeBefore,node);
        oldnodeBefore.next=node.prev;
        size++;
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
    if(node==null)return;
    // DListNode prev=prev(node);
    // DListNode next=next(node); // 这里我犯了一个错误用了prev和next 上面说了prev和next当时头尾结点的时候返回null
    DListNode prev=node.prev;
    DListNode next=node.next;
    next.prev=prev;
    prev.next=next;
    node.prev=node.next=null;
    size--;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}

```

```java
/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public Object item;
  protected DListNode prev;
  protected DListNode next;

  /**
   *  DListNode() constructor.
   *  @param i the item to store in the node.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  DListNode(Object i, DListNode p, DListNode n) {
    item = i;
    prev = p;
    next = n;
  }
}
```

Part III  (3 points)
--------------------
> Implement a "lockable" doubly-linked list ADT:  a list in which any node can be
> "locked."  A locked node can never be removed from its list.  Any attempt to
> remove a locked node has no effect (not even an error message).  Your locked
> list classes should be in the list package alongside DList and DListNode.

> First, define a LockDListNode class that extends DListNode and carries
> information about whether it has been locked.  LockDListNodes are not locked
> when they are first created.  Your LockDListNode constructor(s) should call a
> DListNode constructor to avoid code duplication.

思路：在LockDListNode中定义布尔类型的islocked属性

```java
public class LockDListNode extends DListNode{
	public boolean islocked;
	
	LockDListNode (Object i, DListNode p, DListNode n){
		super(i,p,n);
		islocked=false;
	}
}
```

> Second, define a LockDList class that extends DList and includes an additional
> method
>
> public void lockNode(DListNode node) { ... }
>
> that permanently locks "node".

> DO NOT CHANGE THE SIGNATURE OF lockNode().  The parameter really is supposed to
> be of static type DListNode, not LockDListNode.  I chose this signature for the
> convenience of the users of your LockDList.  It saves them the nuisance of
> having to cast every node they want to lock.  Instead, it’s your job to take
> care of that cast (from DListNode to LockDListNode).

```java
public class LockDList extends DList{
	 public LockDList(){
    super();
   }
		protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
			return new LockDListNode(item,prev,next);
    }

  
  	public void remove(DListNode node) {
			if(((LockDListNode)node).isLocked) return;
      super.remove(node);   
    }
  
  	public void lockNode(DListNode node) {
			((LockDListNode)node).islocked=true;
    }
」
```

注意点：1.在LockDList中生成新LockDListNode的时候利用LockDListNode的构造方法

2.注意需要调用isLocked属性时需要将传入的DListNode强制向下转型
