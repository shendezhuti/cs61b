# CS 61B Homework 10 Due noon Wednesday, May 7, 2014

> This homework will give you practice implementing linear-time sorting
> algorithms.  This is an individual assignment; you may not share code with
> other students.
>
> Your job is to implement counting sort and radix sort for arrays of ints.  All
> your code should appear in the file sort/Sorts.java.  A skeleton is provided
> for you.

Part I  (8 points)
------------------
> Implement counting sort on int arrays.
>
> public static int[] countingSort(int[] keys, int whichDigit);
>
> The most important difference between the counting sort you will implement here
> and the one presented in lecture is that this counting sort uses one base-16
> digit in each int as its sort key, and ignores all the other digits.  That way,
> your counting sort can be used as one pass of radix sort.  (Make sure that your
> counting sort is stable!)
>
> The parameter "whichDigit" tells the method which base-16 digit of each int to
> use as a sort key.  If whichDigit is zero, sort on the least significant (ones)
> digit; if whichDigit is one, sort on the second least significant (sixteens)
> digit; and so on.  An int is 32 bits long, so it has eight digits of four bits
> each.
>
> The high bit of each int is a sign bit.  To keep life simple, we will assume
> that all the numbers are positive, so the high bit is always zero.  Don’t try
> to create an int whose most significant base-16 digit is greater than 7.
> Hexadecimal Primer:  Hexadecimal is a way of expressing a number in base-16.
> We use the usual digits 0...9, plus the additional digits a...f to represent
> ten through fifteen.  You can convert back and forth between an int and
> a hexadecimal string by using the Integer.toString(int, int) and
> Integer.parseInt(String, int) methods.  (Look them up in the online Java API,
> and/or look at how they are used in Sorts.java.)  You won’t use these for
> sorting, but they’re useful for getting numbers in and out of the algorithm in
> a human-readable format.
> One of the best reasons to use base-16 digits in radix sort is because they can
> be extracted very quickly from a key by using bit operations.  This means that,
> in your countingSort method, you should use bit operations to extract the
> digits, and not throw away the speed advantage by using something silly like
> toString() to extract each digit.  The bit operation that will serve you best
> is Java’s "&" operator.  If you write "x & 15", it masks the int x against the
> bit pattern "0000...00001111", so only the least significant base-16 digit
> survives, and the others are set to zero.  This allows you to extract the least
> significant digit.
> Want to extract a different digit?  Divide the int by some appropriate divisor
> first.  Recall that integer division always rounds down (toward zero), so you
> can eliminate low-order digits this way if you choose the right divisor.  This
> moves the digit you’re looking for down to the least significant position, so
> you can mask it against a 15.  (For faster performance, shift the bits to the
> right, if you know how to do so.)
>
> Warning:  Do not confuse & with &&.  && will not do bit masking.

本次作业加深了对counting sort 和radix sort的理解！！**只有自己做作业才知道自己有多菜，要继续努力！！注意点以及思路**  
(1)readme.pdf提到本次容器中待排序的数字都是 base-16 digit，就是16进制的意思，可以看到在main方法中有一个测试输入，并且有 Integer.parseInt()方法。  
(2)由于int是32位的，whichDigit这个变量在0-7之间。因此给某个指定的whichDigit变量，我们就对这个整个32位int数组中的某4位进行排序，比如whichDigit=0,我们就是对32位的前4位进行排序。  
这里要注意一点，我们所说的32位是以2进制的标准来看的，如果以16进制来看，int类型变量只有8位。  
(3) 我们在counting sort 方法里要新构建三个数组(实际上也可以只用2个，但是为了我们自己画图便于理解算法的思路，我们用三个数组）  
第一个是AfterBit数组，长度为keys.length。这个数组是存放了所有即将被排序的32位数，并将其转换为4位数的临时数组。 AfterBit数组将32位数转化为4位数的处理方式是  afterBit*=(keys>>(whichDigit\*4))&15;*  
第二个是count数组，长度为16。为什么是16，一个四位的二进制的范围是0-15，因此count数组的长度是16。这个count数组代表AfterBit数组中的元素 的 某个指定数 之前所有的数字总和。  
感觉讲的不是很清楚，但是如果我们自己理解了counting sort的原理，去画个图，上面的拗口的语句就能理解。  

第三个数组是我们的result 数组，长度就是keys.length。用于存放返回的结果   

(4)完成了counting sort以后，在radix sort中我们就可以利用写好的counting sort。循环内传入输入数组，whichDigit的变量从0到7。这样就得到了最后的排序数组。  

```java
/**
   *  countingSort() sorts an array of int keys according to the
   *  values of _one_ of the base-16 digits of each key.  "whichDigit"
   *  indicates which digit is the sort key.  A zero means sort on the least
   *  significant (ones) digit; a one means sort on the second least
   *  significant (sixteens) digit; and so on, up to a seven, which means
   *  sort on the most significant digit.
   *  @param key is an array of ints.  Assume no key is negative.
   *  @param whichDigit is a number in 0...7 specifying which base-16 digit
   *    is the sort key.
   *  @return an array of type int, having the same length as "keys"
   *    and containing the same keys sorted according to the chosen digit.
   *
   *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
   **/
  public static int[] countingSort(int[] keys, int whichDigit) {
    // Replace the following line with your solution.
    int []count=new int[16];
    int []result=new int [keys.length];
    int[] afterBit = new int[keys.length];

    for(int i=0;i<keys.length;i++){
        afterBit[i]=(keys[i]>>(whichDigit*4))&15;
    }
    for(int i=0;i<keys.length;i++){
        count[afterBit[i]]++;
    }
    int total=0;
    int c=0;
    for(int i=0;i<count.length;i++){
      c=count[i];
      count[i]=total;
      total=total+c;
    }

    for(int i=0;i<result.length;i++){
      result[count[afterBit[i]]]=keys[i];
      count[afterBit[i]]++;
    }
    return result;

  }
```



Part II  (2 points)
-------------------
> Implement radix sort on int arrays.  Your radix sort should use your counting
> sort to do each pass.
>
> public static int[] radixSort(int[] keys);
>
> A small test is provided in Sorts.main, which you can run by typing
> "java sort.Sorts".  We recommend you add more test code of your own.
> Your main method and other test code will not be graded.

```java
/**
   *  radixSort() sorts an array of int keys (using all 32 bits
   *  of each key to determine the ordering).
   *  @param key is an array of ints.  Assume no key is negative.
   *  @return an array of type int, having the same length as "keys"
   *    and containing the same keys in sorted order.
   *
   *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
   **/
  public static int[] radixSort(int[] keys) {
    // Replace the following line with your solution.
    int[] sort = new int[keys.length];
    sort = keys;
    for(int i = 0; i <= 7; i++){
      sort = countingSort(sort, i);
    }
    return sort;
  }
```



Submitting your solution
------------------------
> Make sure your methods countingSort and radixSort do NOT print anything to the
> screen!  (Your main method can print anything it likes.)
