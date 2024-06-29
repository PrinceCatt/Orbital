package org.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.backend.entity.Message;
import org.example.backend.mapper.MessageMapper;
import org.example.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int saveMessage(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public List<Message> list(LambdaQueryWrapper<Message> eq) {
        return messageMapper.selectList(eq);
    }
}
