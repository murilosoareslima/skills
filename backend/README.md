# Backend
Projeto em Java com intuito de praticar alguns conhecimentos e conceitos de projeto backend.

Neste projeto será utilizado Spring Boot, Maven, Java 11, Elastisearch e Kafka.

No caso do Elasticsearch, o mesmo, para ser utilizado localmente, foi configurado no Docker da seguinte forma.

## Elasticsearch

### Configurando o Elasticsearch no docker

```
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -it docker.elastic.co/elasticsearch/elasticsearch:8.1.0
```

### Configurando o Kibana no docker

```
docker run -d --name kibana --link elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:8.1.0
```

A estrutura do Document que será utilizado por essa api, pode ser vista a baixo, já no formato do comando que o Elasticsearch pede para que a estrutura seja criada.

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

Para os testes, este projeto irá utilizar os plugins **Surefire** e **FailSafe** para separarmos a execução dos testes unitários dos testes de integração

**OBS**: Este readme será atualizado conforme o projeto for avançando.
