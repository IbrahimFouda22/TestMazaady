package com.mazaady.test.screens.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mazaady.test.R
import com.mazaady.test.databinding.BottomSheetLayoutBinding
import com.mazaady.test.databinding.FragmentDiscoveryBinding
import com.mazaady.test.databinding.ItemBottomSheetLayoutBinding
import com.mazaady.test.databinding.ItemPropertyLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoveryFragment : Fragment() {

    private var mainCatIndex: Int = -1
    private var subCatIndex: Int = -1
    private var processTypeIndex: Int = -1
    private var ID: Int = -1
    private var propItem: PropertyItem? = null
    private var optionItem: PropertyUi? = null
    private lateinit var list: List<String>
    private val viewModel: DiscoveryViewModel by viewModels()
    private lateinit var binding: FragmentDiscoveryBinding
    private val propertyItems = mutableListOf<PropertyItem>()
    private val optionChildItems = mutableListOf<PropertyItem>()
    private val isLinearHasData = mutableMapOf<Int, Boolean>()
    private val tableMap = mutableMapOf<String, String>()
    private val mainCatMap = mutableMapOf<String, String>()
    private val subCatMap = mutableMapOf<String, String>()
    private val processTypeMap = mutableMapOf<String, String>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDiscoveryBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapterCategories = ArrayAdapter<String>(requireContext(), R.layout.item_menu_layout)
        val adapterProcessType = ArrayAdapter<String>(requireContext(), R.layout.item_menu_layout)

        binding.edtProcessType.setDropDownBackgroundResource(R.drawable.shape_box_menu)
        list = listOf("other", "unspecified", "Sell", "Rent", "Waiver")
        adapterProcessType.addAll(list)
        binding.edtProcessType.setAdapter(adapterProcessType)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                if (it.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    viewModel.resetError()
                }
                if (it.mainCats.isNotEmpty() && adapterCategories.isEmpty) {
                    adapterCategories.addAll(it.mainCats.map { data -> data.slug })
                    binding.edtMainCat.setAdapter(adapterCategories)
                }
                if (it.properties.isNotEmpty() && viewModel.state.value.changeProperties) {
                    viewModel.changeProperties(false)
                    it.properties.forEach { property ->
                        val itemLayout = ItemPropertyLayoutBinding.inflate(layoutInflater)
                        itemLayout.autoComplete.setDropDownBackgroundResource(R.drawable.shape_box_menu)
                        itemLayout.autoComplete.onItemClickListener = onClick
                        itemLayout.hint = property.slug
                        val adapter =
                            ArrayAdapter<String>(requireContext(), R.layout.item_menu_layout)
                        adapter.addAll(property.options.map { data -> data.slug })
                        itemLayout.autoComplete.setAdapter(adapter)
                        binding.propertyDynamicLinear.addView(itemLayout.root)
                        val v = View.generateViewId()
                        itemLayout.optionDynamicLinear.id = v
                        propertyItems.add(PropertyItem(property.id, v, adapter))
                    }
                }
                if (it.optionChild.isNotEmpty()) {
                    isLinearHasData[ID] = true
                    it.optionChild.forEach { optionChild ->
                        val itemLayout = ItemPropertyLayoutBinding.inflate(layoutInflater)
                        itemLayout.autoComplete.setDropDownBackgroundResource(R.drawable.shape_box_menu)
                        itemLayout.hint = optionChild.slug
                        val adapter =
                            ArrayAdapter<String>(requireContext(), R.layout.item_menu_layout)
                        optionChildItems.add(
                            PropertyItem(
                                optionChild.id,
                                View.generateViewId(),
                                adapter
                            )
                        )
                        adapter.addAll(optionChild.options.map { data -> data.slug })
                        itemLayout.autoComplete.setAdapter(adapter)
                        val view = requireActivity().findViewById<LinearLayout>(ID)
                        view.addView(itemLayout.root)
//                        binding.childDynamicLinear.addView(itemLayout.root)
                    }
                }
            }
        }

        binding.edtMainCat.setOnItemClickListener { _, _, index, _ ->
            if (mainCatIndex != index) {
                mainCatIndex = index
                binding.edtSubCat.text.clear()
                val adapterSubCategories =
                    ArrayAdapter<String>(requireContext(), R.layout.item_menu_layout)
                adapterSubCategories.addAll(
                    viewModel.state.value.mainCats[index].cats.map { data -> data.slug }
                )
                binding.edtSubCat.setAdapter(adapterSubCategories)
                viewModel.resetPropAndOptionChild()
                mainCatMap["Main Category"] = viewModel.state.value.mainCats[index].slug
                subCatMap.clear()
                tableMap.clear()
            }
        }

        binding.edtSubCat.setOnItemClickListener { _, _, index, _ ->
            if (subCatIndex != index) {
                subCatIndex = index
                viewModel.getProperties(viewModel.state.value.mainCats[mainCatIndex].cats[index].id)
                binding.propertyDynamicLinear.removeAllViews()
                subCatMap["Sub Category"] = viewModel.state.value.mainCats[mainCatIndex].cats[index].slug
                tableMap.clear()
            }
        }
        binding.edtProcessType.setOnItemClickListener { _, _, index, _ ->
            processTypeIndex = index
            viewModel.visibilityProcessTypeField(
                index == 0,
            )
            processTypeMap["Process Type"] = list[index]
        }

        binding.btnSubmit.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            val layout = BottomSheetLayoutBinding.inflate(layoutInflater)

            if(mainCatMap.isNotEmpty()) {
                val itemLayout = ItemBottomSheetLayoutBinding.inflate(layoutInflater)
                itemLayout.title = mainCatMap.keys.first()
                itemLayout.value = mainCatMap.values.first()
                layout.linearBottomSheet.addView(itemLayout.root)
            }
            if(subCatMap.isNotEmpty()) {
                val itemLayout = ItemBottomSheetLayoutBinding.inflate(layoutInflater)
                itemLayout.title = subCatMap.keys.first()
                itemLayout.value = subCatMap.values.first()
                layout.linearBottomSheet.addView(itemLayout.root)
            }
            if(processTypeMap.isNotEmpty()) {
                val itemLayout = ItemBottomSheetLayoutBinding.inflate(layoutInflater)
                itemLayout.title = processTypeMap.keys.first()
                itemLayout.value = processTypeMap.values.first()
                layout.linearBottomSheet.addView(itemLayout.root)
            }
            if(processTypeIndex > -1 && binding.edtProcessTypeField.text.toString().isNotEmpty()) {
                val itemLayout = ItemBottomSheetLayoutBinding.inflate(layoutInflater)
                itemLayout.title = "Specify here"
                itemLayout.value = binding.edtProcessTypeField.text.toString()
                layout.linearBottomSheet.addView(itemLayout.root)
            }
            if(tableMap.isNotEmpty()) {
                tableMap.forEach {item->
                    val itemLayout = ItemBottomSheetLayoutBinding.inflate(layoutInflater)
                    itemLayout.title = item.key
                    itemLayout.value = item.value
                    layout.linearBottomSheet.addView(itemLayout.root)
                }
            }
            bottomSheet.setContentView(layout.root)
            bottomSheet.show()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.edtMainCat.setDropDownBackgroundResource(R.drawable.shape_box_menu)
        binding.edtSubCat.setDropDownBackgroundResource(R.drawable.shape_box_menu)
    }

    private val onClick =
        AdapterView.OnItemClickListener { parent, _, position, _ ->
            val propertyId = propertyItems.find {
                it.autoCompleteTextView == parent.adapter
            }
            val optionId = viewModel.state.value.properties.find {
                it.id == propertyId?.id
            }
            optionId?.let {
                if (ID > -1 && isLinearHasData[propertyId?.linearId] == true) {
                    propertyId?.linearId?.let { it1 ->
                        requireActivity().findViewById<LinearLayout>(
                            it1
                        ).removeAllViews()
                        isLinearHasData[propertyId.linearId] = false
                    }
                }
                propItem = propertyId
                optionItem = optionId
                ID = propertyId?.linearId ?: 0
//                    binding.childDynamicLinear.removeAllViews()
                tableMap[optionId.slug] = optionId.options[position].slug
                viewModel.getOptionChild(optionId.options[position].id)
            }
        }
}
