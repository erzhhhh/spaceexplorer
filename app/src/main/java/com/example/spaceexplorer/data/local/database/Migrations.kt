package com.example.spaceexplorer.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS favorite_articles (
                id INTEGER NOT NULL PRIMARY KEY,
                title TEXT NOT NULL,
                url TEXT NOT NULL,
                imageUrl TEXT NOT NULL,
                newsSite TEXT NOT NULL,
                summary TEXT NOT NULL,
                publishedAt TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}