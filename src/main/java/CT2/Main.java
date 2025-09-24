package CT2;
import java.util.Scanner;

class HHO {
    int capacity = 3;
    int h = 2;
    int o = 1;
    int hTotal;
    int oTotal;

    public HHO(int hCount, int oCount) {
        this.hTotal = hCount;
        this.oTotal = oCount;
    }

    synchronized void consumeH() throws InterruptedException {
        while (hTotal > 0) {
            if (capacity == 0) {
                capacity = 3;
                h = 2;
                o = 1;
            }
            while (h == 0) {
                wait();
            }
            if (hTotal == 0) return; // double check after wait
            System.out.print("H");
            h--;
            capacity--;
            hTotal--;
            notifyAll();
            break;
        }
    }

    synchronized void consumeO() throws InterruptedException {
        while (oTotal > 0) {
            if (capacity == 0) {
                capacity = 3;
                h = 2;
                o = 1;
            }
            while (o == 0) {
                wait();
            }
            if (oTotal == 0) return; // double check after wait
            System.out.print("O");
            o--;
            capacity--;
            oTotal--;
            notifyAll();
            break;
        }
    }
}

class O implements Runnable {
    HHO hho;
    public O(HHO con) {
        hho = con;
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                hho.consumeO();
                synchronized(hho) {
                    if (hho.oTotal == 0) break;
                }
            }
        } catch (InterruptedException ignored) {}
    }
}

class H implements Runnable {
    HHO hho;
    public H(HHO con) {
        hho = con;
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                hho.consumeH();
                synchronized(hho) {
                    if (hho.hTotal == 0) break;
                }
            }
        } catch (InterruptedException ignored) {}
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter a sequence of H and O characters :");
        Scanner sc = new Scanner(System.in);
        String s = sc.next();

        int hCount = 0, oCount = 0;
        for (char c : s.toCharArray()) {
            if (c == 'H') hCount++;
            else if (c == 'O') oCount++;
        }
        System.out.printf("Output: ");
        HHO consume = new HHO(hCount, oCount);

        // Start required number of H and O threads
        int hThreads = hCount;
        int oThreads = oCount;
        for (int i = 0; i < hThreads; i++) new H(consume);
        for (int i = 0; i < oThreads; i++) new O(consume);
    }
}