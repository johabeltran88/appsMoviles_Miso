package com.example.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.test.R
import com.example.test.common.validateDate
import com.example.test.common.validateImage
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityCollectorAddArtistBinding
import com.example.test.ui.adapters.AlbumAdapter
import com.example.test.viewmodel.CollectorAddArtistViewModel

class CollectorAddArtistActivity : AppCompatActivity(), OnItemClickListener {

    private var _binding: ActivityCollectorAddArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorAddArtistViewModel
    lateinit var albumAdapter: AlbumAdapter

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

        albumAdapter = AlbumAdapter(emptyList())
        val lisView: Spinner = findViewById(R.id.albumes)

        viewModel.listaAlbumes.observe(this) { albums ->
            val lista: ArrayList<String> = ArrayList()
            for (i in albums) {
                lista.add(i.id!!.toString() + "-" + i.name.toString())
            }
            var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_selectable_list_item,
                lista
            )
            lisView.adapter = arrayAdapter
        }

        binding.albumes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.albumPosition.value =
                    binding.albumes.selectedItemPosition.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnCancel.setOnClickListener { finish() }

        viewModel.name.observe(this) {
            it.apply {
                viewModel.errorName.postValue(
                    validateValue(viewModel.name.value, 50, binding.root.context)
                )
            }
        }

        binding.name.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorName.postValue(
                    validateValue(viewModel.name.value, 50, binding.root.context)
                )
        }

        viewModel.image.observe(this) {
            it.apply {
                viewModel.errorImage.postValue(
                    validateImage(viewModel.image.value, binding.root.context)
                )
            }
        }

        binding.image.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorImage.postValue(
                    validateImage(viewModel.image.value, binding.root.context)
                )
        }

        viewModel.birthDate.observe(this) {
            it.apply {
                viewModel.errorBirthDate.postValue(
                    validateDate(viewModel.birthDate.value, binding.root.context)
                )
            }
        }

        binding.birthDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorBirthDate.postValue(
                    validateDate(viewModel.birthDate.value, binding.root.context)
                )
        }

        viewModel.description.observe(this) {
            it.apply {
                viewModel.errorDescription.postValue(
                    validateValue(viewModel.description.value, 500, binding.root.context)
                )
            }
        }

        binding.description.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorDescription.postValue(
                    validateValue(viewModel.description.value, 500, binding.root.context)
                )
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.errorName.postValue(
                validateValue(
                    viewModel.name.value,
                    50,
                    binding.root.context
                )
            )
            viewModel.errorImage.postValue(
                validateImage(viewModel.image.value, binding.root.context)
            )
            viewModel.errorBirthDate.postValue(
                validateDate(viewModel.birthDate.value, binding.root.context)
            )
            viewModel.errorDescription.postValue(
                validateValue(viewModel.description.value, 500, binding.root.context)
            )
            viewModel.addArtist()
        }

        viewModel.errorName.observe(this) {
            it.apply {
                viewModel.isValidName.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidName.postValue(false)
            }
        }

        viewModel.errorImage.observe(this) {
            it.apply {
                viewModel.isValidImage.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidImage.postValue(false)
            }
        }

        viewModel.errorBirthDate.observe(this) {
            it.apply {
                viewModel.isValidBirthDate.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidBirthDate.postValue(false)
            }
        }

        viewModel.errorDescription.observe(this) {
            it.apply {
                viewModel.isValidDescription.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidDescription.postValue(false)
            }
        }

        viewModel.error.observe(this) {
            it.apply {
                if (it) {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.notificacion)
                    builder.setMessage(R.string.error)
                    builder.setPositiveButton(R.string.aceptar) { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                    Toast.makeText(
                        binding.root.context,
                        R.string.error,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setTitle(R.string.notificacion)
                    builder.setMessage(R.string.addArtist)
                    builder.setPositiveButton(R.string.aceptar) { dialog, _ ->
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
        viewModel.obtenerAlbumes()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val options: String = p0?.getItemAtPosition(p2) as String
        Toast.makeText(applicationContext, "Clicked by : $options", Toast.LENGTH_LONG).show()
    }
}