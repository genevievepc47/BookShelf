package edu.temple.bookshelf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {

    View layout;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "books";



    private ArrayList<book> books;


    private OnFragmentInteractionListener mListener;

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param books Parameter 1.

     * @return A new instance of fragment BookListFragment.
     */

    public static BookListFragment newInstance(ArrayList<book> books) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = (ArrayList) getArguments().getSerializable(ARG_PARAM1);
            Log.d("Books", books.toArray().toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout =  inflater.inflate(R.layout.fragment_book_list, container, false);

        //final ListView list = layout.findViewById(R.id.list);

        final Button searchButton = layout.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = layout.findViewById(R.id.editText);

                String searchTerm = editText.getText().toString();

                mListener.onFragmentInteraction(searchTerm);

            }
        });



        //this line not correct, make my own xml file and use that instead of "android.R.layout.simple_list_item_1"
        //adapter = new ArrayAdapter<String>(this, R.layout.listitem_row,R.id.textView1, elename);
        //ArrayAdapter<HashMap<String,String>> arrayAdapter = new ArrayAdapter<HashMap<String, String>>(BookListFragment.super.getContext(),android.R.layout.simple_list_item_1, books);

        // Create the adapter to convert the array to views
        bookAdapter adapter = new bookAdapter(getContext(), books);

// Attach the adapter to a ListView
        ListView listView = (ListView) layout.findViewById(R.id.list);
        listView.setAdapter(adapter);
        //list.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //When the user clicks one of the books in the ListView, the fragment should invoke a
                //method in its parent with the index of the book that was clicked.

                //i think this is saying send it back to the parent, so were gonna need an interface
                //then the parent will call the other fragment

                mListener.onFragmentInteraction(position);

            }
        });

        return layout;
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

     */


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int position);
        void onFragmentInteraction(String searchTerm);
    }
}
