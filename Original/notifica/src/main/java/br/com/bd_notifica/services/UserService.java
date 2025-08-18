package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;

import java.util.List;

// Lógica de negócio para usuários
@Service
public class UserService {
    // Repositório para persistência e consultas de usuários
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user){
        // Validação de formato do CPF
        if(user.getCpf() != null && !isValidCPF(user.getCpf())){
            throw new RuntimeException("CPF inválido");
        }
        
        // Validação de formato do email
        if(user.getEmail() != null && !isValidEmail(user.getEmail())){
            throw new RuntimeException("Email inválido");
        }
        
        // Atribuição de perfil padrão
        if(user.getRole() == null){
            user.setRole(UserRole.ESTUDANTE);
        }
        
        return userRepository.save(user);
    }
    
    // Valida CPF (com ou sem pontos/traço)
    private boolean isValidCPF(String cpf){
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") || cpf.matches("\\d{11}");
    }
    
    // Valida formato do email
    private boolean isValidEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Lista todos os usuários
    public List<User> findAll(){
        return userRepository.findAll();
    }

    // Busca por ID
    public User findById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Busca por nome (case insensitive)
    public List<User> findByNome(String nome){
        return userRepository.findByNomeIgnoreCase(nome);
    }

    // Filtra por tipo de usuário
    public List<User> findByRole(UserRole role){
        return userRepository.findByRole(role);
    }

    public User update(Integer id, User user){
        User existingUser = findById(id);

        if(user.getNome() != null && !user.getNome().isBlank()){
            existingUser.setNome(user.getNome());
        }

        if(user.getCpf() != null && !user.getCpf().isBlank()){
            existingUser.setCpf(user.getCpf());
        }

        if(user.getEmail() != null && !user.getEmail().isBlank()){
            existingUser.setEmail(user.getEmail());
        }

        if (user.getSenha() != null && !user.getSenha().isBlank()){
            existingUser.setSenha(user.getSenha());
        }

        if (user.getRole() != null){
            existingUser.setRole(user.getRole());
        }

        if (user.getSala() != null){
            existingUser.setSala(user.getSala());
        }

        return userRepository.save(existingUser);
    }

    public void delete(Integer id) {
        User userToDelete = findById(id);
        
        // Não permite excluir usuários administradores
        if(userToDelete.getRole() == UserRole.ADMIN){
            throw new RuntimeException("Não é possível excluir usuários administradores");
        }
        
        userRepository.delete(userToDelete);
    }

}