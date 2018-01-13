
import com.gree.bean.Person;
import org.junit.Test;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.pager.Pager;

import java.util.List;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-08 10:34
 * @description: 数据库操作测试
 */
public class DaoTest {




    @Test
    public void createDatabase(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);

        // 创建表
        dao.create(Person.class, false); // false的含义是,如果表已经存在,就不要删除重建了.

        Person p = new Person();
        p.setName("fendd");
        p.setAge(160);
        dao.insert(p);
        System.out.println(p.getId());

    }

    /**
     * 删除表
     */
    @Test
    public void deleteTable(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);
        dao.drop(Person.class);

    }

    /**
     * 自动更新id
     */
    @Test
    public void autoByID(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);

        // 创建表
        dao.create(Person.class, false); // false的含义是,如果表已经存在,就不要删除重建了.

        Person p = new Person();
        p.setName("Peter");
        p.setAge(22);
        dao.insert(p);
        System.out.println(p.getId());
    }


    /**
     * 获取	Fetch	一条 SQL 获取一条记录
     */
    @Test
    public void fetch(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);

        // 创建表
        dao.create(Person.class, false); // false的含义是,如果表已经存在,就不要删除重建了.

        //根据名称获取 （如果你的实体声明了 @Name 字段, 字符型主键,或者带唯一性索引的字段）
        Person p = dao.fetch(Person.class,"Peter");
        System.out.println(p);

        //根据 ID 获取 （如果你的实体声明了 @Id 字段, 数值型主键）
    }

    /**
     * sql update
     */
    @Test
    public void update(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);
        Person p = dao.fetch(Person.class,1);
        //p.setAge(45);

        //dao.update(p, "^age$"); //仅更新age,参数是个正则表达式
        // 注意, p至少带@Id/@Name/@Pk中的一种

        //dao.update(list, "^age$"); //更新一个集合也是可以的

        // 常用的+1更新
        //dao.update(Person.class, Chain.makeSpecial("age", "+1").add("location", "yvr"), Cnd.where("name","=", "fen"));
        dao.update(Person.class, Chain.makeSpecial("age", "*2"), Cnd.where("name","=", "fen"));
    }


    /**
     * 查询全部记录
     */
    @Test
    public void query(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);
        List<Person> people = dao.query(Person.class, null);
        System.out.println(people.toString());

    }

    /**
     * 集合运算
     */
    @Test
    public void func(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);
        int count = dao.func(Person.class, "sum", "age");
        System.out.println(count);
        Object person =   dao.func2(Person.class, "min", "age");
        System.out.println(person.toString());
    }

    /**
     * queryResult  pager
     */
    @Test
    public void queryResult(){
        // 创建一个数据源
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/nutz_study");
        dataSource.setUsername("root");
        dataSource.setPassword("greeadmin");
        // 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.
        Dao dao = new NutDao(dataSource);
        Pager pager = dao.createPager(1, 2);
        List<Person> list = dao.query(Person.class, null, pager);
        pager.setRecordCount(dao.count(Person.class));
        //List<Object> lists = new QueryResult(list, pager).toString();

    }


}
