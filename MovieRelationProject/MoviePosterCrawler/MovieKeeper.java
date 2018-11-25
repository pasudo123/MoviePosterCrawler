package edu.doubler.toy.movie;

import java.util.ArrayList;

public interface MovieKeeper {
	
	public static final String SAVE_DIRECTORY_PATH = "E:\\doubler\\poster\\";
	public static final String IMAGE_EXT_PNG = "png";
	
	public void addMoviePosterInfo(String posterUrl, String movieTitle);
	
	public ArrayList<String[]> getMoviePosterList();
}
