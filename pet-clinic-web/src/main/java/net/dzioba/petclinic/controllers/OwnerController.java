package net.dzioba.petclinic.controllers;

import net.dzioba.petclinic.model.Owner;
import net.dzioba.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String VIEW_LIST_OWNERS = "owners/index";
    private static final String VIEW_OWNER_DETAILS = "owners/ownerDetails";
    private static final String VIEW_FIND_OWNERS_FORM = "owners/findOwnersForm";
    private static final String VIEW_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";


    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void customiseFieldsBinding(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String listOwners(Owner owner, Model model, BindingResult bindingResult){

        if (isLastNameNotSpecified(owner)) {
            owner = new Owner();
            owner.setLastName("");
        }

        List<Owner> owners = ownerService.findByLastNameLike("%" + owner.getLastName() + "%");
        if (owners.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return VIEW_FIND_OWNERS_FORM;
        }

        model.addAttribute("owners", owners);
        return VIEW_LIST_OWNERS;
    }

    private boolean isLastNameNotSpecified(Owner owner) {
        return owner == null || owner.getLastName() == null || owner.getLastName().isEmpty();
    }

    @GetMapping("/find")
    public String findOwnersForm(Model model){
        model.addAttribute("owner", new Owner());
        return VIEW_FIND_OWNERS_FORM;
    }

    @GetMapping("/{ownerId}")
    public String ownerDetails(@PathVariable Long ownerId, Model model){
        Owner owner = ownerService.findById(ownerId);

        if (owner == null)
            throw new NoSuchElementException("Owner doesn't exist");

        model.addAttribute("owner", owner);
        return VIEW_OWNER_DETAILS;
    }

    @GetMapping("/new")
    public String createOwnerForm(Model model){
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return VIEW_CREATE_OR_UPDATE_OWNER_FORM;
    }
}
