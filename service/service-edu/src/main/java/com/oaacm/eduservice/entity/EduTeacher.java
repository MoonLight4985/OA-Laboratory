package com.oaacm.eduservice.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * <p>
 *
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduTeacher对象", description="")
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;
    @ApiModelProperty(value = "讲师简介")
    private String intro;
    @ApiModelProperty(value = "讲师资历")
    private String career;
    @ApiModelProperty(value = "讲师头衔")
    private Integer level;

    @ApiModelProperty(value = "头像")
    private String avatar;

    private Integer sort;
    @ApiModelProperty(value = "逻辑删除：1 已删除  0：未删除")
    @TableLogic//作逻辑删除注解
    private Boolean isDeleted;

    private Date gmtCreate;

    private Date gmtModified;


}
