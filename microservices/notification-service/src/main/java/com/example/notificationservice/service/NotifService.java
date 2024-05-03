package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotifRequest;

public interface NotifService {
    void sendMessageToClient(NotifRequest request,String url,String token);
}
