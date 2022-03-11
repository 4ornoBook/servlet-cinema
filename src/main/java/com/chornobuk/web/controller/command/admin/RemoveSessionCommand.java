package com.chornobuk.web.controller.command.admin;

import com.chornobuk.web.controller.Path;
import com.chornobuk.web.controller.command.ICommand;
import com.chornobuk.web.model.dao.MovieSessionDao;
import com.chornobuk.web.model.entity.MovieSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveSessionCommand implements ICommand {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String forward = Path.INDEX_PAGE;
		MovieSession movieSession;
		MovieSessionDao movieSessionDao = new MovieSessionDao();
		long id = Long.parseLong( req.getParameter("sessionId"));
		movieSession = movieSessionDao.get(id);
		movieSessionDao.delete(movieSession);
		List<MovieSession> availableSessions = movieSessionDao.getAvailableSessions();
		req.getSession().setAttribute("availableSessions", availableSessions);
		return forward;
	}
}