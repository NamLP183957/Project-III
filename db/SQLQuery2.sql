select distinct r.id, r.system_id, l.score * i.score/100 as risk_score, rl.level  from risks r, likelihood_levels l, impact_levels i, risk_levels rl
where r.likelihood_level_id = l.id and r.impact_level_id = i.id 
and l.score * i.score/100 >= rl.range_min 
and l.score * i.score/100 <= rl.range_max
and r.system_id = l.system_id
and r.system_id = i.system_id
and r.system_id = rl.system_id

select * from risks where system_id = 9;
select * from risk_levels where system_id = 9;

drop view assessment
create view assessment(risk_id, system_id, risk_score, risk_level_id) as
select distinct r.id, r.system_id, l.score * i.score/100, rl.id  from risks r, likelihood_levels l, impact_levels i, risk_levels rl
where r.likelihood_level_id = l.id and r.impact_level_id = i.id 
and l.score * i.score/100 >= rl.range_min 
and l.score * i.score/100 <= rl.range_max
and r.system_id = l.system_id
and r.system_id = rl.system_id

select * from assessment

select risk_level_id, COUNT(*) from assessment
where system_id = 9
group by risk_level_id



select id, COUNT(*) as num from assessment join risk_levels on risk_level_id = id
where assessment.system_id = 9
group by id


