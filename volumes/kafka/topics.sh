kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-request-create --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-added-event --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-removed-event --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-exists-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-exists-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-create-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-create-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-delete-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-deleteattachment-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-update-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-deleteattachment-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-delete-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic post-update-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-create-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-create-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-create-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-delete-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-update-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-profileadd-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-getall-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-profileadd-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-delete-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic grouppost-getall-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-create-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-update-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-exists-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-profileremove-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-profileremove-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic group-exists-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-create-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-create-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-edit-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-edit-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic comment-delete-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-delete-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic comment-create-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic comment-delete-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic comment-create-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic reply-delete-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-update-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-removefromgroup-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-delete-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-addtogroup-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-getall-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-delete-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-creat-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-getall-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-starttofollow-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-creat-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-stoptofollow-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-get-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-addtogroup-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-update-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-getall-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user-update-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-removefromgroup-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-get-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-getall-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-starttofollow-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-stoptofollow-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic profile-update-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-search-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-search-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadgroup-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-search-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadprofilepost-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadgrouppost-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadprofilepost-response --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-search-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadgroup-request --replication-factor 1 --partitions 1
kafka-topics.sh --bootstrap-server localhost:9092 --create --topic feed-loadgrouppost-request --replication-factor 1 --partitions 1