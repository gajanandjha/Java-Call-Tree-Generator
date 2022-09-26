# Java Call Tree Generator

Generates Call Tree of all the methods visited. 

## Getting started

Start the Server in debug mode on debug port 40109. 

Download the 3 files from this repository.

Run below command on the server where Java application is running

```
javac -cp .:$JAVA_HOME/lib/tools.jar JDIDebugger.java
java -cp .:$JAVA_HOME/lib/tools.jar JDIDebugger > calltree.txt
```
Run the flow on the server that you want to trace and press control-C on the above Java process when flow has completed execution. Put a classname in below command (without .class) that you know gets involved when you run the flow. This is just to get the correct thread from the server trace logs and generate the tree for that thread only.

```
grep YourClassNameHere calltree.txt | head -1 | cut -d"^" -f2 | sed 's/[[]/\\[/g' | sed 's/[]]/\\]/g' | xargs -0 -I{} grep {} calltree.txt | cut -d"^" -f1,3 | sed -E 's|(Entry\^)(.*)$|<li><span class="caret">\2<\/span><ul class="nested">|gm' | sed -E 's|(Exit\^)(.*)$|</ul></li>|gm' > body.txt

cat header.txt body.txt footer.txt > calltree.html

gzip calltree.html
```

Download calltree.html.gz, unzip and view in browser
