# CS 61B Homework 9 Due noon Wednesday, April 23, 2014

Part I  (8 points)
------------------
> Edit the file Maze.java and complete the implementation of the Maze
> constructor.  Use our disjoint sets data structure to create a random
> rectangular maze.  Your random mazes should have two properties:  there is a
> path from any given cell to any other cell, and there are no cycles (loops)--
> in other words, there is _only_ one path from any given cell to any other cell.
>
> Each maze is an h-by-v grid of square cells (where h is the number of cells in
> the horizontal direction, and v is the number of cells in the vertical
> direction).  The cell in the upper left corner is numbered (0, 0).  The cell to
> its right is numbered (1, 0).  The cell below the upper left cell is numbered
> (0, 1).
>
> There are vertical walls and horizontal walls separating adjacent cells.  Each
> interior horizontal wall has the same numbering as the cell immediately above
> it.  Each interior vertical wall has the same numbering as the cell to its
> immediate left.  Hence, horizontal wall (i, j) separates cell (i, j) from cell
> (i, j + 1), and vertical wall (i, j) separates cell (i, j) from cell
> (i + 1, j).  Here is a depiction of a 6-by-3 grid.

>  
>
> ```
>   +-----------+-----------+-----------+-----------+-----------+-----------+
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    |   (0,0) (0,0) (1,0) (1,0) (2,0) (2,0) (3,0) (3,0) (4,0) (4,0) (5,0)   |
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    +---(0,0)---+---(1,0)---+---(2,0)---+---(3,0)---+---(4,0)---+---(5,0)---+
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    |   (0,1) (0,1) (1,1) (1,1) (2,1) (2,1) (3,1) (3,1) (4,1) (4,1) (5,1)   |
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    +---(0,1)---+---(1,1)---+---(2,1)---+---(3,1)---+---(4,1)---+---(5,1)---+
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    |   (0,2) (0,2) (1,2) (1,2) (2,2) (2,2) (3,2) (3,2) (4,2) (4,2) (5,2)   |
>    |           |           |           |           |           |           |  
>    |           |           |           |           |           |           |  
>    +-----------+-----------+-----------+-----------+-----------+-----------+
> ```
>
> 

> Observe that there is an h-by-(v-1) set of horizontal walls, and an (h-1)-by-v
> set of vertical walls.  In your maze, some of these walls will be present and
> some will be missing.  The walls present are indicated by the arrays hWalls and
> vWalls, which are an h-by-(v-1) boolean array and an (h-1)-by-v boolean array,
> respectively.  (Exterior walls are numbered according to the same system, but
> there is no explicit storage for them, because they are presumed to always be
> present.)
>
> The Maze constructor currently creates a "maze" in which every possible wall is
> present.  To make a proper maze, you will need to eliminate walls selectively.
> Do so as follows.
>
> (1)  Create a disjoint sets data structure in which each cell of the maze is
>      represented as a separate item.  Use the DisjointSets class (described in
>      the Lecture 33 notes), which is in the set package we've provided.
>
> (2)  Order the interior walls of the maze in a random order.
>
> ​     One way to do this is to create an array in which every wall (horizontal
> ​     and vertical) is represented.  (How you represent each wall is up to
> ​     you.)  Scramble the walls by reodering them into a random permutation.
> ​     Each possible permutation (ordering) of walls should be equally likely.
>
> ​     Here's how to do that.  Put all the walls into the array.  The idea is to
> ​     randomly choose (from all the walls) the wall that will be at the end of
> ​     the array.  Swap it to the end, then never move it again.  From the
> ​     remaining walls, choose the wall that will come second-last.  Swap it to
> ​     its final position, then never move it again.  Repeat until you've chosen
> ​     a wall for each slot in the array.
>
> ​     Here's an algorithmic rephrasing of what I just said.  Maintain a counter
> ​     w, initially set to the number of walls.  Iterate the following procedure:
> ​     select one of the first w walls in the array at random, and swap it with
> ​     the wth wall in the array (at index w - 1).  This permanently establishes
> ​     the randomly chosen wall as the wth wall.  Then decrease w by one.  Repeat
> ​     this operation until w is one.
>
> (3)  Visit the walls in the (random) order in which they appear in the array.
>      For each wall you visit:
>
> ​        (i)  Determine which cell is on each side of the wall.
> ​       (ii)  Determine whether these two cells are members of the same set
> ​             in the disjoint sets data structure.  If they are, then there is
> ​             already a path between them, so you must leave the wall intact to
> ​             avoid creating a cycle.
> ​      (iii)  If the cells are members of different sets, eliminate the wall
> ​             separating them (thereby creating a path from any cell in one
> ​             set to any cell in the other) by setting the appropriate element
> ​             of hWalls or vWalls to false.  Form the union of the two sets in
> ​             the disjoint sets data structure.
>
> When you have visited every wall once, you have a finished maze!
>
> Be forewarned that the DisjointSets class has no error checking, and will fail
> catastrophically if you union() vertices that are not roots of their respective
> sets, or if you union() a set with itself.  You may want to add error checking
> to DisjointSets.java to help you find your bugs, and/or augment union() so it
> always calls find() on both inputs first.  This error checking can help you
> with Project 3 as well.
>
> All the other methods you need, including test methods, are provided for you.
>
>   toString() converts the maze to a string so you can print it.
>   randInt(c) generates a random number from 0 to c - 1, and is provided to
>       help you write the Maze() constructor.  To keep the mazes interesting,
>       it generates a different sequence of random numbers each time you run the
>       program.
>   diagnose() tests your maze for cycles or unreachable cells with depth-first
>       search.  DON'T CHANGE IT.  YOUR CODE MUST WORK WITH _OUR_ COPY OF THIS
>       METHOD.
>   main() generates a maze (with your constructor), prints it, and tests it.
>
> diagnose() depends on the following two methods, so don't make changes that
> will prevent these from working:
>
>   horizontalWall(x, y) determines whether a horizontal wall is intact.
>   verticalWall(x, y) determines whether a vertical wall is intact.
>
> You may see how you're doing by compiling and running Maze.java.  To look at a
> 30 x 10 maze, run:
>
>   java Maze 30 10
>
> The default dimensions, if you don't specify any on the command line, are
> 39 x 15.

(1)我们要new **一个** DisjointSets的对象，numelements应该是我们所有的格子数，因此是horiz*vert；

(2) 这个部分我感觉是比较让我头大的，我一开始一直没弄明白为什么要有这个步骤，后来才明白(2)是给(3)做准备工作，主要的工作就是将所有的wall放到一个int数组里面（就是说用int将wall表示）具体怎么放可以由我们自己设计，但是之后需要通过我们自己弄明白怎么将每个wall对应的index转为对应的cell。我参考了网上别的思路，具体是：对于Hwall我们就用i+1赋予。
，即walls*=i+1; 对于Vwall我们用负数赋值 即*

```java
int vindex=0;   
for( i=numberOfHWall;i<numberOfHWall+numberOfVWall;i++){   
walls=-vindex;     
vindex=vindex+1;   
}  
```

*之后我们将walls数组中的wall中排序打乱，打乱的方法可以参见readme，还是比较简单的。*

*(3)这个地方比较关键的是怎么将我们之前赋予的值转换成格子坐标，其实很简单！我们每次令w=wall，如果发现w大于0，说明是Hwall，我们需要判断这个横墙的上下两个格子是否是同一个root，不是的话则union！同理如果w小于0，说明是Vwall，需要判断竖墙的左右格子是否是同一个root，不是则union。那么如何判断上下以及左右格子的坐标呢？* 

```java
**
   *  Maze() creates a rectangular maze having "horizontalSize" cells in the
   *  horizontal direction, and "verticalSize" cells in the vertical direction.
   *  There is a path between any two cells of the maze.  A disjoint set data
   *  structure is used to ensure that there is only one path between any two
   *  cells.
   **/
  public Maze(int horizontalSize, int verticalSize) {
    int i, j;

    horiz = horizontalSize;
    vert = verticalSize;
    if ((horiz < 1) || (vert < 1) || ((horiz == 1) && (vert == 1))) {
      return;                                    // There are no interior walls
    }

    // Create all of the horizontal interior walls.  Initially, every
    // horizontal wall exists; they will be removed later by the maze
    // generation algorithm.
    if (vert > 1) {
      hWalls = new boolean[horiz][vert - 1];
      for (j = 0; j < vert - 1; j++) {
        for (i = 0; i < horiz; i++) {
          hWalls[i][j] = true;
        }
      }
    }
    // Create all of the vertical interior walls.
    if (horiz > 1) {
      vWalls = new boolean[horiz - 1][vert];
      for (i = 0; i < horiz - 1; i++) {
        for (j = 0; j < vert; j++) {
          vWalls[i][j] = true;
        }
      }
    }

    /**
     * Fill in the rest of this method.  You should go through all the walls of
     * the maze in random order, and remove any wall whose removal will not
     * create a cycle.  Use the implementation of disjoint sets provided in the
     * set package to avoid creating any cycles.
     *
     * Note the method randInt() further below, which generates a random
     * integer.  randInt() generates different numbers every time the program
     * is run, so that you can make lots of different mazes.
     **/

    DisjointSets st=new DisjointSets(horiz*vert);
    int numberOfHWall=horiz * (vert-1);
    int numberOfVWall=vert * (horiz-1);
    int []walls=new int[numberOfVWall+numberOfHWall];
    for( i=0;i<numberOfHWall;i++){//这里到下面几个for循环是(2)中的要求，要把墙的顺序拆散
      walls[i]=i+1;
    }
    int vindex=0;
    for( i=numberOfHWall;i<numberOfHWall+numberOfVWall;i++){
      walls[i]=-vindex;
      vindex=vindex+1;
    }

    for( i=numberOfVWall+numberOfHWall;i>0;i--){//进行交换
      int index=randInt(i);
      int temp=walls[i-1];
      walls[i-1]=walls[index];
      walls[index]=temp;
    }

    for( i=0;i<numberOfVWall+numberOfHWall;i++){
      int w=walls[i];
      if(w>0){
          int topX=(w-1)/(vert-1); //计算墙的坐标
          int topY=(w-1)%(vert-1);
          int downX=topX;
          int downY=topY+1;
          int topCell = topX + topY * horiz;//计算上下格子的位置
          int downCell = downX + downY * horiz;
          if(st.find(topCell)!=st.find(downCell)){
            hWalls[topX][topY]=false;
            st.union(st.find(topCell),st.find(downCell));
          }
      }else{
          int leftX=(-w)%(horiz-1);
          int leftY=(-w)/(horiz-1);
          int rightX=leftX+1;
          int rightY=leftY;
          int leftCell = leftX + leftY * horiz;
          int rightCell = rightX + rightY * horiz;
          if(st.find(leftCell)!=st.find(rightCell)){
            vWalls[leftX][leftY]=false;
            st.union(st.find(leftCell),st.find(rightCell));
          }
      }
    }

  }

```



Part II  (2 points)
-------------------
> You have probably noticed the similarity between your maze and a graph data
> structure.  Think of the cells of the maze as vertices of a graph.  Two
> adjacent cells are connected by an edge if there is no wall separating them.
> Our diagnose() method uses depth-first search to test that your maze is a tree.
>
> If the depthFirstSearch() method ever examines an "edge" and discovers a cell
> that has already been visited, then there is a cycle in the maze.  (The depth-
> first search implementation used here never walks back over an edge it's just
> traversed, so it won't look back and mistakenly diagnose a cycle.)  If some
> cell is not visited at all, then it is not reachable from the cell where the
> search started.  Hence, depth-first search can diagnose both potential
> deficiencies of a bad maze:  having more than one path between two cells, or
> having no path between two cells.  (You may want to look at the diagnose() and
> depthFirstSearch() methods to see how this is done.)
>
> In a plain-text file called GRADER, suggest (in simple English) how you could
> use depth-first search to generate a random maze (or more importantly, lots of
> different random mazes), without using disjoint sets at all.
>
> (a)  How would your algorithm ensure that there is a path between every pair of
>      cells, but no more than one path between any pair of cells (i.e., no
>      cycles)?
>
> (b)  How does your algorithm use random numbers to generate a different maze
>      each time?  Specifically, what decision should be made by random numbers
>      at each recursive invocation of the depth-first search method?
>
> These questions can be answered with just a few sentences.

 \1. Mark all walls true (unbroken)  
\2. **Randomly** choose a starting cell  
\3. Marked this cell visited  
\4. Loop (every direction that has an unbroken (true) wall **&&** through which you find an unvisited cell)  
-----4.1 **Randomly** choose one direction  
-----4.2 Go through that wall to another cell, mark the wall brokedn (false)  
-----4.2 Recursive DFS call on that cell  

*i. 显然通过marked visited可以保证生成的是树,没有circle; 这是DFS本身的特性;*  
*ii. 每个wall不管是true or false, 都代表一条edge, 所以这是个connected的图, 则DFS一定能遍历, 这也是DFS本身的特性;*  
*iii. 因为起点是randomly chosen, 每一步的方向也是randomly chosen, 所以应该就保证random的树生成了*  

i和ii保证了要求1  
iii I assume保证了要求2吧......

Submitting your solution
------------------------
> Change (cd) to your hw9 directory, which should contain GRADER, Maze.java, any
> other files your solution needs, and the set directory.  The set directory
> should contain DisjointSets.java.  You must submit the latter because you're
> allowed to change it.
>
> Include your name, login, and answer to Part II in GRADER.  Make sure it is
> just called GRADER, not GRADER.txt.  Make sure your homework compiles and runs
> on the _lab_ machines just before you submit.
>
> From your hw9 directory, type "submit hw9".  (Note that "submit" will not work
> if you are inside the set directory!)  After submitting, if you realize your
> solution is flawed, you may fix it and submit again.  You may submit as often
> as you like.  Only the last version you submit before the deadline will be
> graded.
