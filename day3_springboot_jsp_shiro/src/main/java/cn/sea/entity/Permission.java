package cn.sea.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限实体
 *      权限和角色是多对多的关系，所有需要使用集合来维护这种关系
 */
@ApiModel("权限实体Permission")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("权限标识符")
    private String permname;
    @ApiModelProperty("资源路径")
    private String permurl;

    // 定义 角色 集合
    //private List<Role> roles;
}
