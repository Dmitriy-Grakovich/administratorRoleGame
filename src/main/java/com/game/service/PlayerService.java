package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayer();

    Player getPlayerById(Long id);

    //void update(Long userId, String name, String title, Race race, Profession profession, Integer experience);

    void update(Long userId, String name, String title, Race race, Profession profession, Integer experience, Long date, Boolean banned);

    void create(String name, String title, Race race, Profession profession, Integer experience, Long date, Boolean banned);

    void deletePlayer(Long id);
}
