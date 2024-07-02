package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.backend.entity.UserLike;

@Mapper
public interface UserLikeMapper extends BaseMapper<UserLike> {
}
