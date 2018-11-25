package edu.doubler.toy.movie.dao;

/**
 * 데이터베이스에 삽입 Dao <br>
 * 
 * @since 2018 11 25
 * @author PASUDO
 *
 */
public class MoviePosterDao {

	private String movieTitle;
	private String moviePosterPath;

	public MoviePosterDao(String movieTitle, String moviePosterPath) {
		this.movieTitle = movieTitle;
		this.moviePosterPath = moviePosterPath;
	}
	
	public String getMovieTitle() {
		return movieTitle;
	}

	public String getMoviePosterPath() {
		return moviePosterPath;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public void setMoviePosterPath(String moviePosterPath) {
		this.moviePosterPath = moviePosterPath;
	}
}
