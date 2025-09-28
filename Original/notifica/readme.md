# Documentação Docker Compose

O **Docker Compose** é uma ferramenta para definir e gerenciar aplicativos Docker multi-contêiner. Utiliza o arquivo `docker-compose.yml` para configurar os serviços, redes e volumes.

## Comandos Principais

### 1. `docker-compose build`
Constrói as imagens dos serviços definidos no arquivo `docker-compose.yml`.

```sh
docker-compose build
```
- Utilizado após alterações no Dockerfile ou dependências do projeto.

### 2. `docker-compose up`
Inicia os contêineres definidos no arquivo `docker-compose.yml`. Pode criar e iniciar os serviços em segundo plano.

```sh
docker-compose up
docker-compose up -d  # Executa em modo "detached" (em segundo plano)
docker-compose up --build  # Reconstrói as imagens antes de iniciar
docker-compose up --build -d  # Reconstrói e executa em segundo plano
```

### 3. `docker-compose down`
Encerra e remove todos os contêineres, redes e volumes criados pelo `up`.

```sh
docker-compose down
```
- Remove recursos criados, mas mantém as imagens.

## Outros Comandos Úteis

- `docker-compose ps` — Lista os contêineres em execução.
- `docker-compose logs` — Exibe os logs dos serviços.
- `docker-compose logs -f` — Exibe os logs em tempo real.
- `docker-compose logs [serviço]` — Exibe logs de um serviço específico.
- `docker-compose stop` — Para os contêineres sem removê-los.
- `docker-compose start` — Inicia contêineres já criados.
- `docker-compose restart` — Reinicia os serviços.
- `docker-compose exec [serviço] [comando]` — Executa comando dentro de um contêiner.
- `docker-compose pull` — Baixa as imagens mais recentes.
- `docker-compose config` — Valida e exibe a configuração do compose.
- `docker-compose down -v` — Remove contêineres, redes e volumes.
- `docker-compose down --rmi all` — Remove contêineres, redes e imagens.

## Configuração para Desenvolvimento

### Alteração no Frontend
Quando outros desenvolvedores utilizarem Docker, será necessário alterar o caminho de conexão do frontend para se conectar aos serviços containerizados.

- Altere as URLs de API no frontend de `localhost` para o nome do serviço definido no `docker-compose.yml`
- Exemplo: `http://localhost:3000/api` → `http://backend:3000/api`

## Exemplo de Arquivo `docker-compose.yml`

```yaml
version: '3'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    volumes:
      - .:/app
```

## Referências

- [Documentação Oficial Docker Compose](https://docs.docker.com/compose/)
