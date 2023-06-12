MATCH (user:User)
WHERE id(user) = toInteger($userId)

MATCH (role:Role)
WHERE id(role) = toInteger($roleId)

// BelongsTo is ToOne relation,
// so delete previous relations if exist

OPTIONAL MATCH (user)-[b:BelongsTo]->() DELETE b
OPTIONAL MATCH ()-[i:Includes]->(user) DELETE i

MERGE (user)-[:BelongsTo]->(role)
MERGE (role)-[:Includes]->(user)

RETURN user
