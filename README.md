## Marvel App

Este é um app que utiliza a [API da Marvel](https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0) para fazer uma listagem de personagens.

Ao abrir o app, temos dois ícones o primeiro da esquerda para direita, uma lupa que ao clickar, habilita a busca de um personagem especifico, e também um coração que representa os personagens adicionados pelo usuário como favoritos.
Logo abaixo, uma lista de personagens, com uma imagem, o nome e botão para adicionar ou remover o personagem aos favoritos

Quando selecionado um personagem, sera apresentado uma tela de detalhes, sendo composta por algumas informações do personagem, como nome, imagem em uma escala maior para melhor visualização, uma descrição do personagem e novamente um botão para adicionar ou remover como favorito. 

### Captura de tela
![](https://user-images.githubusercontent.com/40353202/192664444-f43361e6-7e0a-4c3b-8a0e-6b06e302f505.png)

### Acessibilidade ♿️🔊
O app foi construído com ferramentas nativas e com os devidos cuidados para que seja acessível através da ferramenta de leitura de tela (Talkback).

### Melhorias

Uma melhoria interessante seria implementar uma busca pelo nome do personagem, consultando a API, mesmo ele não estando na lista carregada para o usuário.

### Iniciando o projeto

1. Para obter suas credenciais, acesse o https://developer.marvel.com/;
2. Clene o repositorio;
3. Para que o app funcione adequandamente, deve ser criado o arquivo `apikey.properties` na pasta raiz do projeto, e criar a chave privada `PRIVATE_KEY` e a publica `PUBLIC_KEY`, como na imagem abaixo 


![image](https://user-images.githubusercontent.com/40353202/192672548-0315d210-0d83-4a8f-a650-70034e56e415.png)
