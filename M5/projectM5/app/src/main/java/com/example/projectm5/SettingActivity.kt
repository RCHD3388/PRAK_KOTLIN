package com.example.projectm5

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.projectm5.databinding.ActivitySettingBinding
import java.text.NumberFormat
import java.util.Locale

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        setupState()

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnLogout.setOnClickListener {
            DataRepo.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun setupState(){
        binding.tvTotalAssets.text = numberFormatter(DataRepo.signedIn.pemasukan - DataRepo.signedIn.pengeluaran)

        binding.tvCashAmount.text = "Rp. ${numberFormatter(DataRepo.signedIn.accounts.find { it.name == "Cash" }!!.balance)}"
        binding.tvJpayAmount.text = "Rp. ${numberFormatter(DataRepo.signedIn.accounts.find { it.name == "JalanPay" }!!.balance)}"
        binding.tvSpayAmount.text = "Rp. ${numberFormatter(DataRepo.signedIn.accounts.find { it.name == "ShoopePay" }!!.balance)}"
        binding.tvHapepayAmount.text = "Rp. ${numberFormatter(DataRepo.signedIn.accounts.find { it.name == "HapePay" }!!.balance)}"
        binding.tvDebitAmount.text = "Rp. ${numberFormatter(DataRepo.signedIn.accounts.find { it.name == "Debit Card" }!!.balance)}"
    }

    fun numberFormatter(balance: Int): String{
        return NumberFormat
            .getNumberInstance(Locale("in", "ID"))
            .format(balance)
    }
}