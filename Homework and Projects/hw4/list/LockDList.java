package list;

public class LockDList extends DList{
	public LockDList (){
		super();
	}

	protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item,prev,next);
	}

	public void remove(DListNode node) {
		if(((LockDListNode)node).islocked) return ;
		super.remove(node); //上课说只有构造器才必须把super放在第一行，method好像没有这个规定？那肯定是我不仔细了
	}

	public void lockNode(DListNode node) {
		((LockDListNode)node).islocked=true;
	}
}