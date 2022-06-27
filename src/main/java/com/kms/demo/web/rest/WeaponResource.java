package com.kms.demo.web.rest;

import com.kms.demo.domain.Weapon;
import com.kms.demo.service.WeaponService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WeaponResource {

    private static final Logger log = LoggerFactory.getLogger(WeaponResource.class);

    private final WeaponService weaponService;

    public WeaponResource(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    //    @PostMapping("/weapons")
    @RequestMapping(value = "/weapons", headers = "Accept=application/json", method = RequestMethod.POST)
    public Weapon createWeapon(@RequestBody @NotNull Weapon weapon) {
        weapon = weaponService.save(weapon);
        return weapon;
    }

    @GetMapping("/weapons/{id}")
    public Weapon getWeapon(@PathVariable Long id) {
        return new Weapon(id, "gegergr", "tgg", "kjferh");
    }
}
