package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.model.Pet;
import net.dzioba.petclinic.model.PetType;
import net.dzioba.petclinic.services.OwnerService;
import net.dzioba.petclinic.services.PetService;
import net.dzioba.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEW_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    @Autowired
    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @InitBinder
    public void customiseFieldsBinding(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("petTypes")
    public Set<PetType> addPetTypesToModel(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner addOwnerToModel(@PathVariable Long ownerId){
        return ownerService.findById(ownerId);
    }

    @GetMapping("/pets/new")
    public String createPetForm(Owner owner, Model model){
        Pet pet = new Pet();
        owner.addPet(pet);
        model.addAttribute("pet", pet);

        return VIEW_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/pets/new")
    public String createPet(@PathVariable Long ownerId, Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("pet", pet);
            return VIEW_CREATE_OR_UPDATE_PET_FORM;
        }

        pet.setOwner(owner);
        petService.save(pet);

        return "redirect:/owners/" + ownerId;
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPetForm(@PathVariable Long petId, Model model){
        Pet pet = petService.findById(petId);
        if (pet == null) throw new NoSuchElementException("Pet doesn't exist");
        model.addAttribute("pet", pet);

        return VIEW_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String editPet(@PathVariable Long ownerId, Owner owner, @PathVariable Long petId, @Valid Pet pet, BindingResult bindingResult, Model model){
        pet.setOwner(owner);
        pet.setId(petId);

        if (bindingResult.hasErrors()){
            model.addAttribute("pet", pet);
            return VIEW_CREATE_OR_UPDATE_PET_FORM;
        }

        petService.save(pet);
        return "redirect:/owners/" + ownerId;
    }




}
