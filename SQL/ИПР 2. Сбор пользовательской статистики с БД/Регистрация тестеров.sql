select *
from users

--максимальное количество регистраций в год
select EXTRACT(YEAR from updated) as год,
count(*) as "кол. тестеров"
from users
where role = 'ROLE_TESTER'
group by год
order by "кол. тестеров" desc

--Максимальное значение за месяц
select EXTRACT(month from updated) as месяц,
count(*) as "кол. тестеров"
from users
where role = 'ROLE_TESTER' and updated BETWEEN '2022-01-01 0:00:00' AND '2022-12-31 23:59:59'
group by месяц
order by "кол. тестеров" desc

--Пик день
select DATE(updated) as пик_день,
count(*) as "кол. тестеров"
from users
where role = 'ROLE_TESTER' and updated BETWEEN '2022-07-01 0:00:00' AND '2022-07-31 23:59:59'
group by пик_день
order by пик_день

--Пик час
select DATE(updated) as день,
date_part('hour', updated) as час,
count(*) as "кол. тестеров"
from users
where role = 'ROLE_TESTER' and updated BETWEEN '2022-07-15 0:00:00' AND '2022-07-15 23:59:59'
group by день, час
order by час


select date_part('hour', updated) as час,
date_part('minute', updated) as минута,
count(*) as "кол. тестеров"
from users
where role = 'ROLE_TESTER' and updated BETWEEN '2022-07-15 22:00:00' AND '2022-07-15 22:59:59'
group by час, минута 
order by час, минута