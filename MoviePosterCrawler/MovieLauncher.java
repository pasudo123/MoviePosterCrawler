package edu.doubler.toy.movie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

/**
 * 
 * 영화포스터 수집런쳐<br>
 * 
 * @since 2018 11 17
 * @author PASUDO
 *
 */
public class MovieLauncher {
	
	// 2018 11 19 :: 90000 ~ 100000 은 하였음
	
	// ID 값 시작점
	private static final int MIN_ID = 50001;
	
	// ID 값 종료점
	private static final int MAX_ID = 100000;	
	
	// 체크 단위
	private static final int UNIT = 100;
	
	public static void main(String[]args) {
		MovieLauncher launcher = new MovieLauncher();
		launcher.start();
	}
	
	private void start() {
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		MovieScraper scraper = null;
		
		MovieKeeper keeper = getCallbackMovieKeeper();
		
		for(int id = MIN_ID; id <= MAX_ID; id++) {
			scraper = new MovieScraper(id, keeper);
			executor.execute(scraper);
		}
		
		System.out.println("-- Task");
		
		
		// reference : https://www.baeldung.com/java-executor-service-tutorial
		try {
			executor.shutdown();
			if(!executor.awaitTermination(1, TimeUnit.DAYS)) {
				executor.shutdownNow();
			}
		} 
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * 멀티스레드 이후 콜백 처리 위함
	 * @return
	 */
	private MovieKeeper getCallbackMovieKeeper() {
		
		MovieKeeper keeper = new MovieKeeper() {
			
			int count = 0;
			
			@Override
			public void keepMoviePoster(URL posterUrl, String movieTitle) {
				
				File saveDirectory = new File(MovieKeeper.SAVE_DIRECTORY_PATH);
				if(!saveDirectory.exists()) {
					saveDirectory.mkdirs();
				}
				
				count += 1;
				if(count % UNIT == 0) {
					System.out.println("--> " + count + "/" + ((MAX_ID - MIN_ID) + 1));
				}

				File saveFile = new File(saveDirectory + "\\" + movieTitle + ".png");
				BufferedImage bufferedImage = null;

				try {
					
					bufferedImage = ImageIO.read(posterUrl);
					ImageIO.write(bufferedImage, MovieKeeper.IMAGE_EXT_PNG, saveFile);
					
				}
				catch(IllegalArgumentException e) {
					System.out.println(e.getMessage());
					System.out.println("Post-URL :: " + posterUrl);
					return;
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
					return;
				}
			}
		};
		
		return keeper;
	}
}
