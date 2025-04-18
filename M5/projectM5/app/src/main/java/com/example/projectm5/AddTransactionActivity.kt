package com.example.projectm5

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.projectm5.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {
    private val vm:DataViewModel by viewModels()
    val categoryPemasukan = arrayOf("Allowance", "Salary", "Bonus", "Other")
    val categoryPengeluaran = arrayOf("Food and Beverage", "Transportation", "Shopping", "Cinema", "Health", "Other")

    lateinit var binding: ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_transaction);
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.vm = vm;
        binding.lifecycleOwner = this;

        val currentStateObserver = Observer<String>{
            setToggleSwitch(it)
        }
        vm.currentAddTransState.observe(this, currentStateObserver)

        binding.btnPengeluaran.setOnClickListener {
            vm.setCurrentAddTransState("pengeluaran")
        }
        binding.btnPemasukan.setOnClickListener {
            vm.setCurrentAddTransState("pemasukan")
        }
        binding.btnSubmit.setOnClickListener {
            var amount = binding.etAmount.text.toString().toInt()
            var category = binding.spCategory.selectedItem.toString()
            var account = binding.spAccounts.selectedItem.toString()
            var note = binding.etNote.text.toString()
        }
    }

    fun setToggleSwitch(mainSwitchState: String){
        var active_background = ColorStateList.valueOf(getColor(R.color.active_switch))
        var active_color = ColorStateList.valueOf(getColor(R.color.active_switch_text))
        var inactive_background = ColorStateList.valueOf(getColor(R.color.white))
        var inactive_color = ColorStateList.valueOf(getColor(R.color.black))

        if(mainSwitchState == "pemasukan"){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryPemasukan)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategory.adapter = adapter
        }else if(mainSwitchState == "pengeluaran"){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryPengeluaran)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategory.adapter = adapter
        }

        binding.btnPengeluaran.setTextColor(if(mainSwitchState == "pemasukan") { inactive_color } else { active_color })
        binding.btnPengeluaran.backgroundTintList = if(mainSwitchState == "pemasukan") { inactive_background } else { active_background }
        binding.btnPemasukan.setTextColor(if(mainSwitchState == "pemasukan") { active_color } else { inactive_color })
        binding.btnPemasukan.backgroundTintList = if(mainSwitchState == "pemasukan") { active_background } else { inactive_background }
    }
}