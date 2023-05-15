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

RETURN from