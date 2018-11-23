package edu.doubler.toy.movie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * 영화포스터 수집런쳐<br>
 * 
 * @since 2018 11 17
 * @author PASUDO
 *
 */
public class MovieLauncher {
	
	// 2018 11 19 :: 90000 ~ 100000 하였음
	// 2018 11 20 :: 70001 ~ 80000 하였음
	// 2018 11 24 :: 80001 ~ 90000 하였음
	// 2018 11 24 :: 60001 ~ 70000 하였음

	private static final Logger logger = LogManager.getLogger(MovieLauncher.class);
	
	// ID 값 시작점
	private static final int MIN_ID = 100001;
	
	// ID 값 종료점
	private static final int MAX_ID = 110000;	
	
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
		finally {
			saveMoviePoster(keeper.getMoviePosterList());
		}
	}
	
	/**
	 * 수집한 영화 포스터 URL 을 파일로 저장.
	 * @param moviePosterList
	 */
	private void saveMoviePoster(ArrayList<String[]> moviePosterList) {
		
		URL posterUrl = null;
		String movieTitle = null;
		
		logger.info("수집 포스터 :: " + moviePosterList.size());
		
		for(String values[] : moviePosterList) {
			
			try {
				
				posterUrl = new URL(values[0]);			// 영화 포스터 URL
				movieTitle = new String(values[1]);		// 영화 제목
				
				File saveDirectory = new File(MovieKeeper.SAVE_DIRECTORY_PATH);
				if(!saveDirectory.exists()) {
					saveDirectory.mkdirs();
				}
				
				File saveFile = new File(saveDirectory + "\\" + movieTitle + ".png");
				BufferedImage bufferedImage = null;
				
				bufferedImage = ImageIO.read(posterUrl);
				ImageIO.write(bufferedImage, MovieKeeper.IMAGE_EXT_PNG, saveFile);
				
			}
			catch(MalformedURLException e) {
				System.err.println(e.getMessage());
				return;
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
	}
	
	/**
	 * 콜백 처리 객체
	 * @return
	 */
	private MovieKeeper getCallbackMovieKeeper() {
		
		MovieKeeper keeper = new MovieKeeper() {
			
			ArrayList<String[]> moviePosterList = new ArrayList<String[]>();

			@Override
			public void addMoviePosterInfo(String posterUrl, String movieTitle) {
				moviePosterList.add(new String[] {posterUrl, movieTitle});
			}

			@Override
			public ArrayList<String[]> getMoviePosterList() {
				return moviePosterList;
			}
		};
		
		return keeper;
	}
}
