package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<User> findByNome(String nome){
        return userRepository.findByNomeIgnoreCase(nome);
    }

    public List<User> findByRole(UserRole role){
        return userRepository.findByRole(role);
    }

    public User update(Integer id, User user){
        User update = findById(id);

        if(user.getNome() != null && !user.getNome().isBlank()){
            update.setNome(user.getNome());
        }

        if(user.getCpf() != null && !user.getCpf().isBlank()){
            update.setCpf(user.getCpf());
        }

        if(user.getEmail() != null && !user.getEmail().isBlank()){
            update.setEmail(user.getEmail());
        }

        if (user.getSenha() != null && !user.getSenha().isBlank()){
            update.setSenha(user.getSenha());
        }

        if (user.getRole() != null){
            update.setRole(user.getRole());
        }

        if (user.getSala() != null){
            update.setSala(user.getSala());
        }

        return userRepository.save(update);
    }

    public void delete(Integer id) {
        User delete = findById(id);
        userRepository.delete(delete);
    }


}