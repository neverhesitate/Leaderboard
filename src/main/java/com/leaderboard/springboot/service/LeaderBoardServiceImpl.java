package com.leaderboard.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderboard.springboot.model.LeaderBoard;
import com.leaderboard.springboot.repository.LeaderBoardRepository;

@Service("leaderBoardService")
public class LeaderBoardServiceImpl implements LeaderBoardService{
	
	@Autowired
	private LeaderBoardRepository leaderBoardRepository;
	
	@Override
	public List<LeaderBoard> populateDummyPlayers(){
		
		leaderBoardRepository.deleteAllInBatch();
		
		List<LeaderBoard> players = new ArrayList<LeaderBoard>();
		LeaderBoard ld1 = new LeaderBoard();
		ld1.setUserName("test1");
		ld1.setTotalScore(10);
		ld1.setCreatedDate(new Date());
		ld1.setUpdatedDate(new Date());
		ld1.setIsDeleted(0);
		
		LeaderBoard ld2 = new LeaderBoard();
		ld1.setUserName("test2");
		ld1.setTotalScore(20);
		ld2.setCreatedDate(new Date());
		ld2.setUpdatedDate(new Date());
		ld2.setIsDeleted(0);
		
	
		leaderBoardRepository.save(ld1);
		leaderBoardRepository.save(ld2);
		
		
		players.add(ld1);
		players.add(ld2);
	
		return players;
	}
	
	@Override
	public List<LeaderBoard> findAllPlayers() {
		return leaderBoardRepository.findAll();
	}
	
	
	@Override
	public LeaderBoard findByUserName(String userName) {
		return leaderBoardRepository.findByUserName(userName);
	}
	
	@Override
	public void saveLeaderBoard(LeaderBoard player) {
		leaderBoardRepository.save(player);
	}
	
	@Override
	public void updateLeaderBoard(LeaderBoard player) {
		leaderBoardRepository.save(player);		
	}
	
	@Override
	public void deleteLeaderBoardById(long id) {
		leaderBoardRepository.delete(id);
	}

	public boolean isUserExist(LeaderBoard player) {
		return findByUserName(player.getUserName())!=null;
	}

	@Override
	public void deleteAllPlayers() {
		leaderBoardRepository.deleteAll();
	}

	@Override
	public boolean isPlayerExist(LeaderBoard player) {
		return leaderBoardRepository.exists(player.getId());
	}

	@Override
	public LeaderBoard findById(long id) {
		return leaderBoardRepository.findOne(id);
	}

}
