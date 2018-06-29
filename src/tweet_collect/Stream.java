package tweet_collect;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


import org.apache.commons.lang.StringEscapeUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Stream {


    static Query query = new Query("screen_name =");
    static TwitterStream twitter;
    static Connection con;
    static TwitterFactory tf;
    static String ConsumerKey = "GJnfLEEHWB5j08A86YJnAYW3R";
    static String ConsumerSecret = "gsylZ0e5kL4JCG1PIstOqNAdbfezCv5xoXmz5WTeWpQNFHtz7j";
    static String oauth_token = "857626179234410496-dPbA2LRwkzpluYBV0dcEacmk94ahXvE";
    static String oauth_token_secret = "9t88Q1yJKZeZWVFgbkcNyLlSXAdr5rXPCcDsrKh2QfyVI";


    public static void main(String args[]) throws IOException {
        Connection con = mySQLInit("root", "");
        executeRequest(con);
        //exit(con);
    }

    public static void executeRequest(Connection con) throws IOException {
        // ***********************************************SET CONNECTION******************************************


        try {
            ConfigurationBuilder cb = new ConfigurationBuilder(); //ConfigurationBuilder declared cb is a new instance of this
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(ConsumerKey) //sets the Consumer Key String
                    .setOAuthConsumerSecret(ConsumerSecret) //sets the Consumer Secret String
                    .setOAuthAccessToken(oauth_token)
                    .setOAuthAccessTokenSecret(oauth_token_secret);
            cb.setJSONStoreEnabled(true);

            twitter = new TwitterStreamFactory(cb.build()).getInstance();

        } catch (Exception e) {


            System.out.println("could not set consumer cred:" + e.getMessage());
            e.printStackTrace();
            System.exit(0);


        }
        PagableResponseList<User> result = null;

        StatusListener listener;
        listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status arg0) {
                // TODO Auto-generated method stub
                System.out.println(arg0);
                addUser(con, arg0);

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }


        };

        FilterQuery filter = new FilterQuery();
        //String[] kwds = {"trump","clinton"};
        //String[] kwds = {"iphone","ipad", "mac", "apple"};
        String[] kwds = {"attack"};
        filter.track(kwds);

        twitter.addListener(listener);

        twitter.filter(filter);
        //QueryResult search = twitter.search(new Query("obama"));


        //int sl = 0;

	/*	if(result.getRateLimitStatus().getRemaining()<1)
				try{
			Thread.sleep(15*60*1000);
			sl++;
			System.out.println("sleep: "+sl);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}*/


    }


    public static Connection mySQLInit(String username, String pass) {
        String SQLURL = "com.mysql.jdbc.Driver";
        Connection con = null;
        Statement st = null;
        try {
            Class.forName(SQLURL);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String connectionString = "jdbc:mysql://localhost?character_set_server=utf8mb4&characterSetResults=utf8&&characterEncoding=utf-8";


        try {
            con = DriverManager.getConnection(connectionString, username, pass);
            st = con.createStatement();
            st.execute("use stream;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

///------------------------------------------------------------------------------------------------------


    public static void exit(Connection con) {

        try {


            con.close();

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

    private static void addUser(Connection con, Status status) {
        User user = status.getUser();
        try{

            Date currentDate = new Date();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateString = sdf.format(currentDate);
            long currentTime = currentDate.getTime();
            String userDate = sdf.format(user.getCreatedAt());
            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());
            String	toReturn = "insert into user_instance values (DEFAULT,"+user.getAccessLevel()+","
                    + "'"+entirelyInBasicMultilingualPlane(user.getDescription())+"',"+user.getFavouritesCount()+","+user.getFollowersCount()+","
                    + user.getFriendsCount()+","+user.getId()+",'"+entirelyInBasicMultilingualPlane(user.getLang())+"',"+user.getListedCount()+",'"
                    + entirelyInBasicMultilingualPlane(user.getLocation())+"','"+entirelyInBasicMultilingualPlane(user.getName())+"','"
                    + entirelyInBasicMultilingualPlane(user.getProfileLinkColor())+"','"+entirelyInBasicMultilingualPlane(user.getProfileSidebarBorderColor())+"','"+entirelyInBasicMultilingualPlane(user.getProfileSidebarBorderColor())+"','"
                    +entirelyInBasicMultilingualPlane(user.getProfileTextColor())+ "','"+entirelyInBasicMultilingualPlane(user.getScreenName())+"','"
                    +user.getStatusesCount()+ "','"+entirelyInBasicMultilingualPlane(user.getTimeZone())+"',"+user.getUtcOffset()+","+user.isContributorsEnabled()+","
                    + user.isDefaultProfile()+","+user.isDefaultProfileImage()+","+user.isFollowRequestSent()+","+user.isGeoEnabled()+","+user.isProfileBackgroundTiled()+","
                    + user.isProfileUseBackgroundImage()+", "+user.isProtected()+","+user.isShowAllInlineMedia()+","+user.isTranslator()+","
                    +user.isVerified() +",'"+userDate+"',"+user.getCreatedAt().getTime()+",0,'"+currentDateString+"',"+currentTime+",'2018-06-09 23:44:02',0);\n";


            Statement st = con.createStatement();
            st.executeUpdate(toReturn);

            long idsCount = status.getUser().getFollowersCount();
            String longitude = null;
            String latitude = null;
            try{
                longitude  = String.valueOf(status.getGeoLocation().getLongitude());
                latitude = String.valueOf(status.getGeoLocation().getLatitude());
            }catch(Exception ex1){}
            long retweetedFrom = -1;
            try{
                retweetedFrom = status.getRetweetedStatus().getId();

            }catch(Exception tw){}
            long originalTweeterid = -1;
            String originalTweeterScr = null;
            String originalTweeterName = null;
            try{
                originalTweeterid = status.getRetweetedStatus().getUser().getId();
                originalTweeterScr = status.getRetweetedStatus().getUser().getScreenName();
                originalTweeterName = status.getRetweetedStatus().getUser().getName();
            }catch(Exception tw2){}
            String InReplyToUserId = null;
            try{
                InReplyToUserId = String.valueOf(status.getInReplyToUserId());
            }catch(Exception tw2){}
            long twitterTime = status.getCreatedAt().getTime();
            String twitterDateString  = sdf.format(status.getCreatedAt());

            int nbhashtags = 0;
            int nbcontributors = 0;
            int nbmediaEntities = 0;
            int nbextendedMediaEntities=0;
            int nbsymbolEntities = 0;
            int nbURLEntities = 0;
            int nbmentionEntities = 0;
            if(status.getHashtagEntities()!= null && status.getHashtagEntities().length>0){
                nbhashtags = status.getHashtagEntities().length;



            }

            if(status.getContributors()!= null && status.getContributors().length>0){
                nbcontributors = status.getContributors().length;

            }
            if(status.getMediaEntities()!= null && status.getMediaEntities().length>0){
                nbmediaEntities = status.getMediaEntities().length;


            }
            if(status.getExtendedMediaEntities()!= null && status.getExtendedMediaEntities().length>0){
                nbextendedMediaEntities = status.getExtendedMediaEntities().length;
            }
            if(status.getSymbolEntities()!= null && status.getSymbolEntities().length>0){
                nbsymbolEntities = status.getSymbolEntities().length;

            }
            if(status.getURLEntities()!= null && status.getURLEntities().length>0){
                nbURLEntities = status.getURLEntities().length;

            }
            if(status.getUserMentionEntities()!= null && status.getUserMentionEntities().length>0){
                nbmentionEntities = status.getUserMentionEntities().length;

            }
            st = con.createStatement();
            st.executeUpdate(addTweet(status,nbhashtags,nbcontributors,nbmediaEntities,nbextendedMediaEntities,nbsymbolEntities,nbURLEntities,nbmentionEntities,retweetedFrom,originalTweeterid,originalTweeterScr,originalTweeterName,currentDateString,currentTime,twitterDateString,twitterTime));
            if(status.getPlace() != null){
                createXML(status,nbhashtags,nbcontributors,nbmediaEntities,nbextendedMediaEntities,nbsymbolEntities,nbURLEntities,nbmentionEntities,retweetedFrom,originalTweeterid,originalTweeterScr,originalTweeterName,currentDateString,currentTime,twitterDateString,twitterTime);
            }
            if(status.getHashtagEntities().length>0)
            {
                String h =addHashtag(user.getName(),user.getScreenName(),status.getId(), status.getHashtagEntities(), status.getUser().getId(), currentDateString, currentTime, twitterDateString, twitterTime);
                st = con.createStatement();
                st.execute( "insert into hashtag values "+h.substring(0, h.length()-1)+";");




            }if(status.getUserMentionEntities().length>0){
                String m = addMentionEntity(status.getUser().getName(),status.getUser().getScreenName(),status.getId(),status.getUserMentionEntities(),
                        status.getUser().getId(),currentDateString,currentTime,twitterDateString, twitterTime);
                st = con.createStatement();
                st.execute( "insert into mentionentity values "+m.substring(0, m.length()-1)+";");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static String addHashtag(String username, String userScr, long tweetid, HashtagEntity[] hashtagEntities,
                                    long userid, String currentDate, long currentTime, String twitterDate, long twitterTime) {
        // TODO Auto-generated method stub
        String toReturn = "";
        for (HashtagEntity he : hashtagEntities) {

            toReturn = toReturn.concat("(DEFAULT,'" + entirelyInBasicMultilingualPlane(username) + "','" + entirelyInBasicMultilingualPlane(userScr) + "','" + entirelyInBasicMultilingualPlane(he.getText()) + "'," + he.getStart() + "," + he.getEnd() + "," + tweetid + "," + userid + ",'" + currentDate + "'," + currentTime + ",'" + twitterDate + "'," + twitterTime + "),");
        }
        return toReturn;

    }

    public static String addTweet(Status status, int nbhashtags, int nbcontributors, int nbmediaEntities, int nbextendedMediaEntities, int nbsymbolEntities,
                                  int nbURLEntities, int nbmentionEntities, long retweetedFrom, long originalTweeterId, String originalTweeterName, String originalTweeterscr, String currentDate, long currentTime, String twitterDate, long twitterTime) {
        String toReturn = "";
        String longitude = null;
        String lattitude = null;

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            longitude = String.valueOf(status.getGeoLocation().getLongitude());
            lattitude = String.valueOf(status.getGeoLocation().getLatitude());
        } catch (Exception ex) {

        }


        toReturn = toReturn.concat("insert into tweet values (DEFAULT, " + status.getCurrentUserRetweetId() + ","
                + status.getFavoriteCount() + ",'" + longitude + "','" + lattitude + "'," + status.getId() + ",'" + entirelyInBasicMultilingualPlane(status.getInReplyToScreenName()) + "',"
                + status.getInReplyToStatusId() + "," + status.getInReplyToUserId() + ",'" + entirelyInBasicMultilingualPlane(status.getLang()) + "'," + status.getRetweetCount() + ",'"
                + entirelyInBasicMultilingualPlane(status.getSource()) + "','" + entirelyInBasicMultilingualPlane(status.getText()) + "'," + status.isFavorited() + "," + status.isPossiblySensitive() + "," + status.isRetweet() + ","
                + status.isRetweeted() + "," + status.isTruncated() + "," + nbhashtags + "," + nbcontributors + "," + nbmediaEntities + "," + nbextendedMediaEntities + ","
                + nbsymbolEntities + "," + nbURLEntities + "," + nbmentionEntities + "," + retweetedFrom + "," + originalTweeterId + "," + "'" + entirelyInBasicMultilingualPlane(originalTweeterscr) + "','" + entirelyInBasicMultilingualPlane(originalTweeterName) + "'," + status.getUser().getId() + ",'" + entirelyInBasicMultilingualPlane(status.getUser().getScreenName()) + "','" + entirelyInBasicMultilingualPlane(status.getUser().getName()) + "','" + currentDate + "'," + currentTime + ",'" + twitterDate + "'," + twitterTime + ");\n");
        return toReturn;


    }

    public static String addMentionEntity(String username, String userScr, long tweetid,
                                          UserMentionEntity[] userMentionEntities, long userid,
                                          String currentDate, long currentTime, String twitterDate, long twitterTime) {
        String toReturn = "";
        for (UserMentionEntity mention : userMentionEntities) {

            toReturn = toReturn.concat("(DEFAULT," + tweetid + "," + userid + ",'" + entirelyInBasicMultilingualPlane(username) + "','" + entirelyInBasicMultilingualPlane(userScr) + "'," + mention.getEnd() + "," + mention.getId() + ",'" + entirelyInBasicMultilingualPlane(mention.getName()) + "','"
                    + entirelyInBasicMultilingualPlane(mention.getScreenName()) + "'," + mention.getStart() + ",'" + entirelyInBasicMultilingualPlane(mention.getText()) + "'"
                    + ",'" + currentDate + "'," + currentTime + ",'" + twitterDate + "'," + twitterTime + "),");

        }

        return toReturn;

    }


    public static String entirelyInBasicMultilingualPlane(String text) {
        if (text != null) {
            String toReturn = "";
            text = text.replace("\\", "");
            text = text.replace("'", "");
            text = StringEscapeUtils.escapeSql(text);
            for (int i = 0; i < text.length(); i++) {
                if (Character.isSurrogate(text.charAt(i))) {
                    toReturn = toReturn.concat(" ");
                } else
                    toReturn = toReturn + text.charAt(i);
            }
            return toReturn;
        }
        return "";
    }

    public static void createXML(Status status, int nbhashtags, int nbcontributors, int nbmediaEntities, int nbextendedMediaEntities, int nbsymbolEntities,
                                 int nbURLEntities, int nbmentionEntities, long retweetedFrom, long originalTweeterId, String originalTweeterName, String originalTweeterscr, String currentDate, long currentTime, String twitterDate, long twitterTime) throws ParserConfigurationException {
        String toReturn = "";
        String longitude = null;
        String lattitude = null;
        String textXMLvalue = null;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            longitude = String.valueOf(status.getGeoLocation().getLongitude());
            lattitude = String.valueOf(status.getGeoLocation().getLatitude());
        } catch (Exception ex) {

        }

        try {

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("tweet");
            doc.appendChild(rootElement);

            Element rootElementInfo = doc.createElement("Id");
            rootElementInfo.appendChild(doc.createTextNode(String.valueOf(status.getId())));
            rootElement.appendChild(rootElementInfo);


            Element rootElementLoca = doc.createElement("location");
            rootElement.appendChild(rootElementLoca);

            Element location = doc.createElement(status.getPlace().getPlaceType());
            location.appendChild(doc.createTextNode(status.getPlace().getName()));
            rootElementLoca.appendChild(location);

            Element country = doc.createElement("country");
            country.appendChild(doc.createTextNode(status.getPlace().getCountryCode()));
            rootElementLoca.appendChild(country);

            Element rootElementText = doc.createElement("text");
            rootElementText.appendChild(doc.createTextNode(status.getText()));
            rootElement.appendChild(rootElementText);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/resources/our_train/" + status.getId() + ".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


}