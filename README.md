# API Gateway
Esse projeto foi desenvolvido para centralizar todas as chamadas para os serviços do SGM, utilizando as tecnlogias JAVA, Spring Security, JWT, ZUUL e Eureka.

````Em ambiente produtivo não será possível chamar os microserviços diretamente, todas as requisições deverão roteadas pelo API-GATEWAY.````

Membros:
 - [Jonathan Cabral](mailto:dev.jonathancabral@gmail.com)
 - [André Graciano](mailto:dev.jonathancabral@gmail.com)

## Build sem Docker
 
    gradlew clean build
    
## build com docker

    docker build -t sgm/api-gateway -f .\Dockerfile .

    docker run -it -p 8081:8081 sgm/api-gateway
    

## Consulta rotas
	http://localhost:8080/actuator/routes
  
### Projetos SGM que são roteados pelo Gateway

  - [Cidadao](https://github.com/tcc-sgm/cidadao/issues)
  - [Auth](https://github.com/tcc-sgm/auth)


## Hierarquia de dependencias

Execute gradle `htmlDependecyReport` para gerar um relatório HTML mostrando a hierarquia de dependencias de cada subprojeto
