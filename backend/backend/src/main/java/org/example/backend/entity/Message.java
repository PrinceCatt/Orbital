package org.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("t_message")
public class Message {

    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("fromUid")
    private int fromUid;
    @TableField("toUid")
    private int toUid;
    private String msg;
    @TableField("createTime")
    private String createTime;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", msg='" + msg + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
