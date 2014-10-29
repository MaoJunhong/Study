package mybatistest.mapper;

import org.apache.ibatis.annotations.Select;

import mybatistest.bean.User;

public interface AUserMapper {
	@Select("select * from BO_user where id=#{id}")
	public User findById(String Id);
}
