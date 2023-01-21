public class MemoryUsageChecker implements Runnable {
    private long maxMemoryUsage;
    private boolean isStopped;
    private Thread checkerThread;

    public MemoryUsageChecker() {
        checkerThread = new Thread(this);
        checkerThread.start();
    }

    public void run() {
        while (!isStopped) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            if (maxMemoryUsage < memoryUsed) {
                maxMemoryUsage = memoryUsed;
            }
        }
    }

    public synchronized void stop() {
        this.isStopped = true;
    }

    public long stopAndGetUsage() throws InterruptedException {
        stop();
        checkerThread.join();
        return maxMemoryUsage / 1024 / 1024;
    }
}
