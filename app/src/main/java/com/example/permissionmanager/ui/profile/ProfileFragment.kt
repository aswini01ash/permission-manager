package com.example.permissionmanager.ui.profile

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.permissionmanager.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val achieveOptions = listOf(
            "Separate personal and business",
            "Career Growth",
            "Manage finances better"
        )

        val whoOptions = listOf(
            "Freelancer / Independent Contractor",
            "Full - Time",
            "Part - Time Employee"
        )

        val sectorOptions = listOf(
            "Real Estate (Agents, Property Management)",
            "IT & Software",
            "Healthcare"
        )

        val context = requireContext()
        binding.actAchieve.setAdapter(
            ArrayAdapter(context, R.layout.simple_dropdown_item_1line, achieveOptions)
        )
        binding.actWho.setAdapter(
            ArrayAdapter(context, R.layout.simple_dropdown_item_1line, whoOptions)
        )
        binding.actSector.setAdapter(
            ArrayAdapter(context, R.layout.simple_dropdown_item_1line, sectorOptions)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
