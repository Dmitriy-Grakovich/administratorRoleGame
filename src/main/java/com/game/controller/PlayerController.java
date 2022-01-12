package com.game.controller;

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
@RequestMapping("/")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @GetMapping("/rest/players")
    public void   getAllPlayers(Model model){
        List<Player> list = playerService.getAllPlayer();

        model.addAttribute("mainTable",list);
    }

    @GetMapping("/rest/players/{id}")
    public  void getPlayerById(@PathVariable(value = "id")Long id, Model model){
        Player player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
    }
    @PostMapping("/rest/players/{id}")
    public void updatePlayer(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "race", required = false) Race race, @RequestParam(value = "profession", required = false) Profession profession,
                             @RequestParam(value = "experience", required = false) Integer experience, @RequestParam(value = "birthday", required = false) Long date,
                             @RequestParam(value = "banned", defaultValue = "false") Boolean banned, @PathVariable("id") Long userId){
        playerService.update(userId, name, title, race, profession, experience, date, banned);
    }


   @PostMapping("/rest/players")
    public void createPlayer( @RequestParam(value = "name", required = false) String name, @RequestParam(value = "title" , required = false) String title,
                              @RequestParam(value = "race", required = false) Race race, @RequestParam(value = "profession", required = false) Profession profession,
                              @RequestParam(value = "experience", required = false) Integer experience, @RequestParam(value = "birthday", required = false) Long date,
                              @RequestParam(value = "banned", defaultValue = "false") Boolean banned){

        playerService.create(name, title, race, profession, experience, date, banned);
    }

    @GetMapping("/rest/players/count")
    public void getPlayer(Model model){
        Integer count = playerService.getAllPlayer().size();
        model.addAttribute("count", count);
    }

    @DeleteMapping ("/rest/players/{id}")
    public void deletePlayer(@PathVariable("id")Long id){
        playerService.deletePlayer(id);
    }
}
