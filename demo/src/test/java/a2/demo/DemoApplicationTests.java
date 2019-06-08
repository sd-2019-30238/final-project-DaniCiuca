package a2.demo;

import a2.demo.model.User;
import a2.demo.repository.PlayerRepository;
import a2.demo.repository.TeamRepository;
import a2.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoApplicationTests {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void addUser() {
		User user = new User();
		user.setUsername("test1");
		user.setPassword("test1");
		user.setEmail("test@gmail.com");
		userRepository.save(user);

		User userFind = userRepository.findById("test1").get();

		assertThat(user.getUsername()).isEqualTo(userFind.getUsername());
	}

	/*@Test
	public void acceptUser()
	{
		User user = new User();
		user.setUsername("test2");
		user.setPassword("test2");
		user.setPaymentMethod("Month");
		userRepository.save(user);
		user.setAccepted(true);
		userRepository.save(user);

		User userFind = userRepository.findById("test2").get();

		assertThat(userFind.getAccepted()).isEqualTo(true);
	}*/

}
