MATCH (user:User)
  WHERE id(user) = toInteger($userId)

MATCH (order:Order)
  WHERE id(order) = toInteger($orderId)

MERGE (user)-[:has]->(order)
MERGE (order)-[:by]->(user)

RETURN user
