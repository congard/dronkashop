# Framework

A simple basic framework for retrieving & writing data from / in Neo4J database.
It uses GraphQL (via [neo4j-graphql-java](https://github.com/neo4j-graphql/neo4j-graphql-java))
and Cypher.

Note, that this framework WILL NOT autogenerate GraphQL/Cypher queries/mutations for you,
you should do it by yourself.

This framework was created for EDUCATIONAL purposes only, it is NOT READY for real-word usage.

## Supported features

1. Properties (query / mutate)
2. ToMany relationship (query / mutate)
3. ToOne relationship (query / mutate)
4. Autodetection of changes
5. Only one instance of an entity with a particular id exists at a time 

## What's next?

This framework can be improved a lot, for example the following features can be implemented:

1. Schema autogeneration (based on classes & their properties)
2. GraphQL/Cypher autogeneration
3. Optimized mutations (generate Cypher for changed properties/queries only)
4. Bidirectional relations (only one instance of a relation with a particular id can exist at a time)
5. Annotations (custom property / entity name etc.)
6. More advanced inheritance support
7. Nullable properties & to-one relations

## Example

Example can be found in package `pl.edu.agh.db2.dronkashop.backend`
