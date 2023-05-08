package pl.edu.agh.db2.dronkashop.framework.entity

import java.util.LinkedList

class ToManyRelation<T : Entity> : LinkedList<Relation<T>>()