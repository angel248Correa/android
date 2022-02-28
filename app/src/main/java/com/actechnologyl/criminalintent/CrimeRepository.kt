package com.actechnologyl.criminalintent

import androidx.lifecycle.LiveData
import androidx.room.Room
import database.CrimeDatabase
import database.migration_1_2
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private  const val  DATABASE_NAME =   "crime-database"

class CrimeRepository private  constructor(context: CriminalIntentApplication) {
    private  val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()

    private  val CrimeDao = database.CrimeDao()

    private  val executor = Executors.newSingleThreadExecutor()

    private val filesDir = context.applicationContext.filesDir

    fun getCrimes(): LiveData<List<Crime>> = CrimeDao.getCrime()
    fun getCrime(id: UUID): LiveData<Crime?> = CrimeDao.getCrime(id)


    fun updateCrime(crime: Crime) {
        executor.execute{
            CrimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute{
            CrimeDao.addCrime(crime)
        }
    }

    fun getPhotoFile(crime: Crime): File = File(filesDir, crime.photoFileName)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: CriminalIntentApplication) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)

            }
        }
        fun get(): CrimeRepository{
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized ")
        }

    }
}