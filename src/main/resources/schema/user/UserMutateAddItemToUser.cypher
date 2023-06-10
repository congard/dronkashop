MATCH (user:User)
  WHERE id(user) = toInteger($userId)

MATCH (item:Item)
  WHERE id(item) = toInteger($itemId)

MERGE (user)-[:sells]->(item)
MERGE (item)-[:publishedBy]->(user)

RETURN user
