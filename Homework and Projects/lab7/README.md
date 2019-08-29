# CS 61B  Lab 7 March 6-7, 2014

> Goal:  This lab will introduce you to gjdb, the Java debugger.

The Java Debugger Tutorial
--------------------------
> Welcome.  You now find yourself in the position of debugging somebody else’s
> bad code in DebugMe.java, which is supposed to compute the partial geometric
> series

> 1+1/2+1/4+1/8 (which equals 1.875)

> DebugMe.main() begins by calling the recursive method createGeomSeries(), which
> creates a linked list in which each node contains one term of the above series.
> Next, main() calls the recursive method listSum() to compute the sum of the
> nodes in the list.  As you may have guessed, it doesn’t work.  If you compile
> and run DebugMe you will see that it prints the value 1.0 instead of 1.875.

> Some documentation on the java debugger (gjdb) is available in the course
> reader and is also linked from the class Web page.  (The Java debugger provided
> with Oracle’s Java Development Kit is called jdb.  Professor Hilfinger modified
> it and built a better interface for it.)  Here is a summary of how you might
> use gjdb to track down the error in this program.

> (1)  Compile the program with the debugging switch ‘-g’.
>
> ​        javac -g DebugMe.java
>
> (2)  Instead of running java, you could run gjdb as follows.
>         gjdb DebugMe
>
> When you see the ‘[-]’ prompt, the debugger is ready to go, and you may
> follow the instructions in the course reader on how to use it.  However, rather
> than run gjdb from a shell, we recommend you run it from emacs.  Emacs will
> interpret the output of the debugger and show you where the debugger is in your
> .java files, which is extremely useful.  To begin, type ‘Meta-x gjdb’ from
> within emacs.  When asked to fill in the gjdb command, use ‘gjdb DebugMe’.  You
> should see a new window appear with the ‘[-]’ prompt.
>
> (3)  gjdb is a command-line driven program.  The most important command you
> should learn how to use is the (limited) on-line help; type help at the prompt.
>
> [-] help 
>
> If you see a command that looks like the one you want, but need more
> information, type ‘help <command>’, replacing <command> with the command you
> want to know more about.
>
> (4)  To run the program from within the debugger, type ‘run’ at the prompt.
>    [-] run
>
> This executes your program FROM THE BEGINNING.
>
> (5) The other most important command is ‘quit’.
>
>   when   gjdb is stuck, not giving you a command prompt, you may simply kill the
> emacs buffer in which gjdb is running.  If you ever need to restart gjdb, make
> sure your cursor is in the debugger window and type ‘C-x k’.
>
> (6)  Let’s see what’s happening within the main function.  So that we can do
> this, we’ll set a breakpoint at the beginning of main().
>
> [-] break DebugMe.main
>
> This tells the debugger to stop when execution reaches the beginning of the
> main method.  (You can also set breakpoints at specific line numbers.  If
> you’ve written small modular programs, you will rarely find that necessary.)
>
> Start the program by typing ‘run’.  When execution reaches the breakpoint,
> emacs will show the DebugMe.java file in a separate window with the current
> line indicated by an ‘=>’ arrow.
>
> (7)  At the beginning of the ‘main’ function, there’s no sign yet of the bug.
> Execute slowly through the code using either the ‘next’ command or the ‘step’
> command.  These two commands have a crucial difference:  ‘next’ executes one
> statement in full, including any method calls.  ‘step’ normally executes one
> statement, but if the statement includes a method call, execution stops at the
> first line of the called method.  Think of ‘step’ as ‘stepping into’ a method.
> In emacs, C-c C-s or the f5 key is shorthand for ’step’, and C-c C-n or the f6
> key is shorthand for ‘next’.
>
> Type f6 twice while watching the ’=>’ arrow in the other window.  The arrow
> should now point to the line that declares ‘sum’ and assigns it a value.
> This means that we are about to execute that line, but haven’t yet.
> (If you stepped too far, you can start over by typing ‘run’ again.)

这里上面主要介绍如何用emacs去debug 那么现在好像没看到过身边有人用emacs了。。。

划水lab

可以直接一些ide进行debug
其实bug很简单，一看就会发现 1/2 的结果不是0.5而是0
>
>
