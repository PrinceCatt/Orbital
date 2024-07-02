package org.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@TableName("t_message")
public class SocketMsg {

    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("fromUid")
    private int fromUid;
    @TableField("toUid")
    private int toUid;
    @TableField("createTime")
    private String createTime;
    private int type; // 0:public message, 1:private message

    private String fromUser;
    private String toUser;
    private String msg;

}
