# Backend
Projeto em Java com intuito de praticar alguns conhecimentos e conceitos de projeto backend.

Neste projeto será utilizado Spring Boot, Maven, Java 11, Elastisearch e Kafka.

Ao clonar o projeto, se faz necessário baixar as dependências do mesmo e caso a própria IDE não faça isso e até mesmo para garantir que tudo esteja correto, o comando abaixo pode ajudar.

```
mvn clean package -Dskip.surefire.tests
```

No caso do Elasticsearch, o mesmo, para ser utilizado localmente, foi configurado no [Docker](https://docs.docker.com/get-docker/) da seguinte forma.

### Exemplo de body válido

```
{
  "name": "Nome Exemplo",
  "age": 18,
  "cpf" : 27348324029,
  "phones": [
    {
      "ddd": "011",
      "number": "111111111"
    },
    {
      "ddd": "011",
      "number": "11111111"
    }
  ],
  "addresses": [
      {
        "cep": "13300000",
        "street": "Rua Exemplo",
        "number": 1000,
        "district": "Bairro Exemplo",
        "city": "Cidade Exemplo",
        "state": "Estado Exemplo",
        "complement": "Complemento Exemplo"
      }
  ]
}
```

## Elasticsearch

### Configurando o Elasticsearch no docker

```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -it docker.elastic.co/elasticsearch/elasticsearch:8.1.0
```

### Configurando o Kibana no docker

```
docker run -d --name kibana --link elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:8.1.0
```

A estrutura do Document que será utilizado por essa api, pode ser vista a baixo, já no formato do comando que o Elasticsearch pede para que a estrutura seja criada. Esta estrutura pode ser inputada diretamente pelo Kibana.

```
PUT /records
{
	"mappings": {
		"properties": {
			"name" : {
				"type" : "text"
			},
			"age" : {
			  "type": "integer"
			},
			"cpf" : {
			  "type": "long"
			},
		  "phones" : {
		    "properties": {
  		    "ddd": { "type" : "text" },
		      "number" : { "type" : "text" }
		    }
		  },
		  "addresses" :   {
		      "properties" : {
  		      "cep" : { "type" : "text" },
		        "street" : { "type" : "text" },
		        "number" :  { "type" : "integer" },
		        "district" : { "type" : "text" },
				"city" : { "type" : "text" },
				"state" : { "type" : "text" },
		        "complement" : { "type" : "text" }
		      }
		    }
		 }
	}
}
```

Abaixo também temos um exemplo de inserção manual de um registro seguindo o padrão acima.

```
PUT /records/_doc/1
{
  "name": "Nome Exemplo",
  "age": 18,
  "cpf" : 11111111111,
  "phones": [
    {
      "ddd": "011",
      "number": "111111111"
    },
    {
      "ddd": "011",
      "number": "11111111"
    }
  ],
  "addresses.cep": "13300000",
  "addresses.street": "Rua Exemplo",
  "addresses.number": 1000,
  "addresses.district": "Bairro Exemplo",
  "addresses.city": "Cidade Exemplo",
  "addresses.state": "Estado Exemplo",
  "addresses.complement": "Complemento Exemplo"
}
```

Exemplo de uma consulta.

```
GET /records/_search
{
  "query" : {
      "match" :  {
        "nome" : "Nome Exemplo"
      }
    }
}
```

## Testes

Para os testes, este projeto irá utilizar os plugins **Surefire** e **FailSafe** para separarmos a execução dos testes unitários dos testes de integração, até por este motivo, o comando de clean package utiliza skip.surefire.tests, pois é como o surifere foi configurado para os testes unitários.

**OBS**: Este readme será atualizado conforme o projeto for avançando.
