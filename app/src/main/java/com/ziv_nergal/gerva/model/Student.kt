package com.ziv_nergal.gerva.model

import com.ziv_nergal.genericrecyclerviewadapter.Model
import com.ziv_nergal.gerva.R
import java.util.*

data class Student(override val id: String, val firstName: String, val lastName: String, val dateOfBirth: Date): Model {
    override fun getViewType(): Int = R.layout.item_student
}