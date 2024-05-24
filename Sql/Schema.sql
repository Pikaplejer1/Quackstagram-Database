
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
        ('user1', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e', 'Bio for user1', 'pfp/user1.png'), -- password1
        ('user2', '6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4', 'Bio for user2', 'pfp/user2.png'), -- password2
        ('user3', '5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764', 'Bio for user3', 'pfp/user3.png'), -- password3
        ('user4', 'b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b', 'Bio for user4', 'pfp/user4.png'), -- password4
        ('user5', '8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023', 'Bio for user5', 'pfp/user5.png'), -- password5
        ('user6', '598a1a400c1dfdf36974e69d7e1bc98593f2e15015eed8e9b7e47a83b31693d5', 'Bio for user6', 'pfp/user6.png'), -- password6
        ('user7', '5860836e8f13fc9837539a597d4086bfc0299e54ad92148d54538b5c3feefb7c', 'Bio for user7', 'pfp/user7.png'), -- password7
        ('user8', '57f3ebab63f156fd8f776ba645a55d96360a15eeffc8b0e4afe4c05fa88219aa', 'Bio for user8', 'pfp/user8.png'), -- password8
        ('user9', '9323dd6786ebcbf3ac87357cc78ba1abfda6cf5e55cd01097b90d4a286cac90e', 'Bio for user9', 'pfp/user9.png'), -- password9
        ('user10', 'aa4a9ea03fcac15b5fc63c949ac34e7b0fd17906716ac3b8e58c599cdc5a52f0', 'Bio for user10', 'pfp/user10.png'), -- password10
        ('user11', '53d453b0c08b6b38ae91515dc88d25fbecdd1d6001f022419629df844f8ba433', 'Bio for user11', 'pfp/user11.png'), -- password11
        ('user12', 'b3d17ebbe4f2b75d27b6309cfaae1487b667301a73951e7d523a039cd2dfe110', 'Bio for user12', 'pfp/user12.png'), -- password12
        ('user13', '48caafb68583936afd0d78a7bfd7046d2492fad94f3c485915f74bb60128620d', 'Bio for user13', 'pfp/user13.png'), -- password13
        ('user14', 'c6863e1db9b396ed31a36988639513a1c73a065fab83681f4b77adb648fac3d6', 'Bio for user14', 'pfp/user14.png'), -- password14
        ('user15', 'c63c2d34ebe84032ad47b87af194fedd17dacf8222b2ea7f4ebfee3dd6db2dfb', 'Bio for user15', 'pfp/user15.png'), -- password15
        ('user16', '17a3379984b560dc311bb921b7a46b28aa5cb495667382f887a44a7fdbca7a7a', 'Bio for user16', 'pfp/user16.png'), -- password16
        ('user17', '69bfb918de05145fba9dcee9688dfb23f6115845885e48fa39945eebb99d8527', 'Bio for user17', 'pfp/user17.png'), -- password17
        ('user18', 'd2042d75a67922194c045da2600e1c92ff6d87e8fb6e0208606665f2d1dfa892', 'Bio for user18', 'pfp/user18.png'), -- password18
        ('user19', '5790ac3d0b8ae8afc72c2c6fb97654f2b73651c328de0a3b74854ade562dd17a', 'Bio for user19', 'pfp/user19.png'), -- password19
        ('user20', '7535d8f2d8c35d958995610f971287288ab5e8c82a3c4fdc2b6fb5d757a5b9f8', 'Bio for user20', 'pfp/user20.png'), -- password20
        ('user21', '91a9ef3563010ea1af916083f9fb03a117d4d0d2a697f82368da1f737629f717', 'Bio for user21', 'pfp/user21.png'), -- password21
        ('user22', 'd23c1038532dc71d0a60a7fb3d330d7606b7520e9e5ee0ddcdb27ee1bd5bc0cd', 'Bio for user22', 'pfp/user22.png'), -- password22
        ('user23', '8b807aa0505a00b3ef49e26a2ade8e31fcd6c700d1a3aeee971b49d73da8e8ff', 'Bio for user23', 'pfp/user23.png'), -- password23
        ('user24', 'fc8a9296208a0b281f84690423c5d77785e493f435e6292cc322840f543729d3', 'Bio for user24', 'pfp/user24.png'), -- password24
        ('user25', '0b544d6d8da1d1af25318e97e0ac5f6bc70bba49919463dc0074ede01a49d381', 'Bio for user25', 'pfp/user25.png'), -- password25
        ('user26', '869f2a98b0e3a6ea928ff0542330ea3c1e0ff8591801693350f1fc3f1e57e4c5', 'Bio for user26', 'pfp/user26.png'), -- password26
        ('user27', '9c7568513b9c85e73f3650c8b00e3259501096ccee9d3dbdae6ff5e84aabe3af', 'Bio for user27', 'pfp/user27.png'), -- password27
        ('user28', '6f5ea1c4acc7a563ea8cb3381a55f0183a2394d679ebb7db8312e047bbf87e0d', 'Bio for user28', 'pfp/user28.png'), -- password28
        ('user29', '48a94846b2a7386432b90ad13bcf9c66f1efdd3a97e0e14968c262c412fe22c8', 'Bio for user29', 'pfp/user29.png'), -- password29
        ('user30', '7c682dea8e934e04343374e3d25cd51edce9cbeb47f7034296052cb5cd6bed84', 'Bio for user30', 'pfp/user30.png'); -- password30



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
   ('user1', 'posts/user1/post1.png', 'Description for post 1 by user1'),
   ('user2', 'posts/user2/post2.png', 'Description for post 2 by user2'),
   ('user3', 'posts/user3/post3.png', 'Description for post 3 by user3'),
   ('user4', 'posts/user4/post4.png', 'Description for post 4 by user4'),
   ('user5', 'posts/user5/post5.png', 'Description for post 5 by user5'),
   ('user6', 'posts/user6/post6.png', 'Description for post 6 by user6'),
   ('user7', 'posts/user7/post7.png', 'Description for post 7 by user7'),
   ('user8', 'posts/user8/post8.png', 'Description for post 8 by user8'),
   ('user9', 'posts/user9/post9.png', 'Description for post 9 by user9'),
   ('user10', 'posts/user10/post10.png', 'Description for post 10 by user10'),
   ('user11', 'posts/user11/post11.png', 'Description for post 11 by user11'),
   ('user12', 'posts/user12/post12.png', 'Description for post 12 by user12'),
   ('user13', 'posts/user13/post13.png', 'Description for post 13 by user13'),
   ('user14', 'posts/user14/post14.png', 'Description for post 14 by user14'),
   ('user15', 'posts/user15/post15.png', 'Description for post 15 by user15'),
   ('user16', 'posts/user16/post16.png', 'Description for post 16 by user16'),
   ('user17', 'posts/user17/post17.png', 'Description for post 17 by user17'),
   ('user18', 'posts/user18/post18.png', 'Description for post 18 by user18'),
   ('user19', 'posts/user19/post19.png', 'Description for post 19 by user19'),
   ('user20', 'posts/user20/post20.png', 'Description for post 20 by user20'),
   ('user21', 'posts/user21/post21.png', 'Description for post 21 by user21'),
   ('user22', 'posts/user22/post22.png', 'Description for post 22 by user22'),
   ('user23', 'posts/user23/post23.png', 'Description for post 23 by user23'),
   ('user24', 'posts/user24/post24.png', 'Description for post 24 by user24'),
   ('user25', 'posts/user25/post25.png', 'Description for post 25 by user25'),
   ('user26', 'posts/user26/post26.png', 'Description for post 26 by user26'),
   ('user27', 'posts/user27/post27.png', 'Description for post 27 by user27'),
   ('user28', 'posts/user28/post28.png', 'Description for post 28 by user28'),
   ('user29', 'posts/user29/post29.png', 'Description for post 29 by user29'),
   ('user30', 'posts/user30/post30.png', 'Description for post 30 by user30');



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

