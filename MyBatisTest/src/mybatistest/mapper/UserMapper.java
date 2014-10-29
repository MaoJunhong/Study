package mybatistest.mapper;

import mybatistest.bean.User;

public interface UserMapper {
	public User findById(String Id);
}
