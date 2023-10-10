package dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedPacketDto {
    /*发红包请求时接收的参数对象*/
    private Integer userId;/*用户账号id*/
    @NotNull
    private Integer total;/*红包个数*/
    @NotNull
    private Integer amount;/*总金额-单位为分*/


}
