package com.chornobuk.web.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class MovieSession extends Entity {
	private long movieId;
	private LocalDate movieDate;
	private LocalTime beginningTime;
	private LocalTime endingTime;
	private int ticketPrice;

	private Movie movie;

	public MovieSession(long id, long movieId, LocalDate movieDate, LocalTime beginningTime, LocalTime endingTime,int ticketPrice, Movie movie) {
		super(id);
		this.movieId = movieId;
		this.movieDate = movieDate;
		this.beginningTime = beginningTime;
		this.endingTime = endingTime;
		this.ticketPrice = ticketPrice;
		this.movie = movie;
	}

	public MovieSession(long id) {
		super(id);
	}

	public MovieSession() {

	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public LocalDate getMovieDate() {
		return movieDate;
	}

	public void setMovieDate(LocalDate movieDate) {
		this.movieDate = movieDate;
	}

	public LocalTime getBeginningTime() {
		return beginningTime;
	}

	public void setBeginningTime(LocalTime beginningTime) {
		this.beginningTime = beginningTime;
	}

	public LocalTime getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(LocalTime endingTime) {
		this.endingTime = endingTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
}

