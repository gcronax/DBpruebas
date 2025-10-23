import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

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

    // Listar todas las plantas
    println("Lista de plantas:")
    CochesDAO.listarCoches().forEach {
        println(" - [${it.id_coche}] ${it.modelo}, ${it.marca}, ${it.consumo}, ${it.hp} ")
    }

    CochesDAO.crearCoche(Coche(modelo = "modelo", marca = "marca", consumo = 8.8, hp = 3),2)

    // Consultar planta por ID
//    val planta = PlantasDAO.consultarPlantaPorId(3)
//    if (planta != null) {
//        println("Planta encontrada: [${planta.id_planta}] ${planta.nombreComun} (${planta.nombreCientifico}), stock ${planta.stock} unidades, precio: ${planta.precio} €")
//    } else {
//        println("No se encontró ninguna planta con ese ID.")
//    }
//
//
//    // Insertar plantas
//    PlantasDAO.insertarPlanta(
//        Planta(
//            nombreComun = "Palmera",
//            nombreCientifico = "Arecaceae",
//            stock = 2,
//            precio = 50.5
//        )
//    )
//
//    // Actualizar planta con id=1
//    PlantasDAO.actualizarPlanta(
//        Planta(
//            id_planta = 1,
//            nombreComun = "Aloe Arborescens",
//            nombreCientifico = "Aloe barbadensis miller",
//            stock = 20,
//            precio = 5.8
//        )
//    )
//
//    // Eliminar planta con id=2
//    PlantasDAO.eliminarPlanta(2)
}