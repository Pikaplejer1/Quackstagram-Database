CREATE TABLE IF NOT EXISTS User(
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
    FOREIGN KEY (username_received) REFERENCES User_profile(username)
                                                  ON UPDATE CASCADE
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

CREATE TABLE IF NOT EXISTS Post
(
    post_id INTEGER PRIMARY KEY,
    username VARCHAR(20),
    post_photo_dir VARCHAR(50),
    post_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    post_desc VARCHAR(200) DEFAULT NULL,
    FOREIGN KEY (username) REFERENCES User_profile(username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
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

-- users
INSERT INTO User_profile (username, password, bio, pfp_dir) VALUES
    ('user1', 'password123', 'Bio of user1', '/images/user1.jpg'),
    ('user2', 'password123', 'Bio of user2', '/images/user2.jpg'),
    ('user3', 'password123', 'Bio of user3', '/images/user3.jpg'),
    ('user4', 'password123', NULL, '/images/user4.jpg'),
    ('user5', 'password123', 'Bio of user5', '/images/user5.jpg'),
    ('user6', 'password123', '', '/images/user6.jpg'),
    ('user7', 'password123', 'Bio of user7', '/images/user7.jpg');

-- notifications
INSERT INTO Notification (username_send, username_received, type) VALUES
    ('user1', 'user2', 'like'),
    ('user2', 'user3', 'follow'),
    ('user3', 'user4', 'comment'),
    ('user4', 'user5', 'mention'),
    ('user5', 'user6', 'like'),
    ('user6', 'user1', 'follow'),
    ('user7', 'user3', 'mention');

-- followers
INSERT INTO Followers (username_followed, username_following) VALUES
    ('user1', 'user2'),
    ('user2', 'user3'),
    ('user3', 'user4'),
    ('user4', 'user5'),
    ('user5', 'user6'),
    ('user6', 'user1'),
    ('user1', 'user7');

-- posts
INSERT INTO Post (post_id, username, post_photo_dir, post_desc) VALUES
    (1, 'user1', '/posts/user1_1.jpg', 'Post by user1'),
    (2, 'user2', '/posts/user2_1.jpg', 'Post by user2'),
    (3, 'user3', '/posts/user3_1.jpg', NULL),
    (4, 'user4', '/posts/user4_1.jpg', 'Post by user4'),
    (5, 'user5', '/posts/user5_1.jpg', 'Post by user5'),
    (6, 'user6', '/posts/user6_1.jpg', 'Post by user6'),
    (7, 'user7', '/posts/user7_1.jpg', 'Post by user7');

INSERT INTO Likes (post_id, username_who_liked) VALUES
    (1, 'user2'),
    (2, 'user3'),
    (3, 'user4'),
    (4, 'user5'),
    (5, 'user6'),
    (6, 'user1'),
    (7, 'user7'),
    (1, 'user3'),
    (2, 'user4');


-- user folowuje siebie
INSERT INTO Followers (username_followed, username_following) VALUES
    ('user1', 'user1');

-- user likuje swoj post
INSERT INTO Likes (post_id, username_who_liked)
    VALUES (1, 'user1');