#!/usr/bin/env python
# encoding: utf-8

import tweepy  # https://github.com/tweepy/tweepy
import json
import requests


# Twitter API credentials
consumer_key = "GJnfLEEHWB5j08A86YJnAYW3R"
consumer_secret = "gsylZ0e5kL4JCG1PIstOqNAdbfezCv5xoXmz5WTeWpQNFHtz7j"
access_token = "857626179234410496-dPbA2LRwkzpluYBV0dcEacmk94ahXvE"
access_token_secret = "9t88Q1yJKZeZWVFgbkcNyLlSXAdr5rXPCcDsrKh2QfyVI"

from TwitterSearch import *
try:
    tso = TwitterSearchOrder() # create a TwitterSearchOrder object
    tso.set_keywords(['voyage']) # let's define all words we would like to have a look for
    # tso.set_language('fr') # we want to see French tweets only
    tso.set_include_entities(True) # and don't give us all those entity information


    # it's about time to create a TwitterSearch object with our secret tokens
    ts = TwitterSearch(
        consumer_key = consumer_key,
        consumer_secret = consumer_secret,
        access_token = access_token,
        access_token_secret = access_token_secret
     )

     # this is where the fun actually starts :)

    for tweet in ts.search_tweets_iterable(tso):
        if (tweet['place'] is not None) and (tweet['place']['place_type'] == 'city'):
            print( '@%s tweeted: %s' % ( tweet['user']['screen_name'], tweet['text'] ) )
            print(tweet['place'])
            print("tweet_id : {}".format(tweet['id']))
            print("tweet_text : {}".format(tweet['text']))
            print("tweet_language : {}".format(tweet['lang']))
            if
            print("tweet_hashtags : {}".format(tweet['entities']['hashtags']))
            print("tweet_user_profil_location : {}".format(tweet['user']['location']))
            print("tweet_user_time_zone : {}".format(tweet['user']['time_zone']))

            print('')

except TwitterSearchException as e:# take care of all those ugly errors if there are some
    print(e)



