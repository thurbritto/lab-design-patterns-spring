# Aplicação de Design Pattern Strategy em Java Spring

Este projeto demonstra a aplicação do design pattern Strategy em uma API que lida com descontos em produtos, com foco em descontos da Black Friday e descontos para feira. A implementação é simples e de fácil entendimento, apenas para praticas os conceitos aprendidos na aula.

## Estrutura do Projeto

- `Product`: É a entidade que representa os dados de um produto no sistema.
- `ProductService`: Serviço que lida com a lógica de negócios relacionada aos produtos.
- `ProductRepository`:  Atua como um repositório que fornece métodos para acessar e manipular dados no database.
- `ProductController`: Controlador Spring que lida com as operações CRUD de produtos e aplica as estratégias de desconto.
- `DiscountStrategy`: Interface que define o contrato para as estratégias de desconto.
- `BlackFridayDiscountStrategy`: Implementação da estratégia de desconto da Black Friday que aplica um desconto de 50% no preço do produto.
- `FeiraDiscountStrategy`: Implementação da estratégia de desconto para a Feira que aplica um desconto de 10% no preço do produto.
- `LabDesignPatternsSpringApplication`: Classe para rodar o sistema.

## Aplicação das Estratégias

A aplicação das estratégias de desconto é feita no controlador `ProductController`. Quando um produto é inserido ou atualizado, a aplicação verifica a data atual e aplica a estratégia apropriada com base na data:

- Se a data atual for 24 de novembro, a estratégia da Black Friday é aplicada com um desconto de 50%.
- Se o dia da semana atual for sábado, a estratégia de desconto para a Feira é aplicada com um desconto de 10%.

A estratégia selecionada é usada para calcular o preço com desconto e atualizar o produto com o preço calculado.

## Exemplo de Uso

- Você precisará fazer uma das seguintes requisições HTTP:
- Crie um novo produto através da rota `POST /products`.
- Ou atualize o produto usando a rota `PUT /products/{id}`.
- O desconto será aplicado automaticamente dependendo da data.

- Caso queira explorar mais, também tem as requisições padrões:
- Pesquise todos produtos no banco de dados através da rota `GET /products`
- Ou pesquise um produto específico através da rota `GET /products/{id}`
- Delete um produto específico pela rota `DELETE /products/{id}`
