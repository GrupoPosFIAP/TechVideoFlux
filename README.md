# API Tech Video Flux
## Bem-vindo à API Tech Video Flux! Aqui você encontrará informações sobre como utilizar os endpoints para criar e manipular dados.

## Sobre
```
- O Tech-video-flux é um serviço back-end de aplicação web de streaming de vídeos utilizando as tecnologias
Spring e Postgres.
- Disponibilizamos endpoints para que seja possível realizar o cadastro de Usuários e o cadastro de
vídeos (com favoritos e estatísticas).
- A solução foi implantada utilizando as tecnologias Spring/Postgres.
```

## Tecnologias adotadas
```
-Java 17: programação server-side
-Spring 3: criação API Restfull e serviços
#-Swagger/OpenAPI: Utilizado para criar a documentação dos endpoints
-Docker: Gerar a imagem da aplicação
-PostgresSQL: Banco utilizado para persistir os dados (está dentro do Google Cloud SQL)
-DBeaver: Front-End para o SGBD
-PostMan: Utilizado nos testes dos endpoints
#-Google Cloud Run: Utilizado para disponibilizar a aplicação de maneira escalável (serverless)
#-Google Cloud SQL: Utilizado para disponibilizar o banco de dados que a aplicação utiliza para persistir os dados.
#-Google Cloud Artifact Registry: Utilizado para armazenar as imagens Docker da aplicação que são geradas.
#-Github Actions: Utilizado para realizar o CD (Continuous Deployment) do projeto Tech-video-flux. 
```

## Como executar a aplicação
### Opção 1: Núvem
```
- Acessar o swagger da aplicação: https://techvideoflux.com.br
- Testar via Postman ou outra plataforma de requisições HTTP
```
### Opção 2: Local
```
- Clonar o repositório https://github.com/GrupoPosFIAP/TechVideoFlux
- Entrar na pasta raiz do projeto e executar o comando "mvn spring-boot:run"
- Testar via Postman ou outra plataforma de requisições HTTP
```

## Endpoints
* [Usuário](#Usuário)
* [Vídeo](#Vídeo)

- PS: Mais informações sobre os endpoints podem ser encontradas através do swagger da aplicação, disponibilizado através da url: https://techvideoflux.com.br/swagger-ui/index.html 

## Usuário

* Rota POST http://localhost:8080/users

Você pode utilizar o seguinte JSON como exemplo para cadastrar um novo Usuário:

```sh
{
    "createdBy": "José da Silva",
    "createdDate": "2024-01-15",
    "updatedBy": "josé da Silva",
    "updatedDate": "",
    "version": "1",
    "delete": "São Paulo",
    "name": "Jose da Silva",
    "email": "josesilva@gmail.com",
    "favorites": {
    }
}
```

* Descrição dos Campos

Campo   | Descrição
--------- | ------
createdBy | Criador do usuário
createdDate | Data de cadastro do usuário
updatedBy | Administrador que atualizou o usuário
updatedDate | Data de atualização do usuário
version | número da versão
delete | 
name | Nome do usuário
email | E-mail do usuário
favorites | Lista com os vídeos favoritos do usuário

####  Se você deseja copiar e editar o JSON de exemplo para criar uma nova Usuário, 
sinta-se à vontade para fazer isso! Basta copiar o JSON acima, modificá-lo conforme 
necessário e enviá-lo como corpo da requisição POST para o endpoint /Usuário.

  * Exemplo de Resposta

    Após enviar a requisição, você receberá a seguinte resposta:  
```sh
{
    "createdBy": "José da Silva",
    "createdDate": "2024-01-15",
    "updatedBy": "josé da Silva",
    "updatedDate": "",
    "version": "1",
    "delete": "São Paulo",
    "name": "Jose da Silva",
    "email": "josesilva@gmail.com",
    "favorites": {
    }
}
```

* Rota GET http://localhost:8080/users/1

Essa rota faz a consulta de Usuário pelo id:

 * Exemplo de Resposta

    Após enviar a requisição, você receberá a seguinte resposta:  
```sh
{
    "createdBy": "José da Silva",
    "createdDate": "2024-01-15",
    "updatedBy": "josé da Silva",
    "updatedDate": "",
    "version": "1",
    "delete": "São Paulo",
    "name": "Jose da Silva",
    "email": "josesilva@gmail.com",
    "favorites": {
    }
}
```

* Rota PUT http://localhost:8080/users/1

Essa rota faz a atualização de Usuário pelo id:

Você pode utilizar o seguinte JSON como exemplo para atualizar um Usuário:

```sh
{
    "createdBy": "José da Silva",
    "createdDate": "2024-01-16",
    "updatedBy": "José da Silva",
    "updatedDate": "",
    "version": "1",
    "delete": "São Paulo",
    "name": "Jose da Silva",
    "email": "josesilva@gmail.com",
    "favorites": {
    }
}
```

 * Exemplo de Resposta

    Após enviar a requisição, você receberá a seguinte resposta:  
```sh
{
    "createdBy": "José da Silva",
    "createdDate": "2024-01-16",
    "updatedBy": "José da Silva",
    "updatedDate": "",
    "version": "1",
    "delete": "São Paulo",
    "name": "Jose da Silva",
    "email": "josesilva@gmail.com",
    "favorites": {
    }
}
```

* Rota DELETE http://localhost:8080/users/1

Essa rota faz a exclusão de Usuário pelo id:


## Vídeo

* Rota POST : http://localhost:8080/videos
  
  Você pode utilizar o seguinte JSON como exemplo para cadastrar um novo Vídeo.

```sh
{
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-15", 
    "categorias": "Back end", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Descrição dos Campos

Campo   | Descrição
--------- | ------
titulo | Título do vídeo
marca | Descrição do vídeo
modelo | URL do vídeo
dataPublicacao | Data da publicação do vídeo
categorias | Categoria do vídeo
contadorFavoritos | Informa a quantidade de favoritos do vídeo
contadorVizualizações | Informa a quantidade de vizualizações do vídeo

* Exemplo de Resposta

  Após enviar a requisição, você receberá a seguinte resposta. 
```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-15", 
    "categorias": "Back end", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Rota GET : http://localhost:8080/videos/1

  Retorna Vídeo informado no id.

```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-15", 
    "categorias": "Back end", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Rota GET : http://localhost:8080/videos 

  Retorna listagem dos vídeos.

* Rota PUT : http://localhost:8080/videos/1
  
  Atualiza dados do Vídeo informado via id.
  Os novos dados devem ser infomado via arquivo JSON.

```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-16", 
    "categorias": "Sistemas Operacionais", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Exemplo de Resposta

  Após enviar a requisição, você receberá a seguinte resposta. 
```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-16", 
    "categorias": "Sistemas Operacionais", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Rota DELETE : http://localhost:8080/videos/1

  Exclui Vídeo informado no id.


* Rota PUT : http://localhost:8080/videos/favoritar/1/1
  
  Favorita o vídeo para o usuário informado via id.

* Exemplo de Resposta
  
```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-16", 
    "categorias": "Sistemas Operacionais", 
    "contadorFavoritos": 1,
    "contadorVisualizacoes": 0
}
```

* Rota PUT : http://localhost:8080/videos/desfavoritar/1/1
  
  Retira o vídeo da condição de favorito para o usuário informado via id.
  
* Exemplo de Resposta
  Após enviar a requisição, você receberá a seguinte resposta. 
```sh
{
    "id": 1
    "titulo": "Apresentação do TechChallenge", 
    "descricao": "Apresentação suscinta do projeto TechVideoFlux", 
    "url": "https://www.techvideoflux.com.br"
    "dataPublicacao": "2024-01-16", 
    "categorias": "Sistemas Operacionais", 
    "contadorFavoritos": 0,
    "contadorVisualizacoes": 0
}
```

* Rota PUT : http://localhost:8080/videos/estatisticas
  
  Informa a quantidade total de vídeos, a quantidade de vídeos favoritados e a média de visualizações .
  
* Exemplo de Resposta
  Após enviar a requisição, você receberá a seguinte resposta. 
```sh
{
    "quantidadeVideos": 2401,
    "quantidadeVideosFavoritos": 49, 
    "mediaVisualizacoes": 7 
}
```


## Escalabilidade horizontal:
A escalabilidade horizontal ocorre através das ferramentas serverless da Google Cloud. A solução adotada foi a
disponibilização do serviço dockerizado através da ferramenta Google Cloud Run, que foi pensada inicialmente para 
trabalhar simultâneamente com até 10 instâncias da aplicação, sendo esse um valor totalmente modificável para o número 
mínimo e máximo de instâncias desejadas. Vale ressaltar que adicionamos toda a parte de Continuous Deployment dentro
do GitHub Actions, permitindo ainda que a quantidade de instâncias sejam configuradas pelo próprio script da pipeline.
Como o Google Cloud Run é uma ferramenta serverless, o gerenciamento das instâncias fica por conta da infraestrutura da Google Cloud, 
permitindo com que seja possível focar apenas no código, e "esquecer" de questões de infraestrutura do projeto.

Abaixo podemos ver um pouco do poder da escalabilidade horizontal:

Aplicação sendo executada em 1 instância em momentos que chegam poucas requisições:
<img src="/src/main/resources/static/1instancia.png">

Aplicação sendo executada em 10 instâncias em momentos que chegam muitas requisições:
<img src="/src/main/resources/static/10instancias.png">

Teste de stress realizado, chegando a 1300 requisições por segundo, todas com sucesso:
<img src="/src/main/resources/static/1300requests.png">

## Dificuldades
```
* O grupo não alinhou a finalização dos módulos da pós-graduação para liberar tempo adequado para o
desenvolvimento do projeto.
* Horários de reunião do grupo divergiu entre alguns participantes.
#* Estruturar e disponibilizar o projeto em uma solução serverless e escalável
```

## Aprendizados
```
* A utilização de IDE (Integrated Developer Envoirement – Ambiente de Desenvolvimento Integrado) idênticas
por parte dos membros da equipe, evita problemas de incompatibilidade.
* Realizar a criação de uma pipeline de CD (Continuous Deployment) através do GitHub Actions
```
