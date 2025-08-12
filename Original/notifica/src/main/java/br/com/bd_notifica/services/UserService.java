package br.com.bd_notifica.services;

import java.time.LocalDate;

import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.utils.Criptografia; // Mantenha esta importação para a criptografia da senha

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity criarUser(UserEntity user){
        return userRepository.createUser(user);
    }

    public UserEntity buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Novo método para registrar um usuário com nome, email, senha e papel.
     * A senha será criptografada antes de ser salva.
     * @param name Nome do usuário.
     * @param email Email do usuário (deve ser único).
     * @param password Senha em texto simples.
     * @param role Papel do usuário (ADMIN, STUDENT, AGENT).
     * @return true se o usuário foi registrado com sucesso, false caso contrário (ex: email já em uso).
     */
    public boolean registrar(String name, String email, String password, UserRole role) {
        // Verifica se já existe um usuário com este email para evitar duplicidade
        if (userRepository.findByEmail(email) != null) {
            System.out.println("Erro ao registrar: Email '" + email + "' já está em uso.");
            return false;
        }

        // Cria uma nova instância de UserEntity
        UserEntity newUser = new UserEntity();
        newUser.setName(name);
        newUser.setEmail(email);
        // Criptografa a senha antes de definir
        newUser.setPassword(Criptografia.gerarHash(password));
        newUser.setRole(role);
        newUser.setCreateOnDate(LocalDate.now()); // Define a data de criação como a data atual

        // Tenta criar o usuário no repositório
        UserEntity createdUser = userRepository.createUser(newUser);
        return createdUser != null; // Retorna true se a criação foi bem-sucedida
    }

    public void criarUserPadrão() {
        // Verifica se já existem usuários padrão para evitar duplicação em cada execução
        if (userRepository.findByEmail("ari@mail.com") == null) {
            UserEntity u1 = new UserEntity(null, "ari", "ari@mail.com", Criptografia.gerarHash("123"), UserRole.ADMIN, LocalDate.now().minusDays(5), null);
            userRepository.createUser(u1);
        }
        if (userRepository.findByEmail("carlos@mail.com") == null) {
            UserEntity u2 = new UserEntity(null, "carlos", "carlos@mail.com", Criptografia.gerarHash("123"), UserRole.STUDENT, LocalDate.now().minusDays(4), null);
            userRepository.createUser(u2);
        }
        if (userRepository.findByEmail("diego@mail.com") == null) {
            UserEntity u3 = new UserEntity(null, "diego", "diego@mail.com", Criptografia.gerarHash("123"), UserRole.AGENT, LocalDate.now().minusDays(3), null);
            userRepository.createUser(u3);
        }
    }
}