package mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/*
 * Create the StringsCollection class here.
 */
class StringsCollection{
    List<String> list = Collections.synchronizedList(new ArrayList<>());

    void addString(String string){
        list.add(string);
    }

    List<String> getStringsCollection(){
        return list;
    }

}


class StringsCollectionRunnable implements Runnable {
    private final StringsCollection stringsCollection;
    private final int stringsCount;
    private final String threadName;

    public StringsCollectionRunnable(StringsCollection stringsCollection, int stringsCount, String threadName) {
        this.stringsCollection = stringsCollection;
        this.stringsCount = stringsCount;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        for (int j = 0; j < stringsCount; j++) {
            this.stringsCollection.addString(threadName + String.valueOf(j + 1));
        }
    }
}

public class M01 {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StringsCollection STRINGS_COLLECTION = new StringsCollection();

    public static void main(String[] args) {
        int threadsCount = Integer.parseInt(SCANNER.nextLine());
        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            int stringsCount = Integer.parseInt(SCANNER.nextLine());

            threads[i] = new Thread(new StringsCollectionRunnable(STRINGS_COLLECTION, stringsCount, String.valueOf(i + 1)));
            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }

        List<String> stringsCollection = STRINGS_COLLECTION.getStringsCollection();
        System.out.println(stringsCollection.size());

        int nonNullStrings = 0;
        for (String string: stringsCollection) {
            if (string != null) {
                nonNullStrings++;
            }
        }

        System.out.println(nonNullStrings);
    }
}