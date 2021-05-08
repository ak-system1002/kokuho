package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.domain.Player;
import com.example.domain.Ranking;
import com.example.mapper.PlayerMapper;

public class TeamRanking {

	public static List<Ranking> getRanking(PlayerMapper playerMapper) {
    	List<Ranking> rankings = new ArrayList<>();

    	int i = 1;
    	List<Player> players = playerMapper.ranking();
    	for (Player player : players) {
    		Ranking ranking = new Ranking();
    		ranking.setRank(i++);
    		ranking.setTeam(player.getTeam());
    		ranking.setPoint(player.getPoint().intValue());
    		rankings.add(ranking);
    	}

    	return rankings;
	}
}
