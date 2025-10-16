import java.io.File
import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException

object carsBD {
    // Ruta al archivo de base de datos SQLite
    private val dbPath = "src/main/resources/cars.sqlite"
    private val dbFile = File(dbPath)
    private val url = "jdbc:sqlite:${dbFile.absolutePath}"

    // Obtener conexión
    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    // Función de prueba: verificar conexión
    fun testConnection(): Boolean {
        return getConnection()?.use { conn ->
            println("Conexión establecida con éxito a ${dbFile.absolutePath}")
            true
        } ?: false
    }

    // Cerrar conexión (para los casos en los que no se utiliza .use)
    fun closeConnection(conn: Connection?) {
        try {
            conn?.close()
            println("Conexión cerrada correctamente.")
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        }
    }
}
fun main() {
    val conn = carsBD.getConnection()
    if (conn != null) {
        println("Conectado a la BD correctamente.")
        carsBD.closeConnection(conn)
    }
}