package com.example.projectm5

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectm5.databinding.ActivityHomeBinding
import com.example.projectm5.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding;
    private var currentHideState = true;
    private lateinit var todayAdapter: DataAdapter
    private lateinit var yesterdayDataAdapter: DataAdapter
    var originalTodayTransaction = arrayListOf<Transaction>()
    var originalYesterdayTransaction = arrayListOf<Transaction>()
    var filteredTodayTransaction = arrayListOf<Transaction>()
    var filteredYesterdayTransaction = arrayListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setActivityState()

        binding.tvPemasukan.text = NumberFormat
            .getNumberInstance(Locale("in", "ID"))
            .format(DataRepo.signedIn.pemasukan)
        binding.tvPengeluaran.text = NumberFormat
            .getNumberInstance(Locale("in", "ID"))
            .format(DataRepo.signedIn.pengeluaran)

        binding.btnHide.setOnClickListener {
            currentHideState = !currentHideState
            toggleHide()
        }
        binding.btnAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
        binding.etSearch.doAfterTextChanged {
            updateRvData(it.toString())
        }
        binding.ivSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    fun updateRvData(keyword: String){
        val filteredTodayList = originalTodayTransaction.filter { transaction ->
            transaction.note.toLowerCase(Locale.getDefault()).contains(keyword.toLowerCase(Locale.getDefault()))
        } as ArrayList<Transaction>
        todayAdapter.listData = filteredTodayList // Update list data di adapter
        todayAdapter.notifyDataSetChanged()

        // Filter data untuk RecyclerView kemarin
        val filteredYesterdayList = originalYesterdayTransaction.filter { transaction ->
            transaction.note.toLowerCase(Locale.getDefault()).contains(keyword.toLowerCase(Locale.getDefault()))
        } as ArrayList<Transaction>
        yesterdayDataAdapter.listData = filteredYesterdayList // Update list data di adapter
        yesterdayDataAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        setActivityState()
    }

    fun setActivityState(){
        binding.tvName.text = DataRepo.signedIn.name
        binding.tvPemasukan.text = NumberFormat
            .getNumberInstance(Locale("in", "ID")).format(DataRepo.signedIn.pemasukan)
        binding.tvPengeluaran.text = NumberFormat
            .getNumberInstance(Locale("in", "ID")).format(DataRepo.signedIn.pengeluaran)
        toggleHide()

        // SETUP TODAY AND YESTERDAY DATA
        originalTodayTransaction = DataRepo.signedIn.transactions.filter {
            isToday(it.date)
        } as ArrayList<Transaction>
        originalYesterdayTransaction = DataRepo.signedIn.transactions.filter {
            isPreviousDayOrEarlier(it.date)
        } as ArrayList<Transaction>

        val todayAssets = calculateTotalAsset(originalTodayTransaction)
        val yesterdayAssets = calculateTotalAsset(originalYesterdayTransaction)

        val formatedTodayAsst = "" +
                "${if(todayAssets < 0) {"-"}else{"+"}} " +
                "Rp ${NumberFormat.getNumberInstance(Locale("in", "ID")).format(convPos(todayAssets))}"
        binding.tvTodayAssets.text = formatedTodayAsst
        val formatedYesterdayAsst = "" +
                "${if(yesterdayAssets < 0) {"-"}else{"+"}} " +
                "Rp ${NumberFormat.getNumberInstance(Locale("in", "ID")).format(convPos(yesterdayAssets))}"
        binding.tvYesterdayAssets.text = formatedYesterdayAsst

        // SETUP RV
        yesterdayDataAdapter = DataAdapter(originalYesterdayTransaction)
        binding.rvYesterday.adapter = yesterdayDataAdapter
        binding.rvYesterday.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        todayAdapter = DataAdapter(originalTodayTransaction)
        binding.rvToday.adapter = todayAdapter
        binding.rvToday.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    fun convPos(amount: Int): Int{
        if(amount < 0) { return amount * -1 }
        return amount
    }

    fun calculateTotalAsset(transactions: MutableList<Transaction>): Int {
        var total = 0
        for (transaction in transactions) {
            when (transaction.type) {
                "pemasukan" -> total += transaction.amount
                "pengeluaran" -> total -= transaction.amount
            }
        }
        return total
    }

    fun isToday(calendar: Calendar): Boolean {
        val today = Calendar.getInstance()
        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
    }

    fun isPreviousDayOrEarlier(calendar: Calendar): Boolean {
        val today = Calendar.getInstance()

        val calendarAtStartOfDay = calendar.clone() as Calendar
        calendarAtStartOfDay.set(Calendar.HOUR_OF_DAY, 0)
        calendarAtStartOfDay.set(Calendar.MINUTE, 0)
        calendarAtStartOfDay.set(Calendar.SECOND, 0)
        calendarAtStartOfDay.set(Calendar.MILLISECOND, 0)

        val todayAtStartOfDay = today.clone() as Calendar
        todayAtStartOfDay.set(Calendar.HOUR_OF_DAY, 0)
        todayAtStartOfDay.set(Calendar.MINUTE, 0)
        todayAtStartOfDay.set(Calendar.SECOND, 0)
        todayAtStartOfDay.set(Calendar.MILLISECOND, 0)

        return calendarAtStartOfDay.before(todayAtStartOfDay)
    }

    fun toggleHide(){
        if(currentHideState){
            binding.btnHide.setImageResource(R.drawable.hide)
            binding.tvBalance.text = "********"
        } else {
            binding.btnHide.setImageResource(R.drawable.view)
            binding.tvBalance.text = NumberFormat.getNumberInstance(Locale("in", "ID")).format(DataRepo.signedIn.pemasukan - DataRepo.signedIn.pengeluaran)
        }
    }
}