package com.hust.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hust.dto.UserDTO;

public class UserRegistrationClient {
	private static RestTemplate restTemplate = new RestTemplate();
	private static final String USER_REGISTRATION_BASE_ULR = "http://localhost:8080/api/user/";

	public UserDTO getUserById(final Long userId) {
		return restTemplate.getForObject(USER_REGISTRATION_BASE_ULR + "/{id}", UserDTO.class, userId);
	}

	public UserDTO[] getAllUsers() {

		return restTemplate.getForObject(USER_REGISTRATION_BASE_ULR, UserDTO[].class);
	}

	public UserDTO createUser(final UserDTO user) {
		return restTemplate.postForObject(USER_REGISTRATION_BASE_ULR, user, UserDTO.class);
	}

	public void updateUser(final Long userId, final UserDTO user) {
		 restTemplate.put(USER_REGISTRATION_BASE_ULR + "/{id}", user, userId);
	}
	public void deleteUser(final Long userId) {
		restTemplate.delete(USER_REGISTRATION_BASE_ULR+"/{id}",userId);
	}
	public ResponseEntity<UserDTO> getUserByUsingExchangeAPI(final Long userId){
		HttpEntity<UserDTO> httpEntity=new HttpEntity<UserDTO>(new UserDTO());
		return restTemplate.exchange(USER_REGISTRATION_BASE_ULR+"/{id}",HttpMethod.GET,httpEntity,UserDTO.class,userId);
	}
	public static void main(String[] args) {
		UserRegistrationClient userRegistrationClient = new UserRegistrationClient();
		UserDTO[] users = userRegistrationClient.getAllUsers();
		for (UserDTO userDTO : users) {
			System.out.println("User-ID" + userDTO.getId() + " User-Name" + userDTO.getName());
		}
	}
}
