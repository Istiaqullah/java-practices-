package thread;
class MyThread extends Thread
{
    public void run()
    {
        while(true){
            System.out.println("Thread 1 Running"); }
    }
}
class MyThread2 implements  Runnable
{
    public void run()
    {
        while(true){
            System.out.println("Thread 2 Running"); }
    }
}
public class Main {
    public static void main(String[] args) {
       /*if we use extends Thread then we can directly create object of MyThread class. */

        MyThread t1 = new MyThread();

        /*if we use implements Runnable then we have to create object of
         Thread class and pass the object of MyThread2 class to it. */
        MyThread2 t2 = new MyThread2();
        Thread th2 = new Thread(t2);

//we can set priority of thread using setPriority() method
        //priority ranges from 1 to 10
        //default priority is 5
        //we can use constants Thread.MIN_PRIORITY, Thread.NORM_PRIORITY, Thread.MAX_PRIORITY
        t1.setPriority(Thread.MAX_PRIORITY);
        th2.setPriority(2);

        t1.start();
        th2.start();

    }
}
