MATCH (category:Category)
  WHERE id(category) = toInteger($categoryId)

MATCH (item:Item)
  WHERE id(item) = toInteger($itemId)

MERGE (category)-[:Includes]->(item)

RETURN category
