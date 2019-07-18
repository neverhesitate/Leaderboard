package com.leaderboard.springboot.service;

import java.util.List;

import com.leaderboard.springboot.model.LeaderBoard;
/**
 * LeaderBoardService
 * @author funlu
 *
 */
public interface LeaderBoardService {
	List<LeaderBoard> populateDummyPlayers();

	LeaderBoard findByUserName(String userName);
	
	void saveLeaderBoard(LeaderBoard player);
	
	void updateLeaderBoard(LeaderBoard player);
	
	void deleteLeaderBoardById(long id);

	List<LeaderBoard> findAllPlayers();
	
	void deleteAllPlayers();
	
	boolean isPlayerExist(LeaderBoard user);
	
}
