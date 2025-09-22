package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.bd_notifica.config.RegraDeNegocioException;
import br.com.bd_notifica.config.RecursoNaoEncontradoException;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.repositories.SalaRepository;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SalaRepository salaRepository;

    public UserService(UserRepository userRepository, SalaRepository salaRepository) {
        this.userRepository = userRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public User save(User user) {
        if (user.getCpf() != null && !isValidCPF(user.getCpf())) {
            throw new RegraDeNegocioException("CPF inválido");
        }
        if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
            throw new RegraDeNegocioException("Email inválido");
        }
        if (user.getRole() == null) {
            user.setRole(UserRole.ESTUDANTE);
        }
        

        if (user.getUsuario() != null) {
            User existingUser = userRepository.findByUsuario(user.getUsuario());
            if (existingUser != null) {
                throw new RegraDeNegocioException("Nome de usuário já existe");
            }
        }


        if (user.getSala() != null && user.getSala().getId() != null) {
            Sala salaGerenciada = salaRepository.findById(user.getSala().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Sala não encontrada com o ID: " + user.getSala().getId()));
            user.setSala(salaGerenciada);
        }

        return userRepository.save(user);
    }

    @Transactional
    public User update(Integer id, User user) {
        User existingUser = findById(id);


        if (user.getNome() != null && !user.getNome().isBlank()) {
            existingUser.setNome(user.getNome());
        }
        if (user.getCpf() != null && !user.getCpf().isBlank()) {
            existingUser.setCpf(user.getCpf());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getSenha() != null && !user.getSenha().isBlank()) {
            existingUser.setSenha(user.getSenha());
        }
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        if (user.getSala() != null && user.getSala().getId() != null) {
            Sala salaGerenciada = salaRepository.findById(user.getSala().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Sala não encontrada com o ID: " + user.getSala().getId()));
            existingUser.setSala(salaGerenciada);
        } else {
            existingUser.setSala(null);
        }

        return userRepository.save(existingUser);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") || cpf.matches("\\d{11}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public List<User> findByNome(String nome) {
        return userRepository.findByNomeIgnoreCase(nome);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public void delete(Integer id) {
        User userToDelete = findById(id);
        if (userToDelete.getRole() == UserRole.ADMIN) {
            throw new RegraDeNegocioException("Não é possível excluir usuários administradores");
        }
        userRepository.delete(userToDelete);
    }

    public User authenticate(String usuario, String senha) {
        return userRepository.findByUsuarioAndSenha(usuario, senha);
    }
}
