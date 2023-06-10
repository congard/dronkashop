MATCH (order:Order)
  WHERE id(order) = toInteger($orderId)

MATCH (payment:Payment)
  WHERE id(payment) = toInteger($paymentId)

MERGE (order)-[:payedWith]->(payment)
MERGE (payment)-[:belongsTo]->(order)

RETURN order
