package com.failedsaptrainees.onlinestore.models;

import com.failedsaptrainees.onlinestore.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "roles")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public RoleModel(String name) {
        this.name = name;
    }

    public RoleModel(Roles role)
    {
        this.name = role.toString();
    }

    public String getRoleName() { return name; };

    public String getName() {
        return "ROLE_" + name;
    }
}
