#  CS 61B Lab 3 February 6-7, 2014

目的：练习使用linked list

#### Part I :  Using SLists (1 point)

> In the main() method, construct a list that looks like:
>     [ 6 9 12 ]
> and print the resulting list.
> Add more lines to change this list to:
>     [ 3 6 9 12 15 ]
> and print the resulting list.

```java
 		  /**
   *  main() runs test cases on the SList class.  Prints summary
   *  information on basic operations and halts with an error (and a stack
   *  trace) if any of the tests fail.
   **/

  public static void main (String[] args) {
    // Fill in your solution for Part I here.
    SList mylist=new SList();
    mylist.insertFront(6);
    mylist.insertEnd(9);
    mylist.insertEnd(12);
    System.out.println(mylist.toString());
    mylist.insertFront(3);
    mylist.insertEnd(15);
    System.out.println(mylist.toString());
    testEmpty();
    testAfterInsertFront();
    testAfterInsertEnd();
  }
```

#### Part II:  Adding to the End of a SList (3 points)

> ```
> A method called insertEnd() exists, but it runs in linear time, because every
> time it is called, it walks down the list to find the end.  Without changing
> the meaning of this method or any other, modify the representation of a SList
> and whatever methods are necessary to make insertEnd() run in constant time.
> Your SList class will need to continually maintain a record of the last (tail)
> SListNode in an SList, and all SList’s methods will have to ensure that this
> record stays current.
> ```

思路：在SList类中定义SListNode tail的field variable 利用tail variable使得insertEnd()方法运行在O(1)

```java
  private SListNode tail;


/**
   *  insertEnd() inserts item "obj" at the end of this list.
   *  @param obj the item to be inserted.
   **/

  public void insertEnd(Object obj) {
    if(tail==null){
      insertFront(obj);
    }else{
      tail.next=new SListNode(obj,null);
      tail= tail.next;
      size++;
    }
  }
```


