package controller;

import dto.RedPacketDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.IRedPacketService;
import test.BaseResponse;
import test.StatusCode;
import org.slf4j.Logger;


/*红包处理逻辑Controller*/
@RestController
public class RedPacketController {
    private static final Logger log= (Logger)LoggerFactory.getLogger(RedPacketController.class);
    /*定义请求路径的前缀*/
    private static final String prefix="red/packet";
    /*注入红包业务逻辑处理接口服务*/
    @Autowired
    private IRedPacketService redPacketService;
    /*发红包请求-请求方法为Post,请求参数采用JSON格式进行交互*/
    @RequestMapping(value=prefix+"/hand/out",method= RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result)
    /*参数检验*/
    {
        if(result.hasErrors())
        {
            return new BaseResponse(StatusCode.InvalidParams);

        }
        BaseResponse response=new BaseResponse(StatusCode.Success);


    try {
        /*核心业务逻辑处理服务-最终返回红包全局唯一标识串*/

        String redId = redPacketService.handOut(dto);
        response.setData(redId);
    }catch(Exception e){
        log.error("发红包异常:dto={}",dto,e.fillInStackTrace());
        response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
    }
    return response;
    }
}
