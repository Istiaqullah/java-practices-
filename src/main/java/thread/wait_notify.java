package thread;

class MyThreadY extends Thread
{
    MyThreadY(String name)
    {
        this.setName(name);
    }

    public void run()
    {
       while (true){
           System.out.println("Thread " + this.getName() + " Running");
           try{ Thread.sleep(5000); } catch(Exception e){}
           System.out.println("Thread " + this.getName() + " Terminated");
       }
    }
}

public class wait_notify {

    public static void main(String[] args) {

        MyThreadY t1 = new MyThreadY("t1");
        MyThreadY t2 = new MyThreadY("t2");
        MyThreadY t3 = new MyThreadY("t3");
        MyThreadY t4 = new MyThreadY("t4");

        t1.start();
        try{ t1.wait(); } catch(Exception e){}
        t2.start();
        t3.start();
        t4.start();
        try{ t4.notify(); } catch (Exception e){}

    }

}
