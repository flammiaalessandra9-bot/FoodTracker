package com.example.productapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/email")
public class UserSettingsController {

    @Autowired
    private UserSettingsRepository repo;

    @PostMapping
    public UserSettings saveEmail(@RequestBody UserSettings settings) {
        return repo.save(settings);
    }
}