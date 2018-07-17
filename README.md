# MemoServer
IDE：推荐使用IntelliJ IDEA  
Server：Tomcat 9.0.8  
Database：MySQL  
客户端程序：[MyMemo](https://github.com/sayssy/MyMemo)
****
#### 如何本地测试
+ 1 运行SQL文件夹下 [`CreateTables.sql`](https://github.com/JimmyRothric/MemoServer/blob/master/SQL/CreateTables.sql)代码，创建数据库。
+ 2 修改 `src > dao` 下 [`BaseDao.java`](https://github.com/JimmyRothric/MemoServer/blob/master/src/dao/BaseDao.java) 
`DriverManager.getConnection(URL, "root", "123456")`方法的第二个和第三个参数，分别改为本机MySQL的用户名和密码。
```
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, "root", "123456");
    }
```
+ 3 修改 `src > httpconnection` 下 [`HttpRequest.java`](https://github.com/JimmyRothric/MemoServer/blob/master/src/httpconnection/HttpRequest.java)
中的静态变量`ACCOUNT_SERVLET_URL_HEAD` 和 `MEMO_SERVLET_URL_HEAD`，ip为localhost，另外根据自己Tomcat配置，还需修改端口号。
```
    private static final String ACCOUNT_SERVLET_URL_HEAD = "http://localhost:8080/AccountServlet";
    private static final String MEMO_SERVLET_URL_HEAD = "http://localhost:8080/MemoServlet";
```
+ 4 运行 `src > servlet` 下任一 `Servlet`，然后运行 `src > test` 下的 `Test.java` 即可测试相关服务代码。

****
#### 如何部署到服务器
以阿里云CentOS 7为例
+ 1 安装配置MySQL，可参考[阿里云CentOS 7下配置及使用mysql](https://blog.csdn.net/zleiw/article/details/78242912)。
+ 2 安装配置jdk和Apache Tomcat。
+ 3 同上1，在服务器上创建数据库。
+ 4 同上2，修改 [`BaseDao.java`](https://github.com/JimmyRothric/MemoServer/blob/master/src/dao/BaseDao.java) 的参数为服务器MySQL的的用户名和密码。
+ 5 将项目打包成war包上传至服务器，部署在Tomcat，路径为 `..\apache-tomcat-9.0.8\webapps`，重启Tomcat服务器，  
可以输入`curl localhost:8080` 测试，若出现主页则部署成功。  
若使用IntelliJ IDEA将项目打包成war包  
注：本项目已添加 Web Application：Archive，可跳过第一步  
  1. `Project Structure> Artifacts` 点击绿色加号键，添加 Web Application：Archive，可以设置war包名称和输出地址，根据提示添加 META-INF  
  2. `Build > Build Artifacts...` 选择刚添加的Artifacts，即可将项目打包成war包，地址为上一步设置的输出地址  
+ 6 若需在IDE中测试服务，同上3，修改`ACCOUNT_SERVLET_URL_HEAD` 和 `MEMO_SERVLET_URL_HEAD` 的ip、端口号为服务器ip、端口号，再运行 `Test.java`，无需则可跳过。
