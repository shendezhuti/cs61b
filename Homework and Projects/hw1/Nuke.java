
import java.io.*;

class Nuke2{
	 public static void main(String []args) throws Exception {
	 	BufferedReader keyboard;
	 	String inputline;
	 	keyboard=new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter a string ");
    System.out.flush();        /* Make sure the line is printed immediately. */
	 	inputline=keyboard.readLine();
	 	StringBuilder sb=new StringBuilder(inputline);
	 	sb.delete(1,2);
	 	System.out.println(sb.toString());	
	 }
}