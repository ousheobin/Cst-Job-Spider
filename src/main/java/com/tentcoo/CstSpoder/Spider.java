package com.tentcoo.CstSpoder;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class Spider {

	private static AtomicInteger currentId = new AtomicInteger(1000) ;
	private static AtomicInteger finishSignal = new  AtomicInteger(0) ;
	private static int maxId = 2457;

	public static void main(String[] args) {

		LinkedList<CstWebArticle> articleList = new LinkedList<CstWebArticle>();

		ExecutorService threadPool = new ThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		for (int i = 0; i < 10; i++) {
			threadPool.execute(() -> {
				int id = currentId.getAndIncrement();
				while (id <= maxId) {
					System.out.println(Thread.currentThread().getName()+" --> "+id);
					try {
						CstWebArticle article = GetData.getArticle(id);
						articleList.add(article);
						if( id == maxId ) {
							finishSignal.incrementAndGet();
						}
						id = currentId.getAndIncrement();
					} catch ( Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		while(finishSignal.get() == 0 ) {
			continue;
		}
		articleList.parallelStream()
				.filter(article -> article.getTitle() != null)
				.filter(article -> article.getTitle().contains("招聘"))
				.filter(article -> article.getTitle().contains("："))
				.collect(Collectors.toList())
				.forEach(System.out::println);
		threadPool.shutdown();
	}
}
