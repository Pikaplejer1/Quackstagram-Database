
DROP TABLE IF EXISTS Likes;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Followers;
DROP TABLE IF EXISTS Notification;
DROP TABLE IF EXISTS User_profile;

CREATE TABLE IF NOT EXISTS User_profile(
    username VARCHAR(20) PRIMARY KEY,
    password VARCHAR(64),
    bio VARCHAR(200) DEFAULT NULL,
    pfp_dir VARCHAR(50) NOT NULL
    );


CREATE TABLE IF NOT EXISTS Notification
(
    username_send VARCHAR(20),
    username_received VARCHAR(20),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    type VARCHAR(15),
    PRIMARY KEY (username_send, username_received, timestamp),
    FOREIGN KEY (username_received) REFERENCES User_profile(username) ON UPDATE CASCADE
                                                  
    );


CREATE TABLE IF NOT EXISTS Followers
(
    username_followed VARCHAR(20),
    username_following VARCHAR(20),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (username_followed, username_following),
    FOREIGN KEY (username_following) REFERENCES User_profile(username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    );
   

CREATE TABLE IF NOT EXISTS Post (
   post_id INTEGER PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(20),
   post_photo_dir VARCHAR(50),
   post_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   post_desc VARCHAR(200) DEFAULT NULL,
   FOREIGN KEY (username) REFERENCES User_profile(username)
   ON UPDATE CASCADE ON DELETE CASCADE
);
   

CREATE TABLE IF NOT EXISTS Likes
(
    post_id INTEGER,
    username_who_liked VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id, username_who_liked),
    FOREIGN KEY (post_id) REFERENCES Post(post_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
    FOREIGN KEY (username_who_liked) REFERENCES User_profile(username)
    ON DELETE CASCADE
    );


INSERT INTO User_profile (username, password, bio, pfp_dir) VALUES
('user1', 'password1', 'Bio for user1', '/pfp/user1.png'),
('user2', 'password2', 'Bio for user2', '/pfp/user2.png'),
('user3', 'password3', 'Bio for user3', '/pfp/user3.png'),
('user4', 'password4', 'Bio for user4', '/pfp/user4.png'),
('user5', 'password5', 'Bio for user5', '/pfp/user5.png'),
('user6', 'password6', 'Bio for user6', '/pfp/user6.png'),
('user7', 'password7', 'Bio for user7', '/pfp/user7.png'),
('user8', 'password8', 'Bio for user8', '/pfp/user8.png'),
('user9', 'password9', 'Bio for user9', '/pfp/user9.png'),
('user10', 'password10', 'Bio for user10', '/pfp/user10.png'),
('user11', 'password11', 'Bio for user11', '/pfp/user11.png'),
('user12', 'password12', 'Bio for user12', '/pfp/user12.png'),
('user13', 'password13', 'Bio for user13', '/pfp/user13.png'),
('user14', 'password14', 'Bio for user14', '/pfp/user14.png'),
('user15', 'password15', 'Bio for user15', '/pfp/user15.png'),
('user16', 'password16', 'Bio for user16', '/pfp/user16.png'),
('user17', 'password17', 'Bio for user17', '/pfp/user17.png'),
('user18', 'password18', 'Bio for user18', '/pfp/user18.png'),
('user19', 'password19', 'Bio for user19', '/pfp/user19.png'),
('user20', 'password20', 'Bio for user20', '/pfp/user20.png'),
('user21', 'password21', 'Bio for user21', '/pfp/user21.png'),
('user22', 'password22', 'Bio for user22', '/pfp/user22.png'),
('user23', 'password23', 'Bio for user23', '/pfp/user23.png'),
('user24', 'password24', 'Bio for user24', '/pfp/user24.png'),
('user25', 'password25', 'Bio for user25', '/pfp/user25.png'),
('user26', 'password26', 'Bio for user26', '/pfp/user26.png'),
('user27', 'password27', 'Bio for user27', '/pfp/user27.png'),
('user28', 'password28', 'Bio for user28', '/pfp/user28.png'),
('user29', 'password29', 'Bio for user29', '/pfp/user29.png'),
('user30', 'password30', 'Bio for user30', '/pfp/user30.png');


INSERT INTO Followers (username_followed, username_following) VALUES
('user2', 'user1'),
('user4', 'user3'),
('user6', 'user5'),
('user8', 'user7'),
('user10', 'user9'),
('user12', 'user11'),
('user14', 'user13'),
('user16', 'user15'),
('user18', 'user17'),
('user20', 'user19'),
('user22', 'user21'),
('user24', 'user23'),
('user26', 'user25'),
('user28', 'user27'),
('user30', 'user29'),
('user1', 'user2'),
('user3', 'user4'),
('user5', 'user6'),
('user7', 'user8'),
('user9', 'user10'),
('user11', 'user12'),
('user13', 'user14'),
('user15', 'user16'),
('user17', 'user18'),
('user19', 'user20'),
('user21', 'user22'),
('user23', 'user24'),
('user25', 'user26'),
('user27', 'user28'),
('user29', 'user30');


INSERT INTO Post (username, post_photo_dir, post_desc) VALUES
('user1', '/posts/user1/post1.png', 'Description for post 1 by user1'),
('user2', '/posts/user2/post2.png', 'Description for post 2 by user2'),
('user3', '/posts/user3/post3.png', 'Description for post 3 by user3'),
('user4', '/posts/user4/post4.png', 'Description for post 4 by user4'),
('user5', '/posts/user5/post5.png', 'Description for post 5 by user5'),
('user6', '/posts/user6/post6.png', 'Description for post 6 by user6'),
('user7', '/posts/user7/post7.png', 'Description for post 7 by user7'),
('user8', '/posts/user8/post8.png', 'Description for post 8 by user8'),
('user9', '/posts/user9/post9.png', 'Description for post 9 by user9'),
('user10', '/posts/user10/post10.png', 'Description for post 10 by user10'),
('user11', '/posts/user11/post11.png', 'Description for post 11 by user11'),
('user12', '/posts/user12/post12.png', 'Description for post 12 by user12'),
('user13', '/posts/user13/post13.png', 'Description for post 13 by user13'),
('user14', '/posts/user14/post14.png', 'Description for post 14 by user14'),
('user15', '/posts/user15/post15.png', 'Description for post 15 by user15'),
('user16', '/posts/user16/post16.png', 'Description for post 16 by user16'),
('user17', '/posts/user17/post17.png', 'Description for post 17 by user17'),
('user18', '/posts/user18/post18.png', 'Description for post 18 by user18'),
('user19', '/posts/user19/post19.png', 'Description for post 19 by user19'),
('user20', '/posts/user20/post20.png', 'Description for post 20 by user20'),
('user21', '/posts/user21/post21.png', 'Description for post 21 by user21'),
('user22', '/posts/user22/post22.png', 'Description for post 22 by user22'),
('user23', '/posts/user23/post23.png', 'Description for post 23 by user23'),
('user24', '/posts/user24/post24.png', 'Description for post 24 by user24'),
('user25', '/posts/user25/post25.png', 'Description for post 25 by user25'),
('user26', '/posts/user26/post26.png', 'Description for post 26 by user26'),
('user27', '/posts/user27/post27.png', 'Description for post 27 by user27'),
('user28', '/posts/user28/post28.png', 'Description for post 28 by user28'),
('user29', '/posts/user29/post29.png', 'Description for post 29 by user29'),
('user30', '/posts/user30/post30.png', 'Description for post 30 by user30');


INSERT INTO Notification (username_send, username_received, type) VALUES
('user1', 'user2', 'like'),
('user1', 'user4', 'follow'),
('user5', 'user6', 'like'),
('user7', 'user8', 'follow'),
('user1', 'user10', 'like'),
('user11', 'user12', 'follow'),
('user13', 'user14', 'like'),
('user1', 'user16', 'follow'),
('user17', 'user18', 'like'),
('user19', 'user20', 'follow'),
('user21', 'user22', 'like'),
('user23', 'user24', 'follow'),
('user25', 'user26', 'like'),
('user27', 'user28', 'follow'),
('user29', 'user30', 'like'),
('user2', 'user1', 'follow'),
('user4', 'user3', 'like'),
('user6', 'user5', 'follow'),
('user8', 'user7', 'like'),
('user10', 'user9', 'follow'),
('user12', 'user11', 'like'),
('user14', 'user13', 'follow'),
('user16', 'user15', 'like'),
('user18', 'user17', 'follow'),
('user20', 'user19', 'like'),
('user22', 'user21', 'follow'),
('user24', 'user23', 'like'),
('user26', 'user25', 'follow'),
('user28', 'user27', 'like'),
('user30', 'user29', 'follow');



INSERT INTO Likes (post_id, username_who_liked) VALUES
(1, 'user2'),
(2, 'user1'),
(2, 'user4'),
(2, 'user3'),
(2, 'user6'),
(6, 'user5'),
(7, 'user8'),
(8, 'user7'),
(9, 'user10'),
(10, 'user9'),
(11, 'user12'),
(12, 'user11'),
(13, 'user14'),
(14, 'user13'),
(15, 'user16'),
(16, 'user15'),
(17, 'user18'),
(18, 'user17'),
(19, 'user20'),
(20, 'user19'),
(21, 'user22'),
(22, 'user21'),
(23, 'user24'),
(24, 'user23'),
(25, 'user26'),
(26, 'user25'),
(27, 'user28'),
(28, 'user27'),
(29, 'user30'),
(30, 'user29');

