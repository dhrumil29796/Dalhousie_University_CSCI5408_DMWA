import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Transaction1 implements Runnable {
    private FileWriter file;
    private String tN;

    public Transaction1(String tN, FileWriter fw) {
        this.tN = tN;
        this.file = fw;
    }

    @Override
    public void run() {
        ResultSet customersResultSet = null;
        try (Connection transaction = DriverManager.getConnection("jdbc:mysql://34.122.7.56/vm1", "drshah", "drshah");
             final Statement statement = transaction.createStatement()) {
            file.append("T1 started\n");

            transaction.setAutoCommit(false);

            customersResultSet = statement.executeQuery("SELECT * FROM customers WHERE customer_zip_code_prefix=4427");
            file.append("SELECT query executed of T1\n");

            int customersCount = 0;
            while (customersResultSet.next()) {
                customersCount++;
            }
            file.append("Read " + customersCount + " customers data successfully for T1.\n");

            final int customersCityUpdated = statement.executeUpdate("UPDATE customers SET customer_city='\" + city + \"' WHERE customer_zip_code_prefix= 4427");
            file.append("Updated city of " + customersCityUpdated + " customers data successfully for T1.\n");

            transaction.commit();
            System.out.println("T1 committed.");
            file.append("T1 committed\n");

            customersResultSet.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}