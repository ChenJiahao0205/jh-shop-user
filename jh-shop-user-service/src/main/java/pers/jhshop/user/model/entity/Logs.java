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
 * 用户行为日志表
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_logs")
@ApiModel(value = "Logs对象", description = "用户行为日志表")
public class Logs extends Model<Logs> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志唯一标识")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID，外键关联users表")
    @TableField("USER_ID")
    private Long userId;

    @ApiModelProperty(value = "操作类型（如：登录、浏览商品、下单等）")
    @TableField("ACTION_TYPE")
    private String actionType;

    @ApiModelProperty(value = "操作的详细信息")
    @TableField("ACTION_DETAIL")
    private String actionDetail;

    @ApiModelProperty(value = "操作时间")
    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

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
