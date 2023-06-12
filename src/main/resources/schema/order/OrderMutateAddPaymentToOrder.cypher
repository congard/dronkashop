MATCH (order:Order)
WHERE id(order) = toInteger($orderId)

MATCH (payment:Payment)
WHERE id(payment) = toInteger($paymentId)

// PayedWith and belongsTo are ToOne relations,
// so delete previous relations if exist

OPTIONAL MATCH (order)-[p:PayedWith]->() DELETE p
WITH payment
OPTIONAL MATCH (payment)-[b:belongsTo]->() DELETE b

MERGE (order)-[:PayedWith]->(payment)
MERGE (payment)-[:belongsTo]->(order)

RETURN order
