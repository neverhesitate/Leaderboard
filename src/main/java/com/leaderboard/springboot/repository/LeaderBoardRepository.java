package com.leaderboard.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leaderboard.springboot.model.LeaderBoard;

/**
 * MobileRepository
 * @author funlu
 *
 */
@Repository
public interface LeaderBoardRepository extends JpaRepository<LeaderBoard, Long> {
	@Query("SELECT l FROM LeaderBoard l WHERE l.userName = ?1")
    LeaderBoard findByUserName(String userName);
}
