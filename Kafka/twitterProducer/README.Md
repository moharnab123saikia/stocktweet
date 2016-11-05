### Dependencies:

* twython
```
sudo pip install twython
```

* Download Kafka binary file from https://kafka.apache.org/downloads.html


### Steps to Run

1. Navigate to Kafka folder
```
cd kafka_2.11-0.10.0.0
```
2. Start zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
If it shows the following error:
```
ERROR Unexpected exception, exiting abnormally (org.apache.zookeeper.server.ZooKeeperServerMain)
java.net.BindException: Address already in use
```
Stop the existing running instance of zookeeper with:
```
sudo service zookeeper stop
```
and start again.
3. Start Kafka Server:
```
bin/kafka-server-start.sh config/server.properties
```
4. Create a topic named: twitterStream
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic twitterStream
```
You can check the created topic with :
```
bin/kafka-topics.sh --list --zookeeper localhost:2181
```
5. Run twitterAPI.py:
```
python twitterAPI
```
It should fetch the data corresponding to stocks :
```
{
'contributors': None, 
'truncated': False, 
'text': 'Screen through high rated articles for US Tech Kings at once $AAPL $GOOG $FB https://t.co/kdZSg01UFl https://t.co/Q2p0x9SAOc', 
'is_quote_status': False, 
'in_reply_to_status_id': None, 
'id': 794742561277612032, 
'favorite_count': 0, 
'source': '<a href="http://www.buzzjust.in/" rel="nofollow">BiteTheBuzz</a>', 
'retweeted': False, 
'coordinates': None, 
'timestamp_ms': '1478316366868', 
'entities': 
          {'user_mentions': [], 
          'symbols': [{'indices': [61, 66], 'text': 'AAPL'}, {'indices': [67, 72], 'text': 'GOOG'}, {'indices': [73, 76], 'text': 'FB'}], 
          'hashtags': [], 
          'urls': [
          {'url': 'https://t.co/kdZSg01UFl', 'indices': [77, 100], 'expanded_url': 'http://www.cityfalcon.com/watchlists?name=US%20Tech%20Giants', 'display_url': u'cityfalcon.com/watchlists?nam\u2026'}], 
          'media': [{'expanded_url': 'https://twitter.com/BUZZ_Just_In/status/794742561277612032/photo/1', 'display_url': 'pic.twitter.com/Q2p0x9SAOc', 'url': 'https://t.co/Q2p0x9SAOc', 'media_url_https': 'https://pbs.twimg.com/media/Cwd-JW8WEAA2RpO.jpg', 'id_str': '794742558496788480', 'sizes': {'small': {'h': 340, 'resize': 'fit', 'w': 680}, 'large': {'h': 512, 'resize': 'fit', 'w': 1024}, 'medium': {'h': 512, 'resize': 'fit', 'w': 1024}, 'thumb': {'h': 150, 'resize': 'crop', 'w': 150}}, 'indices': [101, 124], 'type': 'photo', 'id': 794742558496788480, 'media_url': 'http://pbs.twimg.com/media/Cwd-JW8WEAA2RpO.jpg'}]}, 'in_reply_to_screen_name': None, 'id_str': '794742561277612032', 'display_text_range': [0, 100], 'retweet_count': 0, 'in_reply_to_user_id': None, 'favorited': False, 'user': {'follow_request_sent': None, 'profile_use_background_image': True, 'default_profile_image': False, 'id': 2373813025, 'verified': False, 'profile_image_url_https': 'https://pbs.twimg.com/profile_images/788329987552182272/rB0hiwzA_normal.jpg', 'profile_sidebar_fill_color': 'DDEEF6', 'profile_text_color': '333333', 'followers_count': 830, 'profile_sidebar_border_color': 'C0DEED', 'id_str': '2373813025', 'profile_background_color': 'C0DEED', 'listed_count': 100, 'profile_background_image_url_https': 'https://abs.twimg.com/images/themes/theme1/bg.png', 'utc_offset': None, 'statuses_count': 130155, 'description': 'Multi-award winning FinTech startup making sense of financial news and tweets', 'friends_count': 378, 'location': 'London', 'profile_link_color': '1DA1F2', 'profile_image_url': 'http://pbs.twimg.com/profile_images/788329987552182272/rB0hiwzA_normal.jpg', 'following': None, 'geo_enabled': False, 'profile_banner_url': 'https://pbs.twimg.com/profile_banners/2373813025/1426349451', 'profile_background_image_url': 'http://abs.twimg.com/images/themes/theme1/bg.png', 'name': 'CityFALCON Analyst', 'lang': 'en', 'profile_background_tile': False, 'favourites_count': 2401, 'screen_name': 'BUZZ_Just_In', 'notifications': None, 'url': 'http://www.cityfalcon.com', 'created_at': 'Wed Mar 05 14:01:04 +0000 2014', 'contributors_enabled': False, 'time_zone': None, 'protected': False, 'default_profile': True, 'is_translator': False}, 'geo': None, 'in_reply_to_user_id_str': None, 'possibly_sensitive': False, 'lang': 'en', 'created_at': 'Sat Nov 05 03:26:06 +0000 2016', 'filter_level': 'low', 'in_reply_to_status_id_str': None, 'place': None, 'extended_entities': {'media': [{'expanded_url': 'https://twitter.com/BUZZ_Just_In/status/794742561277612032/photo/1', 'display_url': 'pic.twitter.com/Q2p0x9SAOc', 'url': 'https://t.co/Q2p0x9SAOc', 'media_url_https': 'https://pbs.twimg.com/media/Cwd-JW8WEAA2RpO.jpg', 'id_str': '794742558496788480', 'sizes': {'small': {'h': 340, 'resize': 'fit', 'w': 680}, 'large': {'h': 512, 'resize': 'fit', 'w': 1024}, 'medium': {'h': 512, 'resize': 'fit', 'w': 1024}, 'thumb': {'h': 150, 'resize': 'crop', 'w': 150}}, 'indices': [101, 124], 'type': 'photo', 'id': 794742558496788480, 'media_url': 'http://pbs.twimg.com/media/Cwd-JW8WEAA2RpO.jpg'}]
          }
}
```
