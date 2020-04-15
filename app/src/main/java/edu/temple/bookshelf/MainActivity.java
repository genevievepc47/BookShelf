package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import edu.temple.audiobookplayer.AudiobookService;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener,  BookDetailsFragment.OnFragmentInteractionListener  {
   int orientation;

   RequestQueue requestQueue;
    String searchTerm = "";
    BookListFragment bookListFragment = new BookListFragment();
    BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
    book selectedBook;

   ArrayList<book> bookList = new ArrayList<>();
    //ArrayList<book> returnArray = new ArrayList<>();



    Intent serviceIntent;
    AudiobookService.MediaControlBinder binder;
    boolean connected;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AudiobookService.MediaControlBinder) service;
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;


        }
    } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(MainActivity.this, edu.temple.audiobookplayer.AudiobookService.class);

        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);






        if(savedInstanceState != null)
        {
        bookList = (ArrayList) savedInstanceState.getSerializable("books");
        selectedBook = (book) savedInstanceState.getSerializable("selectedBook");
        }

        requestQueue = Volley.newRequestQueue(this);



        Bundle bundle = new Bundle();
        bundle.putSerializable("books",bookList);
        bookListFragment.setArguments(bundle);


        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container1, bookListFragment)
        .commit();

        View checkContainer2 = findViewById(R.id.container2);

        if(checkContainer2 == null) { //if in portrait
            if(selectedBook != null)
            {
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("book",selectedBook);


                bookDetailsFragment.setArguments(bundle2);


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container1, bookDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
        else//in landscape
        {
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("book",selectedBook);


            bookDetailsFragment.setArguments(bundle2);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();

        }



        //if there is a selected book and only one container then put the book details fragment on top

        //set up fragments, attach both fragments(if container 2 exists)

        //Toast.makeText(MainActivity.this, "toast works " , Toast.LENGTH_SHORT).show();


        final Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.editText);

                searchTerm = editText.getText().toString();


                getBooks();

                if(getSupportFragmentManager().findFragmentById(R.id.container1) instanceof BookDetailsFragment)
                {
                    getSupportFragmentManager().popBackStack();
                }

                //check which fragment is on top. if its the detail one pop it off the top
                ///findFragmentbyId(container) will return the top fragment, if its book details fragment then pop it off
                //popBackstack()//cann to pop detail fragment off and then call updateBooks()

            }
        });//end search button listener

        //stop button
        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.stop();
                //should i erase the now playing?
                TextView playingText = findViewById(R.id.nowPlayingText);
                playingText.setText("Now Playing: ");
            }
        });//end stop button





        //Toast.makeText(MainActivity.this, bookList.get(1).getBookAuthor(), Toast.LENGTH_SHORT).show();




    }

    //the user clicked play in the book details fragment
    //start playing the book!
    @Override
    public void onPlayButtonClick(int id)
    {
        binder.play(id);

        //change the now playing text
        //loop through all the books, match the id
        String bookTitle = "";
        for(int i =0; i < bookList.size(); i++)
        {
            if(bookList.get(i).getbookId() == id)
            {
                bookTitle = bookList.get(i).getBookName();
            }
        }

        TextView playingText = findViewById(R.id.nowPlayingText);
        playingText.setText("Now Playing: " + bookTitle);
    }

    // The Activity handles receiving a message from one Fragment
    // and passing it on to the other Fragment
    //if the user clicks a book
    @Override
    public void onFragmentInteraction(int position) {

        View checkContainer2 = findViewById(R.id.container2);

        if(checkContainer2 == null) { //if in portrait
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", bookList.get(position));
            selectedBook = bookList.get(position);


            //BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
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
            selectedBook = bookList.get(position);

            //BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
            bookDetailsFragment.displayBook(selectedBook);

            /*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();

             */
        }
    }




    private void getBooks()
    {
        bookList.clear();




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
                                int duration = book.getInt("duration");


                                book newBook = new book(title, author, id, bookURL, duration);



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

        bookListFragment.updateBooks(bookList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable("books", bookList);
        outState.putSerializable("selectedBook", selectedBook);
    }

}


