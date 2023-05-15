MATCH (:Order)-[from:Includes]->(:Item)
WHERE id(from) = toInteger(_id)
SET (CASE WHEN quantity is not null THEN from END).quantity = toInteger(quantity)
RETURN from