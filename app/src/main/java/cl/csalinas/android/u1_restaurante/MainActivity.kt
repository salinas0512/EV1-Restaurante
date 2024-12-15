package cl.csalinas.android.u1_restaurante

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.csalinas.android.u1_restaurante.modelo.CuentaTotal
import cl.csalinas.android.u1_restaurante.modelo.Platillo
import cl.csalinas.android.u1_restaurante.modelo.SubtotalMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var cuentaTotal: CuentaTotal
    private lateinit var textViewComida: TextView
    private lateinit var textViewTotal: TextView
    private lateinit var etPastel: EditText
    private lateinit var etCazuela: EditText
    private lateinit var switchPropina: Switch
    private lateinit var textViewPropina: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvSubtotalPastel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cuentaTotal = CuentaTotal()

        // Variables que referencian los componentes de mi archivo .xml
        etPastel = findViewById(R.id.etPastel)
        etCazuela = findViewById(R.id.etCazuela)
        switchPropina = findViewById(R.id.switchPropina)
        textViewComida = findViewById(R.id.tvValorComida)
        textViewTotal = findViewById(R.id.tvValorTotal)
        textViewPropina = findViewById(R.id.tvValorPropina)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)

        // Listener para cambios en el EditText (cantidad)
        etCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarTotal()  // Actualizar total cuando se cambia la cantidad de Cazuela
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarTotal()  // Actualizar total cuando se cambia la cantidad de Pastel de Choclo
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Listener para cambios en el Switch (propina)
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaTotal.aceptarPropina = isChecked
            actualizarTotal()  // Actualizar el total con o sin propina
        }
    }

    // Función para dinámicamente ir actualizando los valores
    private fun actualizarTotal() {
        // Limpiar los pedidos previos
        cuentaTotal.pedidos.clear()

        // Obtener las cantidades de los EditText, asegurándose de que sean enteros
        val cantidadCazuela = etCazuela.text.toString().toIntOrNull() ?: 0
        val cantidadPastel = etPastel.text.toString().toIntOrNull() ?: 0

        // Crear los objetos Platillo
        val platilloCazuela = Platillo("Cazuela", 10000)
        val platilloPastel = Platillo("Pastel de Choclo", 12000)

        // Crear los objetos SubtotalMesa con los valores ingresados
        val subtotalCazuela = SubtotalMesa(platilloCazuela, cantidadCazuela)
        val subtotalPastel = SubtotalMesa(platilloPastel, cantidadPastel)

        // Agregar los pedidos
        cuentaTotal.agregarPedido(subtotalCazuela)
        cuentaTotal.agregarPedido(subtotalPastel)

        // Calcular el total con propina
        val totalConPropina = cuentaTotal.calcularTotalConPropina()

        // Valor de la propina
        val propina = cuentaTotal.calcularPropina()

        // Mostrar el total en los TextViews
        val formatoChile = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        textViewComida.text = formatoChile.format(cuentaTotal.calcularTotalSinPropina())
        textViewTotal.text = formatoChile.format(totalConPropina)
        textViewPropina.text = formatoChile.format(propina)
        tvSubtotalCazuela.text = formatoChile.format(subtotalCazuela.calcularSubtotal())
        tvSubtotalPastel.text = formatoChile.format(subtotalPastel.calcularSubtotal())
    }
}