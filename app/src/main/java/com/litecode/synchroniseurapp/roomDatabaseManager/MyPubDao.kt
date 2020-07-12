package com.litecode.synchroniseurapp.roomDatabaseManager

import androidx.room.*

@Dao
interface MyPubDao {

    @Query("SELECT * FROM pubTable")
    fun getAll(): List<PubTable>

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pubTable: PubTable): Long


    @Update
    fun updatePublication(pubTable: PubTable)

    @Query("UPDATE pubTable set status= 0 WHERE id= :id")
    fun updatePublicationwithPrecision(id: Int)


    @Delete
    fun deleteSportPost(pubTable: PubTable)


    @Query("SELECT * FROM pubTable WHERE id ==:post_id")
    fun getSportPost(post_id: Int): PubTable
}
