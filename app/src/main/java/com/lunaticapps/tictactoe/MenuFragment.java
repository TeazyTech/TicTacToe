package com.lunaticapps.tictactoe;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lunaticapps.tictactoe.databinding.FragmentMenuBinding;


public class MenuFragment extends BottomSheetDialogFragment {

    FragmentMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        final String appPackageName = requireActivity().getPackageName();

        binding.home.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PlayerNameActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        binding.gameRule.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RulesActivity.class);
            intent.putExtra("NAME", "rules");
            startActivity(intent);
        });

        binding.privacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RulesActivity.class);
            intent.putExtra("NAME", "policy");
            startActivity(intent);
        });


        binding.rateGame.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName))));

        binding.shareGame.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing game \n" + "https://play.google.com/store/apps/details?id="+appPackageName);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share this Game"));
        });

        binding.moreApps.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=LunaticApps"))));

        // Assuming you have a reference to the hosting activity
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();

            // Set the initial state of the Switch based on MediaPlayer state
            binding.musicSwitch.setChecked(activity.isMediaPlayerPlaying());

            binding.musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    activity.startMediaPlayer();
                } else {
                    activity.stopMediaPlayer();
                }
            });
        }


        return binding.getRoot();
    }


}