package pl.edu.agh.db2.dronkashop.framework.entity

import java.util.*

class ToManyRelation<T : Entity> : LinkedList<Relation<T>>()