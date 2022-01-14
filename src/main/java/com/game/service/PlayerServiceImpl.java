package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.IdInValidException;
import com.game.exception.PlayerNotFountException;
import com.game.repository.PlayerRepository;
import com.game.specification.PlayerSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

;
@Component
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerSpecification playerSpecification;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerSpecification playerSpecification) {
        this.playerRepository = playerRepository;
        this.playerSpecification = playerSpecification;
    }

    @Override
    public List<Player> getAllPlayer(Map<String, String> params, Pageable pageable) {
        Specification<Player> specification = playerSpecification.getSpecification(params);

        return playerRepository.findAll(specification, pageable).getContent();
    }


    @Override
    public Player getPlayerById(Long id) {
        if(id== 0L)
            throw new IdInValidException();
        return playerRepository.findById(id).orElseThrow(PlayerNotFountException::new);
    }


    @Override
    public Player create(Player playerDto) {
        String name = playerDto.getName();
        String title = playerDto.getTitle();
        Race race = playerDto.getRace();
        Profession profession = playerDto.getProfession();
        Date date = playerDto.getBirthday();
        Integer experience = playerDto.getExperience();
        Boolean banned = playerDto.getBanned();

        if (name == null || title == null || race == null || profession == null || date == null || experience == null ||
                date.getTime() < 0 || experience > 10000000 || experience < 0
                || title.length()>30 || name.length()>30) {
            throw new IdInValidException();
        } else {
        Player player = new Player();
        int level = (int) (Math.sqrt(200 * experience + 2500) - 50) / 100;
        player.setName(name);
        player.setTitle(title);
        player.setRace(race);
        player.setProfession(profession);
        player.setBirthday(date);
        player.setLevel(level);
        player.setBanned(banned);
        player.setExperience(experience);
        player.setUntilNextLevel(50 * (level + 1) * (level + 2) - experience);

        return playerRepository.save(player);
        }

    }

    @Override
    public Integer getCountPlayers(Map<String, String> params) {
        Specification<Player> example = playerSpecification.getSpecification(params);

        return (int) playerRepository.count(example);
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
            int exp = player.getExperience();
            if (playerDto.getExperience() != null) {
                if ( playerDto.getExperience() > 10000000 || playerDto.getExperience() < 0){
                    throw new IdInValidException();
                } else {
                    exp = playerDto.getExperience();

                    player.setExperience(exp);

                }
            int level = (int) (Math.sqrt(200 * exp + 2500) - 50) / 100;

            player.setLevel(level);
            player.setUntilNextLevel(50 * (level + 1) * (level + 2) - exp);
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
