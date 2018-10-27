package de.markusressel.datamunch.view.fragment.system.update

import com.airbnb.epoxy.Typed2EpoxyController
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity

class UpdateController : Typed2EpoxyController<List<UpdateEntity>, Boolean>() {

    override fun buildModels(data: List<UpdateEntity>?, data2: Boolean?) {
    }

}