
/**
 *  A DList2 is a mutable doubly-linked list.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList{
	protected DListNode head;//注意这个地方的head是哨兵点
	protected int size;

/* DList2 invariants:
   *  1)  head != null.
   *  2)  For any DListNode2 x in a DList2, x.next != null.
   *  3)  For any DListNode2 x in a DList2, x.prev != null.
   *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
   *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
   *      (denoted by "head"), that can be accessed from the sentinel by
   *      a sequence of "next" references.
   */

/**
* DList() constructor for an empty List
*/
	public DList(){
		head=new DListNode();
		head.item[0]=Integer.MIN_VALUE;
		head.next=head;
		head.prev=head;
		size=0;
	}


/**
*	DList()constructor for an node
*/
	public DList(int []a){
		head=new DListNode();
		head.item[0]=Integer.MIN_VALUE;
		head.next=new DListNode(a);
		head.prev=head.next;
		head.next.prev=head;
		head.prev.next=head;
		size=1;
	}
/**
   *  DList2() constructor for a two-node DList2.
   */

	public DList(int []a,int []b){
		head = new DListNode();
    head.item[0] = Integer.MIN_VALUE;
    head.next = new DListNode();
    head.next.item = a;
    head.prev = new DListNode();
    head.prev.item = b;
    head.next.prev = head;
    head.next.next = head.prev;
    head.prev.next = head;
    head.prev.prev = head.next;
		size=2;
	}

	/**
   *  insertFront() inserts an item at the front of a DList2.
   */
  public void insertFront(int []i) {
  	DListNode newhead=new DListNode(i);
    DListNode oldhead=head.next;
    head.next=newhead;
    newhead.next=oldhead;
    newhead.prev=head;
    oldhead.prev=newhead;
    size++;
  }

  /**
   *  removeFront() removes the first item (and first non-sentinel node) from
   *  a DList2.  If the list is empty, do nothing.
   */
  public void removeFront() {
  	if(size!=0){
    DListNode newhead=head.next.next;
    DListNode oldhead=head.next;
    oldhead.prev=null;
    oldhead.next=null;
    head.next=newhead;
    newhead.prev=head;
    size--;
   }
  }
   public void insertEnd (int []i){
   		DListNode newtail=new DListNode(i);
   		DListNode oldtail=head.prev;
   		head.prev=newtail;
   		newtail.prev=oldtail;
   		newtail.next=head;
   		oldtail.next=newtail;
   		size++;
   }
   public void insertBetween(int []i,DListNode  pre){
      DListNode newNode=new DListNode(i);
      pre.next.prev=newNode;
      newNode.next=pre.next;
      newNode.prev=pre;
      pre.next=newNode;
      size++;
   }
   public void removeBetween(DListNode pre){
    pre.next=pre.next.next;
    pre.next.prev=pre;
    size--;
   }
   public void removeEnd(){
   	if(size!=0){
   	DListNode newtail=head.prev.prev;
    DListNode oldtail=head.prev;
    oldtail.prev=oldtail.next=null;
    head.prev=newtail;
    newtail.prev=head;
   	size--;
   	}
   }
    /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
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

  public DListNode getHead(){
  	if(head.next==head){
  	return null;
  	}
  	return head.next;
  }

}