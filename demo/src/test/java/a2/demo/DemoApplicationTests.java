package a2.demo;

import a2.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoApplicationTests {

	/*@Autowired
	private UserWriteRepository userRepository;

	@Autowired
	private BookWriteRepository bookRepository;

	@Autowired
	private StaffWriteRepository staffRepository;

	@Test
	public void addUser() {
		User user = new User();
		user.setUsername("test1");
		user.setPassword("test1");
		user.setPaymentMethod("Day");
		userRepository.save(user);

		User userFind = userRepository.findById("test1").get();

		assertThat(user.getUsername()).isEqualTo(userFind.getUsername());
	}

	/*@Test
	public void addUserCommand()
	{
		User event=new RegisterUser();
		user.setUsername("test1");
		user.setPassword("test1");
		user.setPaymentMethod("Day");

		ICommandHandler handler = new RegisterUserCommandHandler();
		Mediator mediator = new Mediator();
		mediator.addHandler(handler);
		mediator.mediate(event);

		User userFind = userRepository.findById("test1").get();

		assertThat(user.getUsername()).isEqualTo(userFind.getUsername());
	}*/

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
