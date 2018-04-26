package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class searchTags extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String USER_INDEX = "userindex";
    private AlbumUsers albumUsers;
    private User currentUser;
    private int userIndex;
    private Spinner spinner;
    private AutoCompleteTextView autoCompleteTextView;
    private Button searchButton;
    private Button cancelButton;
    private  List<Photos> searchList;
   private ArrayList<String> allLocation;
   private ArrayList<String> allPerson;
   private AutoCompleteTextView auto2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_bytags);

        final Bundle bundle = getIntent().getExtras();
        bundle.getInt(USER_INDEX);

        userIndex = bundle.getInt(USER_INDEX);
        Log.d("SEARCHTAG",userIndex+"");

        spinner = (Spinner) findViewById(R.id.tag_spinner);
        searchButton = (Button) findViewById(R.id.dialogSearch);
        cancelButton = (Button) findViewById(R.id.tagCancel);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.SearchAutoTextview);
        auto2 = (AutoCompleteTextView) findViewById(R.id.auto2);
        auto2.setVisibility(View.INVISIBLE);

          allLocation = new ArrayList<>();
             allPerson = new ArrayList<>();

        albumUsers = AlbumUsers.loadFromDisk(this);
        currentUser = albumUsers.getUsers().get(userIndex);

        searchList = new ArrayList<>();


        for(int i=0;i<currentUser.getAlbums().size();i++){
            for(int j = 0;j < currentUser.getAlbums().get(i).getListofphotos().size();j++){
               for(int x=0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                   if(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location")){
                       allLocation.add(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue());
                   }
               }


            }
        }

        for(int i=0;i<currentUser.getAlbums().size();i++){
            for(int j = 0;j < currentUser.getAlbums().get(i).getListofphotos().size();j++){
                for(int x=0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                    if(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person")){
                        allPerson.add(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue());
                    }
                }


            }
        }

       // Log.d("ARRAYLISt",currentUser.getAlbums().get(0).getListofphotos().get(0).getLocationlist().size()+"");
       Log.d("ARRAYLIST",allLocation.size()+"");
        Log.d("ARRAYLISt",allPerson.size()+"");

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Person");
        categories.add("Location");
        categories.add("Both");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);






//        if(spinner.getSelectedItem().toString().equalsIgnoreCase("location")){
//            Log.d("SPINNERSEL1",spinner.getSelectedItem().toString());
//            locationArapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//             locationArapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allLocation);
//                locationArapter.setDropDownViewResource();
//            autoCompleteTextView.setAdapter(locationArapter);
//
//
//        }
//
//        if(spinner.getSelectedItem().toString().equalsIgnoreCase("person")) {
//            Log.d("SPINNERSEL1",spinner.getSelectedItem().toString());
//            locationArapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allPerson);
//            autoCompleteTextView.setAdapter(locationArapter);
//
//        }


//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               String textrec = autoCompleteTextView.getText().toString();
////               if(autoCompleteTextView.getText().toString().isEmpty()){
////                   AlertDialog.Builder builder = new AlertDialog.Builder(searchTags.this);
////                   builder.setTitle("Invalid");
////                   builder.setMessage("No Results");
////
////                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                       @Override
////                       public void onClick(DialogInterface dialog, int which) {
////                           dialog.dismiss();
////                           return;
////                       }
////                   });
////                   builder.show();
////               }
////               else{
////                   for(int i =0;i<currentUser.getAlbums().size();i++){
////                       for(int j =0;i< currentUser.getAlbums().get(i).getListofphotos().size();j++){
////                           for(int x =0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
////                               if(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(textrec)){
////                                  // searchresult.addPhoto(currentUser.getAlbums().get(i).getListofphotos().get(j));
////
////                               }
////                           }
////                       }
////                   }
////
////
////               }
////
////           // Log.d("SEARCHRESULT",searchresult.getListofphotos().size()+"");
//
//
//                if(autoCompleteTextView.getText().toString().isEmpty() || autoCompleteTextView.getText().toString().equalsIgnoreCase("search")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(searchTags.this);
//                    builder.setMessage("Field cannot be empty");
//
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            return;
//                        }
//
//
//                    });
//                    builder.show();
//                }
//                else{
//                    searchList.clear();
//                    String rec = autoCompleteTextView.getText().toString();
//
//                    Spinner recSpinner = (Spinner) findViewById(R.id.tag_spinner);
//                    if(recSpinner.getSelectedItem().toString().equalsIgnoreCase("location")){
//                        Log.d("RESULT1",searchList.size()+"");
//                        for(int i =0;i<currentUser.getAlbums().size();i++){
//                            for(int j =0;j<currentUser.getAlbums().get(i).getListofphotos().size();j++){
//                                for(int x =0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
//                                    if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(rec)) &&(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location")) ){
//                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
//                                    }
//                                    else if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(rec)) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person") )){
//                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
//                                    }
//                                    //need to implement startswith();
//                                }
//                            }
//                        }
//                        autoCompleteTextView.setText("");
//                        Log.d("RESULT",searchList.size()+"");
//                    }
//                    else
//                    {
//
//                        Log.d("RESULT",searchList.size()+"");
//
//                    }
//
//
//
//                }
//                if(searchList.isEmpty()){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(searchTags.this);
//                    builder.setTitle("Empty Reslut");
//                    builder.setMessage("Invalid Tag");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    });
//                    builder.show();
//                }
//                else
//                {
//                    Log.d("TOTALUSERS","searchlist" + searchList.size());
//                    Log.d("TOTALUSERS1","totalalbum" + currentUser.getAlbums().size());
//
//                    Album searchResultAlbum = new Album("SearchR");
//                    searchResultAlbum.setListofphotos(searchList);
//                    currentUser.addAlbum(searchResultAlbum);
//                    Log.d("TOTALUSERS2","totalalbum" + currentUser.getAlbums().size());
//                    albumUsers.saveToDisk(searchTags.this);
//
//                    Intent intent = new Intent(searchTags.this,searchResults.class);
//                    startActivity(intent);
//
//
//
//
//                }
//
//            }
//        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, final long id) {
        final String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if(item.equalsIgnoreCase("location")){
            ArrayAdapter<String> locationAdp = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allLocation);
            autoCompleteTextView.setAdapter(locationAdp);
            auto2.setVisibility(View.INVISIBLE);

        }
        else if(item.equalsIgnoreCase("person")){
            ArrayAdapter<String> personadp = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allPerson);
            autoCompleteTextView.setAdapter(personadp);
            auto2.setVisibility(View.INVISIBLE);

        }
        else if(item.equalsIgnoreCase("both")){
            auto2.setVisibility(View.VISIBLE);
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEXTVIEW",autoCompleteTextView.getText().toString());

                if(autoCompleteTextView.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(searchTags.this);
                    builder.setMessage("Field cannot be empty");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            return;
                        }


                    });
                    builder.show();
                    //works

                }
                else
                {
                    Log.d("TEXTVIEW",searchList.size()+ " ");
                    searchList.clear();

                    if(item.equalsIgnoreCase("location")){
                        for(int i =0;i<currentUser.getAlbums().size();i++){
                            for(int j =0;j<currentUser.getAlbums().get(i).getListofphotos().size();j++){
                                for(int x =0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                                    if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(autoCompleteTextView.getText().toString())) &&(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location")) ){
                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                    }
                                    else if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(autoCompleteTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location") )){
                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                    }
                                    //need to implement startswith();
                                }
                            }
                        }
                        autoCompleteTextView.setText("");
                        Log.d("RESULT",searchList.size()+"");
                    }else if(item.equalsIgnoreCase("person")){

                        for(int i =0;i<currentUser.getAlbums().size();i++){
                            for(int j =0;j<currentUser.getAlbums().get(i).getListofphotos().size();j++){
                                for(int x =0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                                    if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(autoCompleteTextView.getText().toString())) &&(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person")) ){
                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                    }
                                    else if((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(autoCompleteTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person") )){
                                        searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                    }
                                    //need to implement startswith();
                                }
                            }
                        }
                        autoCompleteTextView.setText("");


                    }
                    else if(item.equalsIgnoreCase("Both")){

                    }

                }

                Log.d("RESULT**",searchList.size()+"");


                if(searchList.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(searchTags.this);
                    builder.setTitle("Empty Result");
                    builder.setMessage("Invalid Tag");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                }
                else
                {
                    Log.d("TOTALUSERS","searchlist" + searchList.size());
                    Log.d("TOTALUSERS1","totalalbum" + currentUser.getAlbums().size());

                    Album searchResultAlbum = new Album("SearchR");
                    searchResultAlbum.setListofphotos(searchList);
                    currentUser.addAlbum(searchResultAlbum);
                    Log.d("TOTALUSERS2","totalalbum" + currentUser.getAlbums().size());
                    albumUsers.saveToDisk(searchTags.this);

                    Intent intent = new Intent(searchTags.this,searchResults.class);
                    startActivity(intent);




                }
            }




        });




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
