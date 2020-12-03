package cn.sea.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色实体，
 *      角色表和权限表是多对多的关系，需要需要通过集合来维护这种关系
 */

@ApiModel("Role 角色实体")
@Data
@Accessors(chain = true) // 开启链式调用
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("角色名称")
    private String rolename;

    // 定义权限集合
    private List<Permission> permissions;

}
