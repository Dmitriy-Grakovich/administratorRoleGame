package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayer();

    Player getPlayerById(Long id);

    Player create(String name, String title, Race race, Profession profession, Integer experience, Date date, Boolean banned);

    void deletePlayer(Long id);

    Player update(Player playerDto, Long id);
}
