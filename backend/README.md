# Backend
Projeto em Java com intuito de praticar alguns conhecimentos e conceitos de projeto backend.

Neste projeto será utilizado Spring Boot, Maven, Java 11, Elastisearch e Kafka.

Ao clonar o projeto, se faz necessário baixar as dependências do mesmo e caso a própria IDE não faça isso e até mesmo para garantir que tudo esteja correto, o comando abaixo pode ajudar.

```
mvn clean package -Dskip.surefire.tests
```

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
No caso do Elasticsearch, o mesmo, para ser utilizado localmente, foi configurado no [Docker](https://docs.docker.com/get-docker/) da seguinte forma.

## Elasticsearch

### Configurando o Elasticsearch no docker

```
docker run -d --name=elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.1
```

### Configurando o Kibana no docker

```
docker run -d --name=kibana --link=elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:7.10.1
```

### Configurando autenticação do elasticsearch

Conecte-se ao elastic search, clicando em CLI na tela do Docker for windows ou executando o comando abaixo

```
docker exec -it elasticsearch bash
```

Vá até a pasta config.
```
cd config/
```

Edite o arquivo elasticsearch.yml
```
vi elasticsearch.yml
```

Adicione a seguinte linha no arquivo e salve-o
```
xpack.security.enabled: true
```

Reinicie o elasticsearch e volte a entrar nele via terminal, para alterar definir as senhas
aos usuários existentes. Para isso vá até a pasta bin
```
cd bin/
```

Execute o comando a baixo
```
elasticsearch-setup-passwords interactive
```

Responda com y a pergunta que aparecer no terminal e defina as senhas que deseja para cada usuário conforme for aparecendo. Em seguida, com o usuário elastic e a senha que definiu, é possível utilizar na autenticação com o elastic pela API e será o mesmo usuário e senha para acessarmos o kibana.

### Configurando a autenticação no Kibana

Agora, vamos configurar a autenticação do kibana com o elasticsearch. Para isso, entre da mesma forma que fizemos no elasticsearch. Pelo CLI da tela
ou via comando
```
docker exec -it kibana bash
```

Vá até o diretório config.
```
cd bin/
```

Execute o comando abaixo
```
kibana-keystore create
```

Depois do aviso que o keystore foi criado, execute o comando abaixo
```
kibana-keystore add elasticsearch.username
```

informe o usuário elastic e de Enter. Em seguida, execute o próximo comando
```
kibana-keystore add elasticsearch.password
```

Coloque a mesma senha que foi definida quando fizemos a alteração no elasticsearch, reinicie o kibana e via browser, entre com o usuário e senha configurado no passo anterior.

### Estrutua do Document utilizado no Elasticsearch

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
