package com.game.controller;

import com.game.dto.PlayerDto;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/rest/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @GetMapping()
    public void   getAllPlayers(Model model, @PathVariable(value = "name", required = false) String name, @PathVariable(value = "title", required = false) String title,
                                @PathVariable("race") Race race, @PathVariable("profession") Profession profession,
                                @PathVariable("after") Long after, @PathVariable("before") Long before, @PathVariable("banned") Boolean banned,
                                @PathVariable("minExperience") Integer minExperience, @PathVariable("maxExperience") Integer maxExperience,
                                @PathVariable("minLevel") Integer minLevel, @PathVariable("maxLevel") Integer maxLevel,
                                @PathVariable("order") PlayerOrder order, @PathVariable("pageNumber") Integer pageNumber,
                                @PathVariable("pageSize") Integer pageSize){
        List<Player> list = playerService.getAllPlayer();
        for (Player player:list){
            System.out.println(player);
        }
        model.addAttribute("mainTable",list);
    }

    @GetMapping("/{id}")
    public  Player getPlayerById(@PathVariable(value = "id")Long id){
        Player player = playerService.getPlayerById(id);
        return player;
    }
    @PostMapping("/{id}")
    public Player updatePlayer(@PathVariable(value = "id")Long id, @RequestBody Player playerDto){
        return playerService.update(playerDto, id);
    }


   @PostMapping()
    public Player createPlayer(@RequestBody Player playerDto){
        Player player = playerService.create(playerDto.getName(), playerDto.getTitle(), playerDto.getRace(),
                playerDto.getProfession(), playerDto.getExperience(), playerDto.getBirthday(), playerDto.getBanned());
       System.out.println(playerDto);
       System.out.println(player);
        return player;
    }

    @GetMapping("/count")
    public void getPlayer(Model model, @PathVariable("name") String name, @PathVariable("title") String title,
                          @PathVariable("race") Race race, @PathVariable("profession") Profession profession,
                          @PathVariable("after") Long after, @PathVariable("before") Long before, @PathVariable("banned") Boolean banned,
                          @PathVariable("minExperience") Integer minExperience, @PathVariable("maxExperience") Integer maxExperience,
                          @PathVariable("minLevel") Integer minLevel, @PathVariable("maxLevel") Integer maxLevel){
        Integer count = playerService.getAllPlayer().size();
        model.addAttribute("count", count);
    }

    @DeleteMapping ("/{id}")
    public void deletePlayer(@PathVariable("id")Long id){
        playerService.deletePlayer(id);
    }
}
