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
 * 用户表新增Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserCreateReq", description = "用户表新增Req")
public class UserCreateReq implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    private Boolean validFlag;
}
