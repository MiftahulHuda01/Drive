package com.huda.drive.ui.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import com.huda.drive.R
import com.huda.drive.adapter.CategoryAdapter
import com.huda.drive.adapter.KendaraanAdapter
import com.huda.drive.databinding.BottomSheetDetailKendaraanBinding
import com.huda.drive.databinding.FragmentUserHomeBinding
import com.huda.drive.model.Category
import com.huda.drive.model.Kendaraan
import com.huda.drive.util.Utils

class UserHomeFragment : Fragment(), CategoryAdapter.OnItemClickListener,
    KendaraanAdapter.OnItemClickListener {

    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var kendaraanAdapter: KendaraanAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var dialog: BottomSheetDialog
    private lateinit var sheet: BottomSheetDetailKendaraanBinding
    private var kendaraanList = mutableListOf<Kendaraan>()
    private var filteredList = mutableListOf<Kendaraan>()

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        val categoryList = mutableListOf(
            Category("Honda", R.drawable.logo_honda),
            Category("Yamaha", R.drawable.logo_yamaha),
            Category("Suzuki", R.drawable.logo_suzuki),
            Category("Kawasaki", R.drawable.logo_kawasaki)
        )

        kendaraanList = mutableListOf(
            Kendaraan(
                0,
                "CRF150L",
                "img_crf150l",
                "Honda",
                325000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                1,
                "Aerox",
                "img_aerox",
                "Yamaha",
                175000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                2,
                "Nmax",
                "img_nmax",
                "Yamaha",
                350000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                3,
                "Yamaha R15",
                "img_r15",
                "Yamaha",
                335000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                4,
                "Scoopy",
                "img_scoopy",
                "Honda",
                255000,
                "-BBM Full Tangki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                5,
                "Vario",
                "img_vario",
                "Honda",
                175000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                6,
                "Ninja ZX6R",
                "img_ninja_zx6r",
                "Kawasaki",
                475000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                7,
                "Ninja 250R",
                "img_ninja250",
                "Kawasaki",
                315000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                8,
                "GSXR",
                "img_gsxr",
                "Suzuki",
                275000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                9,
                "Satria FU",
                "img_satria_fu",
                "Suzuki",
                175000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                10,
                "PCX",
                "img_pcx",
                "Honda",
                175000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            ),
            Kendaraan(
                11,
                "Sonic",
                "img_sonic",
                "Honda",
                175000,
                "-BBM Full Tanki; -2 Helm; -1 Jas Hujan"
            )
        )

        filteredList.addAll(kendaraanList)

        categoryAdapter = CategoryAdapter(requireContext(), categoryList)
        binding.rvCategory.adapter = categoryAdapter

        kendaraanAdapter = KendaraanAdapter(requireContext(), filteredList)
        binding.rvKendaraan.adapter = kendaraanAdapter

        categoryAdapter.setOnItemClickListener(this)
        kendaraanAdapter.setOnItemClickListener(this)

        binding.etSearch.addTextChangedListener { string ->
            filteredList.clear()
            filteredList.addAll(kendaraanList.filter {
                it.name!!.contains(
                    string!!, ignoreCase = true
                )
            })
            kendaraanAdapter.notifyDataSetChanged()
        }

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(category: Category) {
        filteredList.clear()
        filteredList.addAll(kendaraanList.filter {
            it.merk!!.contains(
                category.name, ignoreCase = true
            )
        })
        kendaraanAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(kendaraan: Kendaraan) {
        sheet = BottomSheetDetailKendaraanBinding.inflate(
            layoutInflater
        )
        val v = sheet.root

        dialog = BottomSheetDialog(requireActivity(), R.style.DialogStyle)
        dialog.setCancelable(true)
        dialog.setContentView(v)
        dialog.show()

        Glide.with(sheet.imgItem)
            .load(Utils.decodeStringtoInt(requireContext(), kendaraan.img!!))
            .into(sheet.imgItem)
        sheet.tvItem.text = kendaraan.name
        sheet.tvPrice.text = "${Utils.formatPrice(kendaraan.price)}/Hari"

        val filter1 = kendaraan.features?.replace("-", "• ")
        val filter2 = filter1?.replace("; ", "\n")?.trim()
        sheet.tvFeature.text = filter2

        sheet.btnOrder.setOnClickListener {
            dialog.dismiss()

            val bundle = bundleOf("kendaraan" to kendaraan)
            findNavController().navigate(R.id.userOrderFragment, bundle)
        }
    }
}