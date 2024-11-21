package pers.jhshop.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户支付表
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_payment")
@ApiModel(value = "Payment对象", description = "用户支付表")
public class Payment extends Model<Payment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息唯一标识")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID，外键关联users表")
    @TableField("USER_ID")
    private Long userId;

    @ApiModelProperty(value = "支付方式（如：支付宝、微信等）")
    @TableField("PAYMENT_METHOD")
    private String paymentMethod;

    @ApiModelProperty(value = "支付账户信息（如银行卡号、支付宝账号等）")
    @TableField("ACCOUNT_INFO")
    private String accountInfo;

    @ApiModelProperty(value = "支付信息创建时间")
    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "支付信息最后更新时间")
    @TableField("UPDATED_AT")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    @TableField("VALID_FLAG")
    private Boolean validFlag;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
