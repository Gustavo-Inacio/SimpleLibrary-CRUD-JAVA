package service.logic;

import DAO.AuthourDAO;
import dto.enter.authour.RemoveAuthourDTO;
import model.Authour;

public class AuthourService {

	private AuthourDAO authourDao;
	
	public AuthourService(AuthourDAO autdao) {
		this.authourDao = autdao;
	}
	
	public void addAuthour(Authour authour) {
		this.authourDao.insert(authour);
	}
	
	public void removeAuthour(RemoveAuthourDTO dto) {
		
	}

}
