import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// A4 - Problem 3 class
public class A4Problem3 {

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

      // Storing the highest frequency of the keyword Canada within each news
      // article's description
      int highestFreq = 0;

      // Storing the highest relative frequency of the keyword Canada within
      // each news article's description
      double highestRelativeFreq = 0.0;

      // Storing the relative frequency of the keyword Canada within
      // each news article's description
      double relativeFreq;

      // Storing the number of news articles that are fetched from the
      // MongoDB collection
      int newsArticle = 0;

      // Decalring a constant for keyword CANADA
      final String CANADA = "Canada";

      // Storing the number of documents in the drshah collection
      final long numberOfDocuments = mongoCollection.countDocuments();

      // Printing the number of documents in the drshah collection
      System.out.println("---------------------------------------------------------------");
      System.out.println("Total Documents: " + numberOfDocuments);

      // Fetching only the description from the news articles
      mongoCollection.createIndex(Indexes.text("description"));

      // Instantiating FindIterable with Document type
      FindIterable<Document> findIterable = mongoCollection.find();

      // Creating a new arraylist for keywords that are needed to be searched
      List<String> keywords = new ArrayList<>();

      // Adding the keywords to the keywords arraylist
      keywords.add("Canada");
      keywords.add("Moncton");
      keywords.add("Toronto");

      // Creating a list of descriptions from the news articles
      List<String> descriptionList = new ArrayList<>();

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

      // Creating a new list that stores the contents of descriptionList
      List<String> descriptionList1 = new ArrayList<>(descriptionList);

      // Storing the size of descriptionList in N
      final int N = descriptionList.size();

      // Setting the count of keywords Canada, Moncton, Toronto to 0
      float countCanada = 0f;
      float countMoncton = 0f;
      float countToronto = 0f;

      // Lopping through the descriptionList and incrementing the respective
      // keyword counts
      for (String string : descriptionList) {
        if (string.contains("Canada")) {
          countCanada = countCanada + 1;
        }
        if (string.contains("Moncton")) {
          countMoncton = countMoncton + 1;
        }
        if (string.contains("Toronto")) {
          countToronto = countToronto + 1;
        }
      }

      // Printing the result
      System.out.println("---------------------------------------------------------------");
      System.out.printf("%10s %8s %15s %20s", "Search Query", "(df)", "(N)/(df)", "Log10(N/df)\n");
      System.out.printf("%10s %10s %15s %22s", "Canada", countCanada, (N / countCanada), Math.log10(N / countCanada) + "\n");
      System.out.printf("%10s %10s %15s %22s", "Moncton", countMoncton, (N / countMoncton), Math.log10(N / countMoncton) + "\n");
      System.out.printf("%10s %10s %15s %22s", "Toronto", countToronto, (N / countToronto), Math.log10(N / countToronto) + "\n");
      System.out.println("---------------------------------------------------------------");

      // Creating a 2d Map (a Hashtable) that will store the frequency
      // mapping of the keyword Canada from the fetched news description
      Map<Integer, Map<Integer, Integer>> frequencyMap = new Hashtable<>();

      // Storing the size of the descriptionList1Size arraylist
      final long descriptionList1Size = descriptionList1.size();

      // Looping through the descriptionList
      for (int i = 0; i < descriptionList1Size; i++) {

        // Creating a local frequency variable
        int frequency = 0;

        // Storing each news description in a string
        String string = descriptionList1.get(i);

        // Storing the keyword Canada in a pattern object
        Pattern pattern = Pattern.compile(CANADA);

        // Using matcher to match the pattern with the string fetched from
        // the description
        Matcher matcher = pattern.matcher(string);

        // Lopping the matcher
        while (matcher.find()) {
          frequency++;
        }

        // Creating a final map that will store the actual result
        Map<Integer, Integer> finalMap = new HashMap<>();

        // Initializing the countKeyword to 0
        int countKeyword = 0;

        // Checking whether the description is
        if (!(" ".equals(string.substring(string.length() - 1))) || !(" ".equals(string.substring(0, 1)))) {
          for (int j = 0; j < string.length(); j++) {
            if (string.charAt(j) == ' ') {
              countKeyword++;
            }
          }
          countKeyword += 1;
        }

        // Storing in the finalMap
        finalMap.put(frequency, countKeyword);

        // Storing in the frequencyMap
        frequencyMap.put(i, finalMap);

        // Checking if the frequency is higher than the highest frequency
        if (frequency > highestFreq) {
          highestFreq = frequency;
          newsArticle = i;
        }

        // Calculating the relative frequency
        relativeFreq = ((double) frequency / (double) countKeyword);

        // Checking if the relative frequency is higher than the highest
        // frequency
        if (relativeFreq > highestRelativeFreq) {
          highestRelativeFreq = relativeFreq;
        }
      }

      // Getting the size of the frequencyMap
      final long frequencyMapSize = frequencyMap.size();

      // Printing the result
      System.out.println("Term: " + CANADA);
      System.out.println("---------------------------------------------------------------");
      System.out.println("Canada appeared in " + frequencyMapSize + " documents");
      System.out.println("---------------------------------------------------------------");
      System.out.println(frequencyMap);
      System.out.println("---------------------------------------------------------------");
      System.out.println("News Article Number: " + newsArticle);
      System.out.println("Frequency: " + highestFreq);
      System.out.println("Highest Relative Frequency: " + highestRelativeFreq);
      System.out.println("---------------------------------------------------------------");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}