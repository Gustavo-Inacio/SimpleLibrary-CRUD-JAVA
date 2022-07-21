package service.logic;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.AuthourDAO;
import dto.enter.authour.RemoveAuthourDTO;
import model.Authour;
import util.Exceptions.MissingDataException;
import util.JPA.JPAUtil;

public class AuthourService {

	private AuthourDAO authourDao;
	
	public AuthourService(AuthourDAO autdao) {
		this.authourDao = autdao;
	}
	
	public AuthourService() {
		EntityManager em = JPAUtil.getEntityManeger();
		this.authourDao = new AuthourDAO(em);
	}
	
	public List<Authour> getAllStatus1(){
		return authourDao.selectAllStatus1();
	}
	
	public void addAuthour(Authour authour) {
		this.authourDao.insert(authour);
	}
	
	public void removeAuthour(RemoveAuthourDTO dto) throws MissingDataException {
		if(dto.getId() == null) 
			throw new MissingDataException("The authour id MUST be informed for delete acition");
		
		this.authourDao.remove(dto.getId());
	}
	
	public List<Authour> getListByInfo(Authour auhtour) throws MissingDataException {
		
		return this.authourDao.getPartOfInfo(auhtour);
	}
	
	public void updateAuthour(Authour authour) throws MissingDataException {
		if(authour.getId() == null) 
			throw new MissingDataException("The authour id MUST be informed for update acition");
		
		this.authourDao.update(authour);
	}

}
