package com.ziv_nergal.gerva

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.gerva.databinding.ActivityMainBinding
import com.ziv_nergal.gerva.model.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), Text.Listener, Button.Listener, Card.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(
                layoutInflater,
                null,
                false
            ).root
        )

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
        ).also { recyclerView.adapter = it }
    }

    override fun onTextViewClicked(model: Text) {
        Toast.makeText(
            this,
            "onTextViewClicked\n${model.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCardFlipped() {
        Toast.makeText(
            this,
            "Card flipped!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onButtonClicked(button: Button) {
        (recyclerView.adapter as? GenericRecyclerViewAdapter)?.updateData(
            arrayListOf(
                Student("1", "Ziv", "Nergal", Date(63, 2, 1)),
                Student("2", "Telem", "Tobi", Date(72, 11, 3)),
                Student("3", "Eyal", "Leshes", Date(99, 8, 5)),
                Student("4", "Shay", "Shimoni", Date(89, 5, 2)),
                Student("5", "Netanel", "Amar", Date(56, 3, 2)),
                Student("6", "Shoval", "Hazan", Date(85, 4, 2)),
                Student("7", "Tal", "Zion", Date(95, 5, 2)),
                Student("8", "Maayan", "Zuntz", Date(81, 5, 2))
            )
        )
    }
}