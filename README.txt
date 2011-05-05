				External kanban Board for Jira
				------------------------------

This project provides a KanbanBoard view for the Jira Issue tracking system (http://www.atlassian.com/software/jira/)
It uses the following libraries and frameworks:

-GWT for the presentation layer
-GUICE as the Dependency Injection Engine
-Axis1.4 to communicated with Jira through SOAP (Note:  Axis2 wasn't used since it is still not well supported by Jira)


Installation
------------
All you should need for this plugin is ant.

	1.  Execute the ant task:  getWSDL.  If this task fails for any reason you can always retrieve your specific WSDL file by going to your jira installation URL:
	http://{host}:{port}/rpc/soap/jirasoapservice-v2?wsdl
	Where {host} and {port} change according to the URL of the jira installation.
	Example:  http://jira.atlassian.com/rpc/soap/jirasoapservice-v2?wsdl

	2.  Execute the ant task:  overwriteJiraSOAPFiles (be careful to not execute this task if you modify the SOAP classes generated from the WSDL file afterwards!)



Customisation  
---------------------

The 2 configuration files are:

1.  etc/dist/default.properties
-Contains global configuration parameters such as username and password used to connect to Jira, the URL to connect to Jira using SOAP, the configFile used to 
establish the columns and queries to access jira

2.  etc/dist/configExample.xml (Note:  the name of this file changes according to the value specified in the property:  "jira.kanban.configFile"
-This file establishes the columns that the kanbanBoard is going to have and defines the jira queries (using JQL:  "Jira Query Language") used to retrieve
the issues from Jira

Eclipse IDE
-----------
To checkout the project under eclipse the easiest way is to use egit (if you want to explore the power of git we recommend cygwin+git for Windows and git for Linux but you have to like using terminals ;))
1.  Use the Eclipse Update manager to install the EGit plugin from http://download.eclipse.org/egit/updates.  (Install both JGit and Eclipse Git Team Provider) 
2.  Select File -> Import -> Git -> Projects from Git, Press Next.  Click on the button Clone... (on the right).
3.  Under URI type the following:  git://github.com/jorgeandresvasquez/externalKanbanBoardForJira.git
-Leave all of the other options with their default values and type Next.
4.  Select the specfic branch that you want to work on, for now there are no branches so master should work fine.
Navigate to the directory where you want to save the local copy of the branch and click on Finish.

5.  Once the Git Repository has been created, select it and click Next.
6.  Select the Option:  "use the New Projects wizard" and click on Finish. 
7.  From the Select a wizard select the option:  Java Project.  In Location select the same location that you selected previously on step 4. and Click on Next.
8.  Change the Default output folder to war/WEB-INF/classes (you have to create the classes directory) and click on Finish.
9.  The project doesn't compile immediately as it needs to be configured as a GWT proyect.
10.  Install the GWT plugin for eclipse (follow instructions at:  http://code.google.com/eclipse/docs/getting_started.html).
11.  Right click on the project and select Google...Web Toolkit Settings...
12.  Check the option:  Use Google Web Toolkit and select Use Default SDK radio button.
13.  The project still won't compile because we need to generate the SOAP classes to be able to communicate with JIRA.  These are not under version control in GIT 
since they change according to the WSDL that is being used which changes according to the Jira version that is installed.  At this point execute the ant task:  overwriteJiraSOAPFiles.
Note:  The project comes with a default wsdl file, if you want to use your own version you can download it and put it under:  etc/dist/jirasoapservice-v2.wsdl.  (There's an ant task for this purpose too:  getWSDL, but it
requires you to define the property:  jira.soap.url.  This sometimes has issues if you're using https and in the end you'll have to download the wsdl manually and save it at etc/dist/jirasoapservice-v2.wsdl)     
14.  Refresh the project and it will compile at this point.
15.  There are some important configuration files that must be present under the classes folder, so next thing would be to run the ant task:  build.  This places the kanbanConfigFile (by default the application comes
with:  configExample.xml), the default.properties and KanbanConfig.xsd under src directory and eventually the compiler of Eclipse places them under classes directory.
16. Right click on the project and select the option:  Google...GWT Compile (This only has to be done once) 

Execution
---------

When running make sure that to append the id parameter with the respective kanbanConfiguration that you want to visualize.
Example:  http://127.0.0.1:8888/KanbanBoard.html?gwt.codesvr=127.0.0.1:9997&id=sampleKanbanConfig

Good external links related to the topics
-----------------------------------------

See a tutorial on creating a SOAP client in JIRA:

http://confluence.atlassian.com/display/JIRA/Creating+a+SOAP+Client

Good reference notes on Apache Axis:

http://axis.apache.org/axis/java/client-side-axis.html

On kanban:

http://en.wikipedia.org/wiki/Kanban

Good example of a very graphically-appealing kanban board:

http://www.crisp.se/kanban/kanban-example.pdf

Some examples and operators of the JQL (Jira Query Language):

http://confluence.atlassian.com/display/JIRA/Advanced+Searching
