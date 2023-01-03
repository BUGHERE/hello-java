package Uses;

public class ThreadTest {
    public static void main(String[] args) {
       MyThread t = new MyThread();
       t.start();
       try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
       t.interrupt();                            // 设置中断标志为 true
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.print("hello");
                if(this.isInterrupted()){        // 查看中断标志，若为 true 结束循环
                    break;
                }
            }
        }
    }

}
