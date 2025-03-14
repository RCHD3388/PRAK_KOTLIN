package com.example.m2_prak_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.m2_prak_project.databinding.ActivityGameBinding
import java.util.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding;
    var score: Int = 0;
    var live: Int = 5;
    var currentHidden: String = "";
    var currentAns: String = "";
    val textList = mutableListOf<String>(
        "act", "dog",
//        "ear", "top", "bat", "can", "fit", "log", "sun", "red",
//        "stop", "bake", "mile", "rest", "dust", "grip", "torn", "hand", "clap", "bend",
//        "trade", "stone", "grape", "vase", "pride", "flame", "drive", "dance", "chase", "price",
//        "stripe", "dancer", "planet", "garden", "stream", "branch", "gravel", "market", "ransom"
    )
    private var fieldList: ArrayList<Button> = arrayListOf<Button>();
    private var heartList: ArrayList<ImageView> = arrayListOf<ImageView>();

    fun initNewBindState(){
        currentHidden = textList.random()
        currentAns = ""
        var length = currentHidden.length
        for ((index, fieldComp) in fieldList.withIndex()) {
            fieldComp.text = ""
            if(index < length){
                fieldComp.visibility = View.VISIBLE
            }else{
                fieldComp.visibility = View.GONE
            }
        }
    }

    fun setFieldValue(){
        var length = currentAns.length;
        for ((index, fieldComp) in fieldList.withIndex()) {
            if(index < length){
                fieldComp.setText(currentAns[index].toString())
            }else{
                fieldComp.setText("")
            }
        }
    }

    fun handleSuccess(){
        score += currentHidden.length;
        binding.tvPoint.setText("${score} Points")
    }

    fun handleFailed(){
        live -= 1;
        for((index, heartComp) in heartList.withIndex()){
            if(index < live){
                heartComp.setImageResource(R.drawable.heart)
            }else{
                heartComp.setImageResource(R.drawable.emptyheart)
            }
        }
        currentAns = "";
        setFieldValue()

        // check gameover
        if(live == 0){
            var endActivity = Intent(this, EndActivity::class.java)
            endActivity.putExtra("SCORE", score);
            startActivity(endActivity)
            finish()
        }
    }

    fun handleCheck(){
        if(currentAns.length == currentHidden.length){
            if(currentAns.equals(currentHidden, ignoreCase = true)){
                handleSuccess()
                initNewBindState()
            }else{
                handleFailed()
            }
        }
    }

    fun handleClickCharKey(character: String){
        currentAns += character
        setFieldValue()
        handleCheck();
    }
    fun handleClickBackKey(){
        currentAns = if (currentAns.isNotEmpty()) currentAns.removeRange(currentAns.length - 1, currentAns.length) else currentAns
        setFieldValue()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fieldList.addAll(arrayListOf<Button>(
            binding.field1, binding.field2, binding.field3,
            binding.field4, binding.field5, binding.field6,
        ))
        heartList.addAll(arrayListOf<ImageView>(
            binding.ivLive1, binding.ivLive2, binding.ivLive3,
            binding.ivLive4, binding.ivLive5
        ))
        initNewBindState()

        binding.btnCheat.setOnClickListener {
            currentAns = currentHidden
            handleCheck()
        }

        val buttons = listOf(
            binding.btnA, binding.btnB, binding.btnC, binding.btnD, binding.btnE,
            binding.btnF, binding.btnG, binding.btnH, binding.btnI, binding.btnJ,
            binding.btnK, binding.btnL, binding.btnM, binding.btnN, binding.btnO,
            binding.btnP, binding.btnQ, binding.btnR, binding.btnS, binding.btnT,
            binding.btnU, binding.btnV, binding.btnW, binding.btnX, binding.btnY,
            binding.btnZ
        )

        val chars = ('A'..'Z').toList()

        buttons.zip(chars).forEach { (button, char) ->
            button.setOnClickListener { handleClickCharKey(char.toString()) }
        }

        binding.btnDel.setOnClickListener { handleClickBackKey() }
    }
}