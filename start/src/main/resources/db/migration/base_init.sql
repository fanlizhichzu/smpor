/*
 Navicat Premium Data Transfer

 Source Server         : shinegis28
 Source Server Type    : PostgreSQL
 Source Server Version : 110011
 Source Host           : 192.168.20.28:5432
 Source Catalog        : shinegisclient
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110011
 File Encoding         : 65001

 Date: 29/03/2022 16:17:45
*/


-- ----------------------------
-- Sequence structure for serial
-- ----------------------------
CREATE SEQUENCE if NOT EXISTS "public"."serial"
    INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 9223372036854775807;

-- ----------------------------
-- Table structure for com_permission
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."com_permission" (
                                           "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                           "comid" varchar COLLATE "pg_catalog"."default",
                                           "load" bool,
                                           "visible" bool,
                                           "sid" varchar COLLATE "pg_catalog"."default",
                                           "ssid" varchar COLLATE "pg_catalog"."default"
);
COMMENT ON TABLE "public"."com_permission" IS '组件权限关联表';

-- ----------------------------
-- Table structure for com_subscheme
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."com_subscheme" (
                                          "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                          "comid" varchar COLLATE "pg_catalog"."default",
                                          "ssid" varchar COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."com_subscheme"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."com_subscheme"."comid" IS '组件id';
COMMENT ON COLUMN "public"."com_subscheme"."ssid" IS '子方案id';
COMMENT ON TABLE "public"."com_subscheme" IS '组件--子方案中间表';

-- ----------------------------
-- Table structure for components
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."components" (
                                       "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar COLLATE "pg_catalog"."default",
                                       "config" text COLLATE "pg_catalog"."default",
                                       "icon" varchar(255) COLLATE "pg_catalog"."default",
                                       "iconclass" varchar(128) COLLATE "pg_catalog"."default",
                                       "index" int4,
                                       "label" varchar(64) COLLATE "pg_catalog"."default",
                                       "update_time" timestamp(6),
                                       "stat" bool DEFAULT false,
                                       "create_time" timestamp(6) NOT NULL DEFAULT now()
);
COMMENT ON COLUMN "public"."components"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."components"."stat" IS '选中状态';
COMMENT ON COLUMN "public"."components"."create_time" IS '创建时间';
COMMENT ON TABLE "public"."components" IS '组件库';

-- ----------------------------
-- Table structure for config
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."config" (
                                   "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                   "key" varchar(64) COLLATE "pg_catalog"."default",
                                   "value" varchar(1024) COLLATE "pg_catalog"."default",
                                   "description" varchar(255) COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."config"."id" IS 'UUID主键';
COMMENT ON COLUMN "public"."config"."key" IS '名称';
COMMENT ON COLUMN "public"."config"."value" IS '地址';
COMMENT ON COLUMN "public"."config"."description" IS '描述信息';
COMMENT ON TABLE "public"."config" IS '核心配置表';

-- ----------------------------
-- Table structure for control_panel
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."control_panel" (
                                          "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "sgg_history_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "panel_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "level" int2,
                                          "init" bool NOT NULL DEFAULT false,
                                          "config" text COLLATE "pg_catalog"."default",
                                          "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                          "update_time" timestamp(6),
                                          "init_position" text COLLATE "pg_catalog"."default",
                                          "panel_en" varchar(64) COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."control_panel"."id" IS '面板id,主键UUID';
COMMENT ON COLUMN "public"."control_panel"."sgg_history_id" IS '关联SGG的历史版本的主键id';
COMMENT ON COLUMN "public"."control_panel"."panel_name" IS '面板名称';
COMMENT ON COLUMN "public"."control_panel"."level" IS '层级';
COMMENT ON COLUMN "public"."control_panel"."init" IS '是否初始化加载';
COMMENT ON COLUMN "public"."control_panel"."config" IS '自定义的配置消息';
COMMENT ON COLUMN "public"."control_panel"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."control_panel"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."control_panel"."init_position" IS '初始化位置';
COMMENT ON COLUMN "public"."control_panel"."panel_en" IS '面板英文名称';
COMMENT ON TABLE "public"."control_panel" IS '控制面板表';

-- ----------------------------
-- Table structure for enum
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."enum" (
                                 "enum_id" varbit(64) NOT NULL,
                                 "enum_group_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                 "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                 "value" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                 "enum_desc" varchar(255) COLLATE "pg_catalog"."default",
                                 "status" bool NOT NULL DEFAULT true,
                                 "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                 "update_time" timestamp(6),
                                 "source" int2 NOT NULL DEFAULT 1
);
COMMENT ON COLUMN "public"."enum"."enum_id" IS '枚举id';
COMMENT ON COLUMN "public"."enum"."enum_group_id" IS '组id';
COMMENT ON COLUMN "public"."enum"."name" IS 'key';
COMMENT ON COLUMN "public"."enum"."value" IS 'value';
COMMENT ON COLUMN "public"."enum"."enum_desc" IS '枚举描述';
COMMENT ON COLUMN "public"."enum"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."enum"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."enum"."source" IS '枚举来源 1:shinegis 2: zwzt';
COMMENT ON TABLE "public"."enum" IS '枚举表';

-- ----------------------------
-- Table structure for enum_group
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."enum_group" (
                                       "enum_group_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "parent_group_id" varchar(64) COLLATE "pg_catalog"."default",
                                       "group_name" varchar(64) COLLATE "pg_catalog"."default",
                                       "group_desc" varchar(255) COLLATE "pg_catalog"."default",
                                       "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                       "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."enum_group"."enum_group_id" IS '主键UUID';
COMMENT ON COLUMN "public"."enum_group"."parent_group_id" IS '父id';
COMMENT ON COLUMN "public"."enum_group"."group_name" IS '枚举组名称';
COMMENT ON COLUMN "public"."enum_group"."group_desc" IS '枚举组描述';
COMMENT ON COLUMN "public"."enum_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."enum_group"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."enum_group" IS '枚举组表';

-- ----------------------------
-- Table structure for file_record
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."file_record" (
                                        "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                        "name" varchar(64) COLLATE "pg_catalog"."default",
                                        "suffix" varchar(12) COLLATE "pg_catalog"."default",
                                        "upload_time" timestamp(6) DEFAULT now(),
                                        "is_delete" bool NOT NULL DEFAULT true
);
COMMENT ON COLUMN "public"."file_record"."id" IS '主键';
COMMENT ON COLUMN "public"."file_record"."name" IS '文件名';
COMMENT ON COLUMN "public"."file_record"."suffix" IS '文件后缀';
COMMENT ON COLUMN "public"."file_record"."upload_time" IS '上传时间';
COMMENT ON COLUMN "public"."file_record"."is_delete" IS '是否已删除';
COMMENT ON TABLE "public"."file_record" IS '文件记录表';

-- ----------------------------
-- Table structure for g_draw
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_draw" (
                                   "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                   "create_time" timestamp(6) DEFAULT now(),
                                   "update_time" timestamp(6),
                                   "config" text COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."g_draw"."id" IS '主键id';
COMMENT ON COLUMN "public"."g_draw"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."g_draw"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."g_draw"."config" IS '配置字段';

-- ----------------------------
-- Table structure for g_map
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_map" (
                                  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "group_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "toc_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "enable" bool NOT NULL DEFAULT false,
                                  "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                  "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."g_map"."id" IS '主键uuid';
COMMENT ON COLUMN "public"."g_map"."group_id" IS '关联组id';
COMMENT ON COLUMN "public"."g_map"."toc_id" IS '绑定图层id';
COMMENT ON COLUMN "public"."g_map"."enable" IS '启用状态';
COMMENT ON COLUMN "public"."g_map"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."g_map"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."g_map" IS '二三维底图配置表';

-- ----------------------------
-- Table structure for g_map_group
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_map_group" (
                                        "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                        "base_code" varchar(16) COLLATE "pg_catalog"."default",
                                        "name" varchar(64) COLLATE "pg_catalog"."default",
                                        "type" varchar(4) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '2D'::character varying,
                                        "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                        "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."g_map_group"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."g_map_group"."base_code" IS '编码';
COMMENT ON COLUMN "public"."g_map_group"."name" IS '名称';
COMMENT ON COLUMN "public"."g_map_group"."type" IS '二三维类型';
COMMENT ON COLUMN "public"."g_map_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."g_map_group"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."g_map_group" IS '二三维底图配置组表';

-- ----------------------------
-- Table structure for g_roam
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_roam" (
                                   "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                   "name" varchar(64) COLLATE "pg_catalog"."default",
                                   "type" varchar(32) COLLATE "pg_catalog"."default",
                                   "properties" text COLLATE "pg_catalog"."default",
                                   "geometry" text COLLATE "pg_catalog"."default",
                                   "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                   "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."g_roam"."id" IS '主键 UUID';
COMMENT ON COLUMN "public"."g_roam"."name" IS '名称';
COMMENT ON COLUMN "public"."g_roam"."type" IS '类型';
COMMENT ON COLUMN "public"."g_roam"."properties" IS '配置信息';
COMMENT ON COLUMN "public"."g_roam"."geometry" IS 'geoJSON数据';
COMMENT ON COLUMN "public"."g_roam"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."g_roam"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."g_roam" IS '漫游飞行表';

-- ----------------------------
-- Table structure for g_scene
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_scene" (
                                    "id" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
                                    "catalog_id" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
                                    "fly_roam" varchar(40) COLLATE "pg_catalog"."default",
                                    "x" numeric,
                                    "y" numeric,
                                    "z" numeric,
                                    "heading" int2,
                                    "pitch" int2,
                                    "roll" int2,
                                    "sort" int2 NOT NULL DEFAULT nextval('serial'::regclass),
                                    "base_map" text COLLATE "pg_catalog"."default",
                                    "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                    "update_time" timestamp(6),
                                    "name" varchar(64) COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."g_scene"."id" IS '主键id';
COMMENT ON COLUMN "public"."g_scene"."catalog_id" IS '场景目录id';
COMMENT ON COLUMN "public"."g_scene"."fly_roam" IS '飞行漫游id';
COMMENT ON COLUMN "public"."g_scene"."x" IS 'x经度';
COMMENT ON COLUMN "public"."g_scene"."y" IS 'y纬度';
COMMENT ON COLUMN "public"."g_scene"."z" IS 'z视高';
COMMENT ON COLUMN "public"."g_scene"."heading" IS '偏航角';
COMMENT ON COLUMN "public"."g_scene"."pitch" IS '俯视角';
COMMENT ON COLUMN "public"."g_scene"."roll" IS '翻滚角';
COMMENT ON COLUMN "public"."g_scene"."sort" IS '排序';
COMMENT ON COLUMN "public"."g_scene"."base_map" IS '底图服务地址';
COMMENT ON COLUMN "public"."g_scene"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."g_scene"."update_time" IS '最后修改时间';
COMMENT ON COLUMN "public"."g_scene"."name" IS '场景名称';
COMMENT ON TABLE "public"."g_scene" IS '场景表';

-- ----------------------------
-- Table structure for g_scene_catalog
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."g_scene_catalog" (
                                            "id" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
                                            "name" varchar(32) COLLATE "pg_catalog"."default",
                                            "describe" varchar(255) COLLATE "pg_catalog"."default",
                                            "is_default" bool NOT NULL DEFAULT false,
                                            "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                            "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."g_scene_catalog"."id" IS '主键ID';
COMMENT ON COLUMN "public"."g_scene_catalog"."name" IS '目录名称';
COMMENT ON COLUMN "public"."g_scene_catalog"."describe" IS '描述';
COMMENT ON COLUMN "public"."g_scene_catalog"."is_default" IS '是否默认';
COMMENT ON COLUMN "public"."g_scene_catalog"."create_time" IS '新建时间';
COMMENT ON COLUMN "public"."g_scene_catalog"."update_time" IS '最后修改时间';
COMMENT ON TABLE "public"."g_scene_catalog" IS '场景目录表';

-- ----------------------------
-- Table structure for global_config
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."global_config" (
                                          "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "group_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'default'::character varying,
                                          "describe" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
                                          "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "value" varchar(128) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying,
                                          "create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          "update_time" timestamp(6),
                                          "is_safe" bool
);
COMMENT ON COLUMN "public"."global_config"."id" IS '主键,UUID';
COMMENT ON COLUMN "public"."global_config"."group_name" IS '组名称';
COMMENT ON COLUMN "public"."global_config"."describe" IS '字段描述';
COMMENT ON COLUMN "public"."global_config"."name" IS '字段名称';
COMMENT ON COLUMN "public"."global_config"."value" IS '值';
COMMENT ON COLUMN "public"."global_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."global_config"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."global_config" IS '全局配置';

-- ----------------------------
-- Table structure for high_param
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."high_param" (
                                       "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "value" text COLLATE "pg_catalog"."default",
                                       "toc_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "type" varchar(12) COLLATE "pg_catalog"."default" NOT NULL,
                                       "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                       "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."high_param"."id" IS '主键id';
COMMENT ON COLUMN "public"."high_param"."name" IS '名称 非空唯一';
COMMENT ON COLUMN "public"."high_param"."value" IS '字段值';
COMMENT ON COLUMN "public"."high_param"."toc_id" IS '关联图层id';
COMMENT ON COLUMN "public"."high_param"."type" IS '字段类型';
COMMENT ON COLUMN "public"."high_param"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."high_param"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."high_param" IS '高级参数表';

-- ----------------------------
-- Table structure for note_toc
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."note_toc" (
                                     "note_toc_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                     "name" varchar(64) COLLATE "pg_catalog"."default",
                                     "user_id" varchar(64) COLLATE "pg_catalog"."default",
                                     "schema_id" varchar(64) COLLATE "pg_catalog"."default",
                                     "grid" int2,
                                     "init_checked" int2,
                                     "fields" text COLLATE "pg_catalog"."default",
                                     "style_options" text COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                     "update_time" timestamp(6),
                                     "type" varchar(16) COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."note_toc"."note_toc_id" IS '主键UUID,对应的图层表名';
COMMENT ON COLUMN "public"."note_toc"."name" IS '注解图层名称';
COMMENT ON COLUMN "public"."note_toc"."user_id" IS '关联 userId';
COMMENT ON COLUMN "public"."note_toc"."schema_id" IS '关联方案id';
COMMENT ON COLUMN "public"."note_toc"."grid" IS '是否分块加载';
COMMENT ON COLUMN "public"."note_toc"."init_checked" IS '是否默认显示';
COMMENT ON COLUMN "public"."note_toc"."fields" IS '属性字段';
COMMENT ON COLUMN "public"."note_toc"."style_options" IS '默认样式';
COMMENT ON COLUMN "public"."note_toc"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."note_toc"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."note_toc"."type" IS '图层类型';
COMMENT ON TABLE "public"."note_toc" IS '注解图层记录表';

-- ----------------------------
-- Table structure for permission
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."permission" (
                                       "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar(64) COLLATE "pg_catalog"."default",
                                       "privilege_code" varchar(64) COLLATE "pg_catalog"."default",
                                       "config" text COLLATE "pg_catalog"."default",
                                       "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                       "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."permission"."privilege_code" IS '特权码';
COMMENT ON COLUMN "public"."permission"."config" IS '配置信息';
COMMENT ON TABLE "public"."permission" IS '权限表';

-- ----------------------------
-- Table structure for press
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."press" (
                                  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "schema_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                  "height" float8,
                                  "flatten_type" bool NOT NULL DEFAULT true,
                                  "is_show" bool NOT NULL DEFAULT true,
                                  "enabled" bool NOT NULL DEFAULT false,
                                  "positions" text COLLATE "pg_catalog"."default",
                                  "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                  "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."press"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."press"."schema_id" IS '关联方案id';
COMMENT ON COLUMN "public"."press"."height" IS '高度';
COMMENT ON COLUMN "public"."press"."flatten_type" IS '开挖true, 压平false';
COMMENT ON COLUMN "public"."press"."is_show" IS '显隐';
COMMENT ON COLUMN "public"."press"."enabled" IS '是否开启编辑';
COMMENT ON COLUMN "public"."press"."positions" IS '点位坐标';
COMMENT ON TABLE "public"."press" IS '开挖压平数据表';

-- ----------------------------
-- Table structure for resources
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."resources" (
                                      "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                      "scheme_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                      "role_id" varchar(64) COLLATE "pg_catalog"."default",
                                      "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                      "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."resources"."id" IS '主键uuid';
COMMENT ON COLUMN "public"."resources"."scheme_id" IS '子方案id';
COMMENT ON COLUMN "public"."resources"."role_id" IS '关联角色id';
COMMENT ON COLUMN "public"."resources"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."resources"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."resources" IS '资源表';

-- ----------------------------
-- Table structure for resources_business
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."resources_business" (
                                               "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                               "resources_id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                               "type" int2 NOT NULL DEFAULT 1,
                                               "business_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                               "init" bool,
                                               "create_time" timestamp(6) DEFAULT now(),
                                               "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."resources_business"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."resources_business"."resources_id" IS '关联的资源id';
COMMENT ON COLUMN "public"."resources_business"."type" IS '资源类型:
1：图层
2：组件';
COMMENT ON COLUMN "public"."resources_business"."business_id" IS '关联的业务资源的id';
COMMENT ON COLUMN "public"."resources_business"."init" IS '图层资源是否初始化加载';
COMMENT ON COLUMN "public"."resources_business"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."resources_business"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."resources_business" IS '资源信息关联表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."role" (
                                 "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                 "name" varchar(64) COLLATE "pg_catalog"."default",
                                 "sub_guid" varchar(64) COLLATE "pg_catalog"."default",
                                 "type" varchar(32) COLLATE "pg_catalog"."default",
                                 "parent_id" varchar(64) COLLATE "pg_catalog"."default",
                                 "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                 "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."role"."id" IS '主键';
COMMENT ON COLUMN "public"."role"."name" IS '名称';
COMMENT ON COLUMN "public"."role"."type" IS '类型';
COMMENT ON COLUMN "public"."role"."parent_id" IS '父id';
COMMENT ON COLUMN "public"."role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."role"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."role" IS '角色信息表';

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."role_permission" (
                                            "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                            "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                            "permission_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL DEFAULT now(),
                                            "privilege_code" varchar(64) COLLATE "pg_catalog"."default",
                                            "config" text COLLATE "pg_catalog"."default",
                                            "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                            "update_time" timestamp(6),
                                            "scheme_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
);
COMMENT ON COLUMN "public"."role_permission"."role_id" IS '关联role_id';
COMMENT ON COLUMN "public"."role_permission"."permission_id" IS '关联权限id';
COMMENT ON COLUMN "public"."role_permission"."privilege_code" IS '特权码';
COMMENT ON COLUMN "public"."role_permission"."config" IS '配置信息';
COMMENT ON COLUMN "public"."role_permission"."scheme_id" IS '方案id';
COMMENT ON TABLE "public"."role_permission" IS '角色——权限表';

-- ----------------------------
-- Table structure for scheme
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."scheme" (
                                   "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                   "name" varchar(64) COLLATE "pg_catalog"."default",
                                   "label" varchar(64) COLLATE "pg_catalog"."default",
                                   "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                   "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."scheme"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."scheme"."update_time" IS '修改时间';
COMMENT ON TABLE "public"."scheme" IS '配置方案表';

-- ----------------------------
-- Table structure for sgg_config_history
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."sgg_config_history" (
                                               "sgg_history_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                               "schema_id" varchar(64) COLLATE "pg_catalog"."default",
                                               "rev" int2,
                                               "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                               "update_time" timestamp(6),
                                               "used" bool NOT NULL DEFAULT false
);
COMMENT ON COLUMN "public"."sgg_config_history"."sgg_history_id" IS '历史版本主键UUID';
COMMENT ON COLUMN "public"."sgg_config_history"."schema_id" IS '关联方案id';
COMMENT ON COLUMN "public"."sgg_config_history"."rev" IS '版本号';
COMMENT ON COLUMN "public"."sgg_config_history"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sgg_config_history"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sgg_config_history"."used" IS '是否当前使用';
COMMENT ON TABLE "public"."sgg_config_history" IS 'SGG配置历史表';

-- ----------------------------
-- Table structure for subscheme
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."subscheme" (
                                      "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                      "parentid" varchar COLLATE "pg_catalog"."default",
                                      "name" varchar COLLATE "pg_catalog"."default",
                                      "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                      "update_time" timestamp(6),
                                      "config" text COLLATE "pg_catalog"."default",
                                      "file_info" varchar(255) COLLATE "pg_catalog"."default",
                                      "base_toc" text COLLATE "pg_catalog"."default",
                                      "custom_config" text COLLATE "pg_catalog"."default",
                                      "base_config" text COLLATE "pg_catalog"."default",
                                      "component_dir" text COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."subscheme"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."subscheme"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."subscheme"."file_info" IS '文件信息';
COMMENT ON COLUMN "public"."subscheme"."base_toc" IS '基础图层信息';
COMMENT ON COLUMN "public"."subscheme"."custom_config" IS '自定义配置信息';
COMMENT ON COLUMN "public"."subscheme"."base_config" IS '基础配置信息';
COMMENT ON COLUMN "public"."subscheme"."component_dir" IS '组件目录';
COMMENT ON TABLE "public"."subscheme" IS '子方案';

-- ----------------------------
-- Table structure for subscheme_toc
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."subscheme_toc" (
                                          "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "toc_id" varchar(64) COLLATE "pg_catalog"."default",
                                          "name" varchar(255) COLLATE "pg_catalog"."default",
                                          "init_checked" bool NOT NULL DEFAULT false,
                                          "selected" bool NOT NULL DEFAULT true,
                                          "type" int2 NOT NULL DEFAULT 1,
                                          "schema_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "parent_id" varchar(64) COLLATE "pg_catalog"."default",
                                          "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                          "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."subscheme_toc"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."subscheme_toc"."toc_id" IS '关联toc_id';
COMMENT ON COLUMN "public"."subscheme_toc"."name" IS '图层名称,初始导入不设置，自定义改是显示';
COMMENT ON COLUMN "public"."subscheme_toc"."init_checked" IS '是否初始化加载';
COMMENT ON COLUMN "public"."subscheme_toc"."selected" IS '是否选中';
COMMENT ON COLUMN "public"."subscheme_toc"."type" IS '0:目录 1: 图层';
COMMENT ON COLUMN "public"."subscheme_toc"."schema_id" IS '关联方案id';
COMMENT ON COLUMN "public"."subscheme_toc"."parent_id" IS '父id';
COMMENT ON COLUMN "public"."subscheme_toc"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."subscheme_toc"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."subscheme_toc" IS '方案图层表';

-- ----------------------------
-- Table structure for t_xzq_data
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."t_xzq_data" (
                                       "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar(32) COLLATE "pg_catalog"."default",
                                       "url" varchar(255) COLLATE "pg_catalog"."default",
                                       "username" varchar(64) COLLATE "pg_catalog"."default",
                                       "password" varchar(64) COLLATE "pg_catalog"."default",
                                       "describe" varchar(255) COLLATE "pg_catalog"."default",
                                       "layer" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
);
COMMENT ON COLUMN "public"."t_xzq_data"."id" IS '主键UUID';
COMMENT ON COLUMN "public"."t_xzq_data"."name" IS '此数据库的名称，方便区分';
COMMENT ON COLUMN "public"."t_xzq_data"."url" IS '数据库连接地址';
COMMENT ON COLUMN "public"."t_xzq_data"."username" IS '数据库用户';
COMMENT ON COLUMN "public"."t_xzq_data"."password" IS '数据库密码';
COMMENT ON COLUMN "public"."t_xzq_data"."describe" IS '描述字段';
COMMENT ON TABLE "public"."t_xzq_data" IS '行政区数据库配置表';

-- ----------------------------
-- Table structure for t_xzq_table
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."t_xzq_table" (
                                        "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                        "data_layer" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "level" int2 NOT NULL,
                                        "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                        "describe" varchar(255) COLLATE "pg_catalog"."default"
);
COMMENT ON COLUMN "public"."t_xzq_table"."id" IS '主键id';
COMMENT ON COLUMN "public"."t_xzq_table"."data_layer" IS '关联数据库id';
COMMENT ON COLUMN "public"."t_xzq_table"."level" IS 'xzq等级：
 1：省
 2：市
 3：区县
 4: 乡镇
 5：村';
COMMENT ON COLUMN "public"."t_xzq_table"."name" IS '表名';
COMMENT ON COLUMN "public"."t_xzq_table"."describe" IS '描述字段';
COMMENT ON TABLE "public"."t_xzq_table" IS '行政区记录表信息';

-- ----------------------------
-- Table structure for toc
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."toc" (
                                "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                "parent" varchar(64) COLLATE "pg_catalog"."default",
                                "label" varchar(64) COLLATE "pg_catalog"."default",
                                "config" text COLLATE "pg_catalog"."default",
                                "type" varchar(12) COLLATE "pg_catalog"."default",
                                "update_time" timestamp(6),
                                "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                "sort" int4 NOT NULL
);
COMMENT ON COLUMN "public"."toc"."type" IS '文件类型';

-- ----------------------------
-- Table structure for toc_permission
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."toc_permission" (
                                           "id" varchar COLLATE "pg_catalog"."default" NOT NULL,
                                           "tocid" varchar COLLATE "pg_catalog"."default",
                                           "permissionid" varchar COLLATE "pg_catalog"."default",
                                           "inital" bool,
                                           "editor" bool,
                                           "delete" bool,
                                           "sid" varchar COLLATE "pg_catalog"."default",
                                           "ssid" varchar COLLATE "pg_catalog"."default"
);
COMMENT ON TABLE "public"."toc_permission" IS '图层目录权限关联表';

-- ----------------------------
-- Table structure for toc_subscheme
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."toc_subscheme" (
                                          "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                          "toc_id" varchar(64) COLLATE "pg_catalog"."default",
                                          "ssid" varchar(64) COLLATE "pg_catalog"."default",
                                          "init" bool NOT NULL DEFAULT false,
                                          "enable" varchar(255) COLLATE "pg_catalog"."default",
                                          "map_index" int4,
                                          "only_layer" bool NOT NULL DEFAULT false
);
COMMENT ON COLUMN "public"."toc_subscheme"."id" IS '主键';
COMMENT ON COLUMN "public"."toc_subscheme"."toc_id" IS '图层id';
COMMENT ON COLUMN "public"."toc_subscheme"."ssid" IS '子方案id';
COMMENT ON COLUMN "public"."toc_subscheme"."init" IS '是否初始化加载';
COMMENT ON COLUMN "public"."toc_subscheme"."enable" IS '编辑信息';
COMMENT ON COLUMN "public"."toc_subscheme"."only_layer" IS '是否只加载图层';
COMMENT ON TABLE "public"."toc_subscheme" IS '图层--子方案关表';

-- ----------------------------
-- Table structure for tocsolution
-- ----------------------------
CREATE TABLE if NOT EXISTS "public"."tocsolution" (
                                        "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
                                        "name" varchar(64) COLLATE "pg_catalog"."default",
                                        "config" text COLLATE "pg_catalog"."default",
                                        "create_time" timestamp(6) NOT NULL DEFAULT now(),
                                        "update_time" timestamp(6)
);
COMMENT ON COLUMN "public"."tocsolution"."create_time" IS '开始时间';
COMMENT ON COLUMN "public"."tocsolution"."update_time" IS '修改时间';

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."serial"', 1, false);

-- ----------------------------
-- Primary Key structure for table com_permission
-- ----------------------------
ALTER TABLE "public"."com_permission" ADD CONSTRAINT "com_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table com_subscheme
-- ----------------------------
ALTER TABLE "public"."com_subscheme" ADD CONSTRAINT "com_subscheme_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table components
-- ----------------------------
ALTER TABLE "public"."components" ADD CONSTRAINT "components_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config
-- ----------------------------
ALTER TABLE "public"."config" ADD CONSTRAINT "config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table control_panel
-- ----------------------------
ALTER TABLE "public"."control_panel" ADD CONSTRAINT "control_panel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table enum
-- ----------------------------
ALTER TABLE "public"."enum" ADD CONSTRAINT "enum_pkey" PRIMARY KEY ("enum_id");

-- ----------------------------
-- Primary Key structure for table enum_group
-- ----------------------------
ALTER TABLE "public"."enum_group" ADD CONSTRAINT "enum_group_pkey" PRIMARY KEY ("enum_group_id");

-- ----------------------------
-- Primary Key structure for table file_record
-- ----------------------------
ALTER TABLE "public"."file_record" ADD CONSTRAINT "file_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_draw
-- ----------------------------
ALTER TABLE "public"."g_draw" ADD CONSTRAINT "g_draw_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_map
-- ----------------------------
ALTER TABLE "public"."g_map" ADD CONSTRAINT "g_map_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_map_group
-- ----------------------------
ALTER TABLE "public"."g_map_group" ADD CONSTRAINT "g_map_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_roam
-- ----------------------------
ALTER TABLE "public"."g_roam" ADD CONSTRAINT "g_roam_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_scene
-- ----------------------------
ALTER TABLE "public"."g_scene" ADD CONSTRAINT "g_scene_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table g_scene_catalog
-- ----------------------------
ALTER TABLE "public"."g_scene_catalog" ADD CONSTRAINT "g_scene_catalog_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table global_config
-- ----------------------------
ALTER TABLE "public"."global_config" ADD CONSTRAINT "global_config_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table high_param
-- ----------------------------
ALTER TABLE "public"."high_param" ADD CONSTRAINT "high_param_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table note_toc
-- ----------------------------
ALTER TABLE "public"."note_toc" ADD CONSTRAINT "note_toc_pk" PRIMARY KEY ("note_toc_id");

-- ----------------------------
-- Primary Key structure for table permission
-- ----------------------------
ALTER TABLE "public"."permission" ADD CONSTRAINT "permission_pkey1" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table press
-- ----------------------------
ALTER TABLE "public"."press" ADD CONSTRAINT "table_name_pk" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table resources
-- ----------------------------
ALTER TABLE "public"."resources" ADD CONSTRAINT "resources_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table resources_business
-- ----------------------------
ALTER TABLE "public"."resources_business" ADD CONSTRAINT "resources_business_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table role
-- ----------------------------
ALTER TABLE "public"."role" ADD CONSTRAINT "permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table role_permission
-- ----------------------------
ALTER TABLE "public"."role_permission" ADD CONSTRAINT "role_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table scheme
-- ----------------------------
ALTER TABLE "public"."scheme" ADD CONSTRAINT "scheme_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sgg_config_history
-- ----------------------------
ALTER TABLE "public"."sgg_config_history" ADD CONSTRAINT "sgg_config_history_pkey" PRIMARY KEY ("sgg_history_id");

-- ----------------------------
-- Primary Key structure for table subscheme
-- ----------------------------
ALTER TABLE "public"."subscheme" ADD CONSTRAINT "subscheme_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table subscheme_toc
-- ----------------------------
ALTER TABLE "public"."subscheme_toc" ADD CONSTRAINT "subscheme_toc_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_xzq_data
-- ----------------------------
ALTER TABLE "public"."t_xzq_data" ADD CONSTRAINT "g_xzq_datasource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_xzq_table
-- ----------------------------
ALTER TABLE "public"."t_xzq_table" ADD CONSTRAINT "t_xzq_table_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table toc
-- ----------------------------
ALTER TABLE "public"."toc" ADD CONSTRAINT "toc_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table toc_permission
-- ----------------------------
ALTER TABLE "public"."toc_permission" ADD CONSTRAINT "toc_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table toc_subscheme
-- ----------------------------
ALTER TABLE "public"."toc_subscheme" ADD CONSTRAINT "toc_subscheme_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tocsolution
-- ----------------------------
ALTER TABLE "public"."tocsolution" ADD CONSTRAINT "tocsolution_pkey" PRIMARY KEY ("id");
