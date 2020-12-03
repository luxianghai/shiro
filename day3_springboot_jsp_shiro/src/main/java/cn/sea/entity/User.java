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
 * 用户实体
 *      用户和角色是多对多的关系，所以需要使用集合来维护这种关系
 */

@ApiModel("用户User实体")
@Data
@Accessors(chain = true) // 开启链式调用
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @ApiModelProperty("主键id")
    private String id; // 主键id
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    private String salt; // 随机盐

    // 定义 角色 集合
    private List<Role> roles;
}
