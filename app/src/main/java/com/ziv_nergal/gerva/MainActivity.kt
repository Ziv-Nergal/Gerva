package com.ziv_nergal.gerva

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ziv_nergal.genericrecyclerviewadapter.GenericRecyclerViewAdapter
import com.ziv_nergal.gerva.databinding.ActivityMainBinding
import com.ziv_nergal.gerva.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

                VerticalSpacer(20),

                Text(getString(R.string.text_description)),

                VerticalSpacer(20),

                Card(R.drawable.ic_baseline_ads_click_24),

                VerticalSpacer(150),

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
        (recyclerView.layoutManager as? LinearLayoutManager)?.let { manager ->
            val first = manager.findFirstVisibleItemPosition()
            val second = manager.findLastVisibleItemPosition()

            var millis: Long = 0

            MainScope().launch {
                first.rangeTo(second).forEach {
                    manager.findViewByPosition(it)?.let { viewHolder ->
                        delay(millis)
                        millis += 30

                        viewHolder.animate().scaleX(1.05f).scaleY(1.05f).withEndAction {
                            viewHolder.animate().scaleY(1f).scaleX(1f)
                        }
                    }
                }
            }
        }
    }

    companion object {
        @BindingAdapter("bind:height")
        @JvmStatic
        fun setHeight(view: View, height: Int) {
            val params: ViewGroup.LayoutParams = view.layoutParams
            params.height = height
            view.layoutParams = params
        }
    }
}