package com.example.imufur.mymusicapp15;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> albums;
    private RatingBar ratingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



      //  albums2 = new ArrayList<String>();
      //  albums2.add("Linkin Park | Meteora | 2003 | eded | 4*");
      //  albums2.add("Rammstein | Reise, Reise | 2004 | edede | 5*");

        SharedPreferences sp =getSharedPreferences("albumsApp", 0);
        Set<String> albumsset = sp.getStringSet("albumskey", new HashSet<String>());

        albums = new ArrayList<String>(albumsset);

        SimpleAdapter adapter = createSimpleAdapter(albums); //cria adapter dos icons, mudar em todos os adapters
        ListView listView = (ListView) findViewById(R.id.listView_albums);
        listView.setAdapter(adapter);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,
                R.array.albums_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter_spinner);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //codigo executado quando clica item da lista


               /*AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
               adb.setTitle("Delete?");
               adb.setMessage("Are you sure you want to delete " + position);
               final int positionToRemove = position;
               adb.setNegativeButton("Cancel", null);
               adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       contacts.remove(positionToRemove);
                       adapter.notifyDataSetChanged();
                   }});
               adb.show();
           }
       });*/
//fews
                ListView listView = (ListView) findViewById(R.id.listView_albums);

                HashMap item = (HashMap) listView.getItemAtPosition(position);


                albums.remove(position);

                SimpleAdapter adapter = createSimpleAdapter(albums);

                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, "apagou o contacto " + item, Toast.LENGTH_SHORT).show();


                return true;
            }
        });



    }
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sp = getSharedPreferences("albumsApp", 0);
        SharedPreferences.Editor editor = sp.edit();
        HashSet albumsset = new HashSet(albums);

        editor.putStringSet("albumskey", albumsset);
        editor.commit();

        Toast.makeText(MainActivity.this, "A guardar dados", Toast.LENGTH_SHORT).show();

    }



    public void onClick_search(View view) {
        // ir buscar referência para a edittext, spinner e listviewb
        EditText et = (EditText) findViewById(R.id.editText_search);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ListView lv = (ListView) findViewById(R.id.listView_albums);

        // ir à edittext buscar o termo a pesquisar
        String termo = et.getText().toString();

        if (termo.equals("")) { // se o termo a pesquisar for uma string vazia
            // mostra os albuns todos
            SimpleAdapter adapter = createSimpleAdapter(albums);
            lv.setAdapter(adapter);

            Toast.makeText(MainActivity.this, this.getString(R.string.saa), Toast.LENGTH_SHORT).show();
        } else { // se houver algo para pesquisar

            String itemSeleccionado = (String) sp.getSelectedItem();

            // pesquisar o termo nos contactos, e guardar o resultado da pesquisa
            // numa lista nova
            ArrayList<String> resultados = new ArrayList<>();

            if (itemSeleccionado.equals(this.getString(R.string.All))) {
                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    boolean contem = c.contains(termo);

                    if (contem == true) {
                        resultados.add(c);
                    }
                }
            } else if (itemSeleccionado.equals(this.getString(R.string.arts))) {
                // código pesquisar só no nome
                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    String[] s = c.split("\\|");
                    String name = s[0];

                    boolean contem = name.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            } else if (itemSeleccionado.equals(this.getString(R.string.alb)) ){
                // códido pesquisar só no album
                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    String[] s = c.split("\\|");
                    String number = s[1];

                    boolean contem = number.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            }

            else if (itemSeleccionado.equals(this.getString(R.string.ano)) ){

                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    String[] s = c.split("\\|");
                    String number = s[2];

                    boolean contem = number.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }

                }
            }

            else if (itemSeleccionado.equals(this.getString(R.string.editor)) ){

                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    String[] s = c.split("\\|");
                    String number = s[3];

                    boolean contem = number.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }

                }
            }


            else if (itemSeleccionado.equals(this.getString(R.string.rating)) ){
                 //se rating tiver selecionado no spinner
                for (int i = 0; i < albums.size(); i++) {
                    String c = albums.get(i);

                    String[] s = c.split("\\|");
                    String number = s[4];

                    boolean contem = number.contains(termo);

                    if (contem) {
                        resultados.add(c);
                    }
                }
            }

            boolean vazia = resultados.isEmpty();

            if (vazia == false) {
                // mostrar na listview a lista nova que contém o resultado da pesquisa
                SimpleAdapter adapter = createSimpleAdapter(albums);
                lv.setAdapter(adapter);

                // mostrar uma mensagem a dizer que a pesquisa teve sucesso
                Toast.makeText(MainActivity.this, this.getString(R.string.sa), Toast.LENGTH_SHORT).show();
            } else { // se a lista está vazia
                // mostra os contactos todos
                SimpleAdapter adapter = createSimpleAdapter(albums);
                lv.setAdapter(adapter);

                Toast.makeText(MainActivity.this, this.getString(R.string.ma), Toast.LENGTH_SHORT).show(); //toast vai buscar a string ma e a traducao
            }
        }
    }


    //pesquisar o termo nos albums2 e guardar o resultado da pesuqisa numa lista nova





    public void onClick_add(View view) {AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialogwindow, null));
// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button

                //ir buscar as edit texts
                AlertDialog al = (AlertDialog) dialog;

                EditText etartist = (EditText) al.findViewById(R.id.artist);
                EditText etalbum = (EditText) al.findViewById(R.id.album);
                EditText etano = (EditText) al.findViewById(R.id.ano);
                EditText etrating = (EditText) al.findViewById(R.id.rating);
                EditText eteditor = (EditText) al.findViewById(R.id.editor);


                //ir buscar as strings das edit texts

                String artist = etartist.getText().toString();
                String album = etalbum.getText().toString();
                String ano = etano.getText().toString();
                String rating = etrating.getText().toString();
                String editora = eteditor.getText().toString();

                //criar um novo album

                String albun = artist + " | " + album + " |" + editora + " |" + ano + " | " + rating;

                //adicionar o album a lista de albums2

                albums.add(albun);

                //mostrar a lista de albums2 atualizada


                ListView listView = (ListView) findViewById(R.id.listView_albums);
                SimpleAdapter adapter = createSimpleAdapter(albums);
                listView.setAdapter(adapter);

                Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.naa), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

            }
        });
// Set other dialog properties
        builder.setTitle(this.getString(R.string.new_album_dw));
        builder.setMessage(this.getString(R.string.info_dw));

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private SimpleAdapter createSimpleAdapter(ArrayList<String> albums) {
        List<HashMap<String, String>> simpleAdapterData = new ArrayList<HashMap<String, String>>();

        for (String c : albums) {
            HashMap<String, String> hashMap = new HashMap<>();

            String[] split = c.split("\\|");

            hashMap.put("Artista", split[0].trim());
            hashMap.put("Album", split[1].trim());
            hashMap.put("Ano", split[2].trim());
            hashMap.put("Editora", split[3].trim());
            hashMap.put("Rating", split[4].trim());

            simpleAdapterData.add(hashMap);
        }

        String[] from = {"Artista", "Album", "Ano", "Editora", "Rating"};  //phone vai para textview_phone e name vai para textview_name
        int[] to = {R.id.textView_artista, R.id.textView_album, R.id.textView_ano, R.id.textView_editora, R.id.textView_rating };
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), simpleAdapterData, R.layout.listview_item, from, to);
        return simpleAdapter;
    }

}
