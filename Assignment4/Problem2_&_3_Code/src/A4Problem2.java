import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

// A4 - Problem 2 class
public class A4Problem2 {

  // Driver method
  public static void main(String[] args) {
    try {
      // Instantiating MongoClient by passing the MongoDB connection host and port
      MongoClient mongoClient = new MongoClient("localhost", 27017);

      // Instantiating MongoDatabase to connect to the
      // myMongoNews database
      MongoDatabase mongoDatabase = mongoClient.getDatabase("myMongoNews");

      // Instantiating MongoCollection to connect to the drshah collection in
      // myMongoNews database
      MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("drshah");

      // Declaring an object of Document
      Document document;

      // Storing the number of documents in the drshah collection
      final long numberOfDocuments = mongoCollection.countDocuments();

      // Printing the number of documents in the drshah collection
      System.out.println("---------------------------------------------------------------");
      System.out.println("Total Documents: " + numberOfDocuments);

      // Instantiating FindIterable with Document type
      FindIterable<Document> findIterable = mongoCollection.find();

      // Creating a list of descriptions from the news articles
      List<String> descriptionList = new ArrayList<>();

      Set<String> matchWords = new HashSet<>();

      // Looping through the findIterable of Document type
      for (Document newsDocument : findIterable) {

        // Storing the fetched documents
        document = newsDocument;

        // Getting the description from the document and storing it in the
        // String named descriptionValue
        String descriptionValue = document.getString("description");

        // Adding the description string to the descriptionList
        descriptionList.add(descriptionValue);
      }

      // Created a new map to store the word and its frequency
      Map<String, Integer> wordMap = new HashMap<>();

      // Creating an Iterator
      Iterator<String> iterator = descriptionList.iterator();

      // An array of string
      String[] stringArray;

      // A counter for news article
      int newsArticleCount = 0;

      // Looping through the iterator and splitting the description and adding
      // it with its frequency in the wordMap Hashmap
      while (iterator.hasNext()) {
        System.out.println("---------------------------------------------------------------");
        String description = iterator.next();
        newsArticleCount++;

        // Printing the news article number and it's description
        System.out.println("News Article: " + newsArticleCount);
        System.out.println("News Description: " + description);

        // Splitting the read description by spaces
        stringArray = description.split("\\s");
        for (String string : stringArray) {
          if (wordMap.containsKey(string)) {
            wordMap.put(string, wordMap.get(string) + 1);
          } else {
            wordMap.put(string, 1);
          }
        }

        // Removing the empty string added in the wordMap
        wordMap.remove("");

        // Creating a new file for positiveWords
        File positiveWords = new File("positive-words.txt");

        // Local variable to store the positive score
        int positiveScore = 0;

        // Reading the contents of the positive-words.txt file
        BufferedReader br = new BufferedReader(new FileReader(positiveWords));

        // Storing the positiveLine
        String positiveLine;

        // Looping through the positiveLine and the words in positive-words.txt
        while ((positiveLine = br.readLine()) != null) {
          for (String key : wordMap.keySet()) {
            if (positiveLine.equals(key)) {
              positiveScore += wordMap.get(key);
              matchWords.add(positiveLine);
            }
          }
        }

        // Creating a new file for negativeWords
        File negativeWords = new File("negative-words.txt");

        // Local variable to store the negative score
        int negativeScore = 0;

        // Reading the contents of the negative-words.txt file
        BufferedReader br1 = new BufferedReader(new FileReader(negativeWords));

        // Storing the negativeLine
        String negativeLine;

        // Looping through the negativeLine and the words in negative-words.txt
        while ((negativeLine = br1.readLine()) != null) {
          for (String key : wordMap.keySet()) {
            if (negativeLine.equals(key)) {
              negativeScore += wordMap.get(key);
              matchWords.add(negativeLine);
            }
          }
        }

        // Printing the matching words in the news description
        System.out.println("Match: " + matchWords);

        // Negating the negative word score
        negativeScore = -negativeScore;

        // Calculating the polarity
        int polarity = positiveScore + negativeScore;

        // Checking the polarity
        if (polarity > 0) {
          System.out.println("Polarity: positive");
        } else if (polarity == 0) {
          System.out.println("Polarity: neutral");
        } else {
          System.out.println("Polarity: negative");
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
