package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class CreateVoucherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_voucher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }
//        view.findViewById<ImageView>(R.id.btnSave).setOnClickListener {
//            val pot = view.findViewById<EditText>(R.id.etPot).text.toString().toInt()
//            val min = view.findViewById<EditText>(R.id.etMin).text.toString().toInt()
//            val typeno = view.findViewById<RadioButton>(R.id.rbNom).isChecked
//            val typeper = view.findViewById<RadioButton>(R.id.rbPer).isChecked
//            val type = if(typeno) "nominal" else "persen"
//            if(pot >= 1 && min >= 1000 && (typeno || typeper)){
////                MockDB.loggedInUser!!.vouchers.add(Voucher(type, pot, min))
//                Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(context, "Data Tidak Valid", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}