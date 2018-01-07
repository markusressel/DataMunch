package de.markusressel.datamunch.domain.repository

import de.markusressel.datamunch.domain.FileUploadConfig
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting {@link FileUploadConfig} related data.
 *
 * Created by Markus on 07.01.2018.
 */
interface FileUploadConfigRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link FileUploadConfig}.
     */
    fun allConfigurations(): Observable<List<FileUploadConfig>>

    /**
     * Get an {@link Observable} which will emit a {@link FileUploadConfig}.
     *
     * @param configurationId The id used to retrieve FileUploadConfig data.
     */
    fun configuration(configurationId: Int): Observable<FileUploadConfig>


}