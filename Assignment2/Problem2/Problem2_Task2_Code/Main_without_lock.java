import java.io.FileWriter;
import java.io.IOException;

public class Main_without_lock {

    public static void main(String[] args) {

        try( FileWriter fw = new FileWriter("transactionLog.txt", true)){

            Transaction1 db1 = new Transaction1("City T1", fw);
            Transaction2 db2 = new Transaction2("City T2", fw);
            Transaction3 db3 = new Transaction3("City T3", fw);
            Thread t1 = new Thread(db1);
            Thread t2 = new Thread(db2);
            Thread t3 = new Thread(db3);
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
