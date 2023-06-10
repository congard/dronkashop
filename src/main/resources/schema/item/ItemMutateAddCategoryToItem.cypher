MATCH (category:Category)
  WHERE id(category) = toInteger($categoryId)

MATCH (item:Item)
  WHERE id(item) = toInteger($itemId)

MERGE (item)-[:belongsTo]->(category)

RETURN item