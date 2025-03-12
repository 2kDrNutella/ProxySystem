CREATE DEFINER=`root`@`localhost` PROCEDURE `calculatePlaytime`(
	IN userUUID CHAR(36)
)
BEGIN
	DECLARE lastLoginTimestamp TIMESTAMP;
	DECLARE currentTimestamp TIMESTAMP;
	DECLARE playtimeInSeconds INT;
	DECLARE currentPlaytime BIGINT;

    SELECT lastLogin INTO lastLoginTimestamp FROM user WHERE uuid = userUUID;
    SELECT playtime INTO currentPlaytime FROM user WHERE uuid = userUUID;

    SET currentTimestamp = NOW();

	SET playtimeInSeconds = TIMESTAMPDIFF(SECOND, lastLoginTimestamp, currentTimestamp);

    UPDATE user SET playtime = currentPlaytime + playtimeInSeconds WHERE UUID = userUUID;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkUserUpToDate`(
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
    	    INSERT INTO user (uuid, username, playtime, lastlogin) VALUES (userUUID, newUsername, 0, now());
    END IF;
END


CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByUserName`(
	IN username VARCHAR(16)
)
BEGIN
	DECLARE user_uuid CHAR(36);

    SELECT UUID INTO user_uuid FROM user WHERE username = username;

    IF user_uuid IS NOT NULL THEN
        SELECT * FROM user u WHERE u.UUID = user_uuid;
    ELSE
        SELECT 'User not found' AS message;
    END IF;
END