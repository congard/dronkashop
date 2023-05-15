CALL {
    WITH $$queryOrderedItem_id as _id
    MATCH ((o:Order)-[n:Includes]->(i:Item))
    WHERE id(n) = toInteger(_id)
    RETURN n, o, i
}
RETURN {
    order: {
        _id: id(o)
    },
    item: {
        _id: id(i)
    },
    quantity: n.quantity
}