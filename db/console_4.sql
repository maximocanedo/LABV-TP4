select beginDay, startTime, endTime, finishDay from schedules
inner join bdmedicos.doctor_schedules ds ON schedules.id = ds.schedule
inner join bdmedicos.doctors d ON ds.doctor = d.id
where d.id = 200 and (beginDay = UPPER(DAYNAME('2024-01-15')) OR finishDay = UPPER(DAYNAME('2024-01-15')));

SELECT UPPER(DAYNAME('2024-01-15'));