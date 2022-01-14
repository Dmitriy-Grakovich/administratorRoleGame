package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;

public interface PlayerService {

    List<Player> getAllPlayer(Map<String, String> params, Pageable pages);

    Player getPlayerById(Long id);

    void deletePlayer(Long id);

    Player update(Player playerDto, Long id);

    Player create(Player playerDto);

    Integer getCountPlayers(Map<String, String> params);
}
