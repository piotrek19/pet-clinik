package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Vet;
import net.dzioba.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {

    private final VetService vetService;

    @Autowired
    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets", "/vets/index", "/vets/index.html"})
    public String listVets(Model model){
        Set<Vet> vets = vetService.findAll();
        model.addAttribute("vets", vets);
        return "vets/index";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVets(){

        return vetService.findAll();
    }
}
