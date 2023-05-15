MATCH (from:Role)
WHERE id(from) = toInteger($_id)

// add `includes` to-many relations
CALL {
    WITH from, $includesAdded
    MATCH (to:User)
    WHERE id(to) in $includesAdded
    MERGE (from)-[:Includes]->(to)
}

// delete `includes` to-many relations
CALL {
    WITH from, $includesDeleted
    MATCH (to:User)
    WHERE id(to) in $includesDeleted
    MATCH (from)-[r:Includes]->(to) DELETE r
}

RETURN from