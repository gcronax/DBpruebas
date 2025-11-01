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
                        println(" - [${it.id_coche}] ${it.modelo}, ${it.marca}, ${it.consumo}, ${it.hp} ")
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
                    println("Lista de coches:")
                    CochesDAO.listarCoches().forEach {
                        println(" - [${it.id_coche}] ${it.modelo}, ${it.marca}, ${it.consumo}, ${it.hp} ")
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
fun menuRepuesto() {
    TODO("Not yet implemented")
}



