create database MemoDB;
use MemoDB;
create table Account
(
	id char(20),
	name varchar(20) not null,
	password varchar(20) not null,
	primary key (id)
) default charset = utf8;
create table Memo
(
	id char(20),
	accid char(20),
	title varchar(20) not null,
	content varchar(1000) not null,
	createDate datetime not null,
	lastModifyDate datetime,
	notificationDate datetime,
	state int not null,
	primary key (id),
	foreign key (accid) references Account(id)
) default charset = utf8;