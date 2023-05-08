MATCH (from:Item)
WHERE id(from) = toInteger($_id)

// add belongsTo to-many relations
CALL {
    WITH from, $belongsToAdded
    MATCH (to:Category)
    WHERE id(to) in $belongsToAdded
    MERGE (from)-[:BelongsTo]->(to)
}

// delete belongsTo to-many relations
CALL {
    WITH from, $belongsToDeleted
    MATCH (to:Category)
    WHERE id(to) in $belongsToDeleted
    MATCH (from)-[r:BelongsTo]->(to) DELETE r
}

// update publishedBy to-one relation
CALL {
    WITH from, $publishedBy
    MATCH (to:User)
    WHERE id(to) = toInteger($publishedBy)
    OPTIONAL MATCH (from)-[r:PublishedBy]->(:User) DELETE r
    MERGE (from)-[:PublishedBy]->(to)
}

RETURN from