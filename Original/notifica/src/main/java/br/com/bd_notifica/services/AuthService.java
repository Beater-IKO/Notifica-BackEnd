package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.config.RegraDeNegocioException;
import br.com.bd_notifica.config.RecursoNaoEncontradoException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    // repositório para acessar dados dos usuários
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // altera a senha do usuário
    @Transactional
    public void changePassword(Integer userId, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        // verifica se a senha atual está correta
        if (!user.getSenha().equals(currentPassword)) {
            throw new RegraDeNegocioException("Senha atual incorreta");
        }

        // verifica se as senhas coincidem
        if (!newPassword.equals(confirmPassword)) {
            throw new RegraDeNegocioException("Nova senha e confirmação não coincidem");
        }

        // verifica o tamanho mínimo da senha
        if (newPassword.length() < 6) {
            throw new RegraDeNegocioException("Nova senha deve ter pelo menos 6 caracteres");
        }

        user.setSenha(newPassword);
        userRepository.save(user);
    }

    // reseta a senha do usuário e gera uma temporária
    @Transactional
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com este email");
        }

        // gera uma senha temporária
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setSenha(tempPassword);
        userRepository.save(user);
    }

    // valida se a sessão é válida
    public boolean validateSession(String token) {
        return token != null && !token.isEmpty();
    }
}