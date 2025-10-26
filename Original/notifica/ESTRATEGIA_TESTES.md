# Estratégia de Testes - Sistema Notifica

## Resumo da Estratégia

Este documento descreve a estratégia de testes implementada no sistema Notifica, focando em **testes de unidade** e **testes de integração** com cobertura mínima de 90%.

## Tipos de Testes Implementados

### 1. Testes de Unidade
**Definição**: Testes que validam uma única unidade de código (método/classe) de forma isolada, sem dependências externas.

**Exemplos implementados**:
- `TokenServiceTest.testGerarTokenSuccess()` - Valida geração de token JWT
- `TokenServiceTest.testTokenFormat()` - Valida formato do token gerado
- `CategoriaServiceTest.testSaveNullCategoria()` - Valida tratamento de entrada nula
- `ErrorResponseTest` - Valida criação e manipulação de objetos de resposta de erro
- `GenericExceptionsTest` - Valida criação de exceções customizadas

**Características**:
- Não fazem chamadas para outras classes
- Testam lógica específica e isolada
- Executam rapidamente
- Identificados com `@DisplayName("TESTE DE UNIDADE – ...")`

### 2. Testes de Integração
**Definição**: Testes que validam a interação entre múltiplas classes, com repositories mockados para isolamento.

**Exemplos implementados**:
- `UserServiceTest.testSaveValidUser()` - Integração entre UserService, UserRepository e PasswordEncoder
- `CategoriaServiceTest.testSaveValidCategoria()` - Integração entre CategoriaService e CategoriaRepository
- `UserServiceTest.testDeleteAdminUser()` - Integração com validação de regras de negócio

**Características**:
- Testam fluxos completos de funcionalidades
- Repositories sempre mockados (usando Mockito)
- Validam regras de negócio complexas
- Identificados com `@DisplayName("TESTE DE INTEGRAÇÃO – ...")`

## Ferramentas Utilizadas

### JUnit 5
- Framework principal para execução de testes
- Anotações `@Test`, `@DisplayName`, `@BeforeEach`
- Assertions para validações

### Mockito
- Mock de dependências (repositories, services)
- Anotações `@Mock`, `@InjectMocks`
- Verificação de chamadas com `verify()`

### JaCoCo
- Análise de cobertura de código
- Relatórios em HTML, XML e CSV
- Meta: mínimo 90% de cobertura

## Cobertura de Cenários

### Casos de Sucesso
- Operações CRUD básicas
- Validações de entrada
- Transformações de dados

### Casos de Erro e Exceções
- Dados inválidos (CPF, email)
- Recursos não encontrados
- Violações de regras de negócio
- Tentativas de operações não autorizadas

### Casos Limites
- Valores nulos
- Strings vazias
- IDs inexistentes
- Duplicação de dados únicos

## Estrutura dos Testes

```java
@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
    
    @Test
    @DisplayName("TESTE DE [TIPO] – Cenário que [DESCRIÇÃO]")
    void testMethod() {
        // Arrange, Act, Assert
    }
}
```

## Métricas Atuais

- **Total de testes**: 147
- **Testes passando**: 147 (100%)
- **Cobertura geral**: 65% (melhorada de 62%)
- **Cobertura por pacote**:
  - **Config**: 100% ✅ (melhorado de 67%)
  - **Services**: 98% ✅
  - **Enums**: 100% ✅
  - **Entities**: 15%
  - **Controllers**: 0%
- **Tempo de execução**: ~12 segundos

## Comandos Úteis

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Ver relatório de cobertura
# Abrir: target/site/jacoco/index.html
```

## Prioridades e Escolhas

### Alta Prioridade
1. **Services**: Lógica de negócio crítica
2. **Validações**: CPF, email, regras de negócio
3. **Exceções**: Tratamento de erros

### Média Prioridade
1. **Controllers**: Endpoints REST
2. **Configurações**: CORS, segurança
3. **DTOs**: Transformações de dados

### Baixa Prioridade
1. **Entities**: Classes de dados simples
2. **Enums**: Valores constantes
3. **Configurações básicas**: Properties

## Benefícios Alcançados

1. **Confiabilidade**: Detecção precoce de bugs
2. **Manutenibilidade**: Refatoração segura
3. **Documentação**: Testes como especificação
4. **Qualidade**: Cobertura abrangente de cenários
5. **CI/CD**: Integração contínua confiável

## Próximos Passos

1. Manter cobertura acima de 90%
2. Adicionar testes de performance
3. Implementar testes de contrato (API)
4. Automatizar execução em pipeline CI/CD