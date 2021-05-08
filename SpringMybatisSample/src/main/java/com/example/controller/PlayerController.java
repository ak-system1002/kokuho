package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Player;
import com.example.service.PlayerService;

@Controller
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("players", playerService.findAll());
        return "players/index";
    }

    @GetMapping("new")
    public String newPlayer() {
        return "players/new";
    }

    @GetMapping("ranking")
    public String ranking(Model model) {
        model.addAttribute("rankings", playerService.ranking());
        return "players/ranking";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.findOne(id));
        return "players/edit";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.findOne(id));
        model.addAttribute("player.name", playerService.findOne(id).getName());
        model.addAttribute("player.team", playerService.findOne(id).getTeam());
        return "players/show";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute Player player, BindingResult br,Model model) {
    	if (br.hasErrors()) {
    		List<String> errorList = new ArrayList<String>();
    		for(ObjectError error : br.getAllErrors()) {
    			errorList.add(error.getDefaultMessage());
    		}
    		model.addAttribute("validationError",errorList);
            return "players/new";
    	}
        playerService.save(player);
        return "redirect:/players";
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @ModelAttribute Player player) {
        player.setId(id);
        playerService.update(player);
        return "redirect:/players";
    }

    @DeleteMapping("{id}")
    public String destroy(@PathVariable Long id) {
        playerService.delete(id);
        return "redirect:/players";
    }
}
