# Skyline Project
Projeto realizado para processo seletivo com a Ci&T.
O seguinte projeto representa uma API ficticia para a compra de BitCoins.

### Endpoints:
* **POST - http://localhost:8080/cadastro?nome=eduardo&cpf=11122233345**
descrição: Permite cadastro de novo usuário com nome e cpf. A API retorna true caso o usuário seja criado com sucesso e false caso ocorra algum erro.
retorno: {"wasCreated":"boolean"}
exemplo:
```{"wasCreated":"true"}```

* **PUT - http://localhost:8080/creditar?cpf=11122233344&saldo=80000.00**
descrição: Adiciona saldo em reais (R$) para a conta do cpf informado. A API retorna true caso o procedimento seja executado com sucesso e false caso ocorra algum erro.
retorno: {"wasCredited":"boolean"}
exemplo:
```
{"wasCredited":"true"}
```

* **PUT - http://localhost:8080/comprabtc?cpf=11122233344&valor=1.0**
descrição: Caso o usuário possua saldo, permite a compra de BitCoins ao informar o cpf do usuário e valor desejado para compra. A API retorna true caso o procedimento seja executado com sucesso e false caso ocorra algum erro.
retorno: {"wasBought":"boolean"}
exemplo:
```
{"wasBought":"true"}
```

* **GET - http://localhost:8080/getSaldoReal?cpf=11122233344**
descrição: Retorna o saldo em reais (R$) da conta do cpf informado.
retorno: {"saldoReal":"double"}
exemplo:
```
{"saldoReal":"11338.984381999995"}
```

* **GET - http://localhost:8080/getSaldoBtc?cpf=11122233344**
descrição: Retorna o saldo em bitcoins (BTC) da conta do cpf informado.
retorno: {"saldoBtc":"double"}
exemplo:
```
{"saldoBtc":"1.0"}
```

* **GET - http://localhost:8080/valorTotalInvestido?cpf=11122233344**
descrição: Retorna o valor total investido em reais (R$) até o momento pela conta do cpf informado.
retorno: {"valorTotalInvestido":"double"}
exemplo:
```
{"valorTotalInvestido":"0.0"}
```

* **GET - http://localhost:8080/lucroObtido?cpf=11122233344**
descrição: Retorna o lucro obtido em reais (R$) até o momento para a conta do cpf informado. Utiliza o valor investido até o momento e o preço do bitcoin em reais do momento atual para realizar o calculo.
retorno: {"lucroObtido":"double"}
exemplo:

```
{"lucroObtido":"205239.160395"}
```

* **GET - http://localhost:8080/historico?cpf=11122233344**
descrição: 
retorno: Retorna um histórico das últimas 5 (cinco) transações realizadas pela conta do cpf informado. Caso não haja transação suficiente, o valor null será retornado no lugar da transação que não exite.
exemplo: 

```
{"historico":"[
	{"id":5,"idCliente":14,"valorReal":68840.804032,"valorBtc":1.0},
	{"id":4,"idCliente":14,"valorReal":68840.804032,"valorBtc":1.0},
	{"id":3,"idCliente":14,"valorReal":68840.804032,"valorBtc":1.0},
	null,
	null]
"}
```
