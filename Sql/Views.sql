CREATE VIEW content_popularity AS 
SELECT post_id, COUNT(username_who_liked) AS likes FROM Likes
GROUP BY post_id
HAVING COUNT(username_who_liked) > 2
ORDER BY likes DESC;


CREATE VIEW most_active_users AS
SELECT username_send, COUNT(username_send) AS created_notifications
FROM notification
GROUP BY username_send
HAVING COUNT(username_send) > 2
ORDER BY created_notifications DESC;

CREATE VIEW posts_per_day AS
SELECT
    (SELECT COUNT(*) FROM Post) / 
    (SELECT COUNT(DISTINCT DATE(post_timestamp)) FROM Post) AS average_posts_per_day;
   

CREATE INDEX idx_post_id ON Likes(post_id);
DROP INDEX idx_post_id ON Likes;
CREATE INDEX idx_username_send ON Notification(username_send);
DROP INDEX idx_username_send ON Notification;


SELECT * FROM content_popularity;
SELECT * FROM most_active_users;