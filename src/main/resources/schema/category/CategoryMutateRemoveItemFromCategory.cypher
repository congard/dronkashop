MATCH (category:Category)
  WHERE id(category) = toInteger($categoryId)

CALL {
  WITH category, $itemId
  MATCH (item:Item)
    WHERE id(item) = toInteger($itemId)
  MATCH (category)-[r:Includes]->(item) DELETE r
  MATCH (item)-[r:BelongsTo]->(category) DELETE r
}

RETURN category
