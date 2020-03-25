package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> bookList = new ArrayList<>();

        for(int i =0; i<10; i++)
        {
            //put 10 books in the bookList
            bookList.add( getBook());
        }

       //put in the book list fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("books",bookList);

        BookListFragment bookListFragment = new BookListFragment();
        bookListFragment.setArguments(bundle);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container1, bookListFragment)
                .commit();

    }
    private HashMap<String, String> getBook()
    {

        HashMap<String, String> book = new HashMap<>();

        String[] titles = {"In Search of Lost Time","Ulysses", "Don Quixote", "The Great Gatsby", "One Hundred Years of Solitude", "Moby Dick", "War and Peace", "Lolita", "Don Quixote", "Harry Potter series", "David Copperfield", "The Three Musketeers", "River of Smoke", "Death of City", "Politics", "Power Politics", "Dreams from My Father", "The Final Passage", "Freedom in Exile"};
        String[] authors = {"Marcel Proust","James Joyce", "Miguel de Cervantes", "F. Scott Fitzgerald", "Gabriel Garcia Marquez", "Herman Melville", "Leo Tolstoy", "Vladimir Nabokov", "Miguel de Cervantes", "J.K.Rowling", "Charles Dickens", "Alexander Dumas", "Amitav Ghose", "Amrita Pritam", "Aristotle", "Arundati Roy", "Barack Obama", "Caryl Phjillips", "Dalai Lama"};

        Random rand = new Random();

        int rand_int1 = rand.nextInt(19);//get random number from 1 to 20
        int rand_int2 = rand.nextInt(19);//get random number from 1 to 20


        book.put(titles[rand_int1], authors[rand_int2]);


        return book;

    }//end get books method

    /*
 In Search of Lost Time by Marcel Proust. ...
2 . Ulysses by James Joyce. ...
3 . Don Quixote by Miguel de Cervantes. ...
4 . The Great Gatsby by F. Scott Fitzgerald. ...
5 . One Hundred Years of Solitude by Gabriel Garcia Marquez. ...
6 . Moby Dick by Herman Melville. ...
7 . War and Peace by Leo Tolstoy. ...
8 . Lolita by Vladimir Nabokov.


Don Quixote (1512) by Miguel de Cervantes - 500 million. ...
Harry Potter series (1997-2007) by J.K.Rowling - 450 million.
David Copperfield	Charles Dickens


The Three Musketeers	Alexander Dumas


5	River of Smoke	Amitav Ghose

7	Death of City	Amrita Pritam

9	Politics	Aristotle


21	Dreams from My Father	Barack Obama

24	The Final Passage	Caryl Phjillips


30	Freedom in Exile	Dalai Lama


35	The Luminaries	Eleanor Catton
36	A Passage to India	Edward Morgan Forster
37	Kargil : From Surprise to Victory	General V.P Malik
38	The Tin Drum	Gunter Grass
39	Law, Lawyers and Judges	H.R Bharadwaj
40	Time Machine	Herbert George Wells
41	My Truth	Indira Gandhi
42	Ajatha Shatru	Jai Shankar Prasad
43	Discovery of India	Jawaharlal Nehru
44	Glimpses of World History	Jawaharlal Nehru
45	An Autobiography	Jawaharlal Nehru
46	Gita Govinda	Jayadev
47	Prison Diary	Jayaprakash Narayan
48	Walking with Lions	K Natwar Singh
49	Curtain Raisers	K Natwar Singh
50	Straight from the Heart	Kapil Dev
51	Das Kapital	Karl Marx
52	Communist Manifesto	Karl Marx and Fredrik Engels
53	Train to Pakistan	Khushwanth Singh
54	I Dare	Kiran Bedi
55	As I See	Kiran Bedi
56	The Inheritance of Loss	Kiran Desai
57	Too Old to be Bold	Kuldeep Mathur
58	Anna Karenina	Leo Tolstoy
59	A Prisoner’s a Scrap	LK Advani
60	My Experiments with Truth	Mahatma Gandhi
61	The Heart of India	Mark Tully
62	Untouchable	Mulk Raj Anand
63	Godan	Premchand
64	The Guide	R.K Narayan
65	Gitanjali	Rabindranath Tagore
66	Gora	Rabindranath Tagore
67	A Garland of Memories	Ruskin Bond
68	Devdas	Sarat Chandra Chatterji
69	Golden Threshold 	Sarojini Naidu
70	The Broken Wings	Sarojini Naidu
71	Arabian Nights	Sir Richard Burton
72	Essays on Gita	Sri Aurobindo Ghosh
73	A Brief History of Time	Stephen Hawkings
74	Koraner Nari	Taslima Nasreen
75	Against the Day	Thomas Pynchon
76	India : A wounded Civilization	V.S Naipaul
77	Letters between a Father and Son	V S Naipul
78	Bhagwad Gita	Ved Vyas
79	Love and longing in Bombay	Aristotle
80	An Equal Music	Vikram Seth
81	Sachin Cricketer of the Century	Vimal Kumar
82	Half a Life	VS Naipaul
83	An area of Darkness	VS Naipaul
84	Magic Seeds	VS Naipaul
85	As You Like it	William Shakespeare
86	The Test of My Life	Yuvraj Singh
87	Congress after India	Zoya hasan
     */
}


