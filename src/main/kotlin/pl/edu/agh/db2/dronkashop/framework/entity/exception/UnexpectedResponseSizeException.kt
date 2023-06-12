package pl.edu.agh.db2.dronkashop.framework.entity.exception

class UnexpectedResponseSizeException(size: Int)
    : EntityException("Unexpected response size: expected 1 record, got $size")