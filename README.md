# BootApp

#### Project Description

Site management, site reservation management, user management and other functions. The first available version, the interface has been landscaping. Based on jdk1.8.

#### Software framework
Based on spring boot，using spring security to do the security check.


#### Installation Tutorial
First,execute the sql statement under sql folder, generate the corresponding table<br>

temporary database name: test<br>
Username:cff<br>
password:123456<br>
need repackage after change this.

Execute file：
1. Easy way<br>
java -jar BootApp_v1.0.jar
2. Change port<br>
java -jar BootApp_v1.0.jar --server.port=80

#### Instructions for use
the way of visiting：<br>
1. visiting address<br>
127.0.0.1:8012
2. change the port to 80<br>
127.0.0.1
3. change the port to other number<br>
127.0.0.1:other number

druid monitoring：<br>
&nbsp;&nbsp;&nbsp;&nbsp;providing druid monitoring，can check the details of system, visiting and database<br><br>
&nbsp;&nbsp;address：<br>
&nbsp;&nbsp;&nbsp;&nbsp;	visiting ip:port/druid <br>
&nbsp;&nbsp;&nbsp;&nbsp;	if 127.0.0.1:8012/druid or  127.0.0.1/druid<br>
&nbsp;&nbsp;&nbsp;&nbsp;druid has only one user，using admin/123456 when logging in,need to repackage after change this.
	

	


	

	

