package edu.doubler.toy.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.doubler.toy.movie.dao.MoviePosterDao;
import edu.doubler.toy.movie.util.DatabaseProcessorOnMoviePosterTable;

/**
 * 영화 관련 데이터를 DB에 삽입하는 객체 <br>
 * 
 * @since 2018 11 25
 * @author PASUDO
 *
 */
public class MovieDbSaver {

	public static void main(String[]args) {
		
		MovieDbSaver saver = new MovieDbSaver();
		
		// 디비 값 ArrayList 획득
		ArrayList<MoviePosterDao> moviePosterDaoList = saver.getMoviePosterData();

			
		// 데이터 삽입
		DatabaseProcessorOnMoviePosterTable dbProcessor = new DatabaseProcessorOnMoviePosterTable();
		dbProcessor.insertOnMoviePosterTable(moviePosterDaoList);
		
	}

	public ArrayList<MoviePosterDao> getMoviePosterData(){

		ArrayList<MoviePosterDao> moviePosterDaoList = new ArrayList<MoviePosterDao>();
		
		File file = new File("E:\\doubler\\poster");
		File[]fileList = file.listFiles();
		
		for(File posterFile : fileList) {
			
			String title = posterFile.getName();
			String path = posterFile.getAbsolutePath();
			
			int lastDotIndex = title.lastIndexOf(".");
			title = title.substring(0, lastDotIndex);
			
			moviePosterDaoList.add(new MoviePosterDao(title, path));
			
		}
		
		return moviePosterDaoList;
	}

}
