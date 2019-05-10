public class DListNode{
	public int []item;  //item [0] 代表了pixel的重复数量 item[1]表示红色的值 item[2]表示绿色的值 item[3]表示蓝色的值
	public DListNode next;
	public DListNode prev;

	public DListNode(){
		item=new int[4];
		prev=null;
		next=null;
	}
	public DListNode(int []i){
		item=i;
		prev=null;
		next=null;
	}


}