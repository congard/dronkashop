MATCH (from:Payment)
WHERE id(from) = toInteger($_id)

// update `belongsTo` to-one relation
CALL {
    WITH from, $belongsTo
    MATCH (to:Order)
    WHERE id(to) = toInteger($belongsTo)
    OPTIONAL MATCH (from)-[r:belongsTo]->(:Order) DELETE r
    MERGE (from)-[:belongsTo]->(to)
}

RETURN from