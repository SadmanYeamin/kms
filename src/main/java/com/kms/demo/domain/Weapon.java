package com.kms.demo.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "weapon_type")
    private String weaponType;

    @Column(name = "weapon_name")
    private String weaponName;

    @Column(name = "butt_no")
    private String buttNo;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "is_assigned")
    private boolean isAssigned = Boolean.FALSE;

    public Weapon() {}

    public Weapon(Long id, String weaponType, String weaponName, String buttNo, Long assignedTo, boolean isAssigned) {
        this.id = id;
        this.weaponType = weaponType;
        this.weaponName = weaponName;
        this.buttNo = buttNo;
        this.assignedTo = assignedTo;
        this.isAssigned = isAssigned;
    }

    public Weapon(Long id, String weaponType, String weaponName, String buttNo) {
        this.id = id;
        this.weaponType = weaponType;
        this.weaponName = weaponName;
        this.buttNo = buttNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getButtNo() {
        return buttNo;
    }

    public void setButtNo(String buttNo) {
        this.buttNo = buttNo;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    @Override
    public String toString() {
        return (
            "Weapon{" +
            "id=" +
            id +
            ", weaponType='" +
            weaponType +
            '\'' +
            ", weaponName='" +
            weaponName +
            '\'' +
            ", buttNo='" +
            buttNo +
            '\'' +
            ", assignedTo=" +
            assignedTo +
            ", isAssigned=" +
            isAssigned +
            '}'
        );
    }
}
