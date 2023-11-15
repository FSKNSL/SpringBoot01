package com.example.rabbitmq.service;

import com.example.service.model.Msg;

public interface ISysLogService {
   void recordLog(Msg msg) throws  Exception;
}
