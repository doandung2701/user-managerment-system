package com.hust.Rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hust.dto.UserDTO;
import com.hust.exception.CustomErrorType;
import com.hust.repository.UserJpaRepository;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
	public static final Logger Logger = LoggerFactory.getLogger(UserRegistrationRestController.class);
	@Autowired
	private UserJpaRepository userJpaRepository;


	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> listAllUsers() {
		List<UserDTO> users = this.userJpaRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user) {
		Logger.info("Create user:{}",user);
		if (this.userJpaRepository.findByName(user.getName()) != null) {
			return new ResponseEntity<UserDTO>(
					new CustomErrorType(
							"Unable to create new user.A User with name " + user.getName() + " already exist."),
					HttpStatus.NOT_FOUND);
		}
		this.userJpaRepository.save(user);
		return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id) {
		UserDTO user = userJpaRepository.findById(id).get();
		if (user == null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("User with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user) {
		// fetch user based on id and set it to currentUser object of type UserDTO
		UserDTO currentUser = userJpaRepository.findById(id).get();
		if (currentUser==null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to update. User with id "+id+" not found"),HttpStatus.NOT_FOUND);
		}
		// update currentUser object data with user object data
		currentUser.setName(user.getName());
		currentUser.setAddress(user.getAddress());
		currentUser.setEmail(user.getEmail());
		// save currentUser obejct
		userJpaRepository.saveAndFlush(currentUser);
		// return ResponseEntity object
		return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
		UserDTO user=this.userJpaRepository.findById(id).get();
		if (user==null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to delete. User with id "+id+" not found."),HttpStatus.NOT_FOUND);
		}
		this.userJpaRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
