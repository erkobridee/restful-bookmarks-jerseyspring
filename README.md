RESTful Bookmarks Jersey + Spring
=================================

Exemplo de aplicação para salvar links, onde a interface utiliza AngularJS + Twitter Bootstrap e o lado do servidor utilizado o Jersey integrado com o Spring para disponibilizar um serviço de dados RESTful, que aceita uma comunicação JSON ou XML.

Utilizado neste projeto
-----------------------

* Ambiente de desenvolvimento
	* [Maven](http://maven.apache.org/) 3
	* [Eclipse](http://eclipse.org/) Juno JEE
	* [Apache Tomcat](http://tomcat.apache.org/) 6.x
	* Java 1.6+

* Cliente
	* [AngularJS](http://angularjs.org/) 1.0.1
	* [Twitter Bootstrap](twitter.github.com/bootstrap) 2.0.4

* Servidor
	* [Jersey](http://jersey.java.net/) ([User Guide](http://jersey.java.net/nonav/documentation/latest/user-guide.html))
	* [Spring](http://www.springsource.org/)
	* [Hibernate](http://www.hibernate.org/)
	* [HSQLDB](http://hsqldb.org/)
	* [Jetty](http://jetty.codehaus.org/jetty/) para testes, gerenciado pelo Maven

Quanto as versões no Servidor:

	Verificar o arquivo pom.xml
	
Um projeto que auxiliou neste projeto foi o [Wine Cellar Java](https://github.com/ccoenraets/wine-cellar-java), que é um exemplo de uso do Jersey em uma webapp Java. Além do post do Christophe Coenraets - [Sample Application with Angular.js](http://coenraets.org/blog/2012/02/sample-application-with-angular-js/) e [Using Backbone.js with a RESTful Java Back-End](http://coenraets.org/blog/2012/01/using-backbone-js-with-a-restful-java-back-end/).

Montando o ambiente local para uso desse projeto
------------------------------------------------
O projeto disponibilizado no github, não possui nenhum arquivo para incluí-lo diretamente no Eclipse. Então após baixar o projeto no seu computador local.

Conforme especificado anteriormente, os itens citados do ambiente de desenvolvimento serão necessários.

Feito o download/clone do projeto para a sua máquina local, realize o respectivos passos a seguir:

1. Vá até o diretório do projeto
2. Execute os comandos em sequência:
	
	`mvn compile` 
	
	`mvn eclipse:eclipse -Dwtpversion=2.0`
	
3. Importe o projeto no Eclipse

	**Atenção:**

		É necessário ter a variável M2_REPO configurada nas 
		variáveis do ClassPath, apontando para o diretório 
		do .m2/repository do Maven
		
		Lembre-se também de ter adicionado o Apache Tomcat 6.x
		ao Runtime Environments nas preferencias do seu Eclipse

4. Acesse as preferencias do Projeto e atualize no **Project Facets** a versão do Java 1.4 para Java 1.6
5. Feito isso, o projeto está pronto para ser executado no Eclipse. Caso queira gerar o .war do projeto utilize o comando

	`mvn clean install`


Quanto ao RESTful do projeto
----------------------------
A definição do método a ser executado é definido no cabeçalho da requisição enviada para o servidor.

* **GET** - recupera 1 ou mais bookmarks
	* [.../api/bookmarks/]() - lista todos os bookmarks
	* [.../api/bookmarks/{id}]() - retorna o respectivo bookmark pelo seu ID
	* [.../api/bookmarks/search/{name}]() - retorna uma lista dos bookmarks que contém o respectivo nome
* **POST** - insere um novo
	* [.../api/bookmarks/]() - enviado via post
* **PUT** - atualiza um existente
	* [.../api/bookmarks/{id}]() - enviado via post 
* **DELETE** - remove 1 bookmark pelo ID
	* [.../api/bookmarks/{id}]() 


Archetype do Maven que gerou a estrutura inicial do projeto
-----------------------------------------------------------

<pre><code>mvn archetype:generate \
    -DarchetypeGroupId=org.apache.maven.archetypes \
    -DarchetypeArtifactId=maven-archetype-webapp \
    -Dversion=1.0 \
    -DgroupId=com.erkobridee.restful.bookmarks.jerseyspring \
    -DartifactId=restful-bookmarks-jerseyspring</code></pre>

Alterações
----------

* [v1](https://github.com/erkobridee/restful-bookmarks-jerseyspring/tree/v1) 
	* versão inicial funcional
* v2 - ***versão atual***
	* Atualização do Spring para versão 3.1.1.RELEASE
	* Atualização das configurações 
	* Inclusão do Jetty para teste da aplicação diretamente pelo Maven
	
		`mvn jetty:run` - a aplicação irá executar na url: `http://localhost:9090`
	
	
	* Inclusão de testes de Integração
	
		`mvn clean integration Test` - 
		durante a fase de teste de integração a aplicação irá testar a url: `http://localhost:8080/restful-bookmarks-jerseyspring/`


