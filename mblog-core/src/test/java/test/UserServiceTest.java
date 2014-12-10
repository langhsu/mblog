/**
 * 
 */
package test;

import hilo.core.persist.entity.UserPO;
import hilo.core.pojos.User;
import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author langhsu
 *
 */
public class UserServiceTest extends TestCase {
	
	@Test
	public void testReg() {
		User user = new User();
		Assert.notNull(user, "Parameter user can not be null!");
		
		UserPO check = null;
		Assert.isNull(check, "Username already exists!");
		
		System.out.println("end");
	}
}
