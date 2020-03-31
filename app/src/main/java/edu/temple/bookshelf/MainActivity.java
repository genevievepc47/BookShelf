package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {
   int orientation;
   RequestQueue requestQueue;
    String searchTerm = "";

   ArrayList<book> bookList = new ArrayList<>();
    //ArrayList<book> returnArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        //Toast.makeText(MainActivity.this, "toast works " , Toast.LENGTH_SHORT).show();





        getBooks();


        //Toast.makeText(MainActivity.this, bookList.get(1).getBookAuthor(), Toast.LENGTH_SHORT).show();




    }

    // The Activity handles receiving a message from one Fragment
    // and passing it on to the other Fragment
    @Override
    public void onFragmentInteraction(int position) {

        View checkContainer2 = findViewById(R.id.container2);

        if(checkContainer2 == null) { //if in portrait
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", bookList.get(position));

            BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
            bookDetailsFragment.setArguments(bundle);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
        //change to checking if there is a second container, find view by id and check if it is null
        else
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", bookList.get(position));

            BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
            bookDetailsFragment.setArguments(bundle);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(String searchTerm) {
        this.searchTerm = searchTerm;
        getBooks();

    }


    private void getBooks()
    {
        bookList.clear();


        //Toast.makeText(MainActivity.this, "in get books method ", Toast.LENGTH_SHORT).show(); works

        //ArrayList<book> bookList = new ArrayList<>();



        String url = "https://kamorris.com/lab/abp/booksearch.php?search=" + searchTerm;


        //Log.d(null, "in get books");

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        Log.d("Response received", response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject book = response.getJSONObject(i);

                                //Toast.makeText(MainActivity.this, "in get books method method " , Toast.LENGTH_SHORT).show(); not working


                                // Get the current student (json object) data
                                int id = book.getInt("book_id");
                                String title = book.getString("title");
                                String author = book.getString("author");
                                String bookURL = book.getString("cover_url");


                                book newBook = new book(title, author, id, bookURL);



                                bookList.add(newBook);
                                //Toast.makeText(MainActivity.this, "added a new book " + bookList.get(0).getBookAuthor(), Toast.LENGTH_SHORT).show(); not working


                            }

                            //call method for fragment stuff here
                            //set up fragments right at the top when it is empty, hold a reference to it. then do search
                            //tell fragment what books it should show

                            //use starts application, no books shown
                            //then user does a search, process that and get set of books
                            //give the fragment the books

                            setUpFragment(bookList);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        );



        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);

        //return returnArray;


    }//end get books method

    private void setUpFragment(ArrayList<book> bookList)
    {
        //put in the book list fragment
        //Toast.makeText(MainActivity.this, "in set up method " + bookList.get(0).getBookAuthor(), Toast.LENGTH_SHORT).show(); not working

        Bundle bundle = new Bundle();
        bundle.putSerializable("books",bookList);
        //make update books method in book list fragment

        BookListFragment bookListFragment = new BookListFragment();
        bookListFragment.setArguments(bundle);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container1, bookListFragment)
                .commit();

        int position =0;

        //change to checking if there is a second container, find view by id and check if it is null

        View checkContainer2 = findViewById(R.id.container2);

        orientation = getResources().getConfiguration().orientation;
        if (checkContainer2 != null) {
            // In landscape
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("book",bookList.get(position));

            BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
            bookDetailsFragment.setArguments(bundle2);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();


        }
    }

}


