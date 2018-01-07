package de.markusressel.datamunch.domain

/**
 * Class that represents a FileUploadConfig in the domain layer.
 *
 * Created by Markus on 07.01.2018.
 */
class FileUploadConfig(val id: Int,
                       val sourcePath: String,
                       var destinationPath: String)