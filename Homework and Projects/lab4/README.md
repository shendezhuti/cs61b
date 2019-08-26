# CS 61B Lab 4 February 13-14, 2014

> Goal:  This lab will demonstrate how a sentinel node can simplify a
> doubly-linked list implementation.

Getting Started
---------------
> Please make sure you have a partner for this lab.
> The files in the lab4 directory contain classes for two different types of
> doubly-linked list.  The DList1 class does not use a sentinel, whereas the
> DList2 class does.  The DList1 class is not circularly linked, but the DList2
> class is (through the sentinel).  Compile DList1.java and DList2.java (using
> "javac -g DList1.java DList2.java".)
> Your task is to implement two insertFront() and two removeFront() methods--one
> of each for each list class.  insertFront() and removeFront() insert or remove
> an item at the beginning of a list.  Make sure your implementations work for
> empty lists, one-node lists, and larger lists.
> The main() methods of DList1 and DList2 include test code, which you can run
> with "java DList1" and "java DList2".

Part I:  insertFront in DList1 (1 point)
----------------------------------------
> Write a method called DList1.insertFront() that inserts an int at the front of
> "this" DList1.

思路：先判断头结点是不是空，为空直接创建新节点；不为空则创建新节点插入，设置为头部结点

```java
/**
   *  insertFront() inserts an item at the front of a DList1.
   */
  public void insertFront(int i) {
    // Your solution here.
    if(head==null){
    head=new DListNode1(i);
    tail=head;
    size++;
    }else{
    DListNode1 oldhead=head;
    head=new DListNode1(i);
    head.next=oldhead;
    oldhead.prev=head;
    size++;
    }
  }
```

Part II:  removeFront in DList1 (1 point)
-----------------------------------------
> Write a method called DList1.removeFront() that removes the first item (and
> node) from "this" DList1.

```java
/**
   *  removeFront() removes the first item (and node) from a DList1.  If the
   *  list is empty, do nothing.
   */
  public void removeFront() {
    // Your solution here.
    if(size==0)return;
    DListNode1 newhead=head;
    head=head.next;
    if(head!=null)head.prev=null;
    newhead.next=null;
    size--;
    if(size==0){
        head=tail=null;
    }
  }
```

Part III:  insertFront in DList2 (1 point)
------------------------------------------
> Write a method called DList2.insertFront() that inserts an int at the front of
> "this" DList2.  Your code should NOT use any "if" statements or conditionals.

思路：不需要再判断head是否为空

注意：写链表代码的时候最好画个图，每加一行代码可以在图上标注关系，以防出错

```java
/**
   *  insertFront() inserts an item at the front of a DList2.
   */
  public void insertFront(int i) {
    DListNode2 newhead=new DListNode2(i);
    DListNode2 oldhead=head.next;
    head.next=newhead;
    newhead.next=oldhead;
    newhead.prev=head;
    oldhead.prev=newhead;
    size++;
  }
```

Part IV:  removeFront in DList2 (2 points)
------------------------------------------
> Write a method called DList2.removeFront() that removes the first item (and
> non-sentinel node) from "this" DList2.  Your code should not require separate
> branches for the one-node case and the more-than-one-node case.  (You will
> need one "if", to handle the zero-node case.)

```java
/**
   *  removeFront() removes the first item (and first non-sentinel node) from
   *  a DList2.  If the list is empty, do nothing.
   */
  public void removeFront() {
    if(size!=0){
    DListNode2 newhead=head.next.next;
    DListNode2 oldhead=head.next;
    oldhead.prev=null;
    oldhead.next=null;
    head.next=newhead;
    if(newhead!=null)newhead.prev=head;
    size--;
    }
  }
```


