package com.game.controller;

import com.game.entity.Player;

import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Player>  getAllPlayers(@RequestParam Map<String, String> params,
                                       @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                       @RequestParam(required = false, defaultValue = "3") Integer pageSize){
        Pageable pages = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        List<Player> list = playerService.getAllPlayer(params, pages);
        return list.stream().collect(Collectors.toList());
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
        Player player = playerService.create(playerDto);
        return player;
    }

  /*  @GetMapping("/count")
    public Integer getPlayer(@RequestParam Map<String, String> params){
        Integer count = playerService.getCountPlayers(params);
        return count;
    }*/

    @DeleteMapping ("/{id}")
    public void deletePlayer(@PathVariable("id")Long id){
        playerService.deletePlayer(id);
    }
}
