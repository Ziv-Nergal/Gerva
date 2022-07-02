package com.ziv_nergal.gerva

import android.os.Bundle
import android.widget.Toast
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.gerva.base.BindingActivity
import com.ziv_nergal.gerva.databinding.ActivityMainBinding
import com.ziv_nergal.gerva.model.Button
import com.ziv_nergal.gerva.model.Card
import com.ziv_nergal.gerva.model.Image
import com.ziv_nergal.gerva.model.Text

class MainActivity : BindingActivity<ActivityMainBinding>(),
    Text.Listener, Button.Listener, Card.Listener {

    override val layoutResource: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGenericRecyclerViewAdapter()
    }

    private fun initGenericRecyclerViewAdapter() {
        GenericRecyclerViewAdapter(
            listOf(
                Image(R.mipmap.ic_launcher_round),
                Text(getString(R.string.text_description)),
                Button(
                    getString(R.string.app_name),
                    getString(R.string.button_subtitle),
                    R.mipmap.ic_launcher
                ),
                Card()
            ),
            this,
            ExampleViewHolderFactory()
        ) { model, viewHolder ->
            Toast.makeText(
                this,
                "onViewHolderClicked\n${model.toString()}\n" +
                        "index - ${viewHolder.adapterPosition}",
                Toast.LENGTH_SHORT
            ).show()
        }.also { binding.recyclerView.adapter = it }
    }

    override fun onTextViewClicked(model: Text) {
        Toast.makeText(
            this,
            "onSomethingHappened\n${model.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onButtonClicked(button: Button) {
        Toast.makeText(
            this,
            "onExampleButtonClicked\n${button.title}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCardFlipped() {
        Toast.makeText(
            this,
            "" +
                    "Card flipped!",
            Toast.LENGTH_SHORT
        ).show()
    }
}