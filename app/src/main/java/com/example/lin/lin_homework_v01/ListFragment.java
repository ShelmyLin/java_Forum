package com.example.lin.lin_homework_v01;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static final String TAG = "ListFragment";
    public ArrayList<Post> posts = new ArrayList<Post>();
    public static boolean Update = false;
    private ListView list_view;
    private ArrayAdapter<String> adapter;
    ArrayList<String> authors = new ArrayList<String>();
    View view = null;
    Button add_Button;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
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
        view = inflater.inflate(R.layout.fragment_list, container, false);

        add_Button = (Button)view.findViewById(R.id.button_Add_fl);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "add message");
                Add_Message();
            }
        });


        return view;
//        return inflater.inflate(R.layout.fragment_list, container, false);
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
        public void OnItemClick(int pos);
    }



    public void update(ArrayList<Post> m_posts)
    {
        Log.d(TAG, "List Fragment update begins...");
        Log.d(TAG, "receive " + m_posts.size() + " posts");
        posts = m_posts;

        /*add the authors to each ListView item using Adapter*/
        Set_Posts_To_List_View();

        /*listen to user's command and respond*/
        list_view.setOnItemClickListener(new ItemClickEvent());
        Log.d(TAG, "update ends...");

    }
    public void Set_Posts_To_List_View()
    {
        /*add the authors to each ListView item using Adapter*/
        Log.d(TAG, "Set_Posts_To_List_View starts...");

        authors.clear();
        for (int i = 0; i < posts.size(); i++)
        {
            authors.add(posts.get(i).getAuthor());
        }
        adapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, authors);

        list_view = (ListView)view.findViewById(R.id.listView_All);

        list_view.setAdapter(adapter);

        Log.d(TAG, "Set_Posts_To_List_View ends...");
    }


    private final class ItemClickEvent implements AdapterView.OnItemClickListener
    {
        /*arg2 is where user clicks*/
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            mListener.OnItemClick(arg2);
        }
    }

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

}
