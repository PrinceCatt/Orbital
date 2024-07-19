package org.example.backend.controller;

import org.example.backend.entity.SocketMsg;
import org.example.backend.mapper.MessageMapper;
import org.example.backend.mapper.UserMapper;
import org.example.backend.utils.JwtUtils;
import org.example.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/history")
    public Result getMessageHistory(@RequestParam int toUid, HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        String email = JwtUtils.getClaimsByToken(token).getSubject();
        int fromUid = userMapper.findByEmail(email).getId();

        List<SocketMsg> messages = messageMapper.findPrivateMessageHistoryByFromUidAndToUid(fromUid, toUid);
        return Result.ok().data("messages", messages);
    }
}
