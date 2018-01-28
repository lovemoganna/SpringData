package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import javax.persistence.Id;
import java.util.List;

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

    //以上都是按照命名规则的方式来写的.

    /**
     * select t1 from Employee t1
     * 里面的Employee是类名
     * @return
     */
    @Query("select o from Employee o where id=(select max(id) from Employee t)")
    public Employee getEmployeeById();

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
}
