package com.example.lin.lin_homework_v01;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String TAG = "DetailFragment";
    private int cur_p;
    private View view;
    TextView title_TextView;
    TextView author_TextView;
    TextView time_TextView;
    TextView message_TextView;
    Button prev_Button;
    Button next_Button;
    Button add_Button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        Log.d(TAG, "newInstance starts...");
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance ends...");
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate starts...");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d(TAG, "onCreate getArguments: " + mParam1 + ", " + mParam2);
        }
        Log.d(TAG, "onCreate ends...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Get position from the list activity
        Log.d(TAG, "onCreateView starts...");

        view = inflater.inflate(R.layout.fragment_detail, container, false);
        title_TextView   = (TextView)view.findViewById(R.id.textView_Title_Frag);
        author_TextView  = (TextView)view.findViewById(R.id.textView_Author_Frag);
        time_TextView    = (TextView)view.findViewById(R.id.textView_Date_Frag);
        message_TextView = (TextView)view.findViewById(R.id.textView_Message_Frag);
        prev_Button      = (Button)view.findViewById(R.id.button_ShowPrev);
        next_Button      = (Button)view.findViewById(R.id.button_ShowNext);
        add_Button       = (Button)view.findViewById(R.id.button_Add_fd);
        setOnClickListenerForAll();
        cur_p = (int) getArguments().getInt("position_communication");
        Log.d(TAG, "receive: " + cur_p);
        if(cur_p < MainActivity.posts.size()) {
            update(cur_p);
        }else
        {
            Log.d(TAG, "post array is empty now");
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach starts...");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        Log.d(TAG, "onAttach ends...");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach starts...");
        super.onDetach();
        mListener = null;
        Log.d(TAG, "onDetach ends...");
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void update(int pos)
    {
        cur_p = pos;
        SetMessage(cur_p);
        Set_Button();

    }

    public void SetMessage(int position)
    {
          /*set the values to each item*/
        Post p = MainActivity.posts.get(position);
        Log.d(TAG, "[post] title: " + p.getTitle() + ", author: " + p.getAuthor() + ", time: " + p.getTimestamp() + ", message: " + p.getMessage());

        try {

            title_TextView.setText(p.getTitle());
            author_TextView.setText(p.getAuthor());

            time_TextView.setText(coverTimeString(p.getTimestamp()));
            message_TextView.setText(p.getMessage());

        }catch (Exception e)
        {
            Log.d(TAG, "get error when setText: " + e.getMessage());
        }
    }
    /*show time in friend way*/
    public static String coverTimeString(String time)
    {

        String result;
        long senonds = Integer.parseInt(time);
        Date date = new Date(senonds * 1000);
        result = date.toString();
        return result;

    }
    /*hide next button when reach last post, hide prev button when reach */
    public void Set_Button()
    {
        Log.d(TAG, "Set_Button starts...");
        int prev_id = cur_p - 1;
        Button m_button = (Button)view.findViewById(R.id.button_ShowPrev);
        if(prev_id < 0)
        {
            m_button.setEnabled(false);
        }else
        {
            m_button.setEnabled(true);
        }

        int Next_id = cur_p + 1;
        m_button = (Button)view.findViewById(R.id.button_ShowNext);
        if(Next_id > MainActivity.posts.size() - 1)
        {
            m_button.setEnabled(false);
        }else
        {
            m_button.setEnabled(true);
        }
        Log.d(TAG, "Set_Button ends...");
    }

    /*responding Prev button*/
    public void Show_Prev_Message()
    {
        Log.d(TAG, "Show_Prev_Message() starts...");
        cur_p = cur_p - 1;

        Log.d(TAG, "current id :" + cur_p + "/" + MainActivity.posts.size());

        SetMessage(cur_p);
        Set_Button();
        Log.d(TAG, "Show_Prev_Message() ends...");

    }
    /*responding Next button*/
    public void Show_Next_Message()
    {
        Log.d(TAG, "Show_Next_Message() starts...");
        cur_p = cur_p + 1;

        Log.d(TAG, "current id :" + cur_p+ "/" + MainActivity.posts.size());
        SetMessage(cur_p);
        Set_Button();
        Log.d(TAG, "Show_Next_Message() ends...");

    }
    /*responding add button*/
    public void Add_Message()
    {


        Log.d(TAG, "you click Add Message Button");
        if(MainActivity.isTablet)
        {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            AddFragment addFragment = new AddFragment();
            ft.replace(R.id.detailContainer,addFragment);
            ft.commit();

        }else{

            Intent intent = new Intent(getActivity(), AddMessageActivity.class);
            startActivity(intent);
        }

    }
    /*set listener for all buttons*/
    public void setOnClickListenerForAll()
    {
        prev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "show previous message");
                Show_Prev_Message();
            }
        });

        next_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "show next message");
                Show_Next_Message();
            }
        });

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "add message");
                Add_Message();
            }
        });

    }
}
