import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// NewsProcessingEngine class that does the following:
// Extracts the data
// Filters the data
// Stores it in the MongoDB database
public class NewsProcessingEngine {

    // Driver method
    public static void main(String[] args) {

        // Instantiating the NewsApiClient and passing my API Key
        NewsApiClient newsApiClient = new NewsApiClient("f5beb2e18d8f4040a11cc0b54fd7fb9d");

        // Creating a list of searchKeywords
        List<String> searchKeywords = new ArrayList<>();

        // Adding the keywords for which news articles are needed to be fetched into a List of Strings
        // There are 7 keywords in total
        searchKeywords.add("Canada");
        searchKeywords.add("University");
        searchKeywords.add("Dalhousie");
        searchKeywords.add("Halifax");
        searchKeywords.add("Canada Education");
        searchKeywords.add("Moncton");
        searchKeywords.add("Toronto");

        // Creating a JSONObject object to fetch the data from NewsAPI Client
        final JSONObject jsonObject = new JSONObject();

        // Creating a MongoClient object
        MongoClient mongo = new MongoClient("localhost", 27017);

        // Instantiating MongoCredential to null
        MongoCredential credential = null;

        try {
            // Storing the MongoDB credentials
            credential = MongoCredential.createCredential("sampleUser", "myMongoNews", "1234".toCharArray());

            // Printing the message to the Console
            System.out.println("Connected to the MongoDB database");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Connecting to the myMongoNews Database
        MongoDatabase database = mongo.getDatabase("myMongoNews");

        // Instantiating the MongoCollection & getting the articles from the myMongoNews Database collection
        MongoCollection<Document> collection = database.getCollection("drshah");

        // Iterating through the searchKeywords list using a for loop
        for (int i = 0; i < searchKeywords.size(); i++) {

            // Storing the current index of the for loop's iteration to add it in the file name for each keyword
            int index = i;

            // Calling the getEverything() method for each keyword in searchKeywords list
            // The pageSize is limited to 100 articles
            // Also calling the ArticlesResponseCallback() method using the NewsApiClient
            newsApiClient.getEverything(new EverythingRequest.Builder().q(searchKeywords.get(i)).pageSize(100).build(), new NewsApiClient.ArticlesResponseCallback() {

                        // Overriding the onSuccess method and passing the articleResponse object
                        @Override
                        public void onSuccess(ArticleResponse articleResponse) {

                            // Initializing the FileWriter
                            FileWriter fw = null;

                            // Maintaining a counter
                            int counter = 0;

                            // Iterating through the news articles that fetched for the defined searchKeywords
                            for (int k = 0; k < articleResponse.getArticles().size(); k++) {

                                try {
                                    // Using % 5 as we need to divide 5 articles per file
                                    // Dividing the fetched News articles into blocks of 5 articles before storing it in files
                                    if (k % 5 == 0) {

                                        // Creating a new file with the following naming convention,
                                        // <Keyword><counter> -> Keyword is from the searchKeywords list & counter goes from o to 19
                                        // Each file contains 5 articles so there are 20 files for each keyword
                                        fw = new FileWriter("E:\\data\\" + searchKeywords.get(index) + counter + ".json");

                                        // Incrementing the counter as new file is created
                                        counter++;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                // Using the get methods defined by the API to get title and description
                                // Storing the fetched details in the jsonObject using the put command
                                jsonObject.put("title", articleResponse.getArticles().get(k).getTitle());
                                jsonObject.put("description", articleResponse.getArticles().get(k).getDescription());

                                try {
                                    // Writing from the jsonObject to the file previously created
                                    fw.write(jsonObject.toString());

                                    // Flushing the fileWriter object
                                    fw.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        // Overriding the onFailure method
                        @Override
                        public void onFailure(Throwable throwable) {
                            System.out.println(throwable.getMessage());
                        }
                    }
            );
        }
        try {
            // Creating a new file object & reading all the previously created files storing the raw data extracted from NewsAPIClient
            File file = new File("E:\\data");

            // Storing all the files read from location E:\\data into an array of file
            File[] rawFilesList = file.listFiles();

            // Instantiating Scanner
            Scanner sc;

            // Iterating through the rawFilesList array
            for (File f : rawFilesList) {

                // Reading the contents of each file using a Scanner object
                sc = new Scanner(f);

                // Storing the data read from the file in a String named contents
                String contents;

                // Instantiating a new String Builder
                StringBuilder stringBuilder = new StringBuilder();

                // Using Scanner classes object sc to read the contents of the current file & storing it in contents String
                while (sc.hasNextLine()) {
                    contents = sc.nextLine();

                    // Appending the contents read to the String Builder
                    stringBuilder.append(contents).append(" ");
                }
                // After appending to the String Builder, now converting the String Builder to a String
                String s = stringBuilder.toString();

                // Storing the below RegEx in a String
                // The RegEx is basically the opening & closing curly braces, as the raw data is in JSON format
                String regEx = "}\\{";

                // Splitting the read data using the regEx String and storing it in the data[] array of type String
                String[] data = s.split(regEx);

                // Creating a list of type Document named docs
                // Storing all the documents & uploading it to the connected MongoDB Database
                List<Document> docs = new ArrayList<>();

                // Iterating through the news array and calling different methods that will do the work of filtration after getting the Title & Description
                // removingSpecialCharacters()
                // removingHTML()
                // removingEOL()
                // removingURL()
                // removingUnicode()
                for (String datum : data) {
                    Document doc = new Document("title", (removingSpecialCharacters(removingHTML(removingEOL(removingURL(removingUnicode(getTitle(datum))))))))
                            .append("description", (removingSpecialCharacters(removingHTML(removingEOL(removingURL(removingUnicode(getDescription(datum))))))));

                    // Adding the filtered file/doc to the docs array list
                    docs.add(doc);
                }
                // Inserting the documentList to the collection
                collection.insertMany(docs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Public method removingUnicode(), removes all the Unicode Characters which are present in the passed String
    public static String removingUnicode(String s) {

        // Using the RegEx below to replace the Unicode Characters with an empty String
        s = s.replaceAll("\\\\u(\\p{XDigit}{4})", "");

        // Returning the filtered String
        return s;
    }

    // Public method removingSpecialCharacters(), removes all the special characters present in the passed String
    public static String removingSpecialCharacters(String s) {

        // Initializing a result StringBuilder that will store the String free from Special Characters
        StringBuilder result = new StringBuilder();

        // Iterating through the passed String's length
        for (int i = 0; i < s.length(); i++) {

            // Checking the following conditions
            // Matching the needed alphabets to their ASCII values
            // A to Z, a to z, 0 to 9 -> appending only those characters
            if ((s.charAt(i) > 47 && s.charAt(i) <= 57) || s.charAt(i) == 32 || (s.charAt(i) > 64 && s.charAt(i) <= 122)) {

                // Appending the filtered characters to the result StringBuilder
                result.append(s.charAt(i));
            }
        }
        // Returning the result StringBuilder converted to String
        return result.toString();
    }

    // Public getTitle() method that gets the Title filed from the passed String
    public static String getTitle(String s) {

        // Storing the starting index of the matching Title tag from the passed String
        int start = s.indexOf("\"title\":\"") + "\"title\":\"".length();

        // Storing the ending index of the matching Title tag from the passed String
        int end = s.lastIndexOf("\"");

        // Returning the sub-string from start to end
        return s.substring(start, end);
    }

    // Public method removingURL(), removes all the URLs which are present in the passed String
    public static String removingURL(String s) {

        // Using the RegEx below to replace the URLs with an empty String
        s = s.replaceAll("http.*?\\s", "");

        // Returning the filtered String
        return s;
    }

    // Public getDescription() method that gets the Description filed from the passed String
    public static String getDescription(String s) {

        // Storing the starting index of the matching Description tag from the passed String
        int start = s.indexOf("\"description\":\"") + "\"description\":\"".length();

        // Storing the ending index of the matching Description tag from the passed String
        int end = s.indexOf("\",\"title\"");

        // Returning the sub-string from start to end
        return s.substring(start, end);
    }

    // Public method removingEOL(), removes all the END OF LINE Characters which are present in the passed String
    public static String removingEOL(String s) {

        // Using the RegEx below to replace the END OF LINE Characters with an empty String
        s = s.replaceAll("\\\\r\\\\n", "");

        // Returning the filtered String
        return s;
    }

    // Public method removingHTML(), removes all the HTML tags which are present in the passed String
    public static String removingHTML(String s) {

        // Using the RegEx below to replace the HTML Tags with an empty String
        // This includes all the tags present in the String passed
        s = s.replaceAll("<.*?>", "");

        // Returning the filtered String
        return s;
    }

}

