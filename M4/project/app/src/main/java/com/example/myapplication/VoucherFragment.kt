package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class VoucherFragment : Fragment() {

    private lateinit var voucherAdapter: VoucherAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voucher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        voucherAdapter = VoucherAdapter(MockDB.loggedInUser!!.vouchers)
        //view.findViewById<RecyclerView>(R.id.rvVoucher).adapter = voucherAdapter;
        //view.findViewById<RecyclerView>(R.id.rvVoucher).layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            findNavController().navigate(R.id.createVoucherFragment)
        }
    }
}