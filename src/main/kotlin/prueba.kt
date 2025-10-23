import java.sql.SQLException

data class Coche(
    val id_coche: Int? = null, // lo genera SQLite automáticamente
    val modelo: String,
    val marca: String,
    val consumo: Double,
    val hp: Int
)
data class Rueda(
    val id_rueda: Int? = null, // lo genera SQLite automáticamente
    val tipo: String,
    val precio: Double,
    val pulgadas: Double,
    var cantidad: Int
)
data class Repuesto(
    val id_repuesto: Int? = null, // lo genera SQLite automáticamente
    val localizacion: String,
    val precio: Double,
    var cantidad: Int,
    val nombre: String
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

    fun listarRuedas(): List<Rueda> {
        val lista = mutableListOf<Rueda>()
        getConnection()?.use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT * FROM ruedas")
                while (rs.next()) {
                    lista.add(
                        Rueda(
                            id_rueda = rs.getInt("id_rueda"),
                            tipo = rs.getString("tipo"),
                            precio  = rs.getDouble("precio"),
                            pulgadas  = rs.getDouble("pulgadas"),
                            cantidad = rs.getInt("cantidad")
                        )
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return lista
    }

    fun consultarRuedaPorId(id: Int): Rueda? {
        var rueda: Rueda? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM coches WHERE id_rueda = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    rueda = Rueda(
                        id_rueda = rs.getInt("id_rueda"),
                        tipo = rs.getString("tipo"),
                        precio  = rs.getDouble("precio"),
                        pulgadas  = rs.getDouble("pulgadas"),
                        cantidad = rs.getInt("cantidad")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return rueda
    }

    fun insertarRueda(rueda: Rueda) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO ruedas(tipo, precio, pulgadas, cantidad) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, rueda.tipo)
                pstmt.setDouble(2, rueda.precio)
                pstmt.setDouble(3, rueda.pulgadas)
                pstmt.setInt(4, rueda.cantidad)
                pstmt.executeUpdate()
                println("Rueda '${rueda.tipo}' insertada con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarRueda(rueda: Rueda) {
        if (rueda.id_rueda == null) {
            println("No se puede actualizar una rueda sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE ruedas SET tipo = ?, precio = ?, pulgadas = ?, cantidad = ? WHERE id_rueda = ?"
            ).use { pstmt ->
                pstmt.setString(1, rueda.tipo)
                pstmt.setDouble(2, rueda.precio)
                pstmt.setDouble(3, rueda.pulgadas)
                pstmt.setInt(4, rueda.cantidad)
                pstmt.setInt(5, rueda.id_rueda)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Rueda con id=${rueda.id_rueda} actualizado con éxito.")
                } else {
                    println("No se encontró ninguna rueda con id=${rueda.id_rueda}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarRueda(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM ruedas WHERE id_rueda = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("Rueda con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ninguna rueda con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}
object RepuestoDAO {

    fun listarRepuestos(): List<Repuesto> {
        val lista = mutableListOf<Repuesto>()
        getConnection()?.use { conn ->
            conn.createStatement().use { stmt ->
                val rs = stmt.executeQuery("SELECT * FROM coches")
                while (rs.next()) {
                    lista.add(
                        Repuesto(
                            id_repuesto = rs.getInt("id_coche"),
                            localizacion = rs.getString("localizacion"),
                            precio = rs.getDouble("precio"),
                            cantidad  = rs.getInt("cantidad"),
                            nombre = rs.getString("nombre")
                        )
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return lista
    }

    fun consultarRepuestoPorId(id: Int): Repuesto? {
        var repuesto: Repuesto? = null
        getConnection()?.use { conn ->
            conn.prepareStatement("SELECT * FROM repuestos WHERE id_repuesto = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val rs = pstmt.executeQuery()
                if (rs.next()) {
                    repuesto = Repuesto(
                        id_repuesto = rs.getInt("id_coche"),
                        localizacion = rs.getString("localizacion"),
                        precio = rs.getDouble("precio"),
                        cantidad  = rs.getInt("cantidad"),
                        nombre = rs.getString("nombre")
                    )
                }
            }
        } ?: println("No se pudo establecer la conexión.")
        return repuesto
    }

    fun insertarRepuesto(repuesto: Repuesto) {
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "INSERT INTO repuestos(localizacion, precio, cantidad, nombre) VALUES (?, ?, ?, ?)"
            ).use { pstmt ->
                pstmt.setString(1, repuesto.localizacion)
                pstmt.setDouble(2, repuesto.precio)
                pstmt.setInt(3, repuesto.cantidad)
                pstmt.setString(4, repuesto.nombre)
                pstmt.executeUpdate()
                println("Repuesto '${repuesto.nombre}' insertado con éxito.")
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun actualizarRepuesto(repuesto: Repuesto) {
        if (repuesto.id_repuesto == null) {
            println("No se puede actualizar una planta sin id.")
            return
        }
        getConnection()?.use { conn ->
            conn.prepareStatement(
                "UPDATE repuestos SET localizacion = ?, precio = ?, cantidad = ?, nombre = ? WHERE id_repuesto = ?"
            ).use { pstmt ->
                pstmt.setString(1, repuesto.localizacion)
                pstmt.setDouble(2, repuesto.precio)
                pstmt.setInt(3, repuesto.cantidad)
                pstmt.setString(4, repuesto.nombre)
                pstmt.setInt(5, repuesto.id_repuesto)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("repuesto con id=${repuesto.id_repuesto} actualizado con éxito.")
                } else {
                    println("No se encontró ningun repuesto con id=${repuesto.id_repuesto}.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }

    fun eliminarRepuesto(id: Int) {
        getConnection()?.use { conn ->
            conn.prepareStatement("DELETE FROM repuestos WHERE id_repuesto = ?").use { pstmt ->
                pstmt.setInt(1, id)
                val filas = pstmt.executeUpdate()
                if (filas > 0) {
                    println("repuesto con id=$id eliminada correctamente.")
                } else {
                    println("No se encontró ningun repuesto con id=$id.")
                }
            }
        } ?: println("No se pudo establecer la conexión.")
    }
}