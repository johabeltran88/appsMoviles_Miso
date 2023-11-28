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
import com.example.test.common.validateSpinner
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        upArrow?.setColorFilter(resources.getColor(R.color.your_color), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        viewModel = ViewModelProvider(
            this, AddCommentToAlbumViewModel.Factory(this.application)
        )[AddCommentToAlbumViewModel::class.java]

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

        viewModel.email.observe(this) {
            it.apply {
                viewModel.validateEmail()
            }
        }

        binding.email.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateEmail()
        }

        viewModel.telephone.observe(this) {
            it.apply {
                viewModel.validateTelephone()
            }
        }

        binding.telephone.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateTelephone()
        }

        viewModel.rating.observe(this) {
            it.apply {
                viewModel.errorRating.value = validateSpinner(viewModel.rating.value, "Puntuación")
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
                viewModel.validateDescription()
            }
        }

        binding.description.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateDescription()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.addComment()
        }

        viewModel.errorName.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorEmail.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorTelephone.observe(this) {
            it.apply {
                if (!it.equals(""))
                    viewModel.valid.value = false
            }
        }

        viewModel.errorRating.observe(this) {
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

        viewModel.albumId.value = this.intent.getIntExtra("albumId", 0)

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
                    builder.setMessage("El comentario ha sido agregado al albúm")
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
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