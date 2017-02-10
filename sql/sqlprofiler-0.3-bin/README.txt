SQL Profiler v.0.3
by Serge Huber.
(c) 2003 Jahia Ltd. All Rights Reserved.

Introduction
------------

This is a quickly hacked tool to do statistics on SELECT, INSERT, UPDATE 
and DELETE queries in order to know where it is most efficient to create 
indexes. It can also display real-time graphics that give an idea of 
database load. Profiling results may also be saved to a CSV file.

Requirements :
--------------
- JDK 1.4 or more recent (JDK 1.3 will require additional XML jars)
- 70MB RAM

Binary packages
---------------

This software is available in a pre-compiled binary form. If you are
downloading the binaries you can skip the compiling steps described here below.

Compiling
---------

1. Copy the file lib/antlr.jar to ANT_HOME/lib
2. Launch ant by typing :
    ant jar
   This builds a jar file in build/dist called sqlprofiler.jar

Compiling with JBuilder
-----------------------

This project relies on ANTLR to generate some SQL parsing Java source files that
are then used by the JBuilder project during compilation. So in order to
compile with JBuilder you first have to generate the Java parsing files by
running :

    ant antlr

JBuilder project files are available in the following directory :

    metadata/jbprofiler/SQLProfiler

Running the GUI SQL Profiler with P6Spy
---------------------------------------

First you must install P6Spy (www.p6spy.com) onto the system you want to profile.
In order to help you with the configuration a sample spy.properties is provided,
as well as the currently supported p6spy.jar library available in the lib/ 
directory.

STEP 1 : Configure P6Spy as a JDBC driver in your application.

The first step is to install P6Spy into your application. P6Spy is a great 
pass-thru JDBC driver that enables you to log any requests passing through a 
JDBC connection so it is compatible with a lot of systems. This makes this 
SQL profiler compatible with as many systems. P6Spy installs in your application 
just like any JDBC driver does.  Simply replace the JDBC driver class with the 
following : 

com.p6spy.engine.spy.P6SpyDriver

You do not need to change the database URL. P6Spy will pass that directly to the
real driver. What is left in this step is to configure P6Spy to tell it which is 
the real database driver you want to connect to. Copy the supplied spy.properties
file into your classpath (in a web application this means in the WEB-INF/classes
directory, in a standard Java application this means either in a JAR or somewhere
in the classpath (if you don't understand what this means check the JDK's 
documentation for explanations about classpaths). Modify the spy.properties file
to set the appropriate value for the "realdriver" property. This will most likely
be the original JDBC driver class name that you had configured in your application.

You then need to copy the lib/p6spy.jar to the classpath too. In a web application,
this means in the WEB-INF/lib directory, and in an application is means somewhere
on the CLASSPATH environment variable value.

STEP 2 : Launch the SQL Profiler

The SQL Profiler listens to a socket for log4j (http://jakarta.apache.log/log4j)
events. The GUI code was based on the Chainsaw GUI for Log4J so much of the
logging configuration is the same. You must launch the GUI BEFORE launching the
application that you want to analyze.

The profiler produces results when the "Pause" button is clicked. By default the
profiler is active, collecting data.

To launch the GUI simply type :

    java -jar sqlprofiler.jar
    
OR if you have an OutOfMemory error, you can increase the memory allocated to 
the JVM according to the size of your RAM.

    java -Xmx256m -jar sqlprofiler.jar

STEP 3 : Launch your application.

Before launching your application (or your application server), make sure the 
SQL Profiler has appeared on your screen. Once you see the window appear you may
start your application. The SQL Profiler will be connected to the P6Spy logger 
driver once the first JDBC access happens, which might be a while.

Using the GUI
-------------

As the GUI opens a socket in listening mode, you must always start the SQL
Profiler GUI before whatever application you want to analyze. So for example
if you want to analyze the traffic between a web application running under
Tomcat and that communicates with a database via JDBC, you must start the
SQL profiler before starting Tomcat (or before the web app starts initializing).

The GUI automatically starts in "display" mode, which means that as soon as
the connection is establised with the P6Spy source, the display will be
updated every second, displaying the new requests that are being sent to the
database. You may click on a request in the "Profile" view to have a more 
detailed display of a request in the bottom window.

The "Play" button is deactivated upon startup because we are already in "play"
mode. If you click on the "Pause" button, the profiler will display the
statistics for all the request accumulated since either the GUI's startup or
the last "reset" (more on that later). In the lower pane you will see the
statistics for all the requests that are displayed in the upper pane. While in
"pause" mode the display is no longer updated, but the requests are still being
processed in the background. Pressing the "Play" button will enable the display
updating again, and you will see the requests being updated as they come in
every second. Note that when going back to "Play" mode the profile pane still
displays the last "pause" results, in order to keep them visible to see the
statistics longer.

The "report" button (fifth from the left) creates a CSV file with the data 
stored in the profile result table. The file format is :
<%>,<Time(ms)>,<count>,"<Table(s)>","<Column(s)>","<Query prepared if exist or Query SQl>"

The "reset" button (second from the left) purges all accumulated requests from
the memory. This is practical to purge the buffer and analyse specific database
request sequences. For a web application you could do the following :

1. Start the GUI
2. Start the webapp, in starts doing some JDBC requests we will ignore
3. Press the "reset" button on the GUI
4. Make a request to the webapp
5. Press the "pause" button after the request has finished executing
6. Press the "report" button to save profiling results as a CSV file

By doing this we will see the statistics accumulated only during the request to
the web application. Also don't worry about being too precise for the "start"
and "stop" time, as the total time is calculated based on the total time for
the total of JDBC requests rather than the actual total time. In effect we get
statistics about the percentage of slower requests rather than the total
application processing time (which is better left at a Java Profiler tool, this
is only for the moment an SQL profiler after all :)).

The last important button is the one on the left, which enable the user to
create SQL CREATE INDEX files automatically generated based on the statistics
displayed in the Profile window. In order for this to create a file, a previous
"pause" must have been performed, otherwise the file generated will be empty.

If we continue the previous web application example, we could add the following
step :

6. Press the "Save Indexes As" button, enter a file name such as indexes.sql,
and save the file. If you open that file with a text editor you should see lines
such as these :

CREATE INDEX jahia_pages_def_index ON jahia_pages_def ( id_jahia_pages_def );
CREATE INDEX jahia_pages_def_index2 ON jahia_pages_def ( name_jahia_pages_def );

You can the feed the resulting file to your favorite database, reviewing and
modifying it first in case some of the indexes already existed.

Starting with version 0.3, the SQL Profiler introduces the parsing of INSERT,
UPDATE and DELETE queries in profile results, permit to save profile results 
as CSV file.

Starting with version 0.2, the SQL Profiler introduces a new "Analysis" view, 
that shows evolution of requests over time. Both these graphics are updated 
in real-time as the requests come in. If you have a database that is generating
a lot of requests, you might want to clear the data stored in the SQL Profiler
from time to time otherwise the CPU usage will increase as more data is being
processed and this might have a serious impact on analysis, especially if the
application is running on the same machine as the SQL Profiler (I highly 
recommend running them on seperate machine if possible. Check your CPU load to
see if the profiler is interfering with the application).

The first graph shows the number of request per second, seperating them into 
requests type. Here are some details about the first graph's legends : 

- Queries / second : this is the total amount of queries, both going to the 
database, and responses coming back (mostly result sets).
- Statements / second : this is mostly statements sent to the database, which
might be of type INSERT, SELECT, DELETE, UPDATE, etc... 
- Results / second : these are result rows that come back from the database and
are read by the application. The unit here is a result row.
- SELECT statements / second : this counts the number of SELECT statements per
second. In an application that mostly consults data, this will be very close 
to the "Statements / second" line.

The second graph is for the moment highly experimental. In no case should be 
considered an "exact" graph. It gives an indication of the amount going to and
coming from the database. Actually the SQL Profiler here simply uses the logged
database events and uses the string size to calculate the number of bytes per
second. Unfortunately this is not entirely correct as it does not reflect the 
REAL amount of bytes to/from the database. Maybe in the future P6Spy can be 
extended to actually report this, but in the meantime this graph should be 
more considered as an indicator that a real measurement device. Anyway it is 
already quite useful despite these restrictions as it gives a good idea of the
ratio between outgoing traffic (meaning data from your application to the 
database) versus incoming traffic (from the database to your application).

License
-------

This software is licensed under the terms of the Jahia Open Software License,
Version 1.1, with parts licensed under the The Apache Software License,
Version 1.1. Please check the LICENSE.txt and APACHE.txt text file for more
details about the licenses.

Credits
-------

The original GUI code for this application is loosely based on the Apache
Foundation's Jakarta Log4J Chainsaw GUI. So all my thanks go to the original
contributors of this code, notably :

Oliver Burn
Ceki Gülcü
Mark Womack

All the new messy code and bugs are entirely to blame on :

Serge Huber

By thanks to the following contributor :

Jean-Philippe VALENTIN

ENJOY !