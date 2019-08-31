# CS 61B Homework 7 Due noon Wednesday, April 9, 2014

> This homework will give you practice with 2-3-4 trees.  This is an individual
> assignment; you may not share code with other students.
>
> Your task is to implement the insert operation for a 2-3-4 tree, in the file
> Tree234.java (in the dict
> no element is associated with each key.  Furthermore, the keys are ints.
> package).  For simplicity, the tree stores only keys
>
> The central two operations have the following prototypes.
>
> public boolean find(int key);
> public void insert(int key);
>
> find() returns true if the specified key is in the tree, and false otherwise.
> find() is already implemented for you; inspecting the code will help you
> understand the data structure better.  insert() inserts its parameter "key"
> into the tree.  Please implement the top-down insertion algorithm described in
> the Lecture 27 notes, and not the bottom-up insertion algorithm described by
> Goodrich and Tamassia.
>
> Because there is no object associated with each key, there is no reason to
> store duplicate keys.  Hence, if a key is found to already be in the tree,
> don’t insert another copy.
>
> A toString() method is also provided for 2-3-4 trees, and is useful for
> testing.  DO NOT CHANGE IT; the autograder will use it to check your trees.
>
> There’s also a printTree() method, which prints a 2-3-4 tree in an easier-to-
> read, but more space-consuming, tree-shaped manner (albeit sideways).  This
> method will not be tested, and you may change it to your liking.
>
> The Tree234Node class represents a node in a 2-3-4 tree, and has the following
> fields.
>
> int keys;
> int key1;
> int key2;
> int key3;
> Tree234Node parent;
> Tree234Node child1;
> Tree234Node child2;
> Tree234Node child3;
> Tree234Node child4;
>
> The "keys" field is the number of keys stored in the node, and must be 1, 2, or 3
>
> The fields "key1", "key2", and "key3" are filled in (in order) with the int
> keys stored in the node.  If keys == 1, the value of key2 doesn’t matter.  If
> keys <= 2, the value of key3 doesn’t matter.
> The "parent" field is the node’s parent.  The fields "child1" through "child4"
> are the node’s children, and are filled in in order from child1 to child4.  All
> four of these references must be set to null in a leaf node.  If keys == 1,
> child3 should be null, and if keys <= 2, the value of child4 should be null.
> You may not change any of these invariants regarding how a Tree234Node is
> represented, but you may add helper methods to the Tree234 and Tree234Node
> classes, and you may add new fields to the Tree234 class (but not the
> Tree234Node class) if it helps.  However, please make sure that the toString()
>
> methods and the find() method work correctly in their unmodified forms.  DO NOT
> CHANGE toString() or find().
>
> Test code is provided in the main method of the Tree234 class.  To compile,
> change (cd) to your hw7 directory and type "javac -g dict/*.java".  To run,
> type "java dict.Tree234".  The test code does not test whether you correctly
> update the tree’s "size" field; you’ll have to test that yourself.  The test
> code doesn’t test all cases; consider adding more test code if time permits.

做这个2-3-4 tree的时候深深感觉到自己的代码能力不够大佬，就只是一个小菜鸡，需要沉淀学习

在写代码以及复习之前一定要配合定义的属性看看2-3-4树的结构

我们在Tree234Node类中需要加一个方法```insertKey(int key)``` 

```java
 public void insertKey(int key){
    if(keys==1){
      if(key<key1){
        key2=key1;
        key1=key;
      }else{
        key2=key;
      }
    }else if(keys==2){
      if(key<key1){
        key3=key2;
        key2=key1;
        key1=key;
      }else if(key>key1&&key<key2){
        key3=key2;
        key2=key;
      }else{
        key3=key;
      }
    }
    keys++;
  }
```

接下来就是Tree234，首先是```find()```方法
```java
/**
   *  find() prints true if "key" is in this 2-3-4 tree; false otherwise.
   *
   *  @param key is the key sought.
   *  @return true if "key" is in the tree; false otherwise.
   **/
  public boolean find(int key) {
    Tree234Node node=root;
    while(node!=null){
      	if(key<node.key1){
          node = node.child1;
        }else if(key==node.key1){
          return true;
        }else if((node.keys==1)||(key<node.key2)){
          node=node.child2;
        }else if(key==node.key2){
          return true
        }
      		else if((node.keys==2)||(key<node.key3)){
          node=node.child3;
        }else if(key==node.key3){
            return true;
          }else {
            node=node.child4;
          }
    }
    return false;
  }
```

接下来就是Tree234中insert(int key)方法

注意点:Please implement the top-down insertion algorithm described in
the Lecture 27 notes

```java
 /**
   *  insert() inserts the key "key" into this 2-3-4 tree.  If "key" is
   *  already present, a duplicate copy is NOT inserted.
   *
   *  @param key is the key sought.
   **/
  public void insert(int key) {
    // Fill in your solution here.
    if(root==null){
      root=new Tree234Node(null,key);
      return;
    }
    Tree234Node node=root;
    if(node.keys==3){
      if(key<=node.key1){
        rearrangeNode(node);
      }else if(key<=node.key2){
        rearrangeNode(node);
      }else {
        node=rearrangeNode(node);
      }
    }
    while(node.child1!=null){
      if(node.keys==3){
        if (key <= node.key1) {
                    rearrangeNode(node);
                } else if (key <= node.key2) {
                    rearrangeNode(node);
                } else {
                    node = rearrangeNode(node);
                }
      }
      if (key <= node.key1) {
                node = node.child1;
            } else if ((node.keys == 1) || (key <= node.key2)) {
                node = node.child2;
            } else if ((node.keys == 2) || (key <= node.key3)) {
                node = node.child3;
            } else {
                node = node.child4;
            }
    }
    if (node.keys == 3) {
            if (key <= node.key2) {
                rearrangeNode(node);
            } else {
                node = rearrangeNode(node);
            }
        }
     node.insertKey(key);
     size++;
  }

    private Tree234Node rearrangeNode(Tree234Node node){
      if(node.parent==null){
        Tree234Node newRoot=new Tree234Node(null,node.key2);
        newRoot.child1=node;
        node.parent=newRoot;
        newRoot.child2=deleteNSplitNode(node);
        root=newRoot;
        return newRoot.child2;
      }else{
          node.parent.insertKey(node.key2);
          if(node.parent.child1==node){
              node.parent.child4 = node.parent.child3;
              node.parent.child3 = node.parent.child2;
              node.parent.child2 = deleteNSplitNode(node);
              return node.parent.child2;
          }else if(node.parent.child2==node){
               node.parent.child4 = node.parent.child3;
               node.parent.child3 = deleteNSplitNode(node);
               return node.parent.child3;
            } else if (node.parent.child3 == node) {
                node.parent.child4 = deleteNSplitNode(node);
                return node.parent.child4;
            } else {
                System.out.println("Error, Node is not child");
                return new Tree234Node(null, 0);
            }
      }

    }

    private Tree234Node deleteNSplitNode(Tree234Node node){
      Tree234Node newNode =new Tree234Node(node.parent,node.key3);
      node.keys=1;
      if(node.child1!=null){
        newNode.child1=node.child3;
        newNode.child2=node.child4;
        newNode.child1.parent=newNode.child2.parent=newNode;
        node.child3=node.child4=null;
      }
      return newNode;
    }
```

注意insert的时候要记得将树变形，保存balanced状态。
