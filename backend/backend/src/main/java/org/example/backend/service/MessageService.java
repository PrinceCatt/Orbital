package org.example.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.backend.entity.Message;

import java.util.List;

public interface MessageService {

    int saveMessage(Message message);

    List<Message> list(LambdaQueryWrapper<Message> eq);
}
