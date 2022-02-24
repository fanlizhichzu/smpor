/*
 Navicat Premium Data Transfer

 Source Server         : fanlz
 Source Server Type    : PostgreSQL
 Source Server Version : 90623
 Source Host           : localhost:5432
 Source Catalog        : dingdinguser
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90623
 File Encoding         : 65001

 Date: 25/11/2021 10:55:03
*/


-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "account" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "account_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id" varchar(50) COLLATE "pg_catalog"."default",
  "employee_code" varchar(100) COLLATE "pg_catalog"."default",
  "last_name" varchar(36) COLLATE "pg_catalog"."default",
  "namespace" varchar(36) COLLATE "pg_catalog"."default",
  "nick_name_cn" varchar(36) COLLATE "pg_catalog"."default",
  "realm_id" varchar(36) COLLATE "pg_catalog"."default",
  "realm_name" varchar(36) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(36) COLLATE "pg_catalog"."default",
  "tenant_name" varchar(36) COLLATE "pg_catalog"."default",
  "tenant_user_id" varchar(50) COLLATE "pg_catalog"."default",
  "divisioncode" varchar(36) COLLATE "pg_catalog"."default",
  "userorgname" varchar(36) COLLATE "pg_catalog"."default",
  "mobile" varchar(36) COLLATE "pg_catalog"."default",
  "state" int4,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "last_login_time" timestamp(6)
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "public"."users" VALUES ('1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '330000', '1', '1', 1, '2021-10-20 18:39:46', '2021-10-20 18:39:48', '2021-10-20 18:39:50');
INSERT INTO "public"."users" VALUES ('1', 'zjt0000000020', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '330000', '1', '1', 1, '2021-10-20 18:39:46', '2021-10-20 18:39:48', '2021-10-20 18:39:50');
INSERT INTO "public"."users" VALUES ('2', '2', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '330000', '1', '1', 1, '2021-10-21 16:18:26', '2021-10-21 16:18:29', '2021-10-21 16:18:31');
INSERT INTO "public"."users" VALUES ('2', 'fuseg', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '330000', '1', '1', 1, '2021-10-21 16:18:26', '2021-10-21 16:18:29', '2021-10-21 16:18:31');

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("account_id");
