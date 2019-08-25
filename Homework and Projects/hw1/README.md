
# hw1问题描述请参考readme.pdf

# problem1

思想：利用BufferedReader对象中的方法

注意：1.构造BufferedReader对象的时候需要传入InputStreamReader

2.构建InputStreamReader对象时需要传入System.in对象

3.逆序输出的要求可以采用数组保存后逆序输出

```java
import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    /* Replace this comment with your solution.  */

    URL oracle=new URL("https://www."+inputLine+".com/");
    BufferedReader in= new BufferedReader(new InputStreamReader(oracle.openStream()));
    String []res=new String[5];
    for(int i=0;i<5;i++){
     res[i]=in.readLine();
    }
    for(int i=4;i>=0;i--){
      System.out.println(res[i]);
    }
    in.close();
  }
}

```
# problem2

思想：同problem1利用BufferedReader对象的方法读取数据，利用StringBuilder类对象处理

注意：1.StringBuilder对象中delete方法的参数，左开右闭

```java

import java.io.*;

class Nuke2{
	 public static void main(String []args) throws Exception{
	 	BufferedReader keyboard;
	 	String inputline;
	 	keyboard=new BufferedReader(new InputStreamReader(System.in));
	 	System.out.print("Please enter a string: ");
    System.out.flush();        /* Make sure the line is printed immediately. */
	 	inputline=keyboard.readLine();
	 	StringBuilder sb=new StringBuilder(inputline);
	 	sb.delete(1,2);
	 	System.out.println(sb.toString());	
	 }
}
```




