MATCH (category:Category)
  WHERE id(category) = toInteger($categoryId)

MATCH (item:Item)
  WHERE id(item) = toInteger($itemId)

MERGE (category)-[:includes]->(item)
MERGE (item)-[:belongsTo]->(category)

RETURN category
