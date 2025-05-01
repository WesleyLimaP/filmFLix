🎬 filmFLix filmFLix é uma API RESTful desenvolvida com Java + Spring Boot para gerenciamento de um catálogo de filmes. Ela suporta autenticação com JWT, controle de acesso baseado em papéis (admin/membro), CRUD de filmes, gêneros e avaliações, além de recuperação de senha por e-mail.

⚙️ Funcionalidades Cadastro e login com geração de JWT.

Recuperação e redefinição de senha por e-mail.

Listagem e detalhamento de filmes por gênero.

Sistema de reviews com permissões por papel.

CRUD de gêneros e filmes (restrito ao administrador).

Paginação nos endpoints de listagem.

Controle de acesso via roles (ROLE_ADM, ROLE_MEMBER).

🛠 Tecnologias Java

Spring Boot

Spring Security

JWT (Auth0)

JPA/Hibernate

Bean Validation

Spring Mail

🔗 Endpoints 🔐 Autenticação Método Rota Descrição POST /auth/login Login e retorno do token JWT POST /auth/sing-up Cadastro de usuário POST /auth/recover-password Envia email com token de recuperação PUT /auth/reset-password Redefine a senha com token recebido

🎬 Filmes Método Rota Descrição GET /movies Lista todos os filmes (com filtro por gênero) GET /movies/{id} Detalha um filme POST /movies Cadastra novo filme (admin) PUT /movies/{id} Atualiza filme (admin) DELETE /movies/{id} Remove filme (admin)

🎭 Gêneros Método Rota Descrição GET /genre Lista todos os gêneros GET /genre/{id} Detalha um gênero POST /genre Cria um novo gênero (admin) PUT /genre/{id} Atualiza um gênero (admin)

📝 Avaliações (Reviews) Método Rota Descrição GET /review/movies/{movieId} Lista avaliações de um filme GET /review/{id} Detalha uma avaliação POST /review Cria nova avaliação (membro ou admin) PUT /review/{id} Atualiza uma avaliação (autor) DELETE /review/{id} Remove avaliação (autor)

❌ Tratamento de Erros A aplicação lança respostas padronizadas com mensagens e status:

404 - Recurso não encontrado

400 - Dados inválidos (validação ou token inválido)

401 - Não autorizado

409 - Entidade duplicada

Classe: controllerExceptionHandler.java lida com essas exceções globalmente.

🔐 Segurança Autenticação baseada em JWT (/auth/login)

Autorização com @PreAuthorize

Papéis:

ROLE_ADM: Acesso total (filmes, gêneros)

ROLE_MEMBER: Pode criar e gerenciar suas reviews

Token é gerado e validado com o JwtService.