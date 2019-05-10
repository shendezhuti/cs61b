public class Y extends X implements Z{
	Y y;
	X x;

	public Y(){
		super();
	}

	public void test(){

	}
	
	public static void main(String[]args){
		Y[]ya=new Y[4];
		X[]xa=new X[4];
		ya=(Y[])xa;
	}
	

	public int  eat(int z){
		return 0;
	}
}