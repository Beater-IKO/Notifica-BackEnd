package br.com.bd_notifica.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.bd_notifica.enums.UserRole;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "createOnDate", nullable = false)
    private LocalDate createOnDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
    
    public UserEntity() {
    }

    public UserEntity(Long id, String name, String email, String password, UserRole role, LocalDate createOnDate,
            List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createOnDate = createOnDate;
        this.tickets = tickets;
    }

    public UserEntity(UserEntity outro) {
        this.id = outro.id;
        this.name = outro.name;
        this.email = outro.email;
        this.password = outro.password;
        this.role = outro.role;
        this.createOnDate = outro.createOnDate;
        this.tickets = new ArrayList<>(outro.tickets);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDate getCreateOnDate() {
        return createOnDate;
    }

    public void setCreateOnDate(LocalDate createOnDate) {
        this.createOnDate = createOnDate;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", nome='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", dataCriacao=" + createOnDate +
                // NÃO imprima os tickets aqui!
                '}';
    }

	

    
   
}