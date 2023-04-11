# DronkaShop

## Structure

```
pl.edu.agh.db2.dronkashop.backend
    entity          classes like Category, User, Item, etc
    ext             extension functions - w tym pakiecie są dodatkowe
                    funkcje dla już istniejących klas
    provider

resources
    query           tu umieszczamy GraphQL queries
```

## Stack

1. Neo4J
2. GraphQL
3. Kotlin

## Roadmap

- [ ] Projektujemy bazę;
- [ ] Tworzymy bazę korzystając z Cypher;
- [ ] Tworzymy schemat (`schema`) dla GraphQL;
- [ ] Piszemy backend w Kotlinie;
- [ ] Jeżeli mamy czas, tworzymy jakiś tam frontend w JavaFx;
- [ ] Sprawko (ofc lepiej go pisać równolegle);

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

### GraphQL

1. [Biblioteka, z której korzystamy](https://github.com/neo4j-graphql/neo4j-graphql-java#how-does-it-work)
2. **[Introduction to GraphQL](https://graphql.org/learn/)**
