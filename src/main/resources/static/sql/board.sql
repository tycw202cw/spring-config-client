create table board(
	bno bigint primary key auto_increment ,
	title varchar(255) not null ,
	content text not null ,
	read_count int default 0 ,
	created_date timestamp default current_timestamp() ,
	updated_date timestamp default current_timestamp() on update current_timestamp()
);

create table board(
	bno bigint primary key auto_increment ,
	title varchar(255) not null ,
	content text not null ,
	writer varchar(255) not null ,
	read_count int default 0 ,
	created_date timestamp default current_timestamp() ,
	updated_date timestamp default current_timestamp() on update current_timestamp()
);

create table goods(
	gno bigint primary key auto_increment ,
	name varchar(255) not null ,
	content text not null ,
	price int not null ,
	created_date timestamp default current_timestamp() ,
	updated_date timestamp default current_timestamp() on update current_timestamp()
);

create table goods_file(
	fno bigint primary key auto_increment ,
	url varchar(255) not null ,
	name varchar(255) not null ,
	size bigint not null ,
	created_date timestamp default current_timestamp() ,
	updated_date timestamp default current_timestamp() on update current_timestamp() ,
	gno bigint not null ,
	constraint fk_goods_file_goods foreign key(gno) references goods(gno)
);

--auto_increment 된 pk 조회하는 방법
SELECT LAST_INSERT_ID();
