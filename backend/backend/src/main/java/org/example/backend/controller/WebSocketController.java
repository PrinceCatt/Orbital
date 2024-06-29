package org.example.backend.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.backend.entity.Message;
import org.example.backend.service.MessageService;
import org.example.backend.service.WebSocketService;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user/message")
public class WebSocketController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/push")
    public Result pushToWeb(@RequestBody Message message) throws IOException {
        int result = messageService.saveMessage(message);
        webSocketService.sendMessage(message.getMsg());
        if (result > 0) {
            return Result.ok();
        }
        return Result.error().message("Message send error");
    }

    @GetMapping("/close")
    public Result close(int id) {
        webSocketService.close(id);
        return Result.ok();
    }

    @GetMapping("/get")
    public Result getMessage(int uid) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        List<Message> list = messageService.
                list(queryWrapper.lambda().eq(Message::getFromUid, uid).or().eq(Message::getToUid, uid));
        return Result.ok().data("messages", list);
    }

}
