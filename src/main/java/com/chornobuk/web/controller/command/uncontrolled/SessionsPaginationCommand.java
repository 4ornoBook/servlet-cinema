package com.chornobuk.web.controller.command.uncontrolled;

import com.chornobuk.web.controller.Path;
import com.chornobuk.web.controller.command.ICommand;
import com.chornobuk.web.model.MovieSessionQueryConstructor;
import com.chornobuk.web.model.entity.MovieSession;
import com.chornobuk.web.model.repository.implementation.MovieSessionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

public class SessionsPaginationCommand implements ICommand {
	MovieSessionRepository movieSessionRepository = new MovieSessionRepository();

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String forward = Path.INDEX_PAGE;
		int currentPage = (int) req.getSession().getAttribute("currentPage");
		int numberOfPages = (int) req.getSession().getAttribute("numberOfPages");
		int limit = (int) req.getSession().getAttribute("limit");
		int page = currentPage;
		LinkedList<MovieSession> sessions = null;
		MovieSessionQueryConstructor constructor = (MovieSessionQueryConstructor) req.getSession()
				.getAttribute("queryConstructor");
		if (constructor == null) {
			constructor = new MovieSessionQueryConstructor();
			constructor.setSortingByTime("ascending");
			constructor.setSortingByDate("ascending");
		}
		if (req.getParameter("page") != null) {
			page = Integer.parseInt(req.getParameter("page"));
			sessions = new LinkedList<>(
					movieSessionRepository.getLimitedWithOffset(constructor.getQuery(), limit, (page - 1) * limit));
			req.getSession().setAttribute("availableSessions", sessions);
			req.getSession().setAttribute("currentPage", page);

		} else if (req.getParameter("moveTo") != null) {
			String action = req.getParameter("moveTo");
			if (action.equals("nextPage")) {
				page += 1;
				if (page <= numberOfPages) {
					sessions = new LinkedList<>(movieSessionRepository.getLimitedWithOffset(constructor.getQuery(),
							limit, (page - 1) * limit));
				}
			} else {
				page -= 1;
				if (page > 0) {
					sessions = new LinkedList<>(movieSessionRepository.getLimitedWithOffset(constructor.getQuery(),
							limit, (page - 1) * limit));
				}
			}
		}
		if (sessions != null) {
			req.getSession().setAttribute("availableSessions", sessions);
			req.getSession().setAttribute("currentPage", page);
		}
		return forward;
	}
}
