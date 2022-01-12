package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.IdInValidException;
import com.game.exception.PlayerNotFountException;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component
@Service
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
    public Player create(String name, String title, Race race, Profession profession, Integer experience, Date date, Boolean banned) {

        if (name == null || title == null || race == null || profession == null || date == null || experience == null ||
                date.getTime() < 946155600000L || date.getTime() >= 32503669200000L ||  experience > 10000000 || experience < 0
                || title.length()>30 || name.length()>30) {
            throw new IdInValidException();
        } else {
        Player player = new Player();
        int level = (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
        player.setName(name);
        player.setTitle(title);
        player.setRace(race);
        player.setProfession(profession);
        player.setBirthday(date);
        player.setLevel(level);
        player.setBanned(banned);
        player.setExperience(experience);
        player.setUntilNextLevel(Math.abs(50 * (level + 1) + (level + 2) - experience));
        playerRepository.save(player);
        return getPlayerById(player.getId());
        }

    }

    @Override
    public void deletePlayer(Long id) {
        if( id <= 0 ) throw new IdInValidException();
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }

    @Override
    public Player update(Player playerDto, Long id) {
            if(playerDto.getId() != null){
                playerDto.setId(id);
            }

            Player player = getPlayerById(id);
            if (playerDto.getName() != null) {
                if (playerDto.getName().length() > 30){
                    throw new IdInValidException();
                } else {
                    player.setName(playerDto.getName());
                }
            }
            if (playerDto.getTitle() != null){
                if (playerDto.getTitle().length()>30){
                    throw new IdInValidException();
                } else {
                    player.setTitle(playerDto.getTitle());
                }
            }
            if (playerDto.getRace() != null) {
                player.setRace(playerDto.getRace());
            }
            if (playerDto.getProfession() != null) {
                player.setProfession(playerDto.getProfession());
            }
            if (playerDto.getExperience() != null) {
                if ( playerDto.getExperience() > 10000000 || playerDto.getExperience() < 0){
                    throw new IdInValidException();
                } else {
                    int exp = playerDto.getExperience();
                    player.setExperience(exp);
                    int level = (int) (Math.sqrt(2500 + 200 * exp) - 50) / 100;
                    if(playerDto.getLevel()!= null){
                        player.setLevel(playerDto.getLevel());
                    } else {
                    player.setLevel(level);}
                    player.setUntilNextLevel(Math.abs(50 * (level + 1) + (level + 2) - exp));
                }
            }

            if (playerDto.getBanned() != null) {
                player.setBanned(playerDto.getBanned());
            }
            if (playerDto.getBirthday() != null) {
                if(playerDto.getBirthday().getTime() < 946155600000L || playerDto.getBirthday().getTime() >= 32503669200000L ) {
                    throw new IdInValidException();
                } else {
                    player.setBirthday(playerDto.getBirthday());
                }
            }



            return playerRepository.save(player);
        }



}
