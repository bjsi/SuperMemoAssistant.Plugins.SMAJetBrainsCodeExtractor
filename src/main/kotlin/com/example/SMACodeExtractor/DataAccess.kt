package com.example.SMACodeExtractor
import com.jetbrains.rd.util.string.printer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.sql.Connection

object Extract: Table() {
    val Id = integer("Id").autoIncrement().primaryKey()
    val timestamp = datetime("timestamp")
    val selectedCode = varchar("selectedCode", length = 2000)
    val comment = text("comment")
    val priority = integer("priority")
    val exported = bool("exported")
    val file = text("file")
    val language = text("language")
    val project = text("project")
}


fun CreateExtract(selCode: String, com: String, pri: Int, filePath: String, projectName: String, lang: String) {
    Database.connect("jdbc:sqlite:C:/Users/james/Desktop/SMACodeExtracts.db", driver = "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Extract)
        Extract.insert {
            it[timestamp] = DateTime.now()
            it[selectedCode] = selCode
            it[comment] = com
            it[priority] = pri
            it[exported] = false
            it[file] = filePath
            it[language] = lang
            it[project] = projectName
        }
    }
}

