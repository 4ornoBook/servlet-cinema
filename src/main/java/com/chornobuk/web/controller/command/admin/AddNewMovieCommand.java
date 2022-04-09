package com.chornobuk.web.controller.command.admin;

import com.chornobuk.web.controller.Path;
import com.chornobuk.web.controller.command.ICommand;
import com.chornobuk.web.model.entity.Genre;
import com.chornobuk.web.model.entity.Movie;
import com.chornobuk.web.model.repository.implementation.GenreRepository;
import com.chornobuk.web.model.repository.implementation.MovieRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class AddNewMovieCommand implements ICommand {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		Logger log = LogManager.getLogger(AddNewMovieCommand.class);
		String errorTag = "is-invalid";
		String name = req.getParameter("name");
		String forward = Path.ADD_NEW_MOVIE_PAGE;
		String releaseDateString = req.getParameter("releaseDate");
		LocalDate releaseDate = (releaseDateString == null || releaseDateString.isEmpty()) ? null : LocalDate.parse(releaseDateString);
		int lengthInMinutes = Integer.parseInt(req.getParameter("length"));
		String[] genresId = req.getParameterValues("genre");
		String imageURL = req.getParameter("imageURL");
		String description = req.getParameter("description");
//		movie name min 1 max 200
		if (name == null || name.isEmpty() || name.length() > 200) {
			req.setAttribute("movieNameError", errorTag);
		}
//		add release date not greater than now
//		|| releaseDate.isAfter(LocalDate.now()) this don't need because cinema
//		can add session for future movies
		else if (releaseDate == null) {
			req.setAttribute("releaseDateError", errorTag);
		}
//		length min 20 max 600
		else if (lengthInMinutes < 20 || lengthInMinutes > 600) {
			req.setAttribute("lengthError", errorTag);
		}
//		add minimal genre
		else if (genresId == null || genresId.length == 0) {
			req.setAttribute("genresError", errorTag);
		}
//		image url not null (optional check is it url)
		else if (imageURL == null || imageURL.isEmpty()) {
			req.setAttribute("imageURLError", errorTag);
		}
//		description not null
		else if (description == null || description.isEmpty()) {
			req.setAttribute("descriptionError", errorTag);
		} else {
			forward = Path.REDIRECT_COMMAND;
			Movie movie = new Movie();
			MovieRepository movieRepository = new MovieRepository();
			GenreRepository genreRepository = new GenreRepository();
			LinkedList<Genre> genres = genreRepository.getAll()
					.stream()
					.filter(x -> Arrays.binarySearch(genresId, x.getName()) >= 0)
					.collect(Collectors.toCollection(LinkedList::new));

			movie.setName(name);
			movie.setGenres(genres);
			movie.setDescription(description);
			movie.setImageURL(imageURL);
			movie.setLengthInMinutes(lengthInMinutes);
			movie.setReleaseDate(releaseDate);
			movieRepository.add(movie);
			try {
				resp.sendRedirect(Path.INDEX_PAGE);
			} catch (IOException e) {
				log.error("redirect error", e);
			}
		}
		return forward;
	}
}
