package com.ziv_nergal.gerva

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
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

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        initGenericRecyclerViewAdapter()
    }

    private fun initGenericRecyclerViewAdapter() {
        GenericRecyclerViewAdapter(
            listOf(
                Image(R.drawable.puzzle),
                Text(getString(R.string.text_description)),
                Card(),
                Button(
                    getString(R.string.app_name),
                    getString(R.string.button_subtitle),
                    R.drawable.ic_baseline_flip_camera_android_24
                )
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
                Student("1", "Will", "Smith", Date(63, 2, 1)),
                Student("2", "Harry", "Potter", Date(72, 11, 3)),
                Student("3", "Dave", "Lawrence", Date(99, 8, 5))
            )
        )
    }
}