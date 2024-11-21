package pers.jhshop.user.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户支付表新增Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PaymentCreateReq", description = "用户支付表新增Req")
public class PaymentCreateReq implements Serializable {

    private static final long serialVersionUID = 1L;

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
