SELECT * FROM schedules s
    INNER JOIN bdmedicos.doctor_schedules ds ON s.id = ds.schedule
    INNER JOIN bdmedicos.doctors d ON ds.doctor = d.id
    WHERE d.id = 222;

/* DELETE FROM doctor_schedules WHERE schedule > -1;
DELETE FROM schedules WHERE id > -1;
ALTER TABLE schedules MODIFY COLUMN startTime VARCHAR(5);
ALTER TABLE schedules MODIFY COLUMN endTime VARCHAR(5); */

UPDATE UserPermit SET allowed = 1 WHERE user = 'alfonso.walker';
UPDATE users SET password = '' WHERE username = 'alfonso.walker';

-- Obtener los turnos de un médico en un día específico.
SELECT * FROM appointments a WHERE a.doctor = 200 AND DATE(a.date) = '2024-01-16';