package org.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.backend.entity.SocketMsg;

import java.util.List;

public interface MessageService {

    int saveMessage(SocketMsg message);

    List<SocketMsg> list(LambdaQueryWrapper<SocketMsg> eq);
}
