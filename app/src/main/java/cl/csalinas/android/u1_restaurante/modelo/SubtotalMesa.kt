package cl.csalinas.android.u1_restaurante.modelo

class SubtotalMesa (
    private val platillo: Platillo,
    var cantidad : Int = 0
)
{
    // Aquí calculo el subtotal tomando el precio del platillo por la cantidad ingresada por usuario
    fun calcularSubtotal (): Int {
        return platillo.precio * cantidad
    }
    // Función para obtener el nombre del platillo si quisiera usarlo en el mainActiviy
    fun getNombrePlatillo(): String {
        return platillo.nombre
    }
}