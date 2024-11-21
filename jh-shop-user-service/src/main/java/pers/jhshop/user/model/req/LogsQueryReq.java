package pers.jhshop.user.model.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.user.model.entity.Logs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户行为日志表查询Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LogsQueryReq", description = "用户行为日志表查询Req")
public class LogsQueryReq extends Page<Logs> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志唯一标识")
    private Long id;

    @ApiModelProperty(value = "用户ID，外键关联users表")
    private Long userId;

    @ApiModelProperty(value = "操作类型（如：登录、浏览商品、下单等）")
    private String actionType;

    @ApiModelProperty(value = "操作类型（如：登录、浏览商品、下单等）-模糊匹配")
    private String actionTypeLike;

    @ApiModelProperty(value = "操作的详细信息")
    private String actionDetail;

    @ApiModelProperty(value = "操作的详细信息-模糊匹配")
    private String actionDetailLike;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "生效标志(true-生效, false-失效)")
    private Boolean validFlag;



}
