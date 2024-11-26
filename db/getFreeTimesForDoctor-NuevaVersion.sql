DROP PROCEDURE IF EXISTS getFreeTimesForDoctor;
DELIMITER //
CREATE PROCEDURE getFreeTimesForDoctor(
    IN doctor_file INT,
    IN date_required DATE
)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE beginDay VARCHAR(255);
    DECLARE startTime INT;
    DECLARE endTime INT;
    DECLARE finishDay VARCHAR(255);
    DECLARE hayTurno INT DEFAULT 0;

    DECLARE horarios_doctor CURSOR FOR
SELECT s.beginDay, s.startTime, s.endTime, s.finishDay
FROM schedules s
         INNER JOIN doctor_schedules ds ON s.id = ds.schedule
         INNER JOIN doctors d ON ds.doctor = d.id
WHERE d.file = doctor_file AND s.active = 1
  AND (s.beginDay = UPPER(DAYNAME(date_required))
    OR s.finishDay = UPPER(DAYNAME(date_required)));

DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    DROP TEMPORARY TABLE IF EXISTS horarios;
    CREATE TEMPORARY TABLE IF NOT EXISTS horarios (hora INT);

OPEN horarios_doctor;

loop_horarios: LOOP
        FETCH horarios_doctor INTO beginDay, startTime, endTime, finishDay;
        IF done THEN
            LEAVE loop_horarios;
END IF;

        WHILE startTime < endTime DO
SELECT COUNT(*) INTO hayTurno
FROM appointments a
         INNER JOIN doctors d ON d.id = a.doctor
WHERE d.file = doctor_file
  AND a.active = 1
  AND a.status = 'PENDING'
  AND DATE(a.date) = date_required
  AND HOUR(a.date) = startTime;

IF hayTurno = 0 THEN
                    INSERT INTO horarios (hora) VALUES (startTime);
END IF;

                SET startTime = startTime + 1;
END WHILE;
END LOOP;

CLOSE horarios_doctor;

SELECT * FROM horarios;
END //
DELIMITER ;

CALL getFreeTimesForDoctor(15695, '2024-12-02');
