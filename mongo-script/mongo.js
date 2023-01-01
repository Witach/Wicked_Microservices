use SocialApp
show dbs

show dbs


//++++++++++++++++++ POST APP +++++++++++++
db.createCollection("comments")
db.createCollection("groups")
db.createCollection("groupPosts")
db.createCollection("posts")

//++++++++++++++++++ PROFILE APP +++++++++++++
db.createCollection("profiles")
db.createCollection("users")


