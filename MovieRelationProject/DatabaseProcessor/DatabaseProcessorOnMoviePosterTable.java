package edu.doubler.toy.movie.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.doubler.toy.movie.dao.MoviePosterDao;

public class DatabaseProcessorOnMoviePosterTable {
	
	private static final int BATCH_UNIT = 2000;
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	
	private static final String INSERT_QUERY = "INSERT INTO MOVIE_POSTER_TB (MOVIE_TITLE, MOVIE_POSTER_PATH) VALUES (?, ?)";
	
	/**
	 * Connection 객체 세팅
	 */
	private void setConnection() {
		if(connection == null) {
			this.connection = ConnectionMaker.getConnection();
		}
	}
	
	/**
	 * MOVIE_POSTER_TB 에 데이터 삽입
	 * @return
	 */
	public void insertOnMoviePosterTable(ArrayList<MoviePosterDao> moviePosterDaoList) {
		
		setConnection();
		
		// 배치 처리
		/**
		 * BATCH Process
		 * (1) addBatch()		:: 작성
		 * (2) executeBatch()	:: 실행
		 * (3) clearBatch()		:: 제거
		 */
		try {
			
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(INSERT_QUERY);
			
			String title = null;
			String path = null;
			
			for(int i = 0; i < moviePosterDaoList.size(); i++) {

				title = moviePosterDaoList.get(i).getMovieTitle();
				path = moviePosterDaoList.get(i).getMoviePosterPath();
				
				preparedStatement.setString(1, title);
				preparedStatement.setString(2, path);
				preparedStatement.addBatch();
				 
				if((i + 1) % BATCH_UNIT == 0) {
					System.out.println("executeBatch()");
					preparedStatement.executeBatch();
					connection.commit();
					preparedStatement.clearBatch();
					preparedStatement.clearParameters();
				}
			}
			
			preparedStatement.executeBatch();
			connection.commit();
			preparedStatement.clearBatch();
			preparedStatement.clearParameters();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
				
				if(preparedStatement != null) {
					preparedStatement.close();
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * MOVIE_POSTER_TB 에서 데이터 조회
	 * @return
	 */
	public ArrayList<String[]> selectOnMoviePosterTable(){
		
		
		
		return null;
	}
	
}
