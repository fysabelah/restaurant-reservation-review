# Sistema de reserva e avaliação de restaurantes - Desafio Tech Challenge - Módulo 3

Projeto desenvolvido como meio de avaliação do módulo. O sistema consiste em uma API REST, onde os restaurantes podem se cadastrar e os usuário podem registrar suas reservas e deixar avaliações.

Dentre os requisitos, temos:
* Restaurantes podem registrar informações a seu respeito, como, localização, tipo de cozinha, horário de funcionamento, capacidade e nome;
* Usuários podem registrar reserva;
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

    # API
    MONGODB_PORT=27017
    PROFILE=prod

Há três application.properties. Um para desenvolvimento (application-dev.properties) e outro para produção (application-prod.properties). Este são controlados a partir da propriedade `spring.profiles.active` no arquivo application.properties.
Set a variável PROFILE, no arquivo .env conforme preferir. Para desenvolvimento, sugiro uso do perfil `dev`. Já para deploy deve ser utilizado o perfil `prod`.

    DICA: A principal diferença esta no host do Mongo. Enquanto no perfil dev está localhost, em prod está mongodb que é o nome do serviço no docker. O banco também é alterado de acordo com o perfil.

### Como rodar

A aplicação faz uso do MongoDB, para não ser necessário configurar, foi criado ao arquivo compose. Aqui temos três possibilidades:

1. Criar todas as imagens através do comando `docker compose up` e depois,
   * Parar o container referente a API
   * Setar `spring.profiles.active` para `dev`.
   * Executar a aplicação a partir da IDE.
2. Criar os container referente apenas o MongoDB e MongoExpress,
   * Comentar a parte referente a criação de imagem/container da API e executar o comando `docker compose up`.
   * Setar `spring.profiles.active` para `dev`.
   * Executar a aplicação a partir da IDE.
3. Executar tudo em container.
   * Neste caso, ao setar `spring.profiles.active` para `dev` será necessário alterar a propriedade `spring.data.mongodb.host` para `mongodb`. Já ao setar para `prod`, não será necessário alterações.
      * No entanto, ao executar neste formato, qualquer alteração de código será necessário a recriação da imagem.

### Link
Considerando que tudo deu certo, será possível acessar os _links_ abaixo.
* [Swagger](http://localhost:8080/doc-app-restaurant.html)
* [Interface Web para MongoDB](http://localhost:27018)