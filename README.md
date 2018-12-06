# BootApp

#### 项目介绍
场地管理、场地预订管理、用户管理等功能。第一个可用版本，界面已经美化。 基于jdk1.8。

#### 软件架构
基于spring boot，使用spring security做安全校验


#### 安装教程
首先将sql文件夹下的sql语句执行完成，生成相应的表。<br>

数据库名暂时为test<br>
用户名cff<br>
密码123456<br>
更改需要重新打包

执行文件：
1. 简易方式<br>
java -jar BootApp_v1.0.jar
2. 更改端口<br>
java -jar BootApp_v1.0.jar --server.port=80

#### 使用说明
访问方式：<br>
1. 访问地址<br>
127.0.0.1:8012
2. 更改端口为80<br>
127.0.0.1
3. 更改端口其他<br>
127.0.0.1:其他

druid监控：<br>
&nbsp;&nbsp;&nbsp;&nbsp;提供druid监控，可以查看系统、访问及数据库的详情。<br><br>
&nbsp;&nbsp;地址：<br>
&nbsp;&nbsp;&nbsp;&nbsp;	访问ip:端口/druid <br>
&nbsp;&nbsp;&nbsp;&nbsp;	如 127.0.0.1:8012/druid或者  127.0.0.1/druid<br>
&nbsp;&nbsp;&nbsp;&nbsp;druid只有一个用户，登录使用admin/123456,更改需要重新打包
	

	

