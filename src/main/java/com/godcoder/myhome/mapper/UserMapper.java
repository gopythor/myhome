package com.godcoder.myhome.mapper;

import com.godcoder.myhome.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    List<User> getUsers(@Param("text") String text);

}
