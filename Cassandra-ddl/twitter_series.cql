DROP KEYSPACE IF EXISTS twitter_series;
CREATE KEYSPACE twitter_series WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };

use twitter_series;

CREATE TABLE TrendingMinute (
   year int,
   month int,
   day int,
   hour int,
   minute int,
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker),year, month, day, hour, minute)
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC, hour DESC, minute DESC);

CREATE TABLE TrendingHour (
   year int,
   month int,
   day int,
   hour int,
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker),year, month, day, hour)
) WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC, hour DESC);


CREATE TABLE trendingday(
   year int,
   month int,
   day int,   
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker), year, month, day)
) 
WITH CLUSTERING ORDER BY (year DESC, month DESC, day DESC);

CREATE TABLE TrendingWeek (
   year int,
   week int,
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker),year, week)
) WITH CLUSTERING ORDER BY (year DESC, week DESC);

CREATE TABLE TrendingMonth (
   year int,
   month int,
   frequency int,
   ticker text,
   sentiment int,
   PRIMARY KEY ((ticker),year, month)
) WITH CLUSTERING ORDER BY (year DESC, month DESC);

CREATE TABLE TrendingYear (
   year int,
   ticker text,
   frequency int,
   sentiment int,
   PRIMARY KEY ((ticker),year)
) WITH CLUSTERING ORDER BY (year DESC);




