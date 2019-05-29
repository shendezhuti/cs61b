import dict.*;

public class test{
	private HashTableChained hashtable;

	public test(){
		hashtable=new HashTableChained();
	}



	public static void main(String[]args){
		test t=new test();
		t.hashtable.insert(1,1);
		t.hashtable.insert(2,2);
		t.hashtable.remove(1);
		t.hashtable.remove(1);
	}


}