DROP PROCEDURE IF EXISTS get_all_users;
DROP PROCEDURE IF EXISTS update_user;
DROP PROCEDURE IF EXISTS delete_user;

DELIMITER //
CREATE PROCEDURE get_all_users(IN p_country VARCHAR(100), IN p_sort VARCHAR(4))
BEGIN
    IF LOWER(p_sort) = 'desc' THEN
        SELECT id, name, email, country FROM users
        WHERE country LIKE CONCAT('%', COALESCE(p_country, ''), '%') ORDER BY name DESC;
    ELSE
        SELECT id, name, email, country FROM users
        WHERE country LIKE CONCAT('%', COALESCE(p_country, ''), '%') ORDER BY name ASC;
    END IF;
END//

CREATE PROCEDURE update_user(IN p_id INT, IN p_name VARCHAR(100), IN p_email VARCHAR(150), IN p_country VARCHAR(100))
BEGIN
    UPDATE users SET name = p_name, email = p_email, country = p_country WHERE id = p_id;
END//

CREATE PROCEDURE delete_user(IN p_id INT)
BEGIN
    DELETE FROM users WHERE id = p_id;
END//
DELIMITER ;
