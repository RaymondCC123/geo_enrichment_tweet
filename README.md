# geo_enrichment_tweet


I often use a combination of methods to locate Twitter users. Various methods will have varying certainty. Efe Sevin's response about using the user reported location field is very useful. Generally, these profile locations can be useful (depending on your needs). You do have to deal with misspellings and formatting of these locations and filter out obvious spurious places i.e. "Mars".


There is another option that has worked well for me as well. The Twitter Search API has a geocode parameter you can pass to search for Tweets. For the geocode parameter, you specify latitude, longitude, and a radius (mi or km). You can create many such "circles" that cover all your areas of interest at a granularity you need. Interestingly, Twitter returns tweets within your given circle by using their own internal methods to geolocate tweets. Twitter uses a combination of device/gps coordinates, user provided profile location, and (I believe/suspect) network/ip address location to determine tweets that fit within the geocode search parameter.

Here's an example query:

https://api.twitter.com/1.1/search/tweets.json?q=%20&geocode=37.781157%2C-122.398720%2C1mi

Note: the q or query parameter is required; however, I used a space (%20) as a way to search for all tweets from the given lat,long,radius.


We used a similar method for topic modeling tobacco-related tweets. Also, Dredze and colleagues have developed an interesting tool (named CARMEN) to geolocate tweets based on a combination of methods. I've attached a link to one of their articles.


Also, I think it's important to note that the latitude/longitude (provided through a GPS enabled device) is tied to a Twitter user NOT a specific tweet/status update. This is true as well with user-reported profile location. This is just something to keep in mind.

Twitter Search API : https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets
