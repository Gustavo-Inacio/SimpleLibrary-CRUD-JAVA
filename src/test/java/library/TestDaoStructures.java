package library;

import org.junit.Assert;
import org.junit.Test;

import model.Authour;

public class TestDaoStructures {
	
	@Test
	public void shouldDoOk() {
		Authour authour = new Authour();
		
		authour.setName("teste");
		Assert.assertEquals(authour.getName(),"teste");
	}
}
