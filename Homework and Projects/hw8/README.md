# CS 61B  Homework 8 Due noon Wednesday, April 16, 2014

> This homework will give you practice implementing sorting algorithms,and will
> illustrate how sorting linked lists can be different from sorting arrays.
> This is an individual assignment; you may not share code with other students.
>
> Your job is to implement two sorting algorithms for linked lists.  The data
> structure you will use is a catenable queue.  "Catenable" means that two queues
> can be concatenated into a single queue efficiently--in O(1) time.  The
> LinkedQueue data structure is implemented as a singly-linked list with a tail
> pointer, much like the one you worked with in Lab 3.
>
> The LinkedQueue class (in the list package) has the following methods.
>
> public LinkedQueue();
> public int size();
> public boolean isEmpty();
> public void enqueue(Object item);
> public Object dequeue() throws QueueEmptyException;
> public Object front() throws QueueEmptyException;
> public String toString();
> public Object nth(int n);
> public void append(LinkedQueue q);
>
> The second-last method, nth(), returns item n in the queue (with the assumption
> that items are numbered from 1) without removing it, taking Theta(n) time.
> The last method, append(), concatenates the contents of q onto the end of
> "this" queue and leaves q empty, taking constant time.
>
> You will implement mergesort and quicksort in the file ListSorts.java.  In
> Parts I and II below, assume that the input LinkedQueue (to be sorted) contains
> only Comparable items, so that casting items to Comparable is safe.  All
> comparisons should be done using the method compareTo().  Your code should be
> work with any Comparable objects, not just the Integer objects used by the test
> code.  (In other words, do not cast queue items to Integers.)
>
> The dequeue() and front() methods can throw QueueEmptyExceptions; make sure
> that these exceptions are always caught.  (If your code is bug-free, such an
> exception will never occur, so you can handle caught exceptions by simply
> printing an error message to let yourself know you have a bug.)  Do not add
> a "throws" clause to any method prototype that doesn’t already have one.

Part I  (4 points)
------------------
> Implement mergesort on LinkedQueues using the following three steps
>
> (1)  Write a method called makeQueueOfQueues() that takes a LinkedQueue as
> input and returns a queue of queues in which each queue contains one item from
> the input queue.  For example, if the input queue is [ 3 5 2 ], this method
> should return the queue [ [ 3 ] [ 5 ] [ 2 ] ].
>
> public static LinkedQueue makeQueueOfQueues(LinkedQueue q);


```java
/**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    	int size=q.size();
    	LinkedQueue que=new LinkedQueue();
    	for(int i=0;i<size;i++){
        LinkedQueue newqueue=new LinkedQueue();
        try{
          newqueue.enqueue(q.dequeue());
        }catch(QueueEmptyException e){
          e.printStackTrace();
        }
        que.enqueue(newqueue);
      }
    return que;
    }
```



>
> (2)  Write a method called mergeSortedQueues() that merges two sorted queues
> into one.  See the comments in ListSorts.java for details.
>
> public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2);

```java
 /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue q=new LinkedQueue();
    while(!q1.isEmpty()&&!q2.isEmpty()){
      try{
         Comparable item1=(Comparable)q1.front();
         Comparable item2=(Comparable)q2.front();
         if(item1.compareTo(item2)<=0){
         q.enqueue(q1.dequeue());
         }else{
          q.enqueue(q2.dequeue());
         }
      }catch(QueueEmptyException e){
        e.printStackTrace();
      }
    }
    while(!q1.isEmpty()){
      try{
        q.enqueue(q1.dequeue());
      }catch(QueueEmptyException e){
        e.printStackTrace();
      }
    }
    while(!q2.isEmpty()){
      try{
        q.enqueue(q2.dequeue());
      }catch(QueueEmptyException e){
        e.printStackTrace();
      }
    }
    return q;
    
  }
```



>(3)  Implement mergeSort(), which sorts a LinkedQueue q as follows.  First, use
> makeQueueOfQueues() to convert q into a queue of queues.  Repeatedly do the
> following:  remove two items from the queue of queues, merge them with
>mergeSortedQueues(), and enqueue the resulting queue on the queue of queues.
> When there is only one queue left on the queue of queues, move its items back
>to q (preferably using the fast append() method).
> 
> public static void mergeSort(LinkedQueue q);

```java
/**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
      if(q.size()<=1){
        return;
      }

     LinkedQueue que=makeQueueOfQueues(q);//注意q这时候已经变成了空的
     while(que.size()>1){
        LinkedQueue q1=null;
        LinkedQueue q2=null;
        try{
          q1=(LinkedQueue)que.dequeue();
          q2=(LinkedQueue)que.dequeue();
        }catch(QueueEmptyException e){
          e.printStackTrace();
        }
        LinkedQueue p=mergeSortedQueues(q1,q2);
        que.enqueue(p);
     }
     try{
          q.append((LinkedQueue)que.dequeue());
     }catch(QueueEmptyException e){
          e.printStackTrace();
        }

  }

```



>Part II  (4 points)
> -------------------

> Implement quicksort on LinkedQueues using the following two steps.
>
> (1)  Implement a partition() method that partitions a queue into three separate
> queues containing items less than, equal to, or greater than a pivot item.
> See the comments for details.
>
> public static void partition(LinkedQueue qIn, Comparable pivot,
>                              LinkedQueue qSmall, LinkedQueue qEquals,
>                              LinkedQueue qLarge);                    

```java
/**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    while(!qIn.isEmpty()){
      try{
        Comparable item=(Comparable)qIn.dequeue();
        if(item.compareTo(pivot)<0){
            qSmall.enqueue(item);
        }else if(item.compareTo(pivot)==0){
            qEquals.enqueue(item);
        }else{
            qLarge.enqueue(item);
        }
      }catch(QueueEmptyException e){
        e.printStackTrace();
      }
    }
  }
```



> (2)  Implement quickSort(), which sorts a LinkedQueue q as follows.  Choose a
>random integer between 1 and q.size().  Find the corresponding item using the
> nth() method, and use the item as the pivot in a call to partition().
> Recursively quickSort() the smaller and larger partitions, and then put all of
> the items back into q in sorted order by using the append() method.
>
> public static void quickSort(LinkedQueue q);
>    
>    There is test code for both mergesort and quicksort; run "java ListSorts".
>    The test code generates different random arrays each time you run it.
>I strongly advise that you also test that your mergesort and quicksort both
> work on lists of size zero and one.  (Our autograder will.)

产生一个1到size的随机数 我参考了别人的方法:int index=((int)(Math.random()*100000))%q.size()+1; 注意因为random()返回的是double，因此我们需要强制转型

```java
 /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    int index=((int)(Math.random()*100000))%q.size()+1;
    Comparable pivot=(Comparable)q.nth(index);
    LinkedQueue qSmall = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();
    partition(q,pivot,qSmall,qEquals,qLarge);
    if(qSmall.size()>1){
      quickSort(qSmall);
    }
    if(qLarge.size()>1){
      quickSort(qLarge);
    }
    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }
```




Part III  (1 point)
-------------------
> Uncomment the timing code in the main() method, then run your sorting routines
> on larger lists.  By changing the SORTSIZE constant, you may change the size of
> the queues you sort.  What are the running times (in milliseconds) for sorting
> lists of sizes 100, 1000, 10,000, 100,000, and 1,000,000?  Put your answers in
> the GRADER file.  (Do NOT put a .txt extension on GRADER!)

Part III.  Running time comparisons

  List size         mergesort             quicksort
      100 				1 msec				1 msec
    1,000				3  					4
   10,000				19					12
  100,000				86					100
1,000,000				2280				1105

Part IV  (1 point)
------------------
> A sort is _stable_ if items with equal keys always come out in the same order
> they went in.  If you sort the database records [ 3:hex 7:boo 3:spa ] by
> number, and the output is [ 3:spa 3:hex 7:boo ], then the sort is not stable
> because two records with the same key (3) traded places.
> Is your mergesort always stable?  Explain why or why not.
> Is your quicksort always stable?  Explain why or why not.
> Give a sentence or two in your explanations that describe where in your code or
> in the data structures the stability of the sort is decided--that is, whether
> or not equal items are kept in their original order at critical parts of the
> sorting process.  Put your answers in the GRADER file.

merge sort不stable； 因为merge 2 sorted list的时候一边取一个front；这两个sub list有可能先后错位；

比如 5 6 6 7和1 6 8 merge时，6的顺序会被打乱

quick sort stable； 因为每次相同的key都按顺序放在中间。

还有一个导致not stable的原因：当size()为奇数，最后一个剩下的queue在下一轮merge时顺序会打乱
