SpringData
==
## 1.传统方式访问数据库
    JDBC
        1. Connection
        2. ResultSet
        3. Statement
        4. TestCase
    SpringJDBCTemplate
    弊端分析
## 2.创建maven项目
1.导入依赖-pom.xml
   junit&&mysqlDriver
````androiddatabinding
<dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.38</version>
    </dependency>

````
## 3.数据表的准备
关系型的数据库mysql
````androiddatabinding
1. 创建一个数据库
ceate database spring_data;

2.切换到当前使用的数据库
use spring_data 

3.创建一张表

create table student(
    id int not null auto_increment,
    name varchar(20) not null,
    age int not null,
    primary key(id)
);

4.查看表的结构
  
  desc student;

5.往表中插入数据

insert into student(id,name,age) values(null,"xiaoming",10);
insert into student(id,name,age) values(null,"xiaohong",12);
insert into student(id,name,age) values(null,"xiaodong",18);
insert into student(id,name,age) values(null,"xiaolong",20);
  
````
6.开发JDBCUtil工具类
 
  1. 获取Connection,关闭Connection,Statement,ResultSet.
  2. 配置的属性放在配置文件里面,然后通过Properties.load(inputStream);就可以调用了.
  2. 释放Connect,ResultSet,Statement资源.

具体实现

1. 配置文件db.properties
````androiddatabinding
 jdbc.url=jdbc:mysql://192.168.25.131:3306/spring_data
 jdbc.user=root
 jdbc.password=root
 jdbc.driverClass=com.mysql.jdbc.Driver
````
2.实现类JdbcUtils
````androiddatabinding
/**
 * 1.获取Connection
 * 2.释放资源
 */
public class JdbcUtil {
    /**
     * 获取Connection
     * @return 所获得的JDBC的Connection
     */
    public static Connection getConnection() throws Exception {

        //采用Properties载入输入流
        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(is);

        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String driverClass = properties.getProperty("jdbc.driverClass");

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }
    
    /**
         * 释放DB相关资源
         * @param resultSet
         * @param statement
         * @param connection
         */
        public static void release(ResultSet resultSet, Statement statement,Connection connection){
            if (resultSet !=null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement !=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection !=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
        }
}

````
3.测试类
```androiddatabinding
public class JdbcTest {
    @Test
    public void testGetconnection() throws Exception {
        Connection connection = JdbcUtil.getConnection();
        Assert.assertNotNull(connection);
    }
}
```

## 4.Dao层开发

1.创建一个接口类JDBCDAO,只提供一个查询方法
```androiddatabinding
 List<Student> query();
```   
2.写出它的实现类JDBCDAOImpl
```androiddatabinding

  1.获取数据库的连接
  2.由数据库的连接获得一个PreparedStatement对象
  3.PreparedStatement执行SQL语句,返回结果集ResultSet
  4.判断结果集ResultSet是否有下一个节点
  5.生成一个Student对象把所查询到的结果.添到Student对象里面去
  6.返回对象集合.list.add(student)
```
Code:
```androiddatabinding
public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> query() throws SQLException {

        List<Student> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String sql = "select id,name,age from student";
        try {
             connection = JdbcUtil.getConnection();
             preparedStatement = connection.prepareStatement(sql);
             resultSet = preparedStatement.executeQuery();

            Student student = null;
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");

                student = new Student();
                student.setId(id);
                student.setAge(age);
                student.setName(name);
                list.add(student);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(resultSet,preparedStatement,connection);
        }
        return list;
    }

}
```
Code Test 
```androiddatabinding
  @Test
    public void testStudentQuery() throws SQLException {
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        List<Student> query = studentDAO.query();
        for (Student student : query) {
            System.out.println("ID:"+student.getId()+" "+"AGE:"+student.getAge()+" "+"NAME:"+student.getName());
        }

    }
```
  
## 5.Spring 提供的JdbcTemplate
 
 1. 导入Spring JDBC的依赖
 ```androiddatabinding
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>4.3.13.RELEASE</version>
    </dependency>

     <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-context</artifactId>
          <version>4.3.5.RELEASE</version>
        </dependency>
```
2. 导入bean的xml配置的表头
```androiddatabinding
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans.xsd">
</beans>

```

3.xml的配置
```androiddatabinding
解释:
DriverManagerDataSource 继承自AbstractDataSource

AbstractDataSource内置了下面这些成员变量
    1. private String url;
    
    2. private String username;
    
    3. private String password;
    
    4. private String catalog;
    
    5. private String schema;
    
    6. private Properties connectionProperties;
所以我们需要配置与其相关的东西
```
下面是详细配置:

```androiddatabinding
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://192.168.25.131:3306/spring_data"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>
    
    <!--Spring JDBC Template信息-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
     </bean>
</beans>
```    

#### 关于xml配置的单元测试

```androiddatabinding

@Before
创建方法

@Test
测试方法

@After
销毁方法

三者的执行顺序是从前往后的.
```
Code:

```androiddatabinding

```

#### 