MATCH (from:User)
WHERE id(from) = toInteger($_id)

// add `sells` to-many relations
CALL {
    WITH from, $sellsAdded
    MATCH (to:Item)
    WHERE id(to) in $sellsAdded
    MERGE (from)-[:Sells]->(to)
}

// delete `sells` to-many relations
CALL {
    WITH from, $sellsDeleted
    MATCH (to:Item)
    WHERE id(to) in $sellsDeleted
    MATCH (from)-[r:Sells]->(to) DELETE r
}

// add `has` to-many relations
CALL {
    WITH from, $hasAdded
    MATCH (to:Order)
    WHERE id(to) in $hasAdded
    MERGE (from)-[:Has]->(to)
}

// delete `has` to-many relations
CALL {
    WITH from, $hasDeleted
    MATCH (to:Order)
    WHERE id(to) in $hasDeleted
    MATCH (from)-[r:Has]->(to) DELETE r
}

// update `belongsTo` to-one relation
CALL {
    WITH from, $belongsTo
    MATCH (to:Role)
    WHERE id(to) = toInteger($belongsTo)
    OPTIONAL MATCH (from)-[r:BelongsTo]->(:Role) DELETE r
    MERGE (from)-[:BelongsTo]->(to)
}

RETURN from