package com.kms.demo.service;

import com.kms.demo.domain.Weapon;
import org.springframework.stereotype.Service;

@Service
public class WeaponService {

    private final WeaponRepository weaponRepository;

    public WeaponService(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    public Weapon save(Weapon weapon) {
        return weaponRepository.save(weapon);
    }
}
