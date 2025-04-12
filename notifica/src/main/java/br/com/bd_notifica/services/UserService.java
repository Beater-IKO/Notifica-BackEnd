package br.com.bd_notifica.services;

import java.time.LocalDate;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity criarUser(UserEntity user){
        return userRepository.createUser(user);
    }

    public UserEntity buscarPorEmailESenha(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public UserEntity buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    public void criarUserPadrão(){
        UserEntity u1 = new UserEntity(null, "ari", "ari@mail.com", "123", UserRole.ADMIN, LocalDate.now().minusDays(5), null);
        UserEntity u2 = new UserEntity(null, "carlos", "carlos@mail.com", "123", UserRole.STUDENT, LocalDate.now().minusDays(4), null);
        UserEntity u3 = new UserEntity(null, "diego", "diego@mail.com", "123", UserRole.AGENT, LocalDate.now().minusDays(3), null);

        userRepository.createUser(u1);
        userRepository.createUser(u2);
        userRepository.createUser(u3);
    }
}