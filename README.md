# Marvel Consulta de Personagens

Este projeto tem como propósito buscar todos os personagens do universo Marvel. 
Deve ser digitado o nome do personagem que deseja consultar, assim que confirmado o nome do personagem, haverá a busca.
Tendo como retorno e exibição o nome, imagem, descrição e lista de quadrinhos que o personagem possui.

O vídeo mostra algumas das experiências que o usuário poderá ter no app.

[![Marvel App Video](https://imgur.com/CeduCvJ.png)](https://youtu.be/TZzp9GtSCEc)

Os testes unitários são de suma importância para a qualidade do código e do produto final, mas também devemos prezar pelos testes manuais, simulando casos de uso do usuário final. 
Abaixo escrevi alguns cenários de caso de uso da aplicação.


| Cenário | Dado que | Quando | Então |
| --- | --- | --- | --- |
| Pesquisa vazia | estou na home do aplicativo | realizo a pesquisa sem nenhum caractere no campo | vejo uma modal de erro |
| Pesquisa com sucesso | estou na home do aplicativo | pesquiso por Spider-man | vejo a tela do herói pesquisado  |
| pesquisa sem internet | estou sem conexão de internet | realizo uma pesquisa  | vejo uma modal sem conexão |
| Encurtamento do título de quadrinhos | realizei uma pesquisa | encontro um quadrinho com título maior que 3 linhas | o título é encurtado com "..." |
| Pesquisa com nome diferente da base de dados | estou na home do aplicativo | pesquiso por Spider Man | vejo uma modal de erro |
| Shimmer | estou no carregamento de uma pesquisa | estou aguardando carregar as informações | vejo shimmer no lugar do herói e imagens de quadrinhos |
| Placeholder | estou aguardando a lista de quadrinhos carregar | encontro uma imagem do quadrinho carregando | vejo um placeholder no lugar das quadrinhos |


Melhorias/Features:
  - Expandir a imagens dos quadrinhos e exibir a descrição de cada um 
  - Lista de sugestões para facilitar na busca de personagens
