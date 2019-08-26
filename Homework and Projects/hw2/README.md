
# CS 61B  Homework 2 Due noon Wednesday, February 5, 2014

作业目的：构建使用Java类并且学习将复杂的task分解为小的工作

内容请参考readme.pdf

#  Part I

####   isLeapYear(int year)

首先leap year是指闰年 可以被4整除但是不能被100整除（并且可以被400）整除

那么判断一个年是否是闰年，思路为：判断这个数能否被400整除，可以则是闰年，不行则再判断该数能否被4整除但是不能被100除，若如此则这个数是闰年。 以上两种情况均返回true，其余情况则返回false

```java
/** Checks whether the given year is a leap year.
   *  @return true if and only if the input year is a leap year.
   */
  public static boolean isLeapYear(int year) {
    if(year%400==0)return true;
    else if(year%4==0&&year%100!=0)return true;
    else return false;
  }
```

#### daysInMonth(int month, int year)

思路：利用switch语法

```java

  /** Returns the number of days in a given month.
   *  @param month is a month, numbered in the range 1...12.
   *  @param year is the year in question, with no digits omitted.
   *  @return the number of days in the given month.
   */
public static int daysInMonth(int month, int year) {
    int normalday=28;
    int thirtyday=30;
    int thirtyoneday=31;
    if(isLeapYear(year)){
        normalday=29;
    }
    switch (month){
          case 4:
            return thirtyday;
             case 6:
            return thirtyday;
             case 9:
            return thirtyday;
             case 11:
            return thirtyday;
          case 2:
            return normalday; 
          default :
          return thirtyoneday;
     }  

  }
```

#### isValidDate(int month, int day, int year)

思路：比较简单 看代码即可

```java
/** Checks whether the given date is valid.
   *  @return true if and only if month/day/year constitute a valid date.
   *
   *  Years prior to A.D. 1 are NOT valid.
   */
  public static boolean isValidDate(int month, int day, int year) {
           if(year<1)return false;
           if(month>12||month<1)return false;
           if(day>daysInMonth(month,year))return false;
           return true;
  }
```

# Part II

####  public Date(int month, int day, int year)



```java
  /** Constructs a date with the given month, day and year.   If the date is
   *  not valid, the entire program will halt with an error message.
   *  @param month is a month, numbered in the range 1...12.
   *  @param day is between 1 and the number of days in the given month.
   *  @param year is the year in question, with no digits omitted.
   */
public Date(int month, int day, int year) {
      if(!isValidDate(month,day,year)){
        System.out.println("wrong input");
        System.exit(0);
      }
      this.month=month;
      this.day=day;
      this.year=year;
      this.sumdays=dayInAll();
  }
```



#### toString()

```java
 /** Returns a string representation of this date in the form month/day/year.
   *  The month, day, and year are expressed in full as integers; for example,
   *  12/7/2006 or 3/21/407.
   *  @return a String representation of this date.
   */
  public String toString() {
    return month+"/"+day+"/"+year;
  }
```

直接使用+来拼接字符串实际上不太好，因为每次+都会新建一个字符串对象，当需要拼接大量字符串的时候采用StringBuilder.append()方法更加的合适



# Part III

#### isBefore(Date d)

思路：我们直接利用Date类的属性sumdays比较

```java
/** Determines whether this Date is before the Date d.
   *  @return true if and only if this Date is before d. 
   */
  public boolean isBefore(Date d) {

    return sumdays<d.sumdays;
```

#### isAfter(Date d)

思路：同isBefore(Date d)方法

```java
/** Determines whether this Date is after the Date d.
   *  @return true if and only if this Date is after d. 
   */
  public boolean isAfter(Date d) {
    return  sumdays>d.sumdays;
  }
```

#### dayInYear()

思路：没啥难度，就是一个数学问题

```java
/** Returns the number of this Date in the year.
   *  @return a number n in the range 1...366, inclusive, such that this Date
   *  is the nth day of its year.  (366 is used only for December 31 in a leap
   *  year.)
   */
  public int dayInYear() {
       int num=0;
       for(int i=1;i<=month;i++){
        if(i<month)num+=daysInMonth(i,year);
        else num+=day;
       }             
       return num;
  }
```

#### difference(Date d)

思路：利用sumdays的这个属性去计算差值

```java
/** Determines the difference in days between d and this Date.  For example,
   *  if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1.
   *  If this Date occurs before d, the result is negative.
   *  @return the difference in days between d and this date.
   */
  public int difference(Date d) {
    return sumdays-d.sumdays;
  }
```

# Part IV

#### Date(String s)

给定：month/day/year的输入格式，完成Date对象的构造器。

思路：利用String对象的split方法分割成字符数组，再利用Interget.parseInt()方法将字符转化为整形

```java
/** Constructs a Date object corresponding to the given string.
   *  @param s should be a string of the form "month/day/year" where month must
   *  be one or two digits, day must be one or two digits, and year must be
   *  between 1 and 4 digits.  If s does not match these requirements or is not
   *  a valid date, the program halts with an error message.
   */
  public Date(String s) {

    String[]res=s.split("/");
    int month=Integer.parseInt(res[0]);
    int day=Integer.parseInt(res[1]);
    int year=Integer.parseInt(res[2]);
    if(!isValidDate(month,day,year)){
        System.out.println("wrong input");
        System.exit(0);
      }
    this.day=day;
    this.month=month;
    this.year=year;
    this.sumdays=dayInAll();
  }
```


