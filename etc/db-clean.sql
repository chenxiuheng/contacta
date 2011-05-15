-- $Id: miirc-dropdb.sql 322 2010-11-09 17:12:41Z michele.bianchi $

-- clean organic tables
delete from azpol        ;
delete from azope        ;

delete from ac_j_acc_grp ;
delete from ac_j_acc_rol ;
delete from ac_j_rol_grp ;
delete from acrol        ;
delete from acgrp        ;
delete from acacc        ;

delete from abadr        ;
delete from abper        ;
delete from aborg        ;

--delete from stcdr     ;
--delete from stext     ;
--delete from stmem     ;
delete from stsip     ;
delete from stvmu     ;

--delete from coapo     ;
--delete from cococ     ;
--delete from cocvg     ;
--delete from copho     ;
--delete from coprd     ;
--delete from pxctx     ;
--delete from pxprf     ;
--delete from pxprf_aud ;
--delete from revinfo   ;
