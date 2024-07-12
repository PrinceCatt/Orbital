package org.example.backend.controller;



import org.example.backend.service.MessageService;
import org.example.backend.service.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


// Not using
@RestController
@RequestMapping("/user/message")
public class WebSocketController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MyWebSocket myWebSocket;



}
