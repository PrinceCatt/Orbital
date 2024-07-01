package org.example.backend.entity;

import lombok.Getter;

@Getter
public enum LikedStatusEnum {
    Like(1,"liked"),
    Unlike(2,"unliked"),
    ;
    private int code;
    private String msg;

    LikedStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
