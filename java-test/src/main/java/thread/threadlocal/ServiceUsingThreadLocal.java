package thread.threadlocal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 奈学教育-秋平老师
 **/
public class ServiceUsingThreadLocal {
    static ThreadLocal output = new ThreadLocal();

    public void service() {
        try {
            final OutputStream s = new FileOutputStream("...");
            new Thread(() -> {
                    output.set(s);
                    try {
                        doService();
                    } catch (IOException e) {
                    } finally {
                        try {
                            if(null != s)
                                s.close();
                        } catch (IOException ignore) {
                        }
                    }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doService() throws IOException {
        ((OutputStream)(output.get())).write(0);
        // ...
    }
}