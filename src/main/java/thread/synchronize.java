package thread;

class Counter
{
    int count;
    public synchronized void increment()
    {
        count++;
    }
}

public class synchronize {

    public static void main(String[] args) {

        Counter c = new Counter();

        Thread t1 = new Thread(new Runnable()
        {
            public void run()
            {
                for(int i=1; i<=10; i++)
                {

                    c.increment();
                    System.out.println("Thread 1: " + i);
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            public void run()
            {
                for(int i=1; i<=10; i++)
                {

                    c.increment();
                    System.out.println("Thread 2: " + i);
                }
            }
        });

        t1.start();
        t2.start();



        try{
            t1.join(); } catch(Exception e){}


        try{
            t2.join(); } catch(Exception e){}

        System.out.println(c.count);

    }

}
