package pers.jhshop.user.model.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.entity.Addresses;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户地址表查询Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AddressesQueryReq", description = "用户地址表查询Req")
public class AddressesQueryReq extends Page<Addresses> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址唯一标识")
    private Long id;

    @ApiModelProperty(value = "用户ID，外键关联users表")
    private Long userId;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "收货地址-模糊匹配")
    private String addressLike;

    @ApiModelProperty(value = "是否是默认地址（1=是，0=否）")
    private Boolean isDefault;

    @ApiModelProperty(value = "地址创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "地址最后更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    private Boolean validFlag;



}