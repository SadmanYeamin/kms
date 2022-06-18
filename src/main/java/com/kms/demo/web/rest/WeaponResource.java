package com.kms.demo.web.rest;

import com.kms.demo.domain.Weapon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WeaponResource {
    private static final Logger log = LoggerFactory.getLogger(WeaponResource.class);
    @PostMapping("/weapons")
    public Weapon createWeapon(@RequestBody Weapon weapon) {
        log.debug("Weapon received: {}", weapon.toString());
        return weapon;
    }

    @GetMapping("/weapons/{id}")
    public Weapon getWeapon(@PathVariable Long id) {

        return new Weapon(id, "gegergr", "tgg", "kjferh");
    }
}
