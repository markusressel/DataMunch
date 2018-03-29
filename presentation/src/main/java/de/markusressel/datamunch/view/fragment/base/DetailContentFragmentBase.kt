package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.view.View
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import kotlinx.android.synthetic.main.content_accounts_user_detail.*

/**
 * Created by Markus on 15.02.2018.
 */
abstract class DetailContentFragmentBase<EntityType : Any> : DaggerSupportFragmentBase() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        MaterialViewPagerHelper
                .registerScrollView(activity, scrollView)
    }

    /**
     * Get the persistence handler for this view
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<EntityType>

    /**
     * Get the entity to edit from persistence
     */
    protected fun getEntityFromPersistence(): EntityType {
        val args = arguments
                ?: throw IllegalStateException("Arguments must not be null!")

        if (args.containsKey(DetailActivityBase.KEY_ENTITY_ID)) {

        } else {

        }

        val entityId: Long = args
                .getLong(DetailActivityBase.KEY_ENTITY_ID)

        return getPersistenceHandler()
                .standardOperation()
                .get(entityId)
    }

    /**
     * Store a modified version of the entity in persistence
     */
    protected fun storeModifiedEntity(modifiedEntity: EntityType) {
        getPersistenceHandler()
                .standardOperation()
                .put(modifiedEntity)
    }

}