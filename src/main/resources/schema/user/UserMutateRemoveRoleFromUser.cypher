MATCH (user:User)
  WHERE id(user) = toInteger($userId)

CALL {
  WITH user, $roleId
  MATCH (role:Role)
    WHERE id(role) = toInteger($roleId)
  MATCH (role)-[r:Includes]->(user) DELETE r
  MATCH (user)-[r:BelongsTo]->(role) DELETE r
}

RETURN user
