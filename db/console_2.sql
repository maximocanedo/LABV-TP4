-- Crear la tabla temporal para generar números
CREATE TEMPORARY TABLE numbers (n INT);

-- Rellenar la tabla de números
INSERT INTO numbers (n) VALUES (0),(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(52),(53),(54),(55),(56),(57),(58),(59),(60),(61),(62),(63),(64),(65),(66),(67),(68),(69),(70),(71),(72),(73),(74),(75),(76),(77),(78),(79),(80);

-- Parámetros
SET @doctorFile = 200;   -- ID del doctor
SET @dia = '2024-01-16'; -- Fecha en la que quieres verificar
SET @start_time = '10:15:00'; -- Hora de inicio
SET @end_time = '12:00:00';   -- Hora de fin


SELECT
    times.time AS available_time,
    a.date,
    a.id,
    a.doctor
INTO @freeTime
FROM (
    SELECT
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) AS time
    FROM
        numbers
    WHERE
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) <= @end_time
) AS times
LEFT JOIN bdmedicos.appointments a
    ON times.time = TIME(a.date)
    AND DATE(a.date) = @dia
    AND a.doctor = @doctorFile
WHERE a.id IS NULL;
-- INNER JOIN schedules s ON s.id = ds.schedule;






-- Generar los horarios en intervalos de 15 minutos y verificar si existen turnos
SELECT
    times.time AS available_time,
    a.date,
    a.id
FROM (
    SELECT
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) AS time
    FROM
        numbers
    WHERE
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) <= @end_time
) AS times
LEFT JOIN bdmedicos.appointments a
    ON times.time = TIME(a.date)
    AND DATE(a.date) = @dia
    AND a.doctor = @doctorFile
WHERE a.id IS NULL;
 /*
SELECT
    times.time AS available_time,
    a.date,
    a.id,
    a.doctor
INTO @freeTime
FROM (
    SELECT
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) AS time
    FROM
        numbers
    WHERE
        ADDTIME(@start_time, SEC_TO_TIME(n * 900)) <= @end_time
) AS times
LEFT JOIN bdmedicos.appointments a
    ON times.time = TIME(a.date)
    AND DATE(a.date) = @dia
    AND a.doctor = @doctorFile
WHERE a.id IS NULL;*/