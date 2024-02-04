# Sistema de reserva e avaliação de restaurantes - Desafio Tech Challenge - Módulo 3 

Projeto desenvolvido como meio de avaliação do módulo. O sistema consiste em uma API REST, onde os restaurantes podem se cadastrar e os usuário podem registrar suas reservas e deixar avaliações.

Dentre os requisitos, temos:
* Restaurantes podem registrar informações a seu respeito, como, localização, tipo de cozinha, horário de funcionamento, capacidade e nome;
* Usuário podem registrar reserva;
* Restaurantes podem alterar o estado da reserva;
* Usuários podem deixar avaliações sobre a experiência no restaurante.

## Desenvolvedores

 - [Aydan Amorim](https://github.com/AydanAmorim)
 - [Danilo Faccio](https://github.com/DFaccio)
 - [Erick Ribeiro](https://github.com/erickmatheusribeiro)
 - [Isabela França](https://github.com/fysabelah)

## Configurações

Na raiz do projeto, crie o arquivo .env com as chaves abaixo. Os valores adicionados podem ser alterados como entender.

    # MongoDB
    MONGO_INITDB_ROOT_USERNAME=usuario_mongo
    MONGO_INITDB_ROOT_PASSWORD=senha_mongo

    # MongoDB Express
    ME_CONFIG_BASICAUTH_USERNAME=usuario_interface_mongo
    ME_CONFIG_BASICAUTH_PASSWORD=senha_interface_mongo

Note que no application.properties há chaves com o mesmo valor. Para aquele arquivo, substitua as chaves pelo valor adicionado no .env, como abaixo.

    spring.data.mongodb.uri=mongodb://usuario_mongo:senha_mongo@localhost:27017/restaurant-app

### Como rodar

A aplicação faz uso do MongoDB, para não ser necessário configurar, foi criado ao arquivo compose. Para executá-lo, rode o comando abaixo no diretório do projeto.

    docker compose up

A interface pode ser acessada através de [http://localhost:27018](http://localhost:27018).