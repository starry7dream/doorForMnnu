package com.cj;

import com.cj.dao.IUserDao;
import com.cj.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class MybatisTest {
    private InputStream  in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao userDao ;

    @Before
    public  void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession =factory.openSession();
        userDao =sqlSession.getMapper(IUserDao.class);

    }
    @After
    public  void destory() throws IOException {
        sqlSession.commit();
        in.close();;
        sqlSession.close();
    }

    @Test
    public  void testFindAll() throws IOException {

        List<User> userList = userDao.findAll();
        for(User user:userList){
            System.out.println(user);
        }
    }
    @Test
    public  void testAddUser() throws IOException {
        User user =new User("test3","123","计算机学院","123","1808190223","192.168.17.23");
        userDao =sqlSession.getMapper(IUserDao.class);
        System.out.println(user);
        int result=userDao.addUser(user);
        //返回ID;
        System.out.println(user);
    }

    @Test
    public  void testFindUserByName() throws IOException {
        List<User> users=userDao.findUserByName("t");
        for (User user:users){
            System.out.println(user);
        }
    }
    @Test
    public  void testUpdateUser() throws IOException {
        User user=userDao.findUserByName("陈").get(0);
        System.out.println(user);
        user.setIp("117.78.123");
        user.setNumber("1808190222");
        userDao.updateUser(user);
        System.out.println(user);
    }

    @Test
    public  void testDeleteUser() throws IOException {
        User user=userDao.findUserByName("t").get(0);
        System.out.println(userDao.deleteUser(user.getId()));
    }
    @Test
    public  void testCheckLogin() throws IOException {
        User user=userDao.checkLogin("陈键","123456");
        System.out.println(user);
    }

    @Test
    public void inviteCode() throws IOException {
        Properties pps = new Properties();
        pps.load(this.getClass().getResourceAsStream("/inviteCode.properties"));
        Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
        while(enum1.hasMoreElements()) {
            String strKey = (String) enum1.nextElement();

        }
    }

}
