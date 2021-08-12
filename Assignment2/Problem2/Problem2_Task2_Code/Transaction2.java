import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Transaction2 implements Runnable {
    private FileWriter file;
    private String tN;

    public Transaction2 (String tN, FileWriter fw) {
        this.tN = tN;
        this.file = fw;
    }

    @Override
    public void run() {
        ResultSet customersResultSet = null;
        try (Connection transaction = DriverManager.getConnection("jdbc:mysql://34.122.7.56/vm1", "drshah", "drshah");
             final Statement statement = transaction.createStatement()) {
            file.append("T2 started\n");

            transaction.setAutoCommit(false);

            customersResultSet = statement.executeQuery("SELECT * FROM customers WHERE customer_zip_code_prefix=4427");
            file.append("SELECT query executed of T2\n");

            int customersCount = 0;
            while (customersResultSet.next()) {
                customersCount++;
            }
            file.append("Read " + customersCount + " customers data successfully for T2.\n");

            final int customersCityUpdated = statement.executeUpdate("UPDATE customers SET customer_city='\" + city + \"' WHERE customer_zip_code_prefix= 4427");
            file.append("Updated city of " + customersCityUpdated + " customers data successfully for T2.\n");

            transaction.commit();
            System.out.println("T2 committed.");
            file.append("T2 committed\n");

            customersResultSet.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}