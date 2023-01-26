
CREATE TABLE estado (
                        id bigint not null auto_increment,
                        nome varchar(80) not null,

                        primary key (id)
) engine=InnoDB default charset=utf8;

INSERT INTO estado (nome) SELECT DISTINCT nome_estado FROM cidade;
ALTER TABLE cidade ADD COLUMN estado_id bigint not null;

UPDATE cidade c SET c.estado_id = (SELECT e.id FROM estado e WHERE e.nome = c.nome_estado);

alter table cidade add constraint fk_cidade_estado
    foreign key (estado_id) references estado(id);

alter table cidade drop column nome_estado;
alter table cidade change nome_cidade nome varchar(80) not null;