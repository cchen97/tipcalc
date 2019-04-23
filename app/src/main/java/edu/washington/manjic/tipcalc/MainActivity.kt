package edu.washington.manjic.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.view.View
import android.widget.*







class MainActivity : AppCompatActivity() {
    private var currAmountStr = ""
    private var currAmount = 0
    private var tipPercent = 15
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputAmount = findViewById<EditText>(R.id.amount)
        val tipButton = findViewById<Button>(R.id.button)

        tipButton.isEnabled = false

        inputAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString() != currAmountStr) {
                    inputAmount.removeTextChangedListener(this)
                    val cleanString = s.toString().replace("[$,.]".toRegex(), "");
                    currAmount = if (cleanString.toIntOrNull() == null) 0 else cleanString.toInt()
                    val formatted = "$%.2f".format(currAmount * 0.01)
                    currAmountStr = formatted

                    inputAmount.setText(formatted)
                    inputAmount.setSelection(formatted.length);
                    inputAmount.addTextChangedListener(this)
                    tipButton.isEnabled = currAmount > 0
                }

            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        tipButton.setOnClickListener{
            Toast.makeText(applicationContext, "$%.2f".format(currAmount * (tipPercent * 0.01) * 0.01), Toast.LENGTH_LONG).show()
        }

        val arraySpinner = arrayOf("10", "15", "18", "20")
        val s = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, arraySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        s.adapter = adapter

        s.onItemSelectedListener= object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val selection = parent.getItemAtPosition(pos)
                tipPercent = selection.toString().replace("%", "").toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        s.setSelection(1)
    }
}
