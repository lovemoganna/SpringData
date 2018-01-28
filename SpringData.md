SpringData
==

## 1.maven依赖
````androiddatabinding
<!--SpringData-->
    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-jpa</artifactId>
      <version>1.8.0.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-entitymanager</artifactId>
      <version>4.3.6.Final</version>
    </dependency>
````

## 2.书写beans.xml

```androiddatabinding
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:cache="http://www.springframework.org/schema/cache"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context-3.1.xsd
          http://www.springframework.org/schema/aop
          http://www.springframework.org/schema/aop/spring-aop-3.1.xsd
          http://www.springframework.org/schema/tx
          http://www.springframework.org/schema/tx/spring-tx-3.1.xsd
          http://www.springframework.org/schema/cache
          http://www.springframework.org/schema/cache/spring-cache-3.1.xsd
          http://www.springframework.org/schema/data/jpa
          http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">

    <!--1.配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://192.168.25.131:3306/spring_data"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <!--2.配置EntityManagerFactory-->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="jpaVendorAdapter" value="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
        <!--配置扫描包-->
        <property name="packagesToScan"  value="com.springdata"/>
        <!--jpa的一些属性-->
        <property name="jpaProperties">
            <props>
                <!--ejb语言的策略-->
                <prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
                <!--数据库的方言采用的是mysqlInnoDB存储引擎-->
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
                <!--执行显示SQL-->
                <prop key="hibernate.show_sql">true</prop>
                <!--自动格式化SQL-->
                <prop key="hibernate.format_sql">true</prop>
                <!--根据实体自动创建表-->
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>
    </bean>
</beans>
```
## 3.书写实体类

````androiddatabinding
需要三个注解:

1.在类上使用@Entity,声明这个类是实体类
2.在get方法上用@ID标识该属性为主键
              @GeneratedValue产生策略是自增
````

实体类:
```androiddatabinding
package com.springdata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author: ligang
 * date: 2018/1/25
 * time: 11:49
 */
@Entity
public class Employee {

    private Integer id;
    private String name;
    private Integer age;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

```

## 4.自动建表的实现

````androiddatabinding

修改表中某一字段的长度
drop table employee;

在实体类上的那个修改的成员变量的get方法上加上@Column(length=20)就可以改变它的长度了.一般字符串类型的长度默认是255.
````

测试类:
````androiddatabinding
 @Before
    public void testBefore() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        System.out.println("先获得配置文件");
    }
    @Test
    public void testSpringDataJpa(){
        System.out.println("我要自动建表了-------------");
    }
````

## 5.beans-new.xml其他配置

````androiddatabinding
<!--3.配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!--4.配置支持注解的事务-->
    <tx:annotation-driven transaction-manager="transactionManager"/>

    <!--5.配置SpringData-->
    <jpa:repositories base-package="com.springdata" entity-manager-factory-ref="entityManagerFactory"/>
    <context:component-scan base-package="com.springdata"/>
````

## 6.测试上面的那些配置

实现类:
```androiddatabinding
package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.repository.Repository;

/**
 * @author: ligang
 * date: 2018/1/25
 * time: 16:06
 */
public interface EmployeeRepository extends Repository<Employee, Integer> {
    /**
     * Respository里面的那两个参数就是:1.实体类 2.主键的ID(类型)
     * @param name
     * @return
     */
     public Employee findByName(String name);
}
```
测试类:
```androiddatabinding
package com.luoyupiaoshang.springdatatest;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: ligang
 * date: 2018/1/25
 * time: 16:11
 */
public class SpringDataTest {
    private ClassPathXmlApplicationContext context;
    private EmployeeRepository employeeRepository;
    
    /**
        先装载配置
    */
    @Before
    public void Setup(){
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeRepository = context.getBean(EmployeeRepository.class);
        System.out.println("setup");
    }
     /**
          执行测试
      */
    @Test
    public void testFindbyName(){
        Employee employee = employeeRepository.findByName("xiaoming");
        System.out.println("ID:"+employee.getId()+"姓名:"+employee.getName()+"年龄:"+employee.getAge());
    }
    /**
        销毁
     */
    @After
    public void tearDown(){
        context=null;
        System.out.println("tearDown");
    }
}

```

分析:
```androiddatabinding
Hibernate: 
    select
        employee0_.id as id1_0_,
        employee0_.age as age2_0_,
        employee0_.name as name3_0_ 
    from
        employee employee0_ 
    where
        employee0_.name=?
        
上面是测试执行产生的SQL语句
可以看出它查询的时候,查询了3个字段,并且给employee起来个别名employee0_ 
并且是根据name字段来查询的.

因为我们的配置设置了执行产生的SQL语句了.
即:
 <!--执行显示SQL-->
<prop key="hibernate.show_sql">true</prop>
<!--自动格式化SQL-->
<prop key="hibernate.format_sql">true</prop>
```

但是值得注意的是:
```androiddatabinding
查询的时候的方法的名字,不能随心所欲的起了.
比如你要把findByName()方法改成test()方法,他就执行不成功了.
所以,代码必须按照要按照一定的规则来设置.
```
##7.关于Repository核心接口的认识

CrudRepository:就是执行save/insert/delete操作.

PagingAndSortingRepository:分页和排序:每页的显示和按照关键字的排序

jpaRepository:jpa的一些东西

jpaSpecificationExecutor:一些其他的东西

Repository接口源码:
```androiddatabinding
package org.springframework.data.repository;

import java.io.Serializable;

public interface Repository<T, ID extends Serializable> {
}
```
#### 1.标记接口
````androiddatabinding
可以看出里面就一个空接口,没提供任何方法.那Repository就是一个标记接口
T就是你的目标实体类,
ID就是那个实体类当中的ID(主键)的类型
````
类似的,我们可以看一下Serializable接口的形式:
````androiddatabinding
public interface Serializable {
}
他没有任何方法,
同样的Serializable也是一个标记接口.
````               
#### 2.自定义接口使用的过程

```androiddatabinding
 之前使用的那个接口.
 public interface EmployeeRepository extends Repository<Employee, Integer>{
        employeeRepository.findByName(String name);
 }
 
```
他在测试类中的使用
```androiddatabinding
context = new ClassPathXmlApplicationContext("beans-new.xml");
EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
Employee employee = employeeRepository.findByName("xiaoming");
```
我们可以打印出来看一下这个employeeRepository
```androiddatabinding
org.springframework.data.jpa.repository.support.SimpleJpaRepository@2bfaba70
显示这个就说明你定义的那个接口EmployeeRepository已经被Spring容器所管理,可以被使用了.
```

#### 3.如果自定义接口没有继承官方提供的Repository<T,ID>接口,会出现错误
```androiddatabinding
就是下面这个错误
org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.springdata.repository.EmployeeRepository' available
此类型的Bean无效,说明自定义的接口没有被Spring容器管理.

此时我们可以使用@RepositoryDefinition(domainClass,idclass)
注解的源码如下:
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RepositoryDefinition {
    Class<?> domainClass();

    Class<? extends Serializable> idClass();
}

```
所以我们可以下列的设置:
```
@RepositoryDefinition(domainClass = Employee.class,idClass = Integer.class)
public interface EmployeeRepository {
    /**
     * extends Repository<Employee, Integer>
     * Respository里面的那两个参数就是:1.实体类 2.主键的ID(类型)
     * @param name
     * @return
     */
     public Employee findByName(String name);
}
```
### 8.Repository的子接口
CrudRepository:继承Repository,实现了CRUD的相关方法
![](http://upload-images.jianshu.io/upload_images/7505161-0b283ab8f5c168b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](http://upload-images.jianshu.io/upload_images/7505161-c1f6c80553a1313c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

jpaRepository:继承了PagingAndsortingRepository,实现了JPA规范的相关方法.

![](http://upload-images.jianshu.io/upload_images/7505161-87bdc782bde6550d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

PagingAndSortingRepository:继承CrudRepository,实现了分页排序的相关的方法.

![](http://upload-images.jianshu.io/upload_images/7505161-820c87ab0c846938.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

分为两个方法:1.可以根据具体的字段来排序2.第二个是分页的类
![](http://upload-images.jianshu.io/upload_images/7505161-1149625b935d0742.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 9.Repository中查询方法定义规则和使用

 1. 了解SpringData中查询方法名称的定义规则
 2. 使用SpringData完成复杂查询方法的命名
 
 
![](http://upload-images.jianshu.io/upload_images/7505161-12d94ed91d010f92.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](http://upload-images.jianshu.io/upload_images/7505161-87f22b1868632f24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

分析:
```
关键字         具体方法        翻译成的SQL语句
如果不按上弄规则来写,会出现异常.
```

下面插入数据,进行测试

````
insert into employee(name,age) values("test1",20)
insert into employee(name,age) ]values("test2",21)
insert into employee(name,age) values("test3",22)
insert into employee(name,age) values("test4",23)
insert into employee(name,age) values("test5",20)
insert into employee(name,age) values("test6",21)
insert into employee(name,age) values("test7",22)
insert into employee(name,age) values("test8",23)
````

**按照特定命名的方法规则来进行查询**

EmployeeRepository接口
````androiddatabinding
/**
 * @author: ligang
 * date: 2018/1/25
 * time: 16:06
 */
@RepositoryDefinition(domainClass = Employee.class,idClass = Integer.class)
public interface EmployeeRepository {
    /**
     * extends Repository<Employee, Integer>
     * Respository里面的那两个参数就是:1.实体类 2.主键的ID(类型)
     * @param name
     * @return
     */
     public Employee findByName(String name);

    /**
     * where name like ?% and age <?
     * name以XXx开始 && age<xxx
     * @param name
     * @param age
     * @return 符合条件的employee列表
     */
    public List<Employee> findByNameStartingWithAndAgeLessThan(String name, Integer age);
    /**
     * where name like %? and age <?
     * name以XXX结束&& age<xxx
     * @param name
     * @param age
     * @return
     */
    public List<Employee> findByNameEndingWithAndAgeLessThan(String name, Integer age);

    /**
     * where name in(?,?......)or age < ?
     * name在某个集合范围之内,或者age<xxx
     * @param name
     * @param age
     * @return
     */
    public List<Employee> findByNameInOrAgeLessThan(List<String> name,Integer age);
    /**
     * where name in(?,?......)and age < ?
     * name在某个集合范围之内,并且age<xxx
     * @param name
     * @param age
     * @return
     */
    public List<Employee> findByNameInAndAgeLessThan(List<String> name,Integer age);
````

测试类
```androiddatabinding
  @Test
    public void test1(){
       /* System.out.println(employeeRepository);
        employee = employeeRepository.findByName("xiaoming");*/
        /*List<Employee> list = employeeRepository.findByNameEndingWithAndAgeLessThan("ong", 30);*/

        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        List<Employee> employees = employeeRepository.findByNameInOrAgeLessThan(list, 22);
        for (Employee employee : employees) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test2(){
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        List<Employee> employees = employeeRepository.findByNameInAndAgeLessThan(list, 22);
        for (Employee employee : employees) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
```
### 8.Query注解的使用

1.在Repository方法中使用,不需要遵循查询方法命名规则.

2. 需要将@Query定义在Repository中的方法之上即可.

3. 命名参数及索引参数的使用.

4. 本地查询


实现Code:

````androiddatabinding
 /**
     * select t1 from Employee t1
     * 里面的Employee是类名
     * @return
     */
    @Query("select o from Employee o where id=(select max(id) from Employee t)")
    public Employee getEmployeeByMaxId();

    /**
     * ?是占位符
     * @param name
     * @param age
     * @return
     */
    @Query("select o from Employee o where o.name=?1 and o.age=?2")
    public List<Employee> queryParams(String name,Integer age);

    /**
     *  :name
     *  与@Param("name")
     *  :后面的内容要和@Param()里面的内容一致.
     * @param name
     * @param age
     * @return
     */
    @Query("select o from Employee o where o.name=:name and o.age=:age")
    public List<Employee> queryParams2(@Param("name") String name,@Param("age") Integer age);

    /**
     * 模糊查询:除了关键字不一致,其他都一样
     * @param name
     * @return
     */
    @Query("select o from  Employee o where o.name like %?1%")
    public List<Employee> queryByLike(String name);

    /**
     * 用 :name 取代 ?1,但是需要在参数前面加上@Param("name")
     * @param name
     * @return
     */
    @Query("select o from Employee o where o.name like %:name%")
    public List<Employee> queryByLike2(@Param("name") String name);

    /**
     * 本地查询的实现
     * @return
     */
    @Query(nativeQuery = false,value = "select count(1) from Employee")
    public Long getCount();
````

测试Code:

```androiddatabinding
 @Test
    public void test3(){
        Employee employee = employeeRepository.getEmployeeByMAxId();
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
    }

    @Test
    public void test4(){
        List<Employee> list = employeeRepository.queryParams("xiaoming", 10);
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test5(){
        List<Employee> list = employeeRepository.queryParams2("xiaoming", 10);
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test6(){
        List<Employee> list = employeeRepository.queryByLike2("test");
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }

    /**
     * 本地查询的测试
     */
    @Test 
    public void test7(){
        Long count = employeeRepository.getCount();
        System.out.println(count);
    }
```
#### 9.更新及删除操作整合事务的使用

@Modifying注解使用:加上表示允许修改值
@Modifying结合@Query注解执行更新操作.
@Transaction在事务中的使用

但是事务一般是在Service层中发生的.一个Service里面可能有多个Dao的调用,必须保证多个dao在同一个事务里面,
比如说银行存钱,取钱的操作.事务一定要保证准确.


更新操作&&删除操作:
```androiddatabinding
 /**
     * 再次注意Employee是类名,不是表名.
     * @param name
     * @param id
     */
    @Modifying
    @Query("update Employee o set o.name = :name where o.id =:id")
    public void updateNameById(@Param("name") String name,@Param("id") Integer id);

    /**
     * 删除操作
     * @param id
     */
    @Modifying
    @Query("delete from Employee o where id = :id")
    public void deleteById(@Param("id")Integer id);
```
由于事务(@Transactional)是在Service层完成的,所以我们需要书写一个EmployeeService类:

````androiddatabinding
package com.springdata.service;

import com.springdata.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author: ligang
 * date: 2018/1/27
 * time: 19:39
 */
@Service
public class EmployeeService {
 @Autowired
 private EmployeeRepository employeeRepository;

 @Transactional(rollbackOn = Exception.class)
 public void update(String name,Integer id){
     employeeRepository.updateNameById(name,id);
 }

 @Transactional(rollbackOn = Exception.class)
 public void delete(Integer id){
  employeeRepository.deleteById(id);
 }

}

````

测试方法:

```androiddatabinding
 @Test
    public void test() {
        employeeService.update("wangxiaoer", 1);
    }
    @Test
    public void test2() {
        employeeService.delete(6);
    }
```
