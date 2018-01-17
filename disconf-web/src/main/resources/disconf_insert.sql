insert into t_user (`user_name`,`real_name`,`password`,`email`,`creator`,`updater`) value ('admin','admin','dRXRlc6aUCMPY3iKTUU6Fg==','371209704@qq.com','admin','admin');

insert into t_user_role (`user_id`,`user_name`,`role_id`,`role_name`,`role_desc`,`creator`,`updater`) value ('1','admin','1','系统管理','系统管理','admin','admin');


insert into t_role (`name`,`description`,`creator`,`updater`) value ('系统管理员','系统管理员','admin','admin');
insert into t_role (`name`,`description`,`creator`,`updater`) value ('开发人员','开发人员','admin','admin');


insert into t_permission (`name`,`description`,`url`,`creator`,`updater`) value ('用户列表页','用户列表页','/user/list','admin','admin');
insert into t_permission (`name`,`description`,`url`,`creator`,`updater`) value ('角色列表页','角色列表页','/role/list','admin','admin');
insert into t_permission (`name`,`description`,`url`,`creator`,`updater`) value ('权限列表页','权限列表页','/permission/list','admin','admin');
insert into t_permission (`name`,`description`,`url`,`creator`,`updater`) value ('APP列表页','用户列表页','/app/list','admin','admin');

insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('1','系统管理员','1','用户列表页','/user/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('1','系统管理员','2','角色列表页','/role/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('1','系统管理员','3','权限列表页','/permission/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('1','系统管理员','4','APP列表页','/app/list','admin','admin');

insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('2','开发人员','1','用户列表页','/user/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('2','开发人员','2','角色列表页','/role/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('2','开发人员','3','权限列表页','/permission/list','admin','admin');
insert into t_role_permission (`role_id`,`role_name`,`permission_id`,`permission_name`,`url`,`creator`,`updater`) value ('2','开发人员','4','APP列表页','/app/list','admin','admin');



insert into t_app (`name`,`description`,`emails`,`group_id`,`group_name`,`creator`,`updater`) value ('app1','description','371209704@qq.com','1','group1','admin','admin');

insert into t_config (`group_id`,`group_name`,`app_id`,`app_name`,`config_name`,`env_id`,`env_name`,`version`,`creator`,`updater`) value (1,'group1',1,'app1','config1',1,'local','1.0.0','admin','admin');


insert into t_env (`name`,`description`,`creator`) value ('local','本地开发环境','admin');
insert into t_env (`name`,`description`,`creator`) value ('test','测试环境','admin');
insert into t_env (`name`,`description`,`creator`) value ('pre','预发环境','admin');
insert into t_env (`name`,`description`,`creator`) value ('online','线上环境','admin');