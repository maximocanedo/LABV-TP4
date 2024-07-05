SELECT u.*
FROM users u
LEFT JOIN bdmedicos.doctors d
ON d.user = u.username
WHERE d.user IS NULL;

SELECT * FROM doctors WHERE user = 'arnette.abbott';