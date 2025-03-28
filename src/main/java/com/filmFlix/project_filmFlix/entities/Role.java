package com.filmFlix.project_filmFlix.entities;

import com.filmFlix.project_filmFlix.enums.Authority;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role(Long id, Authority authority, User user) {
        this.id = id;
        this.authority = authority;

    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

}
