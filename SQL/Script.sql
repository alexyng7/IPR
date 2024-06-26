--Отобразите в выводе все данные из таблицы users.
select * 
from users u 

select * 
from tester t 

--Отобразите в выводе все таблицы, принадлежащие к схеме public, за исключением таблиц, содержащих в названии 'backup'
select table_name from information_schema.tables
where table_schema ='public' and table_name not like '%backup%';

--Отобразите в выводе, какой формат данных имеет столбец created_date из таблицы users.
SELECT DATA_TYPE 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'users' AND COLUMN_NAME = 'created_date';

--Поменяйте всем юзерам (таблица users) пароль на $2a$10$lDyjp2iJ2HhWwrDHI5q37O32CIVqENgrztOCxGyCoJqT7TbxzXX92
update users set password = '$2a$10$lDyjp2iJ2HhWwrDHI5q37O32CIVqENgrztOCxGyCoJqT7TbxzXX92'

--Отобразите в выводе, сколько тестировщиков каждого возраста у нас есть (надо tester джойнить с users)
select date_part('year',age(u.birthday)) as возраст, count(*) as "количество пользователей"
from users u 
right join tester t on u.id = t.id 
where u.birthday notnull 
--and u.role in ('ROLE_TESTER', 'ROLE_NEW_TESTER')
group by возраст


--Отобразите в выводе, сколько новых видео (таблица video), закачивалось за каждый час по которому есть данные
select DATE(v.upload_time) as день,
date_part('hour', upload_time) as час,
count(*) as "кол. видео"
from video v
group by день, час
order by день, час

--Составьте запрос, берущий из таблицы tester_backup все записи, где фамилия 'Иванов Иван Иванович', и город 'Ижевск'
select *
from tester_backup tb 
where fio = 'Иванов Иван Иванович' and city = 'Ижевск'


--Сделайте то же самое с таблицей tester. Проанализируйте планы запроса в обоих случаях.
select *
from tester 
where fio = 'Иванов Иван Иванович' and city = 'Ижевск'

--Посмотрите, есть ли индексы на таблицах tester и tester_backup, сделайте вывод.
SELECT
    tbl.relname AS "table",
    idx.relname AS "index",
    col.attname AS "column"
FROM
    pg_index i
JOIN
    pg_attribute col ON col.attrelid = i.indrelid AND col.attnum = ANY(i.indkey)
JOIN
    pg_class idx ON idx.oid = i.indexrelid
JOIN
    pg_class tbl ON tbl.oid = i.indrelid
WHERE
    tbl.relkind = 'r'
    and tbl.relname in ('tester', 'tester_backup')
ORDER BY
    "table",
    "index"   

--Посмотрите на таблицу pg_stat_statements, объясните что там происходит.
    select *
    from pg_stat_statements

    
--Возьмите оттуда самый долгий запрос, вызванный более 100 раз, посмотрите его план, объясните его и составьте теорию как его можно оптимизировать.
    select *
    from pg_stat_statements
    where calls > 100
    order by max_time desc
    limit 3
    
    explain UPDATE shedlock SET lock_until = timezone($4, CURRENT_TIMESTAMP) + cast($1 as interval), 
    locked_at = timezone($5, CURRENT_TIMESTAMP), locked_by = $2 WHERE shedlock.name = $3 AND shedlock.lock_until <= timezone($6, CURRENT_TIMESTAMP)
    
           
--Откройте таблицу tester_backup, добавьте туда две тысячи тестировщиков с рандомным доходом с помощью цикла.


do $$
begin
for i in 1 .. 20 loop
insert into public.tester_backup  (id, city, fio, income) values((select max(id) from tester_backup tb)+1, 'Кукуево', 'Василий Иванович Пупкин', round(random()*(50000-25000+1))+1);
end loop;
end;
$$;

 select count(id)
 from tester_backup tb
 where city = 'Кукуево' and fio = 'Василий Иванович Пупкин'
 
 
 select *
 from tester_backup tb
 where city = 'Кукуево' and fio = 'Василий Иванович Пупкин' 
 
 
    
    
--Продемонстрируйте результаты наставнику.