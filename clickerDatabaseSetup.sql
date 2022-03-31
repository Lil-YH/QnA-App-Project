create database if not exists clicker;

use clicker; 

drop table if exists responses;

create table responses (
	questionNo int,
	choice varchar(1),
	username varchar(30));

insert into responses values (1, 'A', 'admin');
