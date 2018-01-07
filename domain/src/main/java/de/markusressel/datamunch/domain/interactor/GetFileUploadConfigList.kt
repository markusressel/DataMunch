package de.markusressel.datamunch.domain.interactor

import de.markusressel.datamunch.domain.FileUploadConfig
import de.markusressel.datamunch.domain.executor.PostExecutionThread
import de.markusressel.datamunch.domain.executor.ThreadExecutor
import de.markusressel.datamunch.domain.repository.FileUploadConfigRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Markus on 07.01.2018.
 */
class GetFileUploadConfigList(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : UseCase<List<FileUploadConfig>, Void>(threadExecutor, postExecutionThread) {

    @Inject
    internal lateinit var fileUploadConfigRepository: FileUploadConfigRepository

    override fun buildUseCaseObservable(params: Void): Observable<List<FileUploadConfig>> {
        return this.fileUploadConfigRepository.allConfigurations()
    }

}