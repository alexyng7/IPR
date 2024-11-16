select *
from video

--За какой год есть данные
select EXTRACT(YEAR from upload_time) as год,
count(*) as "кол. видео"
from video v
group by год
order by "кол. видео" desc 

--Максимальное значение за месяц
select EXTRACT(month from upload_time) as месяц,
count(*) as "кол. видео"
from video v
where upload_time BETWEEN '2022-01-01 0:00:00' AND '2022-12-31 23:59:59'
group by месяц
order by "кол. видео" desc

--Пик день
select DATE(v.upload_time) as пик_день,
count(*) as "кол. видео"
from video v
where upload_time BETWEEN '2022-07-01 0:00:00' AND '2022-07-31 23:59:59'
group by пик_день
order by пик_день

--Пик час
select DATE(v.upload_time) as день,
date_part('hour', upload_time) as час,
count(*) as "кол. видео"
from video v
where upload_time BETWEEN '2022-07-15 0:00:00' AND '2022-07-15 23:59:59'
group by день, час
order by час

select date_part('hour', upload_time) as час,
date_part('minute', upload_time) as минута,
count(*) as "кол. видео"
from video
where upload_time BETWEEN '2022-07-15 22:00:00' AND '2022-07-15 22:59:59'
group by час, минута 
order by час, минута