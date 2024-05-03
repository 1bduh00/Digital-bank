package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.NotifRequest;
import com.example.notificationservice.service.NotifService;
import org.springframework.core.env.Environment;

public class NotifServiceImpl implements NotifService {
    private Environment env;
    @Override
    public void sendMessageToClient(NotifRequest request,String url,String token) {

    }


}
