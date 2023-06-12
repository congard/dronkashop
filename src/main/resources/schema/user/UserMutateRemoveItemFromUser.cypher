MATCH (user:User)
  WHERE id(user) = toInteger($userId)

CALL {
  WITH user, $itemId
  MATCH (item:Item)
    WHERE id(item) = toInteger($itemId)
  MATCH (item)-[r:PublishedBy]->(user) DELETE r
  WITH user, item
  MATCH (user)-[r:Sells]->(item) DELETE r
}

RETURN user
