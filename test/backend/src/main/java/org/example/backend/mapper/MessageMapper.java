package org.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.backend.entity.SocketMsg;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<SocketMsg> {

    //find private chat history by fromUid, toUid and type
    @Select("select * from t_message where fromUid = #{fromUid} and toUid = #{toUid}")
    List<SocketMsg> findPrivateMessageHistoryByFromUidAndToUid(@Param("fromUid") int fromUid, @Param("toUid") int toUid);
}
