#  CS 61B  Lab 2 January 30-31, 2014

# Part I

# Part II




```java
Fraction sumOfTwo = f1.add(f2);              // Sum of f1 and f2.
Fraction sumOfThree = f0.add(f1).add(f2);             // Sum of f0, f1, and f2.

System.out.println("The sum of " + f1 + " and " + f2 + " is " + sumOfTwo);
System.out.println("The sum of " + f0 + ", " + f1 + " and " + f2 + " is " +
                   sumOfThree);
```
# Part III

```java
/** Replaces this Fraction's numerator with a new value.
   *  @param numerator is the new numerator.  Must be nonnegative.
   */
public void changeNumerator(int numerator) { // DO NOT CHANGE THIS SIGNATURE!
    // Fix the bug that prevents this method from working correctly.
    if (numerator < 0) {
      System.out.println("Fatal error:  Negative numerator.");
      System.exit(0);
    }
    this.numerator = numerator;
  }
```

修改的地方就是 ``` numerator=numerator```--->```this.numerator=numerator```

```java
/** Returns the number of Fraction objects in existence.
   *  @return the number of Fraction objects in existence.
   */
  public int fracs() {                         // DO NOT CHANGE THIS SIGNATURE!
    // Fix the bug that prevents this method from working correctly.
    if(numberOfFractions==0){
      System.out.println("Fatal error:  numberOfFractions=0");
      System.exit(0);
    }
    return numberOfFractions;
  }
```

修改的地方是增加了判断语句

```java
if(numberOfFractions==0){
      System.out.println("Fatal error:  numberOfFractions=0");
      System.exit(0);
    }
```

# Part IV

思路：readme中已经提示如何找到最大公因数

```java

  /** Computes the greatest common divisor (gcd) of the two inputs.
   * @param x must be nonnegative
   * @param y must be nonnegative
   * @return the gcd of x and y
   */
  static private int gcd(int x, int y) {
    /* Replace the following line with your solution. */
    if(y==0){
      return x;
    }else{
    return gcd(y,x % y);
    } 
  }
```


