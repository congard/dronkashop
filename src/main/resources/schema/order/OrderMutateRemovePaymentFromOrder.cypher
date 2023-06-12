MATCH (order:Order)
  WHERE id(order) = toInteger($orderId)

CALL {
  WITH order, $paymentId
  MATCH (payment:Payment)
    WHERE id(payment) = toInteger($paymentId)
  MATCH (payment)-[r:belongsTo]->(order) DELETE r
  MATCH (order)-[r:PayedWith]->(payment) DELETE r
}

RETURN order
