--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
drop table favori;
create table favori
(
  favori_id     uuid          not null primary key,
  auditeur      varchar(80)   not null,
  ajoute_le      timestamp     not null default current_timestamp,

  morceau_id    varchar(40)   not null,
  titre         varchar(255)  not null,
  titre_album    varchar(400)  null,
  annee_edition  varchar(8)    null,
  auteurs       varchar(400)  null,
  visuel_fip     varchar(2000) null,
  visuel_youtube varchar(2000) null,
  lien_youtube   varchar(2000) null
);
truncate favori;
insert into favori (favori_id, auditeur,
                    morceau_id, titre, lien_youtube)
values ('421a3a53-fdbd-4523-9e23-14dd8cfd006c', 'zoo',
        'bfa30784-fc6a-4c63-b279-715c668bd2a6', 'EL NINO', 'https://www.youtube.com/watch?v=-K3LnkDiRH4');
select * from favori;
