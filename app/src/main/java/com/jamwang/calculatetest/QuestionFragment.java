package com.jamwang.calculatetest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jamwang.calculatetest.databinding.FragmentQuestionBinding;
import com.jamwang.calculatetest.databinding.FragmentTitleBinding;

public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateVMFactory(requireActivity())).get(MyViewModel.class);
        myViewModel.generator();
        myViewModel.getCurrentScore().setValue(0);
        final FragmentQuestionBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
        final StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.button0:
                        builder.append("0");
                        break;
                    case R.id.button1:
                        builder.append("1");
                        break;
                    case R.id.button2:
                        builder.append("2");
                        break;
                    case R.id.button3:
                        builder.append("3");
                        break;
                    case R.id.button4:
                        builder.append("4");
                        break;
                    case R.id.button5:
                        builder.append("5");
                        break;
                    case R.id.button6:
                        builder.append("6");
                        break;
                    case R.id.button7:
                        builder.append("7");
                        break;
                    case R.id.button8:
                        builder.append("8");
                        break;
                    case R.id.button9:
                        builder.append("9");
                        break;
                    case R.id.buttonClear:
                        builder.setLength(0);
                        break;
                }
                if(builder.length() == 0){
                    binding.textView9.setText(getString(R.string.input_indicator));
                }else{
                    binding.textView9.setText(builder);
                }
            }
        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                if(builder.toString().equals("")){
                    Toast.makeText(getContext(), R.string.warning_message, Toast.LENGTH_SHORT).show();
                } else if ((Integer.parseInt(builder.toString().trim())) == myViewModel.getAnswer().getValue()) {
                    myViewModel.answerCorrect();
                    builder.setLength(0);
                    binding.textView9.setText(getResources().getString(R.string.answer_correct_message));
                    //builder.append(getString(R.string.answer_correct_message));
                } else {
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        myViewModel.win_flag = false;
                        myViewModel.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }
}
