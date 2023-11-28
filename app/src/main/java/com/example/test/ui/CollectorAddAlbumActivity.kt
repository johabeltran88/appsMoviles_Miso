package com.example.test.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.validateSpinner
import com.example.test.databinding.ActivityCollectorAddAlbumBinding
import com.example.test.viewmodel.CollectorAddAlbumViewModel

class CollectorAddAlbumActivity : AppCompatActivity() {

    private var _binding: ActivityCollectorAddAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorAddAlbumViewModel
    private var isFirstSelectionGenre = true
    private var isFirstSelectionRecordLabel = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCollectorAddAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(resources.getColor(R.color.your_color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        viewModel = ViewModelProvider(
            this, CollectorAddAlbumViewModel.Factory(this.application)
        )[CollectorAddAlbumViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnCancel.setOnClickListener { finish() }

        viewModel.name.observe(this) {
            it.apply {
                viewModel.validateName()
            }
        }

        binding.name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateName()
        }

        viewModel.image.observe(this) {
            it.apply {
                viewModel.validateImage()
            }
        }

        binding.image.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateImage()
        }

        viewModel.genre.observe(this) {
            it.apply {
                viewModel.errorGenre.value = validateSpinner(viewModel.genre.value, "Género")
            }
        }

        binding.genre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFirstSelectionGenre) {
                    isFirstSelectionGenre = false
                    return
                }
                viewModel.genre.value = binding.genre.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.recordLabel.observe(this) {
            it.apply {
                viewModel.errorRecordLabel.value =
                    validateSpinner(viewModel.recordLabel.value, "Disquera")
            }
        }

        binding.recordLabel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFirstSelectionRecordLabel) {
                    isFirstSelectionRecordLabel = false
                    return
                }
                viewModel.recordLabel.value = binding.recordLabel.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        viewModel.releaseDate.observe(this) {
            it.apply {
                viewModel.validateReleaseDate()
            }
        }

        binding.releaseDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateReleaseDate()
        }

        viewModel.description.observe(this) {
            it.apply {
                viewModel.validateDescription()
            }
        }

        binding.description.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateDescription()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.addAlbum()
        }

        viewModel.errorName.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorImage.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorGenre.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorRecordLabel.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorReleaseDate.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorDescription.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.error.observe(this) {
            it.apply {
                if (it) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle("Notificación")
                    builder.setMessage("Ha ocurrido un error, intentelo de nuevo")
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                    Toast.makeText(
                        binding.root.context,
                        "Ha ocurrido un error, intentelo de nuevo mas tarde",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle("Notificación")
                    builder.setMessage("El album ha sido creado exitosamente")
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
                        dialog.dismiss()
                        val intent =
                            Intent(binding.root.context, CollectorAddAlbumActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

}
