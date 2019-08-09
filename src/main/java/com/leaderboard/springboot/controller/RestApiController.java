package com.leaderboard.springboot.controller;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.leaderboard.springboot.model.LeaderBoard;
import com.leaderboard.springboot.service.LeaderBoardService;
import com.leaderboard.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	LeaderBoardService leaderBoardService; //Service which will do all user data retrieval/manipulation work
	

	// -------------------Create Dummy User---------------------------------------------
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<LeaderBoard>> createDummyPlayers() {
		List<LeaderBoard> players = leaderBoardService.populateDummyPlayers();
		if (players.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LeaderBoard>>(players, HttpStatus.OK);
	}*/
	
	// -------------------Retrieve All Players---------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/player/list", method = RequestMethod.GET)
	public ResponseEntity<List<LeaderBoard>> listAllUsers() {
		List<LeaderBoard> players = leaderBoardService.findAllPlayers();
		if (players.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<LeaderBoard>>(players, HttpStatus.OK);

	}

	// -------------------Retrieve Single Player ById------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		LeaderBoard player = leaderBoardService.findById(id);
		if (player == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeaderBoard>(player, HttpStatus.OK);
	}
	
	// -------------------Retrieve Single Player ByName------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/player", method = RequestMethod.GET)
	public ResponseEntity<?> getPlayerByUserName(@RequestParam(value="userName") String userName) {
		logger.info("Fetching Player with user name {}", userName);
		LeaderBoard player = leaderBoardService.findByUserName(userName);
		if (player == null) {
			logger.error("User with name {} not found.", userName);
			return new ResponseEntity(new CustomErrorType("Player with user name " + userName 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<LeaderBoard>(player, HttpStatus.OK);
	}

	// -------------------Create a Player-------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/player/", method = RequestMethod.POST)
	public ResponseEntity<?> createPlayer(@RequestBody LeaderBoard player, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Player : {}", player);

		if (leaderBoardService.isPlayerExist(player)) {
			logger.error("Unable to create. A Player with name {} already exist", player.getUserName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			player.getUserName()+ " already exist."),HttpStatus.CONFLICT);
		}
		player.setCreatedDate(new Date());
		player.setUpdatedDate(new Date());
		player.setIsDeleted(0);
		leaderBoardService.saveLeaderBoard(player);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(player.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	

	// ------------------- Update a Player ------------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/player/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody LeaderBoard player) {
		logger.info("Updating User with id {}", id);
		
		LeaderBoard currentPlayer = leaderBoardService.findById(id);
		if (currentPlayer == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentPlayer.setImageUrl(player.getImageUrl());
		currentPlayer.setTotalScore(player.getTotalScore());
		currentPlayer.setUserName(player.getUserName());
		currentPlayer.setUpdatedDate(new Date());
		leaderBoardService.updateLeaderBoard(currentPlayer);
		return new ResponseEntity<LeaderBoard>(currentPlayer, HttpStatus.OK);
	}

	// ------------------- Delete a Player-----------------------------------------
    /*
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
    */
	// ------------------- Delete All Players-----------------------------
    /*
	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
    */

}