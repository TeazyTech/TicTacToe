package com.lunaticapps.tictactoe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lunaticapps.tictactoe.databinding.FragmentMenuBinding

class MenuFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        val appPackageName = requireActivity().packageName
        binding.home.setOnClickListener { v: View ->
            val intent = Intent(context, ChooseGameActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        binding.gameRule.setOnClickListener { v: View ->
            val intent = Intent(context, RulesActivity::class.java)
            intent.putExtra("NAME", "rules")
            startActivity(intent)
        }
        binding.privacyPolicy.setOnClickListener { v: View ->
            val intent = Intent(context, RulesActivity::class.java)
            intent.putExtra("NAME", "policy")
            startActivity(intent)
        }
        binding.rateGame.setOnClickListener { v: View ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "https://play.google.com/store/apps/details?id=$appPackageName"
                    )
                )
            )
        }
        binding.shareGame.setOnClickListener { v: View ->
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this amazing game \nhttps://play.google.com/store/apps/details?id=$appPackageName"
            )
            intent.setType("text/plain")
            startActivity(Intent.createChooser(intent, "Share this Game"))
        }
        binding.moreApps.setOnClickListener { v: View ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=LunaticApps")
                )
            )
        }

        // Assuming you have a reference to the hosting activity
        when (activity) {
            is MainActivity -> {
                val activity = activity as MainActivity?

                // Set the initial state of the Switch based on MediaPlayer state
                binding.musicSwitch.isChecked = activity!!.isMediaPlayerPlaying()
                binding.musicSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) {
                        activity.startMediaPlayer()
                    } else {
                        activity.stopMediaPlayer()
                    }
                }
            }

            is MainActivity4x4 -> {
                val activity = activity as MainActivity4x4?

                // Set the initial state of the Switch based on MediaPlayer state
                binding.musicSwitch.isChecked = activity!!.isMediaPlayerPlaying()
                binding.musicSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) {
                        activity.startMediaPlayer()
                    } else {
                        activity.stopMediaPlayer()
                    }
                }
            }

            is MainActivity5x5 -> {
                val activity = activity as MainActivity5x5?

                // Set the initial state of the Switch based on MediaPlayer state
                binding.musicSwitch.isChecked = activity!!.isMediaPlayerPlaying()
                binding.musicSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) {
                        activity.startMediaPlayer()
                    } else {
                        activity.stopMediaPlayer()
                    }
                }
            }
        }
        return binding.root
    }
}