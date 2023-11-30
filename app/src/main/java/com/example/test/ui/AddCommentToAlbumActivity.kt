package com.example.test.ui

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
import com.example.test.common.validateEmail
import com.example.test.common.validateSpinner
import com.example.test.common.validateValue
import com.example.test.databinding.ActivityAddCommentToAlbumBinding
import com.example.test.viewmodel.AddCommentToAlbumViewModel

class AddCommentToAlbumActivity : AppCompatActivity() {

    private var _binding: ActivityAddCommentToAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddCommentToAlbumViewModel
    private var isFirstSelectionRating = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddCommentToAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, AddCommentToAlbumViewModel.Factory(this.application)
        )[AddCommentToAlbumViewModel::class.java]

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

        viewModel.email.observe(this) {
            it.apply {
                viewModel.errorEmail.postValue(
                    validateEmail(viewModel.email.value, binding.root.context)
                )
            }
        }

        binding.email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorEmail.postValue(
                    validateEmail(viewModel.email.value, binding.root.context)
                )
        }

        viewModel.telephone.observe(this) {
            it.apply {
                viewModel.errorTelephone.postValue(
                    validateValue(viewModel.telephone.value, 50, binding.root.context)
                )
            }
        }

        binding.telephone.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.errorTelephone.postValue(
                    validateValue(viewModel.telephone.value, 50, binding.root.context)
                )
        }

        viewModel.rating.observe(this) {
            it.apply {
                viewModel.errorRating.postValue(
                    validateSpinner(
                        viewModel.rating.value,
                        getString(R.string.puntuacion),
                        binding.root.context
                    )
                )

            }
        }

        binding.rating.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (isFirstSelectionRating) {
                    isFirstSelectionRating = false
                    return
                }
                viewModel.rating.value = binding.rating.selectedItemPosition.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
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
            viewModel.errorEmail.postValue(
                validateEmail(viewModel.email.value, binding.root.context)
            )
            viewModel.errorTelephone.postValue(
                validateValue(viewModel.telephone.value, 50, binding.root.context)
            )
            viewModel.errorRating.postValue(
                validateSpinner(
                    viewModel.rating.value,
                    getString(R.string.puntuacion),
                    binding.root.context
                )
            )
            viewModel.errorDescription.postValue(
                validateValue(viewModel.description.value, 500, binding.root.context)
            )
            viewModel.addComment()
        }

        viewModel.errorName.observe(this) {
            it.apply {
                viewModel.isValidName.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidName.postValue(false)
            }
        }

        viewModel.errorEmail.observe(this) {
            it.apply {
                viewModel.isValidEmail.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidEmail.postValue(false)
            }
        }

        viewModel.errorTelephone.observe(this) {
            it.apply {
                viewModel.isValidTelephone.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidTelephone.postValue(false)
            }
        }

        viewModel.errorRating.observe(this) {
            it.apply {
                viewModel.isValidRating.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidRating.postValue(false)
            }
        }

        viewModel.errorDescription.observe(this) {
            it.apply {
                viewModel.isValidDescription.postValue(true)
                if (!it.equals(""))
                    viewModel.isValidDescription.postValue(false)
            }
        }

        viewModel.albumId.value = this.intent.getIntExtra("albumId", 0)

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
                    builder.setMessage(R.string.addComment)
                    builder.setPositiveButton(R.string.aceptar) { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

}