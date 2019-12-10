package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_DATA_DATE, user.dataDate)
        values.put(DBContract.UserEntry.COLUMN_DATA_IN_TIME, user.dataInTime)
        values.put(DBContract.UserEntry.COLUMN_DATA_OUT_TIME, user.dataOutTime)
        values.put(DBContract.UserEntry.COLUMN_DATA_TIME_SPENT, user.dataTimeSpent)
        values.put(DBContract.UserEntry.COLUMN_DATA_REG, user.dataReg)
        values.put(DBContract.UserEntry.COLUMN_DATA_WEEK_OF_YEAR, user.dataWeekOfYear)
        values.put(DBContract.UserEntry.COLUMN_DATA_LEAVE, user.dataLeave)


        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(dataDate: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_DATA_DATE + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(dataDate)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(dataDate: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_DATA_DATE + "='" + dataDate + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dataInTime: String
        var dataOutTime: String
        var dataTimeSpent: String
        var dataReg: String
        var dataWeekOfYear: String
        var dataLeave: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dataInTime = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_IN_TIME))
                dataOutTime = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_OUT_TIME))
                dataTimeSpent = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_TIME_SPENT))
                dataReg = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_REG))
                dataWeekOfYear = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_WEEK_OF_YEAR))
                dataLeave = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_LEAVE))

                users.add(UserModel(dataDate, dataInTime, dataOutTime, dataTimeSpent, dataReg, dataWeekOfYear, dataLeave))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dataDate: String
        var dataInTime: String
        var dataOutTime: String
        var dataTimeSpent: String
        var dataReg: String
        var dataWeekOfYear: String
        var dataLeave: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dataDate = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_DATE))
                dataInTime = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_IN_TIME))
                dataOutTime = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_OUT_TIME))
                dataTimeSpent = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_TIME_SPENT))
                dataReg = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_REG))
                dataWeekOfYear = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_WEEK_OF_YEAR))
                dataLeave = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_LEAVE))

                users.add(UserModel(dataDate, dataInTime, dataOutTime, dataTimeSpent, dataReg, dataWeekOfYear, dataLeave))
                cursor.moveToNext()
            }
        }
        return users
    }


    fun readTimeCompleted(dataWeekOfYear: String): Int {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select " + DBContract.UserEntry.COLUMN_DATA_TIME_SPENT + " from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_DATA_WEEK_OF_YEAR + "='" + dataWeekOfYear + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            //return ArrayList()
        }

        var tempTime = 0
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                tempTime = tempTime + cursor.getInt(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATA_TIME_SPENT))
                cursor.moveToNext()
            }
        }
        return tempTime
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_DATA_DATE + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_DATA_IN_TIME + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATA_OUT_TIME + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATA_TIME_SPENT + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATA_REG + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATA_WEEK_OF_YEAR + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATA_LEAVE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}