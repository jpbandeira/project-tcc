# project-tcc

[Classes diagram](https://drive.google.com/file/d/1bmaxqAN8CRze6pwALM4sysU5xlj3Vjbe/view?usp=sharing)

## Requirements (In Portuguese):

**Trata-se de um sistema para gestão de aluguel de livros para uma biblioteca, com rotinas de aplicação de multas e verificação da situação do material no qual está sendo alugado. As regras desenvolvidas serão:**
- Usuários devidamente cadastrados podem retirar livros com os seguintes critérios
  - Professores podem retirar até 7 obras de titulos diferentes
  - Alunos podem retirar até 5 obras de titulos diferentes
  - Obras raras e de referência não podem ser alugadas
  - Alunos têm o prazo de permanência com cada obra retirada de 15 dias (Somar a data do aluguel mais 15)
  - Professores têm o prazo de até 30 dias de permanência (Somar a data do aluguel mais 30)
- Obras só podem ser transferidas a posse somente se tiver sido dado baixa na devolução  (Cadastrar na tabela de aluguel no momento da ação e deletar da mesma no momento da devolução)
- Regras para aplicação de multas:
  - Ultrapassando o prazo máximo acarreta na cobrança de multa pela quantidade de dias atrasados (Verificar a partir da data de vencimento do aluguel)
  - Atrasos superiores a 30 dias tem cobrança de multa mais bloqueio de realizar empréstimos pelo mesmo período atrasado (Verificar a partir da data de vencimento do aluguel)
