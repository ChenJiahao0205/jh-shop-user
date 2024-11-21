package pers.jhshop.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import pers.jhshop.common.entity.BaseVo;

/**
 * <p>
 * 用户支付表VO
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PaymentVO", description = "用户支付表列表展示VO")
public class PaymentVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
