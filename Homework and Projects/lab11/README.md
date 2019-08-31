# CS 61B Lab 11 April 10-11, 2014

> Please make sure you have a partner for this lab.
>
> Familiarize yourself with the fields and methods of the BinaryTree and
> BinaryTreeNode classes.  BinaryTree is an implementation of the Dictionary
> interface (which you also used in Homework 6) as a binary search tree.
> Note that BinaryTree uses Comparable objects as keys (since it is an ordered
> dictionary), whereas in the Dictionary abstract class, keys are declared as
> plain Objects.  Thus you will occasionally need to cast Objects to Comparables.

Part I:  Finding an Element in a Binary Search Tree (2 points)
--------------------------------------------------------------
> Complete the implementation of the find() method in dict/BinaryTree.java by
> filling in the body of findHelper().  find() takes a key as its single
> parameter, and returns an element associated with that key, or null if there is
> none.  (If there are several elements associated with a key, it doesn’t matter
> which one is returned.)  findHelper() helps by recursively finding and
> returning a node that contains the key (or null if no such node exists).
> Take a look at insertHelper() for inspiration.  find() should run in O(d) time
> on a tree with depth d.

```java
/**
   *  Search for a node with the specified key, starting from "node".  If
   *  a matching key is found (meaning that key1.compareTo(key2) == 0), return
   *  a node containing that key.  Otherwise, return null.
   *
   *  Be sure this method returns null if node == null.
   **/

  private BinaryTreeNode findHelper(Comparable key, BinaryTreeNode node) {
    // Replace the following line with your solution.
    if (node != null) {
      if (key.compareTo(node.entry.key()) == 0) {
        return node;
      } else if (key.compareTo(node.entry.key()) < 0) {
        return findHelper(key, node.leftChild);
      } else {
        return findHelper(key, node.rightChild);
      }
    }
    return null;
  }
```

Part II:  Removing an Element with a Given Key (2 points)
---------------------------------------------------------
> Fill in the body of remove() method in BinaryTree.java.  remove() takes a key
> as its single parameter, and removes one item having that key if the tree
> contains one.  (If there are several elements associated with a key, it doesn’t
> matter which one is removed and returned.)  remove() returns the same value
> that find() returns, but should not call find().  However, remove() SHOULD use
> the findHelper() method you wrote for Part I.
> remove() should run in O(d) time if the depth of the tree is d.

```java
 /** 
   *  remove() searches for an entry with the specified key.  If such an entry
   *  is found, it removes the Entry object from the Dictionary and returns it;
   *  otherwise, it returns null.  If more than one entry has the key, one of
   *  them is chosen arbitrarily, removed, and returned.
   *
   *  @param key the search key.  Must be of a class that implements
   *         java.lang.Comparable.
   *  @return an Entry referencing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/
  public Entry remove(Object key) {
    BinaryTreeNode node=finderHelper((Comparable)key,root);
    if(root =null){
      return null;
    }else{
      Entry entry=node.entry;
      if(node.leftChild==null&&node.rightChild==null){//如果是叶子节点
        if(node==root){//如果是根节点
          root==null;
        }else if(node.parent.leftChild=node){//如果是左子节点
          node.parent.leftChild=null;
        }else{//如果是右子节点
          node.parent.rightChild=null;
        }
      }else if(node.leftChild==null||node.rightChild==null){//如果有一个子节点
        if(node.leftChild==null){
          removeRightNode(node,node.rightChild);
					}else {
          removeLeftNode(node,node.leftChild);
        }
      }else{//内部结点，并且有两个子节点
        if(node.rightChild.leftChild==null){//右子节点是删除结点中的最小值
          node.entry=node.rightChild.entry;
          if(node.rightChild.rightChild!=null){
            node.rightChild.rightChild.parent=node;
          }
          node.rightChild=node.rightChild.rightChild;
        }else{
           BinaryTreeNode minRightNode=findMinRightNode(node.rightChild);
          node.entry=minRightNode.entry;
          if(minRightNode.rightChild==null){
            minRightNode.parent.leftChild=null;
          }else{
             minRightNode.rightChild.parent=minRightNode.parent;
              minRightNode.parent.leftChild=minRightNode.rightChild;
          }
        }
      }
      size--;
      reutrn entry;
    }
  }

 private BinaryTreeNode findMinRightNode(BinaryTreeNode node){
    while (node.leftChild != null) {
      node = node.leftChild;
    }
    return node;
  }

  private void removeRightNode(BinaryTreeNode node,BinaryTreeNode child){
    if(node==root){
      root=child;
      child.parent=null;
    }else{
      node.parent.rightChild=child;
      child.parent=node.parent;
    }
  }

  private void removeLeftNode(BinaryTreeNode node,BinaryTreeNode child){
    if(node==root){
      root=child;
      child.parent=null;
    }else{
      node.parent.leftChild=child;
      child.parent=node.parent; //一定要注意child和node结点不能是null
    }
  }
```

当我们要删除的结点是有两个子节点的时候，如果我们发现该结点的右子节点的左子节点为空，说明该节点的右子结点就是比该节点entry值大的最少的结点。那么我们可以直接将该节点的enty更改为右子结点的entry。

Check-off
---------
> Show your TA or Lab Assistant the code you have written, and run the test
> program.  You’ll receive points for each part that runs correctly.
> 2 points:  If find() works correctly.
> 1 point:   If remove() works for nodes that have no child or one child.
> 1 point:   If remove() works for nodes that have two children.
