-- Create function to get post creator
DELIMITER $$

CREATE FUNCTION GetPostCreator(p_post_id INT) RETURNS VARCHAR(20)
READS SQL DATA
BEGIN
    DECLARE creator_username VARCHAR(20);
    
    -- Retrieve the username of the post creator
    SELECT username INTO creator_username
    FROM Post
    WHERE post_id = p_post_id;
    
    RETURN creator_username;
END $$

DELIMITER ;

-- Create procedure to insert a notification
DELIMITER $$

CREATE PROCEDURE InsertNotification (
    IN p_username_send VARCHAR(20),
    IN p_username_received VARCHAR(20),
    IN p_type VARCHAR(20)
)
BEGIN
    INSERT INTO Notification (username_send, username_received, type)
    VALUES (p_username_send, p_username_received, p_type);
END $$

DELIMITER ;

-- Create trigger for new likes
DELIMITER $$

CREATE TRIGGER after_like_insert
AFTER INSERT ON Likes
FOR EACH ROW
BEGIN
    DECLARE creator_username VARCHAR(20);
    
    -- Get the creator of the post that was liked
    SET creator_username = GetPostCreator(NEW.post_id);

    -- Insert a new notification
    CALL InsertNotification(NEW.username_who_liked, creator_username, 'like');
END $$

DELIMITER ;

-- Create trigger for new followers
DELIMITER $$

CREATE TRIGGER after_followers_insert
AFTER INSERT ON Followers
FOR EACH ROW
BEGIN
    -- Insert a new follow notification
    CALL InsertNotification(NEW.username_following, NEW.username_followed, 'follow');
END $$

DELIMITER ;
