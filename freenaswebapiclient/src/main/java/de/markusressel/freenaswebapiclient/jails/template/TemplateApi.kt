package de.markusressel.freenaswebapiclient.jails.template

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.mountpoint.MountpointModel
import io.reactivex.Single

interface TemplateApi {
    /**
     * Get a list of all templates
     */
    fun getTemplates(limit: Int = RequestManager.DEFAULT_LIMIT,
                     offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<TemplateModel>>

    /**
     * Create a new template
     */
    fun createTemplate(jt_arch: String, jt_instances: Long, jt_name: String, jt_os: String,
                       jt_url: String): Single<TemplateModel>

    /**
     * Update an existing template
     */
    fun updateTemplate(id: Long, jt_arch: String, jt_instances: Long, jt_name: String,
                       jt_os: String, jt_url: String): Single<MountpointModel>

    /**
     * Delete a template
     */
    fun deleteTemplate(templateId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>
}