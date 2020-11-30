CREATE DATABASE shiro;
CREATE TABLE `t_user`
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	`password` VARCHAR(40) NOT NULL COMMENT '密码，使用md5和salt加密',
	salt VARCHAR(255) NOT NULL COMMENT '随机盐'
);

SELECT `id`,`username`,`password`,`salt` FROM `t_user`;