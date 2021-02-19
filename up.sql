create schema labv;
use labv;

create user 'user'@'localhost' identified by 'pass123';
grant select, insert, delete, update on labv.* to user@'localhost';

create table usu_usuario(
    usu_id bigint unsigned auto_increment,
    usu_nome_usuario varchar(50) not null,
    usu_senha varchar(50) not null,
    usu_nome_exibicao varchar(64),
    
    constraint pk_usu_id primary key (usu_id),
    constraint uk_usu_nome_usuario unique (usu_nome_usuario)
);