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

To be able to run and debug the application from within Eclipse we recommend the usage of the GWT plugin.

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
