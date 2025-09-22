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
    // repositórios para acessar dados do banco
    private final UserRepository userRepository;
    private final SalaRepository salaRepository;

    // construtor que recebe os repositórios necessários
    public UserService(UserRepository userRepository, SalaRepository salaRepository) {
        this.userRepository = userRepository;
        this.salaRepository = salaRepository;
    }

    // salva um novo usuário no banco de dados
    @Transactional
    public User save(User user) {
        // verifica se o CPF é válido
        if (user.getCpf() != null && !isValidCPF(user.getCpf())) {
            throw new RegraDeNegocioException("CPF inválido");
        }
        // verifica se o email é válido
        if (user.getEmail() != null && !isValidEmail(user.getEmail())) {
            throw new RegraDeNegocioException("Email inválido");
        }
        // se não tem role definido, coloca como estudante por padrão
        if (user.getRole() == null) {
            user.setRole(UserRole.ESTUDANTE);
        }

        // verifica se o nome de usuário já existe
        if (user.getUsuario() != null) {
            User existingUser = userRepository.findByUsuario(user.getUsuario());
            if (existingUser != null) {
                throw new RegraDeNegocioException("Nome de usuário já existe");
            }
        }

        // se tem sala associada, busca ela no banco para garantir que existe
        if (user.getSala() != null && user.getSala().getId() != null) {
            Sala salaGerenciada = salaRepository.findById(user.getSala().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Sala não encontrada com o ID: " + user.getSala().getId()));
            user.setSala(salaGerenciada);
        }

        return userRepository.save(user);
    }

    // atualiza os dados de um usuário existente
    @Transactional
    public User update(Integer id, User user) {
        // busca o usuário que vai ser atualizado
        User existingUser = findById(id);

        // atualiza apenas os campos que foram enviados (não nulos e não vazios)
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
        if (user.getTelefone() != null) {
            existingUser.setTelefone(user.getTelefone());
        }
        if (user.getPhotoUrl() != null) {
            existingUser.setPhotoUrl(user.getPhotoUrl());
        }

        // atualiza a sala se foi informada, senão remove a associação
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

    // busca todos os usuários cadastrados
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // busca um usuário pelo ID
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

    // verifica se o CPF está no formato correto (com ou sem pontos e traço)
    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") || cpf.matches("\\d{11}");
    }

    // verifica se o email tem um formato válido
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // busca usuários pelo nome (ignora maiúsculas e minúsculas)
    public List<User> findByNome(String nome) {
        return userRepository.findByNomeIgnoreCase(nome);
    }

    // busca usuários por tipo (estudante, professor, admin)
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    // exclui um usuário (não permite excluir administradores)
    public void delete(Integer id) {
        User userToDelete = findById(id);
        if (userToDelete.getRole() == UserRole.ADMIN) {
            throw new RegraDeNegocioException("Não é possível excluir usuários administradores");
        }
        userRepository.delete(userToDelete);
    }

    // faz login do usuário verificando nome de usuário e senha
    public User authenticate(String usuario, String senha) {
        return userRepository.findByUsuarioAndSenha(usuario, senha);
    }

    // atualiza dados básicos do perfil do usuário
    public User updateProfile(Integer id, String nome, String email, String telefone) {
        User user = findById(id);
        if (nome != null && !nome.isBlank()) {
            user.setNome(nome);
        }
        if (email != null && !email.isBlank() && isValidEmail(email)) {
            user.setEmail(email);
        }
        if (telefone != null) {
            user.setTelefone(telefone);
        }
        return userRepository.save(user);
    }

    // atualiza perfil incluindo foto do usuário
    @Transactional
    public User updateProfile(Integer id, String nome, String email, String telefone, String photoUrl) {
        User user = findById(id);
        if (nome != null && !nome.isBlank()) {
            user.setNome(nome);
        }
        if (email != null && !email.isBlank() && isValidEmail(email)) {
            user.setEmail(email);
        }
        if (telefone != null) {
            user.setTelefone(telefone);
        }
        if (photoUrl != null) {
            user.setPhotoUrl(photoUrl);
        }
        return userRepository.save(user);
    }

    // atualiza apenas a foto do usuário
    @Transactional
    public User updatePhoto(Integer id, String photoUrl) {
        User user = findById(id);
        user.setPhotoUrl(photoUrl);
        return userRepository.save(user);
    }
}
