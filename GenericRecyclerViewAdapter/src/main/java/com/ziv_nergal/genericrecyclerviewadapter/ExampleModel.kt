package com.ziv_nergal.genericrecyclerviewadapter

data class ExampleModel(
        val text: String
) : Model {
    interface Listener {
        fun onSomethingHappened(model: ExampleModel)
    }

    override fun getViewType(): Int = R.layout.item_example_text
    override fun isEqualTo(other: Model?): Boolean = (other as? ExampleModel)?.text == this.text
}