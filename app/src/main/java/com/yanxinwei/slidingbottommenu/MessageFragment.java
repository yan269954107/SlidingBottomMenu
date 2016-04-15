package com.yanxinwei.slidingbottommenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 */
public class MessageFragment extends Fragment {
    private static final String MESSAGE_INDEX = "message_index";

    private int mMessageIndex;

    public MessageFragment() {
    }

    /**
     * @param index Parameter 1.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(int index) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(MESSAGE_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessageIndex = getArguments().getInt(MESSAGE_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ((TextView)view.findViewById(R.id.txt_content)).setText(getResources().getString(
                R.string.message_content, mMessageIndex));
        return view;
    }
}
