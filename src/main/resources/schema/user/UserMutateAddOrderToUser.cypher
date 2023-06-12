MATCH (user:User)
WHERE id(user) = toInteger($userId)

MATCH (order:Order)
WHERE id(order) = toInteger($orderId)

MERGE (user)-[:Has]->(order)
MERGE (order)-[:By]->(user)

RETURN user
