CREATE DATABASE shiro;
# 用户表
CREATE TABLE `t_user`
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	`password` VARCHAR(40) NOT NULL COMMENT '密码，使用md5和salt加密',
	salt VARCHAR(255) NOT NULL COMMENT '随机盐'
);

SELECT `id`,`username`,`password`,`salt` FROM `t_user`;
SELECT `id`,`username`,`password`,`salt` FROM `t_user` WHERE `username` = #{username};

# 角色表
CREATE TABLE `t_role` # 角色表
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	rolename VARCHAR(60) NOT NULL COMMENT '角色名称'
);


# 用户与角色的中间表
CREATE TABLE `t_user_role` # 用户和角色的中间表
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	userid INT NOT NULL COMMENT '用户id',
	roleid INT NOT NULL COMMENT '角色id'
);


# 权限表
CREATE TABLE `t_permission` # 权限表
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	permname VARCHAR(60) NOT NULL COMMENT '权限标识符',
	permurl VARCHAR(255) COMMENT '资源路径'
);

# 角色和权限的中间表
CREATE TABLE `t_role_prem` # 角色和权限的中间表
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	roleid INT NOT NULL COMMENT '角色id',
	premid INT NOT NULL COMMENT '权限id'
);

# 插入角色数据
INSERT INTO `t_role`(`rolename`) VALUES('admin'),('user'),('product');
SELECT `id`,`rolename` FROM `t_role`;

# 用户表中的数据
INSERT INTO `t_user` (`id`, `username`, `password`, `salt`) VALUES('1','xiaochen','42c7f2173681d13bfe16b283f0645ba4','ZVn`_0vb');
INSERT INTO `t_user` (`id`, `username`, `password`, `salt`) VALUES('2','zhangsan','26386892afe2df5f238804dbf342eec3','!G!MiJzH');

# 插入角色与权限中间表数据(1号用户具有2号角色，2号用户具有2和3号角色)
INSERT INTO `t_user_role`(`userid`,`roleid`) VALUES(1,1),(2,2),(2,3);
SELECT `id`,`userid`,`roleid` FROM `t_user_role`;

# 根据用户id查询用户的所有角色信息
SELECT u.id uid, u.username, u.password, u.salt ,r.id rid, r.rolename
FROM `t_user` u
LEFT JOIN `t_user_role` ur
ON u.id = ur.userid
LEFT JOIN `t_role` r
ON ur.roleid = r.id
WHERE u.id = 2;


# 插入权限数据
INSERT INTO `t_permission`(`permname`) VALUES('user:*:*'),('product:*:1'),('order:*:*');

# 插入角色和权限的中间表数据(1号角色拥有1和2号和3号权限，2号角色拥有1号权限，3号角色拥有2号权限)
INSERT INTO `t_role_prem`(`roleid`,`premid`) VALUES(1,1),(1,2),(2,1),(3,2),(1,3);

# 根据角色id查询权限信息集合
SELECT r.id rid, r.rolename, p.id pid, p.permname, p.permurl FROM `t_role` r
LEFT JOIN `t_role_prem` rp
ON r.id=rp.roleid
LEFT JOIN `t_permission` p
ON rp.premid = p.id
WHERE r.id = 3;








