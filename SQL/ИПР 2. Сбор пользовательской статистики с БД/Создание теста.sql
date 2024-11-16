select *
from orders


--За какой год есть данные
select EXTRACT(YEAR from created) as год,
count(*) as "кол. тестов"
from orders
group by год
order by "кол. тестов" desc 

--Максимальное значение за месяц
select EXTRACT(month from created) as месяц,
count(*) as "кол. тестов"
from orders
where created BETWEEN '2022-01-01 0:00:00' AND '2022-12-31 23:59:59'
group by месяц
order by "кол. тестов" desc

--Пик день
select DATE(created) as пик_день,
count(*) as "кол. тестов"
from orders
where created BETWEEN '2022-07-01 0:00:00' AND '2022-07-31 23:59:59'
group by пик_день
order by пик_день

--Пик час
select DATE(created) as день,
date_part('hour', created) as час,
count(*) as "кол. тестов"
from orders
where created BETWEEN '2022-07-15 0:00:00' AND '2022-07-15 23:59:59'
group by день, час
order by час 

select date_part('hour', created) as час,
date_part('minute', created) as минута,
count(*) as "кол. тестов"
from orders
where created BETWEEN '2022-07-15 22:00:00' AND '2022-07-15 22:59:59'
group by час, минута 
order by час, минута