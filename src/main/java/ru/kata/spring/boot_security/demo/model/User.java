package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotEmpty(message = "поле должно быть запонено")
    @Size(min = 2, max = 100, message = "Имя должно быть больше 2 символов длиной")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "поле не должно быть пустым")
    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role")
    private List<Role> role;

    public User() {
    }


    public User(int userId, String name, String surname) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }


    public void addRole(Role roles) {
        role.add(roles);
    }

    public Collection<Role> getRoles() {
        return role;
    }

    public void setRole(List<Role> roles) {
        this.role = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}