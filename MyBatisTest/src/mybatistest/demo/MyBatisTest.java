package mybatistest.demo;

import java.io.IOException;
import java.util.List;

import mybatistest.bean.User;
import mybatistest.mapper.UserListMapper;
import mybatistest.mapper.UserMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisTest {

	private static SqlSessionFactory getSessionFactory() {
		SqlSessionFactory sessionFactory = null;
		String resource = "configuration.xml";
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources
					.getResourceAsReader(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		SqlSession sqlSession = getSessionFactory().openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.findById("1");
		System.out.println(user);

		UserListMapper userListMapper = sqlSession
				.getMapper(UserListMapper.class);
		List<User> users = userListMapper.getUserList();
		System.out.println("\n>>>>>>>>>>>>" + users.size() + "<<<<<<<<<<<<<<");

		System.out.println(users.get(0));

		try {
			System.err.println(sqlSession.selectOne(
					"mybatistest.mapper.AUserMapper.findById", "1"));
		} finally {
			sqlSession.close();
		}
	}

}