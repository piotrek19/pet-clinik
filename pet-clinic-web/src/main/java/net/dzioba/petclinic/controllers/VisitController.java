package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.Visit;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private static final String VIEW_CREATE_OR_UPDATE_VISIT_FORM = "pets/createVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    @Autowired
    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void customiseFieldsBinding(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("pet")
    public Pet addPetToModel(@PathVariable Long petId){
        return petService.findById(petId);
    }

    @GetMapping("/visits/new")
    public String createVisitForm(Pet pet, Model model){
        Visit visit = new Visit();
        pet.addVisit(visit);
        model.addAttribute("visit", visit);

        return VIEW_CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping("/visits/new")
    public String createVisit(@PathVariable Long ownerId, @Valid Visit visit, Pet pet, Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("visit", visit);
            return VIEW_CREATE_OR_UPDATE_VISIT_FORM;
        }

        pet.addVisit(visit);
        visitService.save(visit);

        return "redirect:/owners/" + ownerId;
    }





}

