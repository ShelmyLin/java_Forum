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
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String TAG = "AddFragment";
    Button addButton;

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
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        addButton = (Button)view.findViewById(R.id.button_AddMessage);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Message();
            }
        });

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
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public boolean Add_Message()
    {
        Post p = new Post();

        EditText et = (EditText)getView().findViewById(R.id.editText_Author);
        String str = et.getText().toString();
        if(str.length() > 0 && str != " ")
        {
            p.setAuthor(str);
        }else
        {
            Log.d(TAG, "author is empty");
            Toast.makeText(getActivity(), "Please input author", Toast.LENGTH_SHORT).show();
            return false;
        }

        et = (EditText)getView().findViewById(R.id.editText_Title);
        str = et.getText().toString();
        if(str.length() > 0 && str != " ")
        {
            p.setTitle(str);
        }else
        {
            Log.d(TAG, "title is empty");
            Toast.makeText(getActivity(), "Please input title",Toast.LENGTH_SHORT).show();
            return false;
        }

        et = (EditText)getView().findViewById(R.id.editText_Message);
        str = et.getText().toString();
        if(str.length() > 0 && str != " ")
        {
            p.setMessage(str);
        }else
        {
            Log.d(TAG, "message is empty");
            Toast.makeText(getActivity(), "Please input message",Toast.LENGTH_SHORT).show();
            return false;
        }

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        p.setTimestamp(ts);

        //send the data to webservice
        new PostSender(p).execute();

        int id = MainActivity.posts.size();
        //p.setId(id);
        MainActivity.posts.add(p);
        MainActivity.Update = true;
        Log.d(TAG, "add message successfully! posts size: " +MainActivity.posts.size());
        Toast.makeText(getActivity(), "add message successfully",Toast.LENGTH_SHORT).show();

        if(!MainActivity.isTablet)
        {
            Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainActivity.SHOW_POST_MESSAGE, id);
            intent.putExtras(bundle);
            startActivity(intent);
        }else
        {
            /* start detail fragment, replace detail_Container with detailFragment */
            DetailFragment detailFrag = new DetailFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.detail_Container, detailFrag);

            /* transmit the position to detail fragment*/
            Bundle bundle = new Bundle();
            bundle.putInt("position_communication", id);
            detailFrag.setArguments(bundle);

            fragmentTransaction.commit();


        }


        return true;


    }



}
