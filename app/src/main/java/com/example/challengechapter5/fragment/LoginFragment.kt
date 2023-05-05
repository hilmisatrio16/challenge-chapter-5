package com.example.challengechapter5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengechapter5.R
import com.example.challengechapter5.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            loginAccount()
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun loginAccount() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val bundle = Bundle().apply {
                            putString(TAG_EMAIL, email)
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
                    }else{
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(context, R.string.input_cannot_empty, Toast.LENGTH_SHORT).show()
        }
    }


    companion object{
        const val TAG_EMAIL = "tagemail"
    }

}