create table product(
	id serial unique not null,
	name char(20) not null,
	cost integer check(cost>=0)
)

insert into product(name, cost) values ('клубника', 234)
insert into product(name, cost) values ('мясо', 400)
insert into product(name, cost) values ('мандарины', 300)
insert into product(name, cost) values ('инжиры', 500)
insert into product(name, cost) values ('апельсины', 150)

select * from product