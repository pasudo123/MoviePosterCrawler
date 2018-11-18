package edu.doubler.toy.movie;

import java.net.URL;

public interface MovieKeeper {
	
	public static final String SAVE_DIRECTORY_PATH = "E:\\doubler\\poster\\";
	public static final String IMAGE_EXT_PNG = "png";
	
	public void keepMoviePoster(URL posterUrl, String movieTitle);
	
}
