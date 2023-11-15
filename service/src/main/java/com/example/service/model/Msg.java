package com.example.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Msg implements Serializable {
    private Integer userId;/*用户id*/
    private String redId;/*红包标识符*/
    private BigDecimal result;/*抢到的金额*/
}
