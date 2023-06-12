package pl.edu.agh.db2.dronkashop.backend.provider

import pl.edu.agh.db2.dronkashop.backend.entity.Order
import pl.edu.agh.db2.dronkashop.framework.core.ID
import pl.edu.agh.db2.dronkashop.framework.provider.DBProvider
import pl.edu.agh.db2.dronkashop.framework.provider.EntityProvider
import pl.edu.agh.db2.dronkashop.framework.runner.QueryRunner

object OrderProvider {

    /**
     * If Order with the specified id hasn't been loaded yet,
     * it will be loaded, otherwise cached object will be returned
     */
    fun getById(id: ID, runner: QueryRunner = DBProvider.defaultQueryRunner): Order =
        EntityProvider.entityById(id, runner = runner)
}