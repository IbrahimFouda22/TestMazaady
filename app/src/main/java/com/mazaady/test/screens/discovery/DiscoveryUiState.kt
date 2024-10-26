package com.mazaady.test.screens.discovery

import android.widget.ArrayAdapter
import com.mazaady.domain.entity.Cat
import com.mazaady.domain.entity.MainCat
import com.mazaady.domain.entity.Option
import com.mazaady.domain.entity.OptionChild
import com.mazaady.domain.entity.Property

data class DiscoveryUiState(
    val isLoading: Boolean = false,
    val changeProperties: Boolean = true,
    val error: String = "",
    val mainCats: List<MainCatUi> = emptyList(),
    val properties: List<PropertyUi> = emptyList(),
    val optionChild: List<OptionChildUi> = emptyList(),
    val visibilityFieldProcessType: Boolean = false,
)
data class PropertyItem(
    val id: Int,
    val linearId: Int,
    val autoCompleteTextView: ArrayAdapter<String>,
)
data class MainCatUi(
    val id: Int,
    val name: String,
    val slug: String,
    val cats: List<CatUi> = emptyList(),
)

data class CatUi(
    val id: Int,
    val name: String,
    val slug: String,
)
data class PropertyUi(
    val id: Int,
    val name: String,
    val slug: String,
    val options: List<OptionUi> = emptyList(),
)
data class OptionChildUi(
    val id: Int,
    val name: String,
    val slug: String,
    val options: List<OptionUi> = emptyList(),
)
data class OptionUi(
    val id: Int,
    val name: String,
    val slug: String,
)


fun MainCat.toUiState() = MainCatUi(
    id = id,
    name = name,
    slug = slug,
    cats = children.map { it.toUiState() }
)

fun Cat.toUiState() = CatUi(
    id = id,
    name = name,
    slug = slug,
)
fun Property.toUiState() = PropertyUi(
    id = id,
    name = name,
    slug = slug,
    options = options.map { it.toUiState() }
)
fun OptionChild.toUiState() = OptionChildUi(
    id = id,
    name = name,
    slug = slug,
    options = options.map { it.toUiState() }
)
fun Option.toUiState() = OptionUi(
    id = id,
    name = name,
    slug = slug,
)