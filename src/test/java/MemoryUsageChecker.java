public class MemoryUsageChecker implements Runnable {
    private final Thread chekerThread;
    private final long memoryLimit;
    private boolean isStopped;

    public MemoryUsageChecker(long memoryLimit) {
        this.memoryLimit = memoryLimit;
        chekerThread = new Thread(this);
        chekerThread.start();
    }

    public void run() {
        long maxValue = 0;
        while (!isStopped) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long memoryUsed = 0;
            memoryUsed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            if (memoryUsed > maxValue) {
                maxValue = memoryUsed;
                System.out.println("Heap memory usage : " + (maxValue / 1024 / 1024));
            }
//            assertTrue(memoryUsed <= memoryLimit, "Memory usage is " + (memoryUsed / 1024 / 1024) +
//                    " It's higher than needed because memory limit is " + (memoryLimit / 1024 / 1024));
        }

    }

    public void stop() {
        this.isStopped = true;
    }
}
