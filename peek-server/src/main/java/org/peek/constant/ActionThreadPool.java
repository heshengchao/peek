package org.peek.constant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**线程池
 * @author heshengchao
 */
@Slf4j
public class ActionThreadPool {
	private static final  ExecutorService fixedThreadPool = new ThreadPoolExecutor(5, 80,30L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

	public static void execute(final Call call) {
		try {
			fixedThreadPool.execute(new Runnable() {
				@Override public void run() {
					try {
						call.run();
					}catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			});
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	public interface Call{
		public void run();
	}
}
