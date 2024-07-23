package org.example.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("t_userlike")
public class UserLike {

    private int id;
    @TableField("commentId")
    private int commentId;
    @TableField("giveUserId")
    private int giveUserId;
    private int status = LikedStatusEnum.Unlike.getCode();

    public UserLike() {}

    public UserLike(int commentId, int giveUserId, int status) {
        this.commentId = commentId;
        this.giveUserId = giveUserId;
        this.status = status;
    }
}
