package com.lean.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import me.leantech.link.android.Lean;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class JavaFragment extends Fragment {

    public JavaFragment() {
    }

    public static JavaFragment newInstance() {
        JavaFragment fragment = new JavaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lean_examples, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Lean.UserPermissions> permissions = new ArrayList<>();
        permissions.add(Lean.UserPermissions.IDENTITY);
        permissions.add(Lean.UserPermissions.ACCOUNTS);
        permissions.add(Lean.UserPermissions.TRANSACTIONS);
        permissions.add(Lean.UserPermissions.BALANCE);
        // permissions.add(Lean.UserPermissions.PAYMENTS);

        /*
          Old initialization
          final Lean lean = new Lean.Builder()
                  .setAppToken("")
                  .setVersion("latest")
                  .setLanguage("ar")
                  .sandboxMode(true)
                  .setCountry(Lean.LeanCountry.SAUDI_ARABIA.getCountryCode())
                  .showLogs()
                  .build();
         */

        final Lean lean = new Lean.Builder()
                .setAppToken("YOUR_APP_TOKEN")
                .setLanguage(Lean.Language.ENGLISH.getLanguageCode())
                .setCountry(Lean.Country.UNITED_ARAB_EMIRATES.getCountryCode())
                .setVersion("latest")
                .setSandboxMode()
                .showLogs()
                .build();

        Lean.Customization linkCustomization = new Lean.Customization();
        linkCustomization.setButtonTextColor("green");
        linkCustomization.setThemeColor("red");
        linkCustomization.setButtonBorderRadius("0");
        linkCustomization.setOverlayColor("rgba(255, 0, 0, 0.5)");

        EditText customerIdInit = view.findViewById(R.id.customerIdInitEditText);
        EditText bankIdInit = view.findViewById(R.id.bankIdEditText);

        view.findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lean.link(requireActivity(),
                        customerIdInit.getText().toString(),
                        bankIdInit.getText().toString(),
                        permissions,
                        linkCustomization,
                        "",
                        "",
                        response -> {
                            Log.i("LEAN_SDK", "RESPONSE -> " + response.toString());
                            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });


        EditText connectCustomerId = view.findViewById(R.id.customerIdConnectEditText);
        EditText connectBankId = view.findViewById(R.id.bankIdConnectEditText);
        EditText connectPaymentDestinationId = view.findViewById(R.id.paymentDestinationConnectEditText);

        view.findViewById(R.id.connectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lean.connect(
                        requireActivity(),
                        connectCustomerId.getText().toString(),
                        connectBankId.getText().toString(),
                        connectPaymentDestinationId.getText().toString(),
                        permissions,
                        null,
                        "",
                        "",
                        "",
                        "",
                        response -> {
                            Log.i("LeanSDK", "Response received by TPP -> " + response);
                            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });

        EditText reconnectId = view.findViewById(R.id.reconnectIdEditText);

        Lean.Customization reconnectCustomization = new Lean.Customization();
        reconnectCustomization.setButtonTextColor("#000");

        view.findViewById(R.id.reconnectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LeanSDK", "Reconnect ========>");

                lean.reconnect(requireActivity(),
                        reconnectId.getText().toString(),
                        reconnectCustomization,
                        response -> {
                            Log.i("LeanSDK", "Response received by TPP -> " + response);
                            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });

        EditText customerIdCB = view.findViewById(R.id.customerIdCBEditText);
        EditText paymentSourceIdCB = view.findViewById(R.id.paymentSourceIdCBEditText);
        EditText paymentDestinationIdCB = view.findViewById(R.id.paymentDestinationIdCBEditText);

        Lean.Customization createPaymentSourceCustomization = new Lean.Customization();
        createPaymentSourceCustomization.setThemeColor("rgb(255, 0, 0)");

        view.findViewById(R.id.createBeneficiaryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lean.createBeneficiary(
                        requireActivity(),
                        customerIdCB.getText().toString(),
                        paymentSourceIdCB.getText().toString(),
                        paymentDestinationIdCB.getText().toString(),
                        createPaymentSourceCustomization,
                        "",
                        "",
                        response -> {
                            Log.i("LeanSDK", "Response received by TPP -> " + response);
                            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });

        EditText paymentIntentId = view.findViewById(R.id.paymentIntentIdEditText);
        EditText accountId = view.findViewById(R.id.accountIdEditText);
        SwitchCompat showBalanceSwitch = view.findViewById(R.id.showBalanceSwitch);

        Lean.Customization payCustomization = new Lean.Customization();
        payCustomization.setThemeColor("#000");

        view.findViewById(R.id.startPayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lean.pay(
                        requireActivity(),
                        paymentIntentId.getText().toString(),
                        accountId.getText().toString(),
                        showBalanceSwitch.isChecked(),
                        payCustomization,
                        "www.blongo.com",
                        "www.blongo.eu",
                        response -> {
                            Log.i("LeanSDK", "Response received by TPP -> " + response);
                            Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });

        //Paste text

        view.findViewById(R.id.pasteCustomerId1Button).setOnClickListener(v -> {
            pasteText(customerIdInit);
        });

        view.findViewById(R.id.pasteBankIdButton).setOnClickListener(v -> {
            pasteText(bankIdInit);
        });

        view.findViewById(R.id.pasteReconnectId1Button).setOnClickListener(v -> {
            pasteText(reconnectId);
        });

        view.findViewById(R.id.pasteCustomerIdCBButton).setOnClickListener(v -> {
            pasteText(customerIdCB);
        });

        view.findViewById(R.id.pastePaymentIdButton).setOnClickListener(v -> {
            pasteText(paymentIntentId);
        });

        view.findViewById(R.id.pasteAccountIdButton).setOnClickListener(v -> {
            pasteText(accountId);
        });

        view.findViewById(R.id.pastePaymentDestinationIdCBButton).setOnClickListener(v -> {
            pasteText(paymentDestinationIdCB);
        });

        view.findViewById(R.id.pasteCustomerIdCBButton).setOnClickListener(v -> {
            pasteText(customerIdCB);
        });

        view.findViewById(R.id.pastePaymentDestinationIdCBButton).setOnClickListener(v -> {
            pasteText(paymentDestinationIdCB);
        });

        view.findViewById(R.id.pastePaymentSourceIdCBButton).setOnClickListener(v -> {
            pasteText(paymentSourceIdCB);
        });

        view.findViewById(R.id.pastePaymentDestination2Button).setOnClickListener(v -> {
            pasteText(connectPaymentDestinationId);
        });

        view.findViewById(R.id.pasteBankId2Button).setOnClickListener(v -> {
            pasteText(connectBankId);
        });

        view.findViewById(R.id.pasteCustomerId2Button).setOnClickListener(v -> {
            pasteText(connectCustomerId);
        });
    }

    private void pasteText(EditText editText) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        String pasteData = "";

        // If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {
        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
            // since the clipboard has data but it is not plain text
        } else {
            //since the clipboard contains plain text.
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            // Gets the clipboard as text.
            pasteData = item.getText().toString();
            editText.setText(pasteData);
        }
    }
}