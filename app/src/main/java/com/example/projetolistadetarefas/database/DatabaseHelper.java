package com.example.projetolistadetarefas.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, "app.db", null, 1){
        override fun onCreate(db: SQLiteDatabase) {

            val sql = """
            CREATE TABLE posts(
                id INTEGER PRIMARY KEY,
                title TEXT,
                body TEXT
            )
        """.trimIndent()

            db.execSQL(sql)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

            db.execSQL("DROP TABLE IF EXISTS posts")
            onCreate(db)

        }
    }
    fun insertPost(title: String, body: String) {

    val db = writableDatabase

    val sql = "INSERT INTO posts (title, body) VALUES (?, ?)"

    val stmt = db.compileStatement(sql)
    stmt.bindString(1, title)
    stmt.bindString(2, body)

    stmt.executeInsert()
}
}
