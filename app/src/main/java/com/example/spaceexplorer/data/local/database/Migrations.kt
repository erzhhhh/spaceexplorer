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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.migratePublishedAtToEpochMillis(
            tableName = "feed_articles",
            columns = "id, title, url, imageUrl, newsSite, summary"
        )
        db.migratePublishedAtToEpochMillis(
            tableName = "launch_articles",
            columns = "id, title, url, imageUrl, newsSite, summary"
        )
        db.migratePublishedAtToEpochMillis(
            tableName = "favorite_articles",
            columns = "id, title, url, imageUrl, newsSite, summary"
        )
    }

    // publishedAt was stored as an ISO-8601 TEXT column; InstantConverter now persists Instant as
    // epoch-millis INTEGER, and SQLite has no ALTER COLUMN, so the table is rebuilt from scratch.
    private fun SupportSQLiteDatabase.migratePublishedAtToEpochMillis(
        tableName: String,
        columns: String
    ) {
        val oldTableName = "${tableName}_old"
        execSQL("ALTER TABLE $tableName RENAME TO $oldTableName")
        execSQL(
            """
            CREATE TABLE $tableName (
                id INTEGER NOT NULL PRIMARY KEY,
                title TEXT NOT NULL,
                url TEXT NOT NULL,
                imageUrl TEXT NOT NULL,
                newsSite TEXT NOT NULL,
                summary TEXT NOT NULL,
                publishedAt INTEGER
            )
            """.trimIndent()
        )
        execSQL(
            """
            INSERT INTO $tableName ($columns, publishedAt)
            SELECT $columns, CAST(strftime('%s', publishedAt) AS INTEGER) * 1000
            FROM $oldTableName
            """.trimIndent()
        )
        execSQL("DROP TABLE $oldTableName")
    }
}