package library.service;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import DAO.AuthourDAO;
import model.Authour;
import service.logic.AuthourService;

public class AuthourServiceTest {
	@Mock
	private AuthourDAO authourDaoMock;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void shouldCreateAuthour() {
		Authour authour = this.generateAuthour();
		
		AuthourService autServ = new AuthourService(authourDaoMock);
		autServ.addAuthour(authour);
		
		Mockito.verify(authourDaoMock).insert(authour);
	}	
	
	@Test
	void shouldNotInsertAuthourBecauseDataIsMissing() {
		Authour authour = this.generateAuthour();
		authour.setName(null);
		
		AuthourService autServ = new AuthourService(authourDaoMock);
		autServ.addAuthour(authour);
		
		Mockito.verify(authourDaoMock).insert(authour);
	}	
	
	private Authour generateAuthour() {
		LocalDate now = LocalDate.now();
		LocalDate nowMinus20Years = LocalDate.of(now.getYear() - 20, now.getMonth(), now.getDayOfMonth());
		
		Authour authour = new Authour();
		authour.setAge(20);
		authour.setBirthday(nowMinus20Years.atStartOfDay(ZoneId.systemDefault()).toInstant());
		authour.setName("Test of auhtour name");
		authour.setSurname("Test of authour surname");
		
		return authour;
	}
}
