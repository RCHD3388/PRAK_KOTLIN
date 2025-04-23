package com.example.projectm5

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.projectm5.databinding.ActivityAddTransactionBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionActivity : AppCompatActivity() {
    private val vm:DataViewModel by viewModels()
    val categoryPemasukan = arrayOf("Allowance", "Salary", "Bonus", "Other")
    val categoryPengeluaran = arrayOf("Food and Beverage", "Transportation", "Shopping", "Cinema", "Health", "Other")
    private var selectedCalendar: Calendar? = null

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

        vm.setTotalHarga(NumberFormat.getNumberInstance(Locale("in", "ID")).format(DataRepo.signedIn.pemasukan))
        val currentStateObserver = Observer<String>{
            setToggleSwitch(it)
        }
        vm.currentAddTransState.observe(this, currentStateObserver)

        binding.btnPengeluaran.setOnClickListener {
            binding.etAmount.setText("")
            vm.setCurrentAddTransState("pengeluaran")
        }
        binding.btnPemasukan.setOnClickListener {
            binding.etAmount.setText("")
            vm.setCurrentAddTransState("pemasukan")
        }
        binding.btnDatetime.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etAmount.doAfterTextChanged {
            var amount = it.toString()
            if(amount.isNotEmpty()){
                if(vm.currentAddTransState.value == "pemasukan") {
                    vm.setTotalHarga(
                        NumberFormat.getNumberInstance(Locale("in", "ID"))
                            .format(DataRepo.signedIn.pemasukan + amount.toInt())
                    )
                }else{
                    vm.setTotalHarga(
                        NumberFormat.getNumberInstance(Locale("in", "ID"))
                            .format(DataRepo.signedIn.pengeluaran + amount.toInt())
                    )
                }
            }
        }
        binding.btnSubmit.setOnClickListener {
            var amount = binding.etAmount.text.toString()
            var category = binding.spCategory.selectedItem.toString()
            var account = binding.spAccounts.selectedItem.toString()
            var note = binding.etNote.text.toString()

            if(amount.toString().isNotEmpty() &&
                category.isNotEmpty() &&
                account.isNotEmpty() &&
                note.isNotEmpty() &&
                selectedCalendar != null){
                if(amount.toInt() > 0){
                    // convert to realaccount name
                    if(account == "Card") {account = "Debit Card"}
                    if(vm.currentAddTransState.value == "pemasukan"){
                        // add to transaction
                        DataRepo.signedIn.transactions.add(
                            Transaction("pemasukan",
                            amount.toInt(), category, account, note, selectedCalendar!!))
                        // add to account
                        DataRepo.signedIn.accounts.find { it.name == account }!!.balance += amount.toInt()
                        DataRepo.signedIn.pemasukan += amount.toInt()
                        // BERHASIL
                        binding.etAmount.setText("")
                        Toast.makeText(this, "pemasukan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    }else if(vm.currentAddTransState.value == "pengeluaran"){
                        if(DataRepo.signedIn.accounts.find { it.name == account }!!.balance < amount.toInt()){
                            Toast.makeText(this, "Saldo tidak mencukupi pada account yang dipilih", Toast.LENGTH_SHORT).show()
                        }else{
                            // add to transaction
                            DataRepo.signedIn.transactions.add(Transaction("pengeluaran",
                                amount.toInt(), category, account, note, selectedCalendar!!))
                            // add to account
                            DataRepo.signedIn.accounts.find { it.name == account }!!.balance -= amount.toInt()
                            DataRepo.signedIn.pengeluaran += amount.toInt()
                            // BERHASIL
                            binding.etAmount.setText("")
                            Toast.makeText(this, "pengeluaran berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Jumlah transaksi harus lebih dari 0", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
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
            binding.btnSubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_bold))
        }else if(mainSwitchState == "pengeluaran"){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryPengeluaran)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategory.adapter = adapter
            binding.btnSubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.danger))
        }

        vm.setTotalHarga(
            NumberFormat.getNumberInstance(Locale("in", "ID")).format(
                if(mainSwitchState == "pemasukan") {
                    DataRepo.signedIn.pemasukan
                } else {
                    DataRepo.signedIn.pengeluaran
                }
            )
        )

        binding.btnPengeluaran.setTextColor(if(mainSwitchState == "pemasukan") { inactive_color } else { active_color })
        binding.btnPengeluaran.backgroundTintList = if(mainSwitchState == "pemasukan") { inactive_background } else { active_background }
        binding.btnPemasukan.setTextColor(if(mainSwitchState == "pemasukan") { active_color } else { inactive_color })
        binding.btnPemasukan.backgroundTintList = if(mainSwitchState == "pemasukan") { active_background } else { inactive_background }
    }
    private fun showDatePickerDialog() {
        var tempCalendar: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                tempCalendar.set(Calendar.YEAR, year)
                tempCalendar.set(Calendar.MONTH, monthOfYear)
                tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePickerDialog(tempCalendar)
            },
            tempCalendar.get(Calendar.YEAR),
            tempCalendar.get(Calendar.MONTH),
            tempCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    private fun showTimePickerDialog(tempCalendar: Calendar) {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                tempCalendar.set(Calendar.MINUTE, minute)
                updateDateTimeButtonText(tempCalendar)
            },
            tempCalendar.get(Calendar.HOUR_OF_DAY),
            tempCalendar.get(Calendar.MINUTE),
            false // 24 hour view
        )
        timePickerDialog.show()
    }

    private fun updateDateTimeButtonText(tempCalendar: Calendar) {
        selectedCalendar = tempCalendar
        val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        binding.btnDatetime.text = sdf.format(selectedCalendar!!.time)
    }
}