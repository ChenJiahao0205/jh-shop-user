package pers.jhshop.user.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户支付表修改Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PaymentUpdateReq", description = "用户支付表修改Req")
public class PaymentUpdateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "支付信息唯一标识")
    private Long id;

    @ApiModelProperty(value = "用户ID，外键关联users表")
    private Long userId;

    @ApiModelProperty(value = "支付方式（如：支付宝、微信等）")
    private String paymentMethod;

    @ApiModelProperty(value = "支付账户信息（如银行卡号、支付宝账号等）")
    private String accountInfo;

    @ApiModelProperty(value = "支付信息创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "支付信息最后更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    private Boolean validFlag;
}
