DROP PROCEDURE IF EXISTS getFreeTimesForDoctor;
DELIMITER //
CREATE PROCEDURE getFreeTimesForDoctor(
    IN doctor_file INT,
    IN date_required DATE
)
BEGIN
    DECLARE hh INT;
    DECLARE lhh INT;
    DECLARE mm INT;
    DECLARE lmm INT;
    DECLARE schds INT;
    DECLARE i INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE beginDay VARCHAR(255);
    DECLARE startTime TIME;
    DECLARE endTime TIME;
    DECLARE finishDay VARCHAR(255);
    DECLARE hayTurno INT;
    DECLARE horarios_doctor CURSOR FOR
        SELECT s.beginDay, s.startTime, s.endTime, s.finishDay FROM schedules s
        INNER JOIN bdmedicos.doctor_schedules ds ON s.id = ds.schedule
        INNER JOIN bdmedicos.doctors d ON ds.doctor = d.id
        WHERE d.id = doctor_file AND s.active = 1
          AND (s.beginDay = UPPER(DAYNAME(date_required)) OR s.finishDay = UPPER(DAYNAME(date_required)));
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    SET i = 0;
    SET hh = 0;
    SET lhh = 24;
    SET mm = 0;
    SET lmm = 60;
    SET schds = 0;
    SET hayTurno = 0;
    DROP TEMPORARY TABLE IF EXISTS horarios;
    CREATE TEMPORARY TABLE horarios (hora TIME);

    OPEN horarios_doctor;
    por_c_horario: WHILE done = FALSE DO
        -- SELECT 'Before fetch';
        FETCH horarios_doctor INTO beginDay, startTime, endTime, finishDay;
        -- SELECT 'After fetch';
        IF beginDay = UPPER(DAYNAME(date_required)) THEN
            SET hh = HOUR(startTime);
        ELSE SET hh = 0;
        END IF;

        IF finishDay = UPPER(DAYNAME(date_required)) THEN
            SET lhh = HOUR(endTime);
        ELSE SET lhh = 24;
        END IF;

        hw: WHILE hh <= lhh DO
            IF hh = lhh THEN
                SET lmm = MINUTE(endTime);
            ELSE SET lmm = 60;
            END IF;
            mw: WHILE mm < lmm DO
                -- ¿El doctor trabaja a la hora hh:mm?
                    SELECT COUNT(*) INTO hayTurno FROM appointments
                        WHERE doctor = doctor_file
                            AND DATE(date) = DATE(date_required)
                            AND TIME(date)
                                BETWEEN SEC_TO_TIME((hh * 60 * 60) + (mm * 60) - (mm * 14))
                                    AND SEC_TO_TIME((hh * 60 * 60) + (mm * 60));
                    IF hayTurno = 0 THEN
                        INSERT INTO horarios (hora) SELECT SEC_TO_TIME((hh * 60 * 60) + (mm * 60)) as hora;
                    END IF;
                -- ¿Está libre esa hora?
                SET mm = mm + 15;
            END WHILE;
            SET mm = 0;
            SET hh = hh + 1;
        END WHILE hw;



    END WHILE por_c_horario;
    CLOSE horarios_doctor;



    SELECT * FROM horarios;

END //
DELIMITER ;

CALL getFreeTimesForDoctor(200, '2024-01-15');

