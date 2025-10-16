data class Coche(
    val id_coche: Int? = null, // lo genera SQLite automáticamente
    val modelo: String,
    val marca: String,
    val consumo: Double,
    val hp: Int
)
data class Repuesto(
    val id_repuesto: Int? = null, // lo genera SQLite automáticamente
    val localizacion: String,
    val precio: Double
)
data class Rueda(
    val id_rueda: Int? = null, // lo genera SQLite automáticamente
    val tipo: String,
    val precio: Double,
    val pulgadas: Double
    )

object CochesDAO {

    fun listarCoches(): List<Coche> {
        val lista = mutableListOf<Coche>()
        getConnection()?.use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT * FROM coches")
                while (rs.next()) {
                    lista.add(
                        Coche(
                            id_coche = rs.getInt("id_coche"),
                            modelo = rs.getString("modelo"),
                            marca = rs.getString("marca"),
                            consumo  = rs.getDouble("consumo"),
                            hp = rs.getInt("hp")
                        )
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return lista
    }

    // Consultar planta por ID
    fun consultarCochePorId(id: Int): Coche? {
        var coche: Coche? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM plantas WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    coche = Coche(
                        id_planta = rs.getInt("id_planta"),
                        nombreComun = rs.getString("nombre_comun"),
                        nombreCientifico = rs.getString("nombre_cientifico"),
                        stock = rs.getInt("stock"),
                        precio = rs.getDouble("precio")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return coche
    }

    fun insertarPlanta(planta: Coche) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO plantas(nombre_comun, nombre_cientifico, stock, precio) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, planta.nombreComun)
                pstmt.setString(2, planta.nombreCientifico)
                pstmt.setInt(3, planta.stock)
                pstmt.setDouble(4, planta.precio)
                pstmt.executeUpdate()
                println("Planta '${planta.nombreComun}' insertada con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarPlanta(planta: Coche) {
        if (planta.id_planta == null) {
            println("No se puede actualizar una planta sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE plantas SET nombre_comun = ?, nombre_cientifico = ?, stock = ?, precio = ? WHERE id_planta = ?"
            ).use { pstmt ->
                pstmt.setString(1, planta.nombreComun)
                pstmt.setString(2, planta.nombreCientifico)
                pstmt.setInt(3, planta.stock)
                pstmt.setDouble(4, planta.precio)
                pstmt.setInt(5, planta.id_planta)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Planta con id=${planta.id_planta} actualizada con éxito.")
                } else {
                    println("No se encontró ninguna planta con id=${planta.id_planta}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarPlanta(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM plantas WHERE id_planta = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Planta con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ninguna planta con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}