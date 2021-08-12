import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// WordCounterEngine class that does the following:
// Reads the raw data stored in the generated files
// Counts the frequency of keywords
// Returns the frequency of keywords in the extracted raw data
public class WordCounterEngine {

    // Driver method
    public static void main(String[] args) throws IOException {

        // Creating a list of searchKeywords
        List<String> searchKeywords = new ArrayList<>();

        // Creating an HashMap wordFrequencyCount that will store the frequency of a particular searchKeyword
        // As HashMap stores data in <Key,Value> Pairs
        // Key -> searchKeyword -> String
        // Value -> frequency -> Integer
        HashMap<String, Integer> wordFrequencyCount = new HashMap<>();

        // Adding the searchKeywords to the List
        // They are Case Sensitive
        // There are in total of 8 keywords
        searchKeywords.add("Canada");
        searchKeywords.add("NovaScotia");
        searchKeywords.add("education");
        searchKeywords.add("higher");
        searchKeywords.add("learning");
        searchKeywords.add("city");
        searchKeywords.add("accommodation");
        searchKeywords.add("price");

        // Creating a new file object & reading all the previously created files storing the raw data extracted from NewsAPIClient
        File file = new File("E:\\data");

        // Storing all the files read from location E:\\data into an array of file
        File[] rawFilesList = file.listFiles();

        // Instantiating Scanner
        Scanner sc;

        // Creating a words String array that will store the keywords
        String[] words;

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
            String data = stringBuilder.toString();

            // Filtering the data using RegEx & replacing the following with a space
            // Special Characters, HTML Tags, Unicode Characters, URLs, END OF LINE Characters
            data = data.replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{Z}\\p{Z}\\p{Cf}\\p{Cs}\\s]", " ");
            data = data.replaceAll("[-+^]*", "");
            data = data.replaceAll("http.*?\\s", " ");
            data = data.replaceAll("\\\\u(\\p{XDigit}{4})", " ");
            data = data.replaceAll("\\\\r\\\\n", " ");
            data = data.replaceAll("Nova Scotia", "NovaScotia");

            // Splitting the filtered data String by space character
            // Storing the split data into words array
            words = data.split(" ");

            // Iterating through the words array of String storing all the individual words,
            // Present in the filtered data read form files containing raw data
            for (String s : words) {

                // Iterating through the searchKeywords arraylist
                for (String searchKeyword : searchKeywords) {

                    // Checking whether the searchKeyword matches the word from the words[] array
                    if (s.equals(searchKeyword)) {

                        // If the word matches the searchKeyword then,
                        // Getting the frequency for that keyword from the HashMap wordFrequencyCount
                        Integer frequency = wordFrequencyCount.get(s);

                        // Checking if the wordFrequencyCount HashMap does not contain the current keyword then,
                        // Adding the current keyword to the wordFrequencyCount HashMap
                        // Setting it's initial frequency as 1
                        if (!wordFrequencyCount.containsKey(s)) {

                            // Putting the current keyword which was not present in the wordFrequencyCount HashMap
                            // Setting it's frequency count to 1
                            wordFrequencyCount.put(s, 1);

                            // Checking if the word is already present in the wordFrequencyCount HashMap then,
                            // Just increment that keywords frequency count by 1
                        } else if (wordFrequencyCount.containsKey(s)) {

                            // Incrementing the current keywords frequency count by 1 & updating it in the wordFrequencyCount HashMap
                            wordFrequencyCount.put(s, frequency + 1);
                        }
                        // Checking if the word is not present in the words[] array nor the wordFrequencyCount HashMap then,
                        // We set its value to 0
                    } else if (!wordFrequencyCount.containsKey(searchKeyword)) {

                        // Setting the word & its value to 0
                        wordFrequencyCount.put(searchKeyword, 0);
                    }
                }
            }
        }
        // Printing the wordFrequencyCount HashMap in Console
        System.out.println(wordFrequencyCount);
    }
}

