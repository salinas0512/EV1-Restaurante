package cl.csalinas.android.u1_restaurante.modelo

class CuentaTotal (
    val pedidos: MutableList<SubtotalMesa> = mutableListOf(),
    var aceptarPropina: Boolean = true
) {
    // Calcula el total sin propina (sumando los subtotales de todos los pedidos)
    fun calcularTotalSinPropina(): Int {
        var total = 0
        for (pedido in pedidos) {
            total += pedido.calcularSubtotal()
        }
        return total
    }

    // Calcula el monto de la propina (10% del total si está activada)
    fun calcularPropina(): Int {
        val totalSinPropina = calcularTotalSinPropina()
        return if (aceptarPropina) {
            (totalSinPropina * 0.10).toInt()
        } else {
            0
        }
    }

    // Calcula el total final (total sin propina + propina)
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    // Agrega un pedido a la lista de pedidos
    fun agregarPedido(pedido: SubtotalMesa) {
        pedidos.add(pedido)
    }
}