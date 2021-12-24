package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayer();

    Player getPlayerById(Long id);
}
