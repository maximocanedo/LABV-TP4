DROP PROCEDURE IF EXISTS getSchedulesForDoctor;
DELIMITER //
CREATE PROCEDURE getSchedulesForDoctor(
    IN doctor_file INT,
    IN startDate DATE
)
BEGIN
    DECLARE diasEnElMes INT DEFAULT 0;
    DECLARE diaMes INT DEFAULT DAYOFMONTH(startDate);
    DECLARE i INT;
    DECLARE date_required DATE DEFAULT startDate;
    SET i = 0;
    SET diasEnElMes = DAYOFMONTH(LAST_DAY(startDate));
    DROP TEMPORARY TABLE IF EXISTS diasDisponibles;
    CREATE TEMPORARY TABLE diasDisponibles (
        fecha DATE,
        turnosDisponibles INT DEFAULT 0
    );
    dias: WHILE diaMes <= diasEnElMes DO
        SET date_required = CONCAT(YEAR(startDate), '-', MONTH(startDate), '-', (diaMes));
        INSERT INTO diasDisponibles
        SELECT date_required as fecha, COUNT(*) as turnosDisponibles
        FROM schedules s
        INNER JOIN doctor_schedules ds ON ds.schedule = s.id
        INNER JOIN doctors d ON ds.doctor = d.id
        WHERE d.file = doctor_file AND (s.beginDay = UPPER(DAYNAME(date_required)) OR s.finishDay = UPPER(DAYNAME(date_required)))
        HAVING turnosdisponibles > 0;
        SET diaMes = diaMes + 1;
    END WHILE dias;
    SELECT fecha FROM diasDisponibles;
END //
DELIMITER ;

CALL getSchedulesForDoctor(15695, '2024-12-01');
-- SELECT @h as horaa;

