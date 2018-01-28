SpringData高级部分
==
## 1.CrudRepository接口的使用

CrudRepository接口分为下面的方法:
```androiddatabinding
save(entity)
findOne(id)
findAll()
delete(entity)
deleteAll()
save (entites)
exists(id)
delete(id)
delete(entities)
```

save方法使用测试:

code 
```androiddatabinding
自定义的接口
public interface EmployeeCrudRepository extends CrudRepository<Employee,Integer> {

}

EmployeeCrudService类

@Service
public class EmployeeCrudService {
    @Autowired
    private EmployeeCrudRepository employeeCrudRepository;

    @Transactional(rollbackOn = Exception.class)
    public void save(List<Employee> employees){
        employeeCrudRepository.save(employees);
    }
}


```
TestCode 
```androiddatabinding
 @Test
    public void test2() {
        List<Employee> employees = new ArrayList<>();
        Employee e = null;
        for (int i = 0; i < 100; i++) {
            e = new Employee();
            e.setName("name" + i);
            e.setAge(100 - i);
            employees.add(e);
        }
        employee.save(employees);
    }
```
## 2.PagingAndSortingRepository接口使用

1. 该接口包含分页和排序的功能
2. 带排序的查询:findAll(Sort sort)
3. 带排序的分页查询:findAll(Pageable pageable)

源码:

```androiddatabinding
@NoRepositoryBean
public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
	Iterable<T> findAll(Sort sort);//返回所有的实体
	Page<T> findAll(Pageable pageable);//返回所有的page对象
}

Pageable这个接口的实现类有PageRequest,里面有设置页数以及每页显示多少条的方法.
PageRequest(Integer page,Integer size);
```

写一个接口
```androiddatabinding

```
测试类
```androiddatabinding
  @Test
    public void test(){
        //page:当前页数,它是按照索引(index=0.....)来算的  size:每页显示的条数
        PageRequest pageable = new PageRequest(0,5);
        Page<Employee> page = employeePagingAndSortingRepository.findAll(pageable);
        System.out.println(page.getTotalPages());//一共有多少页
        System.out.println(page.getTotalElements());//总记录数
        System.out.println(page.getContent());//显示当前页的数据内容
        System.out.println(page.getNumber()+1);//当前是第1页,不过它是从第0页算起的.
        System.out.println(page.getSize());//每页的页数
        System.out.println(page.getSort());
        System.out.println(page.getNumberOfElements());
        /**
         * 
         20
         100
         [com.springdata.domain.Employee@5707f613, com.springdata.domain.Employee@68b11545, com.springdata.domain.Employee@7d0100ea, com.springdata.domain.Employee@357bc488, com.springdata.domain.Employee@4ea17147]
         0
         5
         null
         5
         */
         
其中page.getContent()我们得到的是一组哈希码数组.所以需要我们生成实体类的ToString()方法
得到的结果是:
[Employee{id=1, name='name0', age=100}, Employee{id=2, name='name1', age=99}, Employee{id=3, name='name2', age=98}, Employee{id=4, name='name3', age=97}, Employee{id=5, name='name4', age=96}]
```
下面进行排序的测试:
```androiddatabinding
每页显示5列数据,按照id降序排列
@Test
    public void testSort(){
        //按照id字段降序排列
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(0,5,sort);
        Page<Employee> page = employeePagingAndSortingRepository.findAll(pageRequest);

        System.out.println(page.getContent());
    //[Employee{id=100, name='name99', age=1}, Employee{id=99, name='name98', age=2}, Employee{id=98, name='name97', age=3}, Employee{id=97, name='name96', age=4}, Employee{id=96, name='name95', age=5}]
    }
```
## 3.JpaRepository接口的使用

包括如下方法:
```androiddatabinding

public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {}
1.findAll查询所有实体
2.save(entity)增加实体
3.findAll(Sort sort)支持排序
4.flush
5.deleteInBatch(entities)删除实体
```
自定义接口
```androiddatabinding
public interface EmployeeJpaRepository extends JpaRepository<Employee,Integer>{
}
```
测试用例:

```androiddatabinding
  @Test
    public void test(){
        //查询ID为99的Employee内容
        Employee one = employeeJpaRepository.findOne(99);
        System.out.println(one);

        //检查id=?的内容是否存在
        System.out.println(employeeJpaRepository.exists(2));
        System.out.println(employeeJpaRepository.exists(122));
        Sort.Order id = new Sort.Order(Sort.Direction.DESC, "id");
        Sort orders = new Sort(id);
        List<Employee> list = employeeJpaRepository.findAll(orders);
        for (Employee employee : list) {
            System.out.println(employee);
        }
        System.out.println(employeeJpaRepository.count());
    }

```
## 4.JpaSpecificationExecutor接口

Specification封装了JPA Criteria查询条件.

![](http://upload-images.jianshu.io/upload_images/7505161-8ea911c6d485fac5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Specification接口
```androiddatabinding
public interface Specification<T> {
	Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
```
Specification的实现类
![](http://upload-images.jianshu.io/upload_images/7505161-e9305558a4afc5fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

自定义接口:
```androiddatabinding

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 17:01
 * 继承多个接口,使其功能性更加强大
 */
public interface EmployeeJpaSpecificationExecutor extends JpaSpecificationExecutor<Employee>,JpaRepository<Employee,Integer>{
}
```
测试用例:
```androiddatabinding

```