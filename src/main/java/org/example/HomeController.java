package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Landing page, so that visiting the root URL shows something instead of a 404. */
@Controller
public class HomeController {

    private final AddressBookRepository addressBookRepository;

    public HomeController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "index";
    }
}
