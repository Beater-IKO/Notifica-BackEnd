# CÓDIGO PARA LIMPEZA

## 1. ARQUIVOS PARA REMOVER
- `TestController.java` - Controller de teste desnecessário em produção

## 2. COMENTÁRIOS PARA REMOVER/TRADUZIR
- MainApplication.java: linhas 5 e 8 (comentários instrucionais)
- Todos os comentários em português devem ser traduzidos para inglês

## 3. ANOTAÇÕES REDUNDANTES PARA REMOVER
- @Repository em todos os repositórios que estendem JpaRepository
- Espaços extras em declarações genéricas

## 4. CÓDIGO COMENTADO PARA REMOVER
- UserService.java: linhas 21-23 (AuditService comentado)

## 5. PROBLEMAS DE SEGURANÇA CRÍTICOS
- UserRepository.findByUsuarioAndSenha() - implementar hash de senha
- Múltiplas vulnerabilidades SSRF nos controllers

## 6. OTIMIZAÇÕES DE PERFORMANCE
- Substituir .isEmpty() por exists() methods
- Corrigir inicialização de LocalDateTime em entidades
- Usar GenerationType.IDENTITY ao invés de AUTO