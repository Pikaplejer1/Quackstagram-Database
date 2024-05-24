#2. Show the total number of posts made by each user. (You will have to decide how this is
#done, via a username or userid)
select count(post_id), p.username
from post p
join quackstagram.user_profile up on up.username = p.username
group by p.username;

#4. Display the top X most liked posts.
select l.post_id,post_desc
from post p
join quackstagram.likes l
on p.post_id = l.post_id
order by l.username_who_liked
LIMIT 5;

#6. List all users who haven’t made a post yet.
select up.username
from user_profile up
left join quackstagram.post p
on up.username = p.username
where p.post_id is null;

#8. Show the user with the highest number of posts.
select up.username, count(up.username) as max_Posts
from user_profile up
join quackstagram.post p
on up.username = p.username
group by p.username
order by max_Posts desc
limit 1;

#10. Find posts that have been liked by all users.
select l.post_id
from likes l
group by l.post_id
having count(username_who_liked) =(
    select count(*)
    from user_profile
    );

#12. Find the average number of likes per post for each user.
select avg(postLikes.likesCount) as "Average likes per post", username
from(
    select l.post_id, count(l.username_who_liked) as likesCount
    from likes l
    group by l.post_id
     ) as postLikes
join post p
on p.post_id = postLikes.post_id
group by username;



#14. List the users who have liked every post of a specific user.
select l.username_who_liked
from likes l
join quackstagram.post p
on l.post_id = p.post_id
where p.username = ?
group by l.username_who_liked
having count(l.post_id) = (
    select count(l.post_id)
    from post p
    where p.username = ?
    );


#16. Find the user(s) with the highest ratio of followers to following.
select f.username_following,
       (count(username_followed)/count(username_following)) as "ratio"
from user_profile up
join quackstagram.followers f
on up.username = f.username_following
group by username_following
order by ratio DESC
limit 1;

#18. Identify users who have not interacted with a specific user’s posts.
select up.username
from user_profile up
left join (
    select username_who_liked
    from likes l
    join quackstagram.post p
    on l.post_id = p.post_id
    where p.username = ?
) as whoLiked
on `whoLiked`.username_who_liked = up.username
where `whoLiked`.username_who_liked is null;


#20. Find users who are followed by more than X% of the platform users.
select f.username_followed,
       count(f.username_following) / (
           select count(*)
           from user_profile
           ) as ratio
from followers f
join quackstagram.user_profile up
on f.username_followed = up.username
group by username_followed
having ratio*100 > 5;


