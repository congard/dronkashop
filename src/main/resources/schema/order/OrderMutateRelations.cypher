MATCH (from:Order)
WHERE id(from) = toInteger($_id)

// update `by` to-one relation
CALL {
    WITH from, $by
    MATCH (to:User)
    WHERE id(to) = toInteger($by)
    OPTIONAL MATCH (from)-[r:By]->(:User) DELETE r
    MERGE (from)-[:By]->(to)
}

// update `payedWith` to-one relation
CALL {
    WITH from, $payedWith
    MATCH (to:Payment)
    WHERE id(to) = toInteger($payedWith)
    OPTIONAL MATCH (from)-[r:PayedWith]->(:Payment) DELETE r
    MERGE (from)-[:PayedWith]->(to)
}

RETURN from