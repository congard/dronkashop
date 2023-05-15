MATCH (from:Category)
WHERE id(from) = toInteger($_id)

// add includes to-many relations
CALL {
    WITH from, $includesAdded
    MATCH (to:Item)
    WHERE id(to) in $includesAdded
    MERGE (from)-[:Includes]->(to)
}

// delete includes to-many relations
CALL {
    WITH from, $includesDeleted
    MATCH (to:Item)
    WHERE id(to) in $includesDeleted
    MATCH (from)-[r:Includes]->(to) DELETE r
}

RETURN from