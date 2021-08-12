import java.io.FileWriter;
import java.io.IOException;

public class Main_with_lock {

    public static void main(String[] args) {

        try( FileWriter fw = new FileWriter("transactionLog.txt", true)){

            LockTransaction1 db1 = new LockTransaction1("City T1", fw);
            LockTransaction2 db2 = new LockTransaction2("City T2", fw);
            LockTransaction3 db3 = new LockTransaction3("City T3", fw);
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
