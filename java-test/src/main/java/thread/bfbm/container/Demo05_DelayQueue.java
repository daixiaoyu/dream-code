
package thread.bfbm.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
/**
 *  巴分巴秒官方交流QQ群:750555573
 */
public class Demo05_DelayQueue {

   private  static BlockingQueue<Task> queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        queue.put(new Task("task01",10000));
        queue.put(new Task("task02",5000));
        queue.put(new Task("task03",2000));
        for (int i = 0; i <3; i++) {
            Task task=queue.take();
            System.out.println("ID:"+task.getDelayedId()+"time:"+task.getDelayTime());
        }
    }
}

class Task implements Delayed {
    private long delayTime;
    private String delayedId;
    public Task(String delayedId, long delayTime) {
        this.delayedId=delayedId;
        this.delayTime = delayTime;
    }

    /**
     * 根据getDelay方法的定义，定义按时间排序的规则
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public String getDelayedId() {
        return delayedId;
    }

    public long getDelayTime() {
        return delayTime;
    }
}
