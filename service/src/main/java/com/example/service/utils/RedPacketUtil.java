package com.example.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {
    /*封装成工具*/
    public static List<Integer> divideRedPacketage(Integer totalAmount,Integer totalPeopleNum)
    {
        List<Integer>amountList=new ArrayList<Integer>();
        if(totalAmount>0&&totalPeopleNum>0)
        {
            Integer restAmount=totalAmount;
            Integer restPeopleNum=totalPeopleNum;
            Random random=new Random();
            for(int i=0;i<totalPeopleNum-1;i++)
            {
           /*   int amount=random.nextInt(restAmount/restPeopleNum*2-1)+1;*/
                int amount = Math.abs(random.nextInt(restAmount / restPeopleNum * 2 - 1)+ 1) ;

                restAmount-=amount;
                restPeopleNum--;
                amountList.add(amount);
            }
            amountList.add(restAmount);
        }
        return amountList;
    }

}
