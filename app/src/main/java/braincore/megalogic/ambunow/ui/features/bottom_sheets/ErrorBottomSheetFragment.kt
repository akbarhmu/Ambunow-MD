package braincore.megalogic.ambunow.ui.features.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import braincore.megalogic.ambunow.databinding.FragmentErrorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentErrorBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: ErrorBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lottieAnimationView.apply {
                setAnimation(args.animation)
                scaleType = ImageView.ScaleType.FIT_CENTER
                playAnimation()
            }
            tvErrorDescription.text = args.message
            btnOk.setOnClickListener { dismiss() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}