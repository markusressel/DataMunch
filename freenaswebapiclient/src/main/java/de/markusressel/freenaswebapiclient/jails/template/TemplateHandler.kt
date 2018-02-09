package de.markusressel.freenaswebapiclient.jails.template

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.mountpoint.MountpointModel
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class TemplateHandler(private val requestManager: RequestManager) : TemplateApi {

    /**
     * Get a list of all templates
     */
    override fun getTemplates(limit: Int, offset: Int): Single<List<TemplateModel>> {
        return requestManager
                .doRequest("/jails/templates/", Method.GET, TemplateModel.ListDeserializer())
    }

    /**
     * Create a new template
     */
    override fun createTemplate(jt_arch: String, jt_instances: Long, jt_name: String, jt_os: String,
                                jt_url: String): Single<TemplateModel> {
        val data = TemplateModel(0, jt_arch, jt_instances, jt_name, jt_os, jt_url)
        return requestManager
                .doJsonRequest("/jails/templates/", Method.POST, data,
                               TemplateModel.SingleDeserializer())
    }

    /**
     * Update an existing template
     */
    override fun updateTemplate(id: Long, jt_arch: String, jt_instances: Long, jt_name: String,
                                jt_os: String, jt_url: String): Single<MountpointModel> {
        val data = TemplateModel(id, jt_arch, jt_instances, jt_name, jt_os, jt_url)
        return requestManager
                .doJsonRequest("/jails/templates/$id/", Method.PUT, data,
                               MountpointModel.SingleDeserializer())
    }

    /**
     * Delete a template
     */
    override fun deleteTemplate(
            templateId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/templates/$templateId/", Method.DELETE)
    }

}