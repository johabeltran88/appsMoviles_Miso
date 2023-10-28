package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.ActivityCollectorAddArtistBinding
import com.example.test.viewmodel.CollectorAddArtistViewModel

class CollectorAddArtistActivity : AppCompatActivity() {

    private var _binding: ActivityCollectorAddArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorAddArtistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCollectorAddArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this, CollectorAddArtistViewModel.Factory(this.application)
        )[CollectorAddArtistViewModel::class.java]

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

        viewModel.description.observe(this) {
            it.apply {
                viewModel.validateDescription()
            }
        }

        binding.description.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateDescription()
        }

        viewModel.birthDate.observe(this) {
            it.apply {
                viewModel.validateBirthDate()
            }
        }

        binding.birthDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateBirthDate()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.addArtist()
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

        viewModel.errorBirthDate.observe(this) {
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
                    builder.setPositiveButton("Aceptar") { dialog, which ->
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
                    builder.setMessage("El artista ha sido creado exitosamente")
                    builder.setPositiveButton("Aceptar") { dialog, which ->
                        dialog.dismiss()
                        val intent =
                            Intent(binding.root.context, CollectorAddArtistActivity::class.java)
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