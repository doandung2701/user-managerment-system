package com.hust;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.hust.Rest.UserRegistrationRestController;
import com.hust.dto.UserDTO;
import com.hust.repository.UserJpaRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserRegistrationRestController.class)
@ContextConfiguration(classes = UserRegistrationSystemApplication.class)
public class UserRegistrationSystemApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserJpaRepository userJpaRepository;

	private MediaType contentType;
	private UserDTO user;
	private Optional<UserDTO> userReturn;
	private static final String securityUserName = "admin";
	private static final String securityUserPassword = "password";

	@Before
	public void setup() {
		contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
				Charset.forName("utf-8"));
		user = new UserDTO();
		user.setName("Ravi Kant Soni");
		user.setAddress("JP Nagar; Bangalore; India");
		user.setEmail("ravikantsoni.author@gmail.com");

	}

	@Test
	public void shouldReturnSuccessMessage() throws Exception {

//		String userCredential = securityUserName + ":" + securityUserPassword;
//		byte[] base64UserCredentialData = Base64.encodeBase64(userCredential.getBytes());
//		HttpHeaders authenticationHeaders = new HttpHeaders();
//		authenticationHeaders.set("Authorization", "Basic " + new String(base64UserCredentialData));
//		HttpEntity<Void> httpEntity = new HttpEntity<Void>(authenticationHeaders);
//		
//		HttpServletRequest request = mock(HttpServletRequest.class);
		when(this.userJpaRepository.findById(1L)).thenReturn(userReturn);
		this.mockMvc.perform(get("/api/user/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.name", is("Ravi Kant Soni")))
				.andExpect(jsonPath("$.address", is("JP Nagar; Bangalore; India")))
				.andExpect(jsonPath("$.email", is("ravikantsoni.author@gmail.com"))).andDo(print());
	}
}
