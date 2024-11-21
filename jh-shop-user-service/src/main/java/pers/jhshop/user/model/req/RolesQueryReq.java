package pers.jhshop.user.model.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.entity.Roles;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户角色表查询Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "RolesQueryReq", description = "用户角色表查询Req")
public class RolesQueryReq extends Page<Roles> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色唯一标识")
    private Long id;

    @ApiModelProperty(value = "角色名称（如：用户、管理员）")
    private String roleName;

    @ApiModelProperty(value = "角色名称（如：用户、管理员）-模糊匹配")
    private String roleNameLike;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "角色描述-模糊匹配")
    private String descriptionLike;

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    private Boolean validFlag;



}
