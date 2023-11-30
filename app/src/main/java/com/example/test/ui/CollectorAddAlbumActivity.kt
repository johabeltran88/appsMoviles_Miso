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
import com.example.test.common.validateDate
import com.example.test.common.validateImage
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
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

        viewModel.releaseDate.observe(this) {
            it.apply {
                viewModel.errorReleaseDate.postValue(
                    validateDate(viewModel.releaseDate.value, binding.root.context)
                )
            }
        }

        binding.releaseDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorReleaseDate.postValue(
                    validateDate(viewModel.releaseDate.value, binding.root.context)
                )
        }

        viewModel.genre.observe(this) {
            it.apply {
                viewModel.errorGenre.postValue(
                    validateSpinner(
                        viewModel.genre.value,
                        getString(R.string.g_nero_),
                        binding.root.context
                    )
                )
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
                viewModel.errorRecordLabel.postValue(
                    validateSpinner(
                        viewModel.recordLabel.value,
                        getString(R.string.disquera_),
                        binding.root.context
                    )
                )
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
            viewModel.errorReleaseDate.postValue(
                validateDate(viewModel.releaseDate.value, binding.root.context)
            )
            viewModel.errorGenre.postValue(
                validateSpinner(
                    viewModel.genre.value,
                    getString(R.string.g_nero_),
                    binding.root.context
                )
            )
            viewModel.errorRecordLabel.postValue(
                validateSpinner(
                    viewModel.recordLabel.value,
                    getString(R.string.disquera_),
                    binding.root.context
                )
            )
            viewModel.errorDescription.postValue(
                validateValue(viewModel.description.value, 500, binding.root.context)
            )
            viewModel.addAlbum()
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

        viewModel.errorGenre.observe(this) {
            it.apply {
                viewModel.isValidGenre.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidGenre.postValue(false)
            }
        }

        viewModel.errorRecordLabel.observe(this) {
            it.apply {
                viewModel.isValidRecordLabel.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidRecordLabel.postValue(false)
            }
        }

        viewModel.errorReleaseDate.observe(this) {
            it.apply {
                viewModel.isValidReleaseDate.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidReleaseDate.postValue(false)
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
                    builder.setMessage(R.string.addAlbum)
                    builder.setPositiveButton(R.string.aceptar) { dialog, _ ->
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
