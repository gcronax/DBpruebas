import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.InputMismatchException

// Ruta al archivo de base de datos SQLite
const val URL_BD = "jdbc:sqlite:src/main/resources/cars.sqlite"
const val URL_BD_test = "jdbc:sqlite:src/main/resources/cars.sqlite"


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

//    val nombre: String = readLine().orEmpty()
//    val edad: Int = readLine()?.toIntOrNull() ?: 0
//    val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0

    var itera = true
    do {
        println()
        println("   Selecciona una opcion: ")
        println("1. Menu Coches")
        println("2. Menu Ruedas")
        println("3. Menu Repuestos")
        println("4. Transaccion crear coche")
        println("5. Salir")

        try {
            val select: Int = readLine()?.toIntOrNull() ?: 0
            when (select) {
                1 -> {
                    menuCoche()
                }
                2 -> {
                    menuRueda()

                }
                3 -> {
                    menuRepuesto()
                }
                4 -> {
                    println("Lista de ruedas:")
                    RuedasDAO.listarRuedas().forEach {
                        println(" - id -> [${it.id_rueda}] tipo:${it.tipo}, precio:${it.precio}, pulgadas:${it.pulgadas}, cantidad:${it.cantidad} ")
                    }
                    print("ID del tipo de rueda a usar: ")
                    val ruedaID: Int = readLine()?.toIntOrNull() ?: 0
                    print("Nombre del modelo para el nuevo coche: ")
                    val modelo: String = readLine().orEmpty()
                    print("Nombre de la marca para el nuevo coche: ")
                    val marca: String = readLine().orEmpty()
                    print("Consumo(Double) para el nuevo coche: ")
                    val consumo: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Potencia para el nuevo coche: ")
                    val hp: Int = readLine()?.toIntOrNull() ?: 0
                    CochesDAO.crearCoche(Coche(modelo = modelo, marca = marca, consumo = consumo, hp = hp ),ruedaID)

                }
                5 -> {
                    itera = false
                }

                else -> {
                    println("Opcion no valida. Por favor, selecciona una opcion del 1 al 5.")
                }
            }

        } catch (e: InputMismatchException) {
            println("Error: Debes introducir un numero valido.")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    } while (itera)



}
fun menuCoche() {

    var itera = true
    do {
        println()
        println("   Selecciona una opcion: ")
        println("1. Listar Coches")
        println("2. Consultar Coche por id")
        println("3. Insertar Coche por id")
        println("4. Actualizar Coche por id")
        println("5. Eliminar Coche por id")
        println("6. Salir")

        try {
            val select: Int = readLine()?.toIntOrNull() ?: 0
            when (select) {
                1 -> {
                    println("Lista de coches:")
                    CochesDAO.listarCoches().forEach {
                        println(" - id -> [${it.id_coche}] modelo:${it.modelo}, marca:${it.marca}, consuo:${it.consumo}, potencia:${it.hp} ")
                    }                }
                2 -> {
                    print("Dame el id: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    CochesDAO.consultarCochePorId(id)

                }
                3 -> {
                    print("Nombre del modelo para el nuevo coche: ")
                    val modelo: String = readLine().orEmpty()
                    print("Nombre de la marca para el nuevo coche: ")
                    val marca: String = readLine().orEmpty()
                    print("Consumo(Double) para el nuevo coche: ")
                    val consumo: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Potencia para el nuevo coche: ")
                    val hp: Int = readLine()?.toIntOrNull() ?: 0
                    CochesDAO.insertarCoche(Coche(modelo = modelo, marca = marca, consumo = consumo, hp = hp ))

                }
                4 -> {
                    print("Dame el id del Coche a actualizar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    print("Nombre del modelo a modificar: ")
                    val modelo: String = readLine().orEmpty()
                    print("Nombre de la marca a modificar: ")
                    val marca: String = readLine().orEmpty()
                    print("Consumo(Double) a modificar: ")
                    val consumo: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Potencia a modificar: ")
                    val hp: Int = readLine()?.toIntOrNull() ?: 0
                    CochesDAO.actualizarCoche(Coche(id,modelo = modelo, marca = marca, consumo = consumo, hp = hp ))


                }
                5 -> {
                    print("Dame el id del Coche a eliminar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    CochesDAO.eliminarCoche(id)

                }
                6 -> {
                    itera = false
                }

                else -> {
                    println("Opcion no valida. Por favor, selecciona una opcion del 1 al 5.")
                }
            }

        } catch (e: InputMismatchException) {
            println("Error: Debes introducir un numero valido.")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    } while (itera)




}

fun menuRueda() {

    var itera = true
    do {
        println()
        println("   Selecciona una opcion: ")
        println("1. Listar Ruedas")
        println("2. Consultar Rueda por id")
        println("3. Insertar Rueda por id")
        println("4. Actualizar Rueda por id")
        println("5. Eliminar Rueda por id")
        println("6. Salir")

        try {
            val select: Int = readLine()?.toIntOrNull() ?: 0
            when (select) {
                1 -> {
                    println("Lista de ruedas:")
                    RuedasDAO.listarRuedas().forEach {
                        println(" - id -> [${it.id_rueda}] tipo:${it.tipo}, precio:${it.precio}, pulgadas:${it.pulgadas}, cantidad:${it.cantidad} ")
                    }              }
                2 -> {
                    print("Dame el id: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    RuedasDAO.consultarRuedaPorId(id)

                }
                3 -> {
                    print("Nombre del tipo para la nueva rueda: ")
                    val tipo: String = readLine().orEmpty()
                    print("Precio(Double) para la nueva rueda: ")
                    val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Pulgadas(Double) para la nueva rueda: ")
                    val pulgadas: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Cantidad para la nueva rueda: ")
                    val cantidad: Int = readLine()?.toIntOrNull() ?: 0
                    RuedasDAO.insertarRueda(Rueda(tipo = tipo, precio = precio,
                        pulgadas = pulgadas, cantidad = cantidad ))

                }
                4 -> {
                    print("Dame el id de la rueda a actualizar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    print("tipo a modificar: ")
                    val tipo: String = readLine().orEmpty()
                    print("precio(Double) a modificar: ")
                    val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("pulgadas(Double) a modificar: ")
                    val pulgadas: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("cantidad a modificar: ")
                    val cantidad: Int = readLine()?.toIntOrNull() ?: 0
                    RuedasDAO.actualizarRueda(Rueda(id,tipo = tipo,
                        precio = precio, pulgadas = pulgadas, cantidad = cantidad ))


                }
                5 -> {
                    print("Dame el id de la rueda a eliminar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    RuedasDAO.eliminarRueda(id)

                }
                6 -> {
                    itera = false
                }

                else -> {
                    println("Opcion no valida. Por favor, selecciona una opcion del 1 al 5.")
                }
            }

        } catch (e: InputMismatchException) {
            println("Error: Debes introducir un numero valido.")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    } while (itera)


}
fun menuRepuesto() {

    var itera = true
    do {
        println()
        println("   Selecciona una opcion: ")
        println("1. Listar repuestos")
        println("2. Consultar repuesto por id")
        println("3. Insertar repuesto por id")
        println("4. Actualizar repuesto por id")
        println("5. Eliminar repuesto por id")
        println("6. Salir")

        try {
            val select: Int = readLine()?.toIntOrNull() ?: 0
            when (select) {
                1 -> {
                    println("Lista de repuestos:")
                    RepuestoDAO.listarRepuestos().forEach {
                        println(" - id -> [${it.id_repuesto}] localizacion:${it.localizacion}, precio:${it.precio}, cantidad:${it.cantidad}, nombre:${it.nombre} ")
                    }
                }
                2 -> {
                    print("Dame el id: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    val repuesto = RepuestoDAO.consultarRepuestoPorId(id)
                    println(" - id -> [${repuesto?.id_repuesto}] localizacion:${repuesto?.localizacion}, precio:${repuesto?.precio}, cantidad:${repuesto?.cantidad}, nombre:${repuesto?.nombre}")
                }
                3 -> {
                    print("localizacion del nuevo repuesto: ")
                    val localizacion: String = readLine().orEmpty()
                    print("Precio(Double) para la nuevo repuesto: ")
                    val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Cantidad para la nuevo repuesto: ")
                    val cantidad: Int = readLine()?.toIntOrNull() ?: 0
                    print("nombre del nuevo repuesto: ")
                    val nombre: String = readLine().orEmpty()
                    RepuestoDAO.insertarRepuesto(Repuesto(localizacion = localizacion,
                        precio = precio, cantidad = cantidad, nombre = nombre ))

                }
                4 -> {
                    print("Dame el id del repuesto a actualizar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    print("localizacion a modificar: ")
                    val localizacion: String = readLine().orEmpty()
                    print("Precio(Double) a modificar: ")
                    val precio: Double = readLine()?.toDoubleOrNull() ?: 0.0
                    print("Cantidad a modificar: ")
                    val cantidad: Int = readLine()?.toIntOrNull() ?: 0
                    print("nombre a modificar: ")
                    val nombre: String = readLine().orEmpty()



                    RepuestoDAO.actualizarRepuesto(Repuesto(id, localizacion = localizacion,
                        precio = precio, cantidad = cantidad ,nombre=nombre))


                }
                5 -> {
                    print("Dame el id del repuesto a eliminar: ")
                    val id: Int = readLine()?.toIntOrNull() ?: 0
                    RepuestoDAO.eliminarRepuesto(id)

                }
                6 -> {
                    itera = false
                }

                else -> {
                    println("Opcion no valida. Por favor, selecciona una opcion del 1 al 5.")
                }
            }

        } catch (e: InputMismatchException) {
            println("Error: Debes introducir un numero valido.")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    } while (itera)

}



