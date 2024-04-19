use model
go

if db_id('goldb') is not null
	drop database goldb
go

create database goldb
go

use goldb
go


create table board (
    bid     int not null identity(1,1) primary key ,
    name    varchar(50) not null,
    UNIQUE (name)
)
go

create table cell (
    cid         int not null identity(1,1) primary key,
    x           int not null,
    y           int not null,
    value       bit not null,
    boardid     int not null
    constraint fkcellboard foreign key (boardid) references board(bid) 
)
go