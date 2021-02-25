create schema labv;
use labv;

create user 'user'@'localhost' identified by 'pass123';
grant select, insert, delete, update on labv.* to user@'localhost';

create table usu_usuario (
    usu_id bigint unsigned auto_increment,
    usu_nome_usuario varchar(50) not null,
    usu_senha varchar(50) not null,
    usu_nome_exibicao varchar(64),
    
    constraint pk_usu primary key(usu_id),
    constraint uk_usu_nome_usuario unique (usu_nome_usuario)
);

create table aut_autorizacao (
    aut_id bigint unsigned auto_increment,
    aut_tipo varchar(16) not null,

    constraint pk_aut primary key(aut_id)
);

create table uau_usuario_autorizacao (
    uau_aut_id bigint unsigned,
    uau_usu_id bigint unsigned,

    constraint fk_uau_aut foreign key(uau_aut_id)
        references aut_autorizacao(aut_id),
    constraint fk_uau_usu foreign key(uau_usu_id)
        references usu_usuario(usu_id),
    constraint pk_uau primary key(uau_aut_id, uau_usu_id)
);

insert into usu_usuario(usu_nome_usuario, usu_senha, usu_nome_exibicao)
    values('rodrigocr16', 'pepino', 'rodrigo reis');

insert into aut_autorizacao(aut_tipo) values('admin');

insert into uau_usuario_autorizacao values(1,1);