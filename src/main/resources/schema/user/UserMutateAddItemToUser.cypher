MATCH (user:User)
WHERE id(user) = toInteger($userId)

MATCH (item:Item)
WHERE id(item) = toInteger($itemId)

MERGE (user)-[:Sells]->(item)
MERGE (item)-[:PublishedBy]->(user)

RETURN user
