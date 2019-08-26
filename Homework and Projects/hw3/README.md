# CS 61B  Homework 3   Due noon Wednesday, February 12, 2014

> This homework assignment is designed to give you practice working with arrays,
> linked lists, and nested loops.  It will also give you practice for the similar
> but harder run-length encoding computations in Project 1.  This is an
> individual assignment; you may not share code with other students



Part I  (5 points)
------------------
> Fill in the smoosh() method in the Homework3 class so that it performs as
> indicated in the comment.  Your solution should not use linked lists, nor
> should it use your squish() method.

思路：利用两个变量left和i，初始值都为0。当内层循环遍历数组当ints[i]=ints[i-1]时，i++而left不变。

```java
 /**
   *  smoosh() takes an array of ints.  On completion the array contains
   *  the same numbers, but wherever the array had two or more consecutive
   *  duplicate numbers, they are replaced by one copy of the number.  Hence,
   *  after smoosh() is done, no two consecutive numbers in the array are the
   *  same.
   *
   *  Any unused elements at the end of the array are set to -1.
   *
   *  For example, if the input array is [ 0 0 0 0 1 1 0 0 0 3 3 3 1 1 0 ],
   *  it reads [ 0 1 0 3 1 0 -1 -1 -1 -1 -1 -1 -1 -1 -1 ] after smoosh()
   *  completes.
   *
   *  @param ints the input array.
   **/

  public static void smoosh(int[] ints) {
    // Fill in your solution here.  (Ours is twelve lines long, not counting
    // blank lines or lines already present in this file.)
    int n=ints.length;
    int left=0,i=0;
    while(i<n){
          ints[left]=ints[i];
          left++;
          i++;
            while(i<n&&ints[i]==ints[i-1]){
              i++;
            } 

    }
    while(left<n){
      ints[left++]=-1;
    }
  }
```

Part II  (3 points)
-------------------
> Fill in the squish() method in the SList class so that it performs as indicated
> in the comment.  Your solution should not use arrays, nor should it use your
> smoosh() method.  Do not change the prototype of the SList constructor or the
> insertEnd method; our test software will call them.

思路：

 

```java
 /**

- squish() takes this list and, wherever two or more consecutive items are
- equals(), it removes duplicate nodes so that only one consecutive copy
- remains.  Hence, no two consecutive items in this list are equals() upon
- completion of the procedure.
  *
- After squish() executes, the list may well be shorter than when squish()
- began.  No extra items are added to make up for those removed.
  *
- For example, if the input list is [ 0 0 0 0 1 1 0 0 0 3 3 3 1 1 0 ], the
- output list is [ 0 1 0 3 1 0 ].
  *
- IMPORTANT:  Be sure you use the equals() method, and not the "=="
- operator, to compare items.
  **/

  public void squish() {
    // Fill in your solution here.  (Ours is eleven lines long.)
 		SListNode dummy=new SListNode(0);
    dummy.next=head;
    while(head!=null&&head.getNext()!=null){//注意防止空指针
    SListNode next=head.getNext();
      if(next.getItem().equals(head.getItem())){
      head.setNext(next.getNext());
      continue;		//继续查找相同的元素
     }
      head=head.next;
    }
   head=dummy.next;
  }
```

Part III  (2 points)
--------------------
> Fill in the twin() method in the SList class so that it performs as indicated
> in the comment.  Your solution should not use arrays.

```java
/**
   *  twin() takes this list and doubles its length by replacing each node
   *  with two consecutive nodes referencing the same item.
   *
   *  For example, if the input list is [ 3 7 4 2 2 ], the
   *  output list is [ 3 3 7 7 4 4 2 2 2 2 ].
   *
   *  IMPORTANT:  Do not try to make new copies of the items themselves.
   *  Make new SListNodes, but just copy the references to the items.
   **/

  public void twin() {
    // Fill in your solution here.  (Ours is seven lines long.)
      SListNode dummy=new SListNode(0);
      dummy.next=head;
    while(head!=null){
      SListNode next=head.getNext();
      head.setNext(new SListNode(head.getItem(),next)); //这一步同时设置了新节点的下一个结点
      head=head.next.next;
    }
    head=dummy.next;
  }
```


