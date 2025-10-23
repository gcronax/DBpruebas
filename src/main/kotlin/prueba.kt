import java.sql.SQLException

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
    val precio: Double,
    var cantidad: Int,
    val nombre: String
)
data class Rueda(
    val id_rueda: Int? = null, // lo genera SQLite automáticamente
    val tipo: String,
    val precio: Double,
    val pulgadas: Double,
    var cantidad: Int
)

object CochesDAO {

    fun crearCoche(id_coche: Int, id_repuesto: Int, cantidad: Int) {
        getConnection()?.use { conn ->
            try {
                conn.autoCommit = false  // Iniciar transacción manual

                // Restar stock a la planta
//                conn.prepareStatement("UPDATE plantas SET stock = stock - $cantidad WHERE id_planta = ?").use { stock ->
//                    stock.setInt(1, id_planta)
//                    stock.executeUpdate()
//                }
//
//                // Añadir línea en tabla jardines_plantas
//                conn.prepareStatement("INSERT INTO jardines_plantas(id_jardin, id_planta, cantidad) VALUES (?, ?, ?)").use { plantar ->
//                    plantar.setInt(1, id_jardin)
//                    plantar.setInt(2, id_planta)
//                    plantar.setInt(3, cantidad)
//                    plantar.executeUpdate()
//                }

                // Confirmar cambios
                conn.commit()
                println("Transacción realizada con éxito.")
            } catch (e: SQLException) {
                if (e.message?.contains("UNIQUE constraint failed") == true) {
                    println("Error: intento de insertar clave duplicada")
                    conn.rollback()
                    println("Transacción revertida.")
                } else {
                    throw e // otros errores, relanzamos
                }
            }
        }
    }


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

    fun consultarCochePorId(id: Int): Coche? {
        var coche: Coche? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    coche = Coche(
                        id_coche = rs.getInt("id_coche"),
                        modelo = rs.getString("modelo"),
                        marca = rs.getString("marca"),
                        consumo = rs.getDouble("consumo"),
                        hp = rs.getInt("hp")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return coche
    }

    fun insertarCoche(coche: Coche) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO coches(modelo, marca, consumo, hp) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.executeUpdate()
                println("Coche '${coche.modelo}' insertada con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarCoche(coche: Coche) {
        if (coche.id_coche == null) {
            println("No se puede actualizar una planta sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE coches SET modelo = ?, marca = ?, consumo = ?, hp = ? WHERE id_coche = ?"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.setInt(5, coche.id_coche)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=${coche.id_coche} actualizado con éxito.")
                } else {
                    println("No se encontró ningun coche con id=${coche.id_coche}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarCoche(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ningun coche con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}

object RuedasDAO {

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

    fun consultarCochePorId(id: Int): Coche? {
        var coche: Coche? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    coche = Coche(
                        id_coche = rs.getInt("id_coche"),
                        modelo = rs.getString("modelo"),
                        marca = rs.getString("marca"),
                        consumo = rs.getDouble("consumo"),
                        hp = rs.getInt("hp")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return coche
    }

    fun insertarCoche(coche: Coche) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO coches(modelo, marca, consumo, hp) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.executeUpdate()
                println("Coche '${coche.modelo}' insertada con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarCoche(coche: Coche) {
        if (coche.id_coche == null) {
            println("No se puede actualizar una planta sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE coches SET modelo = ?, marca = ?, consumo = ?, hp = ? WHERE id_coche = ?"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.setInt(5, coche.id_coche)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=${coche.id_coche} actualizado con éxito.")
                } else {
                    println("No se encontró ningun coche con id=${coche.id_coche}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarCoche(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ningun coche con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}
object RepuestoDAO {

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

    fun consultarCochePorId(id: Int): Coche? {
        var coche: Coche? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    coche = Coche(
                        id_coche = rs.getInt("id_coche"),
                        modelo = rs.getString("modelo"),
                        marca = rs.getString("marca"),
                        consumo = rs.getDouble("consumo"),
                        hp = rs.getInt("hp")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return coche
    }

    fun insertarCoche(coche: Coche) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO coches(modelo, marca, consumo, hp) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.executeUpdate()
                println("Coche '${coche.modelo}' insertada con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarCoche(coche: Coche) {
        if (coche.id_coche == null) {
            println("No se puede actualizar una planta sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE coches SET modelo = ?, marca = ?, consumo = ?, hp = ? WHERE id_coche = ?"
            ).use { pstmt ->
                pstmt.setString(1, coche.modelo)
                pstmt.setString(2, coche.marca)
                pstmt.setDouble(3, coche.consumo)
                pstmt.setInt(4, coche.hp)
                pstmt.setInt(5, coche.id_coche)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=${coche.id_coche} actualizado con éxito.")
                } else {
                    println("No se encontró ningun coche con id=${coche.id_coche}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarCoche(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM coches WHERE id_coche = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Coche con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ningun coche con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}