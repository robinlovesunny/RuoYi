-- ----------------------------
-- 文件信息表
-- ----------------------------
drop table if exists sys_file_info;
create table sys_file_info (
  file_id           bigint(20)      not null auto_increment    comment '文件ID',
  file_name         varchar(200)    default ''                 comment '原始文件名',
  file_path         varchar(500)    default ''                 comment '服务器存储相对路径',
  file_size         bigint(20)      default 0                  comment '文件大小（字节）',
  file_type         varchar(20)     default ''                 comment '文件扩展名',
  category          varchar(50)     default ''                 comment '分类（关联字典 sys_file_category）',
  create_by         varchar(64)     default ''                 comment '上传人',
  create_time       datetime                                   comment '上传时间',
  update_by         varchar(64)     default ''                 comment '更新人',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (file_id)
) engine=innodb auto_increment=100 comment = '文件信息表';


-- ----------------------------
-- 菜单数据 - 文件管理
-- ----------------------------
-- 一级菜单
insert into sys_menu values('5',    '文件管理', '0',   '4', '#',                                          '',  'M', '0', '1', '',                      'fa fa-file-text-o', 'admin', sysdate(), '', null, '文件管理目录');
-- 二级菜单
insert into sys_menu values('118',  '文件列表', '5',   '1', '/system/file',                                '', 'C', '0', '1', 'system:file:view',      'fa fa-file-o',      'admin', sysdate(), '', null, '文件列表菜单');
insert into sys_menu values('119',  '分类管理', '5',   '2', '/system/dict/data/type/sys_file_category',    '', 'C', '0', '1', 'system:dict:view',      'fa fa-bars',        'admin', sysdate(), '', null, '分类管理菜单');
-- 文件管理按钮
insert into sys_menu values('1062', '文件查询', '118', '1',  '#', '',  'F', '0', '1', 'system:file:list',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1063', '文件上传', '118', '2',  '#', '',  'F', '0', '1', 'system:file:upload',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1064', '文件下载', '118', '3',  '#', '',  'F', '0', '1', 'system:file:download', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1065', '文件删除', '118', '4',  '#', '',  'F', '0', '1', 'system:file:remove',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1066', '文件导出', '118', '5',  '#', '',  'F', '0', '1', 'system:file:export',   '#', 'admin', sysdate(), '', null, '');

-- 调整若依官网排序
update sys_menu set order_num = 5 where menu_id = 4;


-- ----------------------------
-- 字典类型 - 文件分类
-- ----------------------------
insert into sys_dict_type values(11, '文件分类', 'sys_file_category', '0', 'admin', sysdate(), '', null, '文件分类列表');


-- ----------------------------
-- 字典数据 - 文件分类
-- ----------------------------
insert into sys_dict_data values(30, 1,  '合同', 'contract', 'sys_file_category', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '合同文件');
insert into sys_dict_data values(31, 2,  '报告', 'report',   'sys_file_category', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '报告文件');
insert into sys_dict_data values(32, 3,  '图片', 'image',    'sys_file_category', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '图片文件');
insert into sys_dict_data values(33, 4,  '其他', 'other',    'sys_file_category', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '其他文件');


-- ----------------------------
-- 角色菜单关联 - admin角色(role_id=1)
-- ----------------------------
insert into sys_role_menu values ('1', '5');
insert into sys_role_menu values ('1', '118');
insert into sys_role_menu values ('1', '119');
insert into sys_role_menu values ('1', '1062');
insert into sys_role_menu values ('1', '1063');
insert into sys_role_menu values ('1', '1064');
insert into sys_role_menu values ('1', '1065');
insert into sys_role_menu values ('1', '1066');
