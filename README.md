# DronkaShop

## Autorzy
- Danylo Knapp
- Kacper Słoniec
- Bartłomiej Szymański


## Structure

The project consists of:

1. Backend (DronkaShop)
   1. [Kotlin code](src/main/kotlin/pl/edu/agh/db2/dronkashop/backend)
   2. [GraphQL/Cypher code](src/main/resources) (schema, queries, mutations) 
2. [Framework](src/main/kotlin/pl/edu/agh/db2/dronkashop/framework)

## Stack

1. Neo4J
2. GraphQL
3. Kotlin

## Configuration

Before building, project should be configured: `credentials.txt` should be created with
the following content:

```
SERVER_URL
USER_NAME
PASSWORD
```

Without this, compilation error will be thrown.

## Roadmap

- [x] Projektujemy & implementujemy framework
- [x] Projektujemy bazę;
- [x] Tworzymy bazę korzystając z Cypher;
- [x] Tworzymy schemat (`schema`) dla GraphQL;
- [x] Piszemy backend w Kotlinie;
- [ ] Jeżeli mamy czas, tworzymy jakiś tam frontend w JavaFx;
- [x] Sprawko (ofc lepiej go pisać równolegle);

## IDE extensions

1. IntelliJ: [Graph Database](https://plugins.jetbrains.com/plugin/20417-graph-database)
2. VSCode: [Cypher Query Language](https://marketplace.visualstudio.com/items?itemName=jakeboone02.cypher-query-language)
3. VSCode: [GraphQL](https://marketplace.visualstudio.com/items?itemName=mquandalle.graphql)

## Links

### Kotlin

Korzystamy z bibliotek napisanych w Kotlinie – a więc też używamy Kotlina.

1. [Basic syntax](https://kotlinlang.org/docs/basic-syntax.html)
2. [Classes](https://kotlinlang.org/docs/classes.html)
3. [Properties](https://kotlinlang.org/docs/properties.html)
4. [Objects](https://kotlinlang.org/docs/object-declarations.html)
5. [Lambdas](https://kotlinlang.org/docs/lambdas.html)
6. [Collections](https://kotlinlang.org/docs/collections-overview.html)
7. [Scope functions](https://kotlinlang.org/docs/scope-functions.html)
8. [`use` keyword](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/use.html)

### Neo4J

1. [Manual](https://neo4j.com/docs/java-manual/current/get-started/)
2. [API Reference](https://neo4j.com/docs/api/java-driver/current/)
3. [Cypher](https://neo4j.com/docs/cypher-manual/current/introduction/)
4. **[Workspace](https://workspace-preview.neo4j.io/)**
5. [Session API](https://neo4j.com/docs/java-manual/current/session-api/)

### GraphQL

1. [Biblioteka, z której korzystamy](https://github.com/neo4j-graphql/neo4j-graphql-java#how-does-it-work)
   1. [Auto Generated Queries and Mutations](https://github.com/neo4j-graphql/neo4j-graphql-java#auto-generated-queries-and-mutations)
2. **[Introduction to GraphQL](https://graphql.org/learn/)**

### Cypher

1. [Conditional Cypher Execution](https://neo4j.com/developer/kb/conditional-cypher-execution/)
2. [Conditional Cypher Execution (docs)](https://neo4j.com/docs/apoc/current/cypher-execution/conditionals/)
3. [CALL {} (subquery)](https://neo4j.com/docs/cypher-manual/current/clauses/call-subquery/)
4. [Stackoverflow: Cypher query with chaining does not propagate](https://stackoverflow.com/questions/75604080/cypher-query-with-chaining-does-not-propagate)
5. [SET](https://neo4j.com/docs/cypher-manual/current/clauses/set/)
