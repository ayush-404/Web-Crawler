package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

public class Spider {
  final static String APIKey = "o3Os2hd37OHmwqBy4i7EdxfIg";
  final static String APISecret = "NEC81tYehDQpWeDtPEqkNu8wOyxkW9m8JUmtfVXLwZIE6eY0nx";
  final static String consumerKey = "1398369485514436608-WjST4jOA3UbjbdCq6wtfbd6VbAcp7m";
  final static String consumerSecret = "4yVPwxCmULGeKPfkd5HzmWH7ahHuAb1AQFIw28Pvnlwrw";

  public static void main(String[] agrs) {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true).setOAuthConsumerKey(APIKey).setOAuthConsumerSecret(APISecret)
        .setOAuthAccessToken(consumerKey).setOAuthAccessTokenSecret(consumerSecret);
    TwitterFactory tf = new TwitterFactory(cb.build());
    maintain(tf.getInstance());
  }

  public static void connect() {
    while (true) {
      try {
        Document doc = Jsoup.connect("https://www.coindesk.com/price/dogecoin").userAgent("Mozilla")
            .cookie("auth", "token").timeout(30000).post();
        System.out.println(doc.getElementsByClass("price-large").text());
        Thread.sleep(1000);
      } catch (IOException | InterruptedException e) {
        if (e instanceof HttpStatusException) {
          connect();
        } else {
          e.printStackTrace();
        }
      }
    }
  }

  public static void maintain(Twitter twitter) {
    String User = "@elonmusk";
    Thread connectThread = new Thread() {
      @Override
      public void run() {
        connect();
      }
    };

    Thread twitterThread = new Thread() {
      @Override
      public void run() {
        twitterFetch(twitter, User);
      }
    };
    Scanner keyboardScanner = new Scanner(System.in);
    Thread inputThread = new Thread() {
      @Override
      public void run() {
        while(true)
        {
          if (keyboardScanner.hasNext()) {
          if (keyboardScanner.next().equals("E")) {
            System.exit(0);;
          }
          try {
            Thread.sleep(1000);
          } catch (Exception e) {
            e.printStackTrace();
          }
          
        }
        }

      }
    };
    connectThread.start();
    twitterThread.start();
    inputThread.start();
  }

  public static void twitterFetch(Twitter twitter, String user) {
    List<Status> statuses = null;
    while (true) {
      try {
        statuses = twitter.getUserTimeline(user);
      } catch (TwitterException e) {
        e.printStackTrace();
        System.out.println("Cannot Get User Tweets");
      }

      for (Status status : statuses) {
        System.out.println(status.getText());
      }
      try {
        Thread.sleep(50000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

}