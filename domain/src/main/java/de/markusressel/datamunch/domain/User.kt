package de.markusressel.datamunch.domain

/**
 * Class that represents a User in the domain layer.
 *
 * Created by Markus on 06.01.2018.
 */
class User(val id: String,
           var username: String,
           var fullName: String,
           var eMail: String,
           var icon: Int)