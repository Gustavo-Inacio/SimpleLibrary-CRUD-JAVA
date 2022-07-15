package service.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.messages.Message;

public interface Action {
	public Message execute(HttpServletRequest request, HttpServletResponse response);
}
