/* ListSorts.java */

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000000;

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
       }catch (QueueEmptyException e){
        e.printStackTrace();
       }
       que.enqueue(newqueue);
      }
    return que;
  }

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

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    
    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
    
  }

}
