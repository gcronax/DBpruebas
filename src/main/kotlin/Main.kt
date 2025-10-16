import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

// Ruta al archivo de base de datos SQLite
const val URL_BD = "jdbc:sqlite:src/main/resources/cars.sqlite"

// Obtener conexión
fun getConnection(): Connection? {
    return try {
        DriverManager.getConnection(URL_BD)
    } catch (e: SQLException) {
        e.printStackTrace()
        null
    }
}

fun main() {
    getConnection()?.use { conn ->
        println("Conectado a la BD")

        conn.createStatement().use { stmt ->
            val rs = stmt.executeQuery("SELECT * FROM coches")
            while (rs.next()) {
                println("${rs.getInt("id")}," +
                        " ${rs.getString("modelo")}," +
                        " ${rs.getString("marca")}," +
                        " ${rs.getDouble("consumo")}," +
                        " ${rs.getInt("hp")}")
            }
        }
    } ?: println("No se pudo conectar")
}