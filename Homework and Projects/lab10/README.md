# CS 61B Lab 10 April 3-4, 2014

> Goal:  to give you experience with general trees and encapsulation.  The trees
> we use in this lab use the SibTree data structure described in Lecture 24
> (which you should review now), and are encapsulated in the same manner as the
> Homework 5 lists (whose design was the subject of Lecture 19).  SibTrees are
> designed to ensure that the SibTree and SibTreeNode invariants (which are
> written out in their respective files) cannot be violated.



> Please make sure you have a partner for this lab.
>
> All the code is in the tree package.  You can compile it from your lab10
> directory with "javac -g tree/*.java".  Extensive test code is provided and
> can be run with "java tree.SibTree".
>
> Familiarize yourself with the fields and methods of the SibTree and SibTreeNode
> classes.  SibTree has two fields, one inherited from the Tree abstract class.

> int size;                 // The number of SibTreeNodes in the SibTree.
> 
> SibTreeNode root;         // The node that serves as the root of the tree
>
> SibTreeNode has six fields, two inherited from the TreeNode abstract class.
>
> Object item;			// item stored at SibTree
>
> boolean valid			// True if and only if this is a valid node
>
> SibTree myTree   		// The SibTree that contains this node
>
> SibTreeNode parent; 	//this node's parent 
>
> SibTreeeNode firstChild;	// this node's first (leftmost) child.
>
> SibTreeNode nextSibling;		//this node's next sibling to the right

> As with the Homework 5 lists, the Tree class defines certain nodes to be
> invalid.  In constrast to the Homework 5 lists, valid and invalid nodes are
> distinguished solely through the state of the "valid" field.  When a TreeNode
> is removed from a tree, it becomes invalid.  Methods like parent(), child(),
> and nextSibling() return an invalid node (never null!) if no such node exists.
> You may create an invalid node by calling the zero-parameter SibTreeNode()
> constructor.  You may test whether a node n is valid by calling
> n.isValidNode().
>
> Every valid SibTreeNode is in some tree, specified by the "myTree" field.
>
> Your task is to implement the parent(), insertChild(), and removeLeaf() methods
> of the SibTreeNode class.  After you write each one, you may use the test code
> to check your progress.



Part I:  Accessing a Node’s Parent (1 point)
--------------------------------------------
> Fill in the body of the parent() method in SibTreeNode.java.  parent() returns
> the SibTreeNode that is the parent of "this" SibTreeNode.  If "this" node is
> the root, return an invalid node.

> Throw an InvalidNodeException if "this" node is not valid.



```java
/**
   *  parent() returns the parent TreeNode of this TreeNode.  Throws an
   *  exception if `this' is not a valid node.  Returns an invalid TreeNode if
   *  this node is the root.
   */
  public TreeNode parent() throws InvalidNodeException {
  	if(ifValidNode()){
      if(this.equals(myTree.root)){
        return new SibTreeNode();
      }else{
        return parent;
      }
      }else{
        throw new InvalidNodeException();
    }
  }
```



Part II:  Inserting New Children (3 points)
-------------------------------------------
> Fill in the body of insertChild().  insertChild() takes two parameters:  an
> item and an integer c.  Create a new child that is the cth child (from the
> left) of "this" node, and references the item indicated.  Existing children
> numbered c or higher are shifted one place to the right to accommodate.  If
> c < 1, act as if c is 1.  If "this" node has fewer than c children, the new
> node is the last sibling.

> Don’t forget that SibTrees have a "size" field that needs to be updated.
>
> Throw an InvalidNodeException if "this" node is not valid.

insertnewchild()方法比较麻烦，需要分类讨论，容易出错，要非常仔细（我也是参考了别人的code）

```java
/**
   *  insertChild() inserts an item as the cth child of this node.  Existing
   *  children numbered c or higher are shifted one place to the right
   *  to accommodate.  If the current node has fewer than c children,
   *  the new item is inserted as the last child.  If c < 1, act as if c is 1.
   *
   *  Throws an InvalidNodeException if "this" node is invalid.
   */
  public void insertChild(Object item, int c) throws InvalidNodeException {
  	
    if(isValidNode()){
      if(c<=1){
        SibTreeNode sibNode = firstChild;
        SibTreeNode newSibNode=new SibTreeNode(myTree,item);
        firstChild=newSibNode;
        newSibNode.parent=this;
        newSibNode.nextSibling=sibNode;
      }else{
        SibTreeNode kid=firstChild;
        SibTreeNode preKid=null;
        int numberOfChildren=children();
        if(numberofChildren==0){
           SibTreeNode newSibNode = new SibTreeNode(myTree, item);
          firstChild = newSibNode;
          newSibNode.parent = this;
        }else{
          while(kid!=null&&c>1){
            preKid=kid;
            kid=kid.nextSibling;
            c--;
          }
          SibTreeNode newSibNode= new SibTreeNode(myTree,item);
          preKid.nextSibling=newSibNode;
          newSibNode.parent=this;
          newSibNode.nextSibling=kid;
        }
      }
      myTree.size++;
    }else{
      throw new InvalidNodeException();
    }
  }
```



BONUS Part III:  Removing a Leaf (1 bonus point)
------------------------------------------------
> Fill in the body of removeLeaf(), which removes "this" node from the tree if it
> is a leaf, and does nothing if it is not a leaf.  Upon completion, "this" node
> should be invalid.
>
> As always, throw an InvalidNodeException if "this" node is not valid.

```java
/**
   *  removeLeaf() removes the node at the current position from the tree if
   *  it is a leaf.  Does nothing if `this' has one or more children.  Throws
   *  an exception if `this' is not a valid node.  If 'this' has siblings to
   *  its right, those siblings are all shifted left by one.
   */
  public void removeLeaf() throws InvalidNodeException {
    // FILL IN YOUR SOLUTION TO PART III HERE.
    if(isValidNode()){
      if(firstChild==null){
        if(this.equals(myTree.root)){
          valid=false;
          myTree.root=null;
        }else{
          if(this.equals(parent.firstChild)){
              parent.firstChild=nextSibling;
              valid=false;
          }else{
            SibTreeNode cur= parent.firstChild;
            while(!(cur.nextSibling).equals(this)){
              cur=cur.nextSibling;
            }
            cur.nextSibling=nextSibling;
            valid=false;
          }
        }
       myTree.size--;
      }
    }else {
      throw new InvalidNodeException();
    }
  }
```


