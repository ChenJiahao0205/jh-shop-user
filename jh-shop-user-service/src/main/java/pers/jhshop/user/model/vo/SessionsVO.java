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
 * 用户会话表VO
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SessionsVO", description = "用户会话表列表展示VO")
public class SessionsVO extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一标识")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码哈希值")
    private String passwordHash;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户状态（1=激活，0=禁用）")
    private Boolean status;

    @ApiModelProperty(value = "账户创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "账户信息最后更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLoginAt;

    @ApiModelProperty(value = "用户角色ID，外键关联roles表")
    private Long roleId;

}