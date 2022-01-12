package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.IdInValidException;
import com.game.exception.PlayerNotFountException;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAllPlayer() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(Long id) {
        if(id== 0L)
            throw new IdInValidException();
        return playerRepository.findById(id).orElseThrow(PlayerNotFountException::new);
    }

    @Override
    public void update(Long userId, String name, String title, Race race, Profession profession, Integer experience, Long date, Boolean banned) {
        if (userId <= 0) throw new IdInValidException();
        Player player = getPlayerById(userId);
        if(player!=null)
            extracted(name, title, race, profession, experience, date, banned, player);
    }

    private void extracted(String name, String title, Race race, Profession profession, Integer experience, Long date, Boolean banned, Player player) {
        if(experience < 0  ) {
            throw new IdInValidException();
        } else {

            int newLevel;
            if(race!=null)
                player.setRace(race);
            if(name!=null)
                player.setName(name);
            if(experience!=null) {
                player.setExperience(experience);
                newLevel = (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
                player.setLevel(newLevel);
                player.setUntilNextLevel(50 * (newLevel + 1) + (newLevel + 2) - experience);
            }
            if(profession!=null)
                player.setProfession(profession);
            if(title!=null)
                player.setTitle(title);
            if(date!=null)
            player.setBirthday(new Date(date));
            player.setBanned(banned);
            playerRepository.save(player);
        }
    }

    @Override
    public void create(String name, String title, Race race, Profession profession, Integer experience, Long date, Boolean banned) {

        if (name == null || title == null || race == null || profession == null || date == null || experience == null ||
                date < 946155600000L || date >= 32503669200000L ||  experience >= 10000000 || experience < 0) {
            throw new IdInValidException();
        }
        Player player = new Player();
        int newLevel = (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;

        player.setId(player.getId());
        player.setName(name);
        player.setTitle(title);
        player.setRace(race);
        player.setProfession(profession);
        player.setBirthday(new Date(date));
        player.setLevel(newLevel);
        player.setBanned(banned);
        player.setUntilNextLevel(50 * (newLevel + 1) + (newLevel + 2) - experience);

    }

    @Override
    public void deletePlayer(Long id) {
        if( id <= 0 ) throw new IdInValidException();
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }


}
