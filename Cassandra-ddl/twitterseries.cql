CREATE KEYSPACE 
	twitterseries 
	WITH replication = 
		{
			'class': 'SimpleStrategy', 
			'replication_factor': 1 
		};

CREATE TABLE twitterseries.trendingday(
   year int,
   month int,
   day int,   
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker), year, month, day)
) 
WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC);

