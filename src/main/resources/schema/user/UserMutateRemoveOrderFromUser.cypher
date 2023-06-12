MATCH (user:User)
  WHERE id(user) = toInteger($userId)

CALL {
  WITH user, $orderId
  MATCH (order:Order)
    WHERE id(order) = toInteger($orderId)
  MATCH (order)-[r:By]->(user) DELETE r
  MATCH (user)-[r:Has]->(order) DELETE r
}

RETURN user
