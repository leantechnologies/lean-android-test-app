package com.lean.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import me.leantech.link.android.Lean

class KotlinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_lean_examples, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        /*
            // Old initialization
            val lean = Lean.Builder()
                .setLanguage("ar")
                .setAppToken("")
                .setVersion("latest")
                .sandboxMode(true)
                .setCountry("sa")
                .showLogs()
                .build()
         */


        val lean: Lean = Lean.Builder()
            .setAppToken("YOUR_APP_TOKEN")
            .setLanguage(Lean.Language.ENGLISH.languageCode)
            .setCountry(Lean.Country.UNITED_ARAB_EMIRATES.countryCode)
            .setVersion("latest")
            .setSandboxMode()
            .showLogs()
            .setEnv("development")
            .build()

        val linkCustomization = Lean.Customization()
        linkCustomization.buttonTextColor = "green"
        linkCustomization.themeColor = "red"
        linkCustomization.buttonBorderRadius = "0"
        linkCustomization.overlayColor = "pink"

        val permissions: ArrayList<Lean.UserPermissions> = ArrayList()
        permissions.add(Lean.UserPermissions.IDENTITY)
        permissions.add(Lean.UserPermissions.ACCOUNTS)
        permissions.add(Lean.UserPermissions.TRANSACTIONS)
        permissions.add(Lean.UserPermissions.BALANCE)
        permissions.add(Lean.UserPermissions.PAYMENTS)

        view.findViewById<View>(R.id.connectButton).setOnClickListener {
            lean.connect(
                requireActivity(),
                "",
                "",
                "",
                permissions,
                linkCustomization,
                null,
                null,
                null,
                null,
                leanListener = object : Lean.Listener {
                    override fun onResponse(response: Lean.Response) {
                        Log.i("LeanSDK", "Response received by TPP: $response")
                        Toast.makeText(
                            requireContext(),
                            "Response - $response",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }

        view.findViewById<View>(R.id.reconnectButton).setOnClickListener {
            lean.reconnect(
                requireActivity(),
                "",
                linkCustomization,
                leanListener = object : Lean.Listener {
                    override fun onResponse(response: Lean.Response) {
                        Log.i("LeanSDK", "Response received by TPP: $response")
                        Toast.makeText(
                            requireContext(),
                            "Response - $response",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }

        view.findViewById<View>(R.id.createBeneficiaryButton).setOnClickListener {
            lean.createBeneficiary(requireActivity(),
                "",
                "",
                "",
                linkCustomization,
                null,
                null,
                leanListener = object : Lean.Listener {
                    override fun onResponse(response: Lean.Response) {
                        Log.i("LeanSDK", "Response received by TPP: $response")
                        Toast.makeText(
                            requireContext(),
                            "Response - $response",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        view.findViewById<View>(R.id.startPayButton).setOnClickListener {
            lean.pay(requireActivity(),
                "",
                "",
                true,
                linkCustomization,
                null,
                null,
                leanListener = object : Lean.Listener {
                    override fun onResponse(response: Lean.Response) {
                        Log.i("LeanSDK", "Response received by TPP: $response")
                        Toast.makeText(
                            requireContext(),
                            "Response - $response",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

    }

    companion object {
        fun newInstance(): KotlinFragment {
            val fragment = KotlinFragment()
            val args = Bundle()
            return fragment
        }
    }
}