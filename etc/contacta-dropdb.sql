-- $Id: miirc-dropdb.sql 322 2010-11-09 17:12:41Z michele.bianchi $

-- contacta
drop table coapo     cascade;
drop table cococ     cascade;
drop table cocvg     cascade;
drop table copho     cascade;
drop table coprd     cascade;
drop table pxctx     cascade;
drop table pxprf     cascade;
drop table pxprf_aud cascade;
drop table revinfo   cascade;
drop table stcdr     cascade;
drop table stext     cascade;
drop table stmem     cascade;
drop table stsip     cascade;
drop table stvmu     cascade;

-- drop organic tables
drop table abadr              cascade;
drop table aborg              cascade;
drop table abper              cascade;
drop table ac_j_acc_grp       cascade;
drop table ac_j_acc_rol       cascade;
drop table ac_j_rol_grp       cascade;
drop table acacc              cascade;
drop table acgrp              cascade;
drop table acrol              cascade;
-- drop table azope              cascade;
-- drop table azpol              cascade;

drop sequence hibernate_sequence cascade;
