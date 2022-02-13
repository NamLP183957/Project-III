declare @total int, @status0 int, @status1 int, @status2 int
select @total = COUNT(*) from troubles where system_id = 9
select @status0 = COUNT(*) from troubles where system_id = 9 and status = 0
select @status1 = COUNT(*) from troubles where system_id = 9 and status = 1
select @status2 = COUNT(*) from troubles where system_id = 9 and status = 2
select @total, @status0, @status1, @status2


select COUNT(*) as total from troubles where system_id = 9
union all
select COUNT(*) as status0 from troubles where system_id = 9 and status = 0
union all
select COUNT(*) as status1 from troubles where system_id = 9 and status = 1
union all
select COUNT(*) as status2 from troubles where system_id = 9 and status = 2

select MONTH(time_happen) as month, COUNT(*) as number 
from troubles where system_id = 9 and status = 2
group by MONTH(time_happen)
order by MONTH(time_happen) asc

select status, COUNT(*) as num from troubles where system_id = 9
group by status
order by status asc

select MONTH(time_happen) as month, COUNT(*) as num from troubles
where system_id = 9 and YEAR(time_happen) = 2021 and status = 0
group by MONTH(time_happen) order by MONTH(time_happen) asc
