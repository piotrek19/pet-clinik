package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String VIEW_LIST_OWNERS = "owners/index";
    private static final String VIEW_OWNER_DETAILS = "owners/ownerDetails";
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listOwners(Model model){
        Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return VIEW_LIST_OWNERS;
    }

    @GetMapping("/{ownerId}")
    public String ownerDetails(@PathVariable Long ownerId, Model model){
        Owner owner = ownerService.findById(ownerId);

        if (owner == null)
            throw new NoSuchElementException("Owner doesn't exist");

        model.addAttribute("owner", owner);
        return VIEW_OWNER_DETAILS;
    }
}
