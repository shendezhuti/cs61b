package list;

public class LockDListNode extends DListNode{
	public boolean islocked;
	
	LockDListNode (Object i, DListNode p, DListNode n){
		super(i,p,n);
		islocked=false;
	}
}