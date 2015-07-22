/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     22/07/2015 1:32:16 PM                        */
/*==============================================================*/


drop table if exists "order";

drop table if exists product;

drop table if exists store;

drop table if exists user;

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table "order"
(
   id                   bigint not null,
   order_number         varbinary(10),
   state                int,
   store_id             int,
   order_time           timestamp,
   payment_time         timestamp,
   deliver_time         timestamp,
   product_id           varchar(100),
   price                doubel
);

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   bigint not null,
   name                 varchar(100) not null,
   description          varchar(100),
   price                double not null default 0.00,
   picture              varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: store                                                 */
/*==============================================================*/
create table store
(
   id                   bigint not null,
   name                 varchar(100),
   address              varchar(200),
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null,
   first_name           varchar(100),
   last_name            varchar(100),
   email                varchar(100),
   password             varchar(100),
   points               int default 0,
   primary key (id)
);

