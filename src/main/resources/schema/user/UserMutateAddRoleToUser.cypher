MATCH (user:User)
  WHERE id(user) = toInteger($userId)

MATCH (role:Role)
  WHERE id(role) = toInteger($roleId)

MERGE (user)-[:belongsTo]->(role)
MERGE (role)-[:includes]->(user)

RETURN user
