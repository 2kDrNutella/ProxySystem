DELIMITER //
CREATE PROCEDURE `checkUserUpToDate`(
	IN userUUID CHAR(36),
	IN newUsername VARCHAR(16)
)
BEGIN
	DECLARE userCount INT;
	DECLARE currentUsername VARCHAR(16);
 	 
	SELECT COUNT(*) INTO userCount FROM user u WHERE u.uuid = userUUID;
    
	IF userCount > 0 THEN
   	 
    	SELECT username INTO currentUsername FROM user u WHERE u.uuid = userUUID;
    	UPDATE user SET lastlogin = now() WHERE uuid = userUUID;

    	IF currentUsername <> newUsername THEN
       	 
        	UPDATE user u SET u.username = newUsername WHERE u.uuid = userUUID;
    	END IF;
	ELSE
   	 
    	INSERT INTO user (uuid, username, coins, juweelen, playtime, lastlogin) VALUES (userUUID, newUsername, 0, 0, 0, now());
	END IF;
   	 
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `calculatePlaytime`(
	IN userUUID CHAR(36)
)
BEGIN
	DECLARE lastLoginTimestamp TIMESTAMP;
	DECLARE currentTimestamp TIMESTAMP;
	DECLARE playtimeInSeconds INT;
	DECLARE currentPlaytime BIGINT;

	-- Holen Sie den letzten Anmeldezeitstempel für den Benutzer
	SELECT lastLogin INTO lastLoginTimestamp FROM user WHERE uuid = userUUID;
	SELECT playtime INTO currentPlaytime FROM user WHERE uuid = userUUID;
    
	-- Holen Sie den aktuellen Zeitstempel
	SET currentTimestamp = NOW();

	-- Berechnen Sie die Spielzeit in Sekunden
	SET playtimeInSeconds = TIMESTAMPDIFF(SECOND, lastLoginTimestamp, currentTimestamp);
    

	-- Aktualisieren Sie das playtime-Feld im Long-Format
	UPDATE user SET playtime = currentPlaytime + playtimeInSeconds WHERE UUID = userUUID;
END;
//
DELIMITER ;

DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByUserName`(
	IN p_username VARCHAR(16)
)
BEGIN
	DECLARE v_user_uuid CHAR(36);

	SELECT UUID INTO v_user_uuid FROM user WHERE username = p_username;
    
	IF v_user_uuid IS NOT NULL THEN
    	SELECT * FROM user WHERE UUID = v_user_uuid;
	ELSE
    	SELECT 'Benutzer nicht gefunden' AS message;
	END IF;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `addUserJewels`(
	IN p_user_id INT,
	IN p_additional_value INT
)
BEGIN
	DECLARE v_user_jewels INT;

	-- Den aktuellen Juwelenwert für den Benutzer abrufen
	SELECT juweelen INTO v_user_jewels FROM user WHERE userid = p_user_id;

	-- Überprüfen, ob der Benutzer gefunden wurde
	IF v_user_jewels IS NOT NULL THEN
    	-- Den neuen Juwelenwert berechnen
    	SET v_user_jewels = v_user_jewels + p_additional_value;

    	-- Den aktualisierten Wert in die Datenbank schreiben
    	UPDATE user SET juweelen = v_user_jewels WHERE userid = p_user_id;

    	SELECT 'Update erfolgreich' AS message, v_user_jewels AS new_jewel_value;
	ELSE
    	SELECT 'Benutzer nicht gefunden' AS message;
	END IF;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `removeUserJewels`(
	IN userUUID CHAR(36),
	IN p_removal_value INT
)
BEGIN
	DECLARE v_user_jewels INT;

	-- Den aktuellen Juwelenwert für den Benutzer abrufen
	SELECT juweelen INTO v_user_jewels FROM user WHERE user.uuid = userUUID;

	-- Überprüfen, ob der Benutzer gefunden wurde
	IF v_user_jewels IS NOT NULL THEN
    	IF v_user_jewels >= p_removal_value THEN
        	-- Den neuen Juwelenwert berechnen
        	SET v_user_jewels = v_user_jewels - p_removal_value;
    	-- Den aktualisierten Wert in die Datenbank schreiben
        	UPDATE user SET juweelen = v_user_jewels WHERE user.uuid = userUUID;
        	SELECT 'SUCCESS' AS message;
    	ELSE
        	SELECT 'NOT ENOUGH JUWELEN' AS message;
    	END IF;
	ELSE
    	SELECT 'NO USER FOUND' AS message;
	END IF;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `setUserJewels`(
	IN userUUID CHAR(36),
	IN p_value INT
)
BEGIN
	DECLARE v_user_jewels INT;
	SELECT juweelen INTO v_user_jewels FROM user WHERE user.uuid = userUUID;
    
	IF v_user_jewels IS NOT NULL THEN
        	UPDATE user SET juweelen = p_value WHERE user.uuid = userUUID;
        	SELECT 'SUCCESS' AS message;
	ELSE
    	SELECT 'NO USER FOUND' AS message;
	END IF;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `addUserCoins`(
	IN userUUID CHAR(36),
	IN p_additonal_value INT
)
BEGIN
	DECLARE v_user_coins INT;

	SELECT coins INTO v_user_coins FROM user WHERE user.uuid = userUUID;

	IF v_user_coins IS NOT NULL THEN
        	SET v_user_coins = v_user_coins + p_additonal_value;
        	UPDATE user SET coins = v_user_coins WHERE user.uuid = userUUID;
        	SELECT 'SUCCESS' AS message;
	ELSE
    	SELECT 'NO USER FOUND' AS message;
	END IF;
END
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `removeUserCoins`(
	IN userUUID CHAR(36),
	IN p_removal_value INT
)
BEGIN
	DECLARE v_user_coins INT;

	-- Den aktuellen Juwelenwert für den Benutzer abrufen
	SELECT coins INTO v_user_coins FROM user WHERE user.uuid = userUUID;

	-- Überprüfen, ob der Benutzer gefunden wurde
	IF v_user_coins IS NOT NULL THEN
    	IF v_user_coins >= p_removal_value THEN
        	-- Den neuen Juwelenwert berechnen
        	SET v_user_coins = v_user_coins - p_removal_value;
    	-- Den aktualisierten Wert in die Datenbank schreiben
        	UPDATE user SET coins = v_user_coins WHERE user.uuid = userUUID;
        	SELECT 'SUCCESS' AS message;
    	ELSE
        	SELECT 'NOT ENOUGH COINS' AS message;
    	END IF;
	ELSE
    	SELECT 'NO USER FOUND' AS message;
	END IF;
END
//
DELIMITER ;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `setUserCoins`(
	IN userUUID CHAR(36),
	IN p_value INT
)
BEGIN
	DECLARE v_user_coins INT;
	SELECT coins INTO v_user_coins FROM user WHERE user.uuid = userUUID;
    
	IF v_user_coins IS NOT NULL THEN
        	UPDATE user SET coins = p_value WHERE user.uuid = userUUID;
        	SELECT 'SUCCESS' AS message;
	ELSE
    	SELECT 'NO USER FOUND' AS message;
	END IF;
END
//
DELIMITER ;







