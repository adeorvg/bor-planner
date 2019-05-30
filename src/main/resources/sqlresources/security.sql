create table authorities (
                           username varchar(50) not null,
                           authority varchar(50) not null,
                           constraint fk_authorities_users foreign key(username) references users(username));

insert into authorities(username,authority)
values('astefaniak','ROLE_ADMIN');
insert into authorities(username,authority)
values('bkowalski','ROLE_ADMIN');
insert into authorities(username,authority)
values('bkowalewicz','ROLE_ADMIN');
insert into authorities(username,authority)
values('bwielski','ROLE_ADMIN');
insert into authorities(username,authority)
values('jnowak','ROLE_ADMIN');
insert into authorities(username,authority)
values('kadamczyk','ROLE_ADMIN');
insert into authorities(username,authority)
values('kwolny','ROLE_ADMIN');
insert into authorities(username,authority)
values('mklos','ROLE_ADMIN');
insert into authorities(username,authority)
values('mnietoperz','ROLE_ADMIN');
insert into authorities(username,authority)
values('mkowalski','ROLE_ADMIN');
insert into authorities(username,authority)
values('mwysoki','ROLE_ADMIN');
insert into authorities(username,authority)
values('mnowakowski','ROLE_ADMIN');
insert into authorities(username,authority)
values('mchlopski','ROLE_ADMIN');
insert into authorities(username,authority)
values('mwiatr','ROLE_ADMIN');
insert into authorities(username,authority)
values('pbosak','ROLE_ADMIN');
insert into authorities(username,authority)
values('sniski','ROLE_ADMIN');
insert into authorities(username,authority)
values('skozlowski','ROLE_ADMIN');
insert into authorities(username,authority)
values('sworkowski','ROLE_ADMIN');
insert into authorities(username,authority)
values('tniewiem','ROLE_ADMIN');
insert into authorities(username,authority)
values('wkrosno','ROLE_ADMIN');
