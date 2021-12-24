package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping( )
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @GetMapping("/rest/players")
    public String  getAllPlayers(Model model) {
        List<Player> list = playerService.getAllPlayer();
        model.addAttribute("players",list);
    return "index";
    }

    @GetMapping("/rest/players/{id}")
    public  String getPlayerById(@PathVariable("id")Long id, Model model){
        Player player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
        return "index";
    }
}
