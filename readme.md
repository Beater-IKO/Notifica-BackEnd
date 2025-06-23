O notifica é um sistema de gerenciamento predial onde pode-se abrir chamados ou como chamamos tickets.

As Tecnologias usadas foram Hibernate, JPA, Swing e o PostgreSQL(banco de dados)

Para iniciar o sistema é necessario abrir pela main, lá abrira a tela de login caso tenha um login precisa apenas logar normalmente, caso não tenha precisa ir para o registro

Temos 3 tipos de usuario: Aluno, Admin e o Agente de Campo

Sempre que o sistema reinicia ele apaga os dados novos por causa do metodo create, e popula automaticamente sempre 3 usuarios e 3 tickets. 

O codigo esta organizado em Controllers, Repositories, Entities, Enums, Services. Além de contar com uma pasta Configs e Utils.

No sistema e possivel realizar registro e login, baseado no seu tipo de usuario você e redirecionado a um menu diferente.

Menu do admin: Você pode adicionar tickets, listar todos os tickets, atualizar os tickets, buscar um ticket por id, deletar os tickts e também é possível buscar por intervalos de datas

Menu do aluno: É possivel criar tickets baseado no usuario logado, listar e deletar os tickets se forem do usuario logado.

Menu do agente: É possivel realizar a atualização do status do ticket como lido, em andamento e realizado.

Os metodos que realizam as conexões com o banco por exemplo adicionar ou buscar algum dado dentro do banco está tudo dentro dos repositories, e estão separados para cada entidade. 

Os Enums servem para definir a area, prioridade do ticket e a role do usuario. Cada enum tem algumas opções dentro como grau_leve, grau_mediano e area interna ou externa.
