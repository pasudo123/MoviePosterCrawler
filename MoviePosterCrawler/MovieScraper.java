package edu.doubler.toy.movie;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 영화 포스터 크롤러<br>
 * 
 * @since 2018 11 18
 * @author PASUDO
 *
 */
public class MovieScraper implements Runnable{
	
	private static final String [] NONE_PATTERN = new String[] {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
	private static final String HTTPS_PROTOCOL = "https:";
	
	private static final String USER_AGENT = "Chrome";
	private static final int TIME_OUT = 10000;
	
	private static final String HTTP_ERROR_MESSAGE = "HTTP error fetching URL";
	private static final String DAUM_MOVIE_DB_URL = "https://movie.daum.net/moviedb/main?movieId=";
	
	private Document document = null;
	private MovieKeeper keeper = null;
	private String movieId = null;
	
	public MovieScraper(int movieId, MovieKeeper keeper) {
		this.movieId = String.valueOf(movieId);
		this.keeper = keeper;
	}
	
	@Override
	public void run() {
		
		try {
			
			document = Jsoup.connect(DAUM_MOVIE_DB_URL + movieId)
					.userAgent(USER_AGENT)
					.timeout(TIME_OUT)
					.get();
			
			Elements elements = document.getElementsByClass("thumb_summary");
			
			if(document.getElementsByClass("subject_movie").size() == 0){
				return;
			}
			
			if(elements.size() == 0) {
				return;
			}
			
			String movieTitle = document.getElementsByClass("subject_movie").get(0).getElementsByClass("tit_movie").text();
			movieTitle = preprocessTitle(movieTitle);
			
			for(Element element : elements) {
				
				Elements imgElements = element.getElementsByTag("img");
				String imageUrl = HTTPS_PROTOCOL + imgElements.attr("src");
				
				// img 태그의 src 미 존재
				if(HTTPS_PROTOCOL.equals(imageUrl)) {
					continue;
				}
				
				keeper.keepMoviePoster(new URL(imageUrl), movieTitle);
			}
		} 
		catch (IOException e) {
			String errorMessage = e.getMessage();
			
			// 발견 영화 없음.
			if(HTTP_ERROR_MESSAGE.equals(errorMessage)) {
				return;
			}
			
			// connect time out 이 발생하는 경우가 있음
			else {
				System.out.println(errorMessage);
				return;
			}
		}
	}
	
	/**
	 * 영화 제목이 파일명이 되기 때문에 불필요 패턴 제거<br>
	 * @param movieTitle
	 * @return
	 */
	private String preprocessTitle(String movieTitle) {
		
		for(String patternString : NONE_PATTERN) {
			movieTitle = movieTitle.replace(patternString, "");
		}
		
		return movieTitle;
	}
}
